package org.ng.hmo.insurance.service;

import org.ng.hmo.insurance.client.OpenMrsFhirClient;
import org.ng.hmo.insurance.client.OpenMrsRestClient;
import org.ng.hmo.insurance.model.ProvisioningCommand;
import org.springframework.stereotype.Service;

@Service
public class ProvisioningService {

    private final OpenMrsFhirClient openMrsFhirClient;
    private final OpenMrsRestClient openMrsRestClient;

    public ProvisioningService(OpenMrsFhirClient openMrsFhirClient, OpenMrsRestClient openMrsRestClient) {
        this.openMrsFhirClient = openMrsFhirClient;
        this.openMrsRestClient = openMrsRestClient;
    }

    public String provisionPatient(ProvisioningCommand cmd) {
        var patientJson = buildPatientJson(cmd);
        var patientUuid = openMrsFhirClient.postPatient(patientJson);
        setAssignedFacilityAttribute(patientUuid, cmd);
        return patientUuid;
    }

    private String buildPatientJson(ProvisioningCommand cmd) {
        return """
                {
                  "resourceType": "Patient",
                  "identifier": [
                    {
                      "use": "official",
                      "type": { "text": "Policy Number" },
                      "value": "%s",
                      "extension": [{
                        "url": "http://fhir.openmrs.org/ext/patient/identifier#location",
                        "valueReference": { "reference": "Location/%s", "type": "Location" }
                      }]
                    }
                  ],
                  "name": [{ "given": [ "%s" ], "family": "%s" }],
                  "gender": "%s",
                  "birthDate": "%s"
                }
                """.formatted(cmd.policyNumber(), cmd.assignedHospitalUuid(), cmd.givenName(), cmd.familyName(), cmd.gender(), cmd.birthdate());
    }

    private void setAssignedFacilityAttribute(String patientUuid, ProvisioningCommand cmd) {
        var attributeJson = """
                { "attributeType": "%s", "value": "%s" }
                """.formatted(cmd.assignedFacilityAttributeTypeUuid(), cmd.assignedHospitalUuid());
        openMrsRestClient.postPersonAttribute(patientUuid, attributeJson);
    }
}
