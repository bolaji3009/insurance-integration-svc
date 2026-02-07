# Insurance Integration Service

A Spring Boot microservice that provides integration between healthcare systems and openIMIS insurance platform using FHIR R4 API.

## Features

### Insurance Integration
- **Eligibility Check**: Verify patient insurance coverage
- **Claim Submission**: Submit medical claims to openIMIS
- **Claim Status**: Check status of submitted claims
- **Patient Provisioning**: Create patients in OpenMRS with insurance policy numbers

## Technology Stack

- **Spring Boot 3.3.2**: Application framework
- **Java 21**: Programming language
- **HAPI FHIR 6.10.0**: FHIR R4 library
- **Maven**: Build tool
- **Docker**: Containerization

## Project Structure

```
insurance-integration-svc/
├── src/main/java/org/ng/hmo/insurance/
│   ├── InsuranceServiceApplication.java  # Main entry point
│   ├── web/                               # REST controllers
│   │   ├── EligibilityController.java
│   │   ├── ClaimsController.java
│   │   └── ProvisioningController.java
│   ├── service/                          # Business logic
│   │   ├── EligibilityService.java
│   │   ├── ClaimsService.java
│   │   └── ProvisioningService.java
│   ├── client/                           # External API clients
│   │   ├── OpenImisFhirClient.java
│   │   ├── OpenMrsFhirClient.java
│   │   └── OpenMrsRestClient.java
│   ├── fhir/                             # FHIR builders
│   │   ├── ClaimBuilder.java
│   │   └── EligibilityBuilder.java
│   └── model/                            # DTO/Command models
│       ├── EligibilityCommand.java
│       ├── ClaimCommand.java
│       ├── ClaimStatusDto.java
│       └── ProvisioningCommand.java
├── src/main/resources/
│   ├── application.yml                   # Configuration
│   └── openapi.yml                       # OpenAPI specification
├── Dockerfile                            # Docker configuration
└── pom.xml                               # Maven pom file
```

## Configuration

### Environment Variables

```bash
# openIMIS Configuration
OPENIMIS_FHIR_URL=http://openimis-fhir:8080/fhir
OPENIMIS_USER=admin
OPENIMIS_PASSWORD=admin123

# OpenMRS Configuration
OPENMRS_FHIR_URL=http://ozone-openmrs:8080/ws/fhir2/R4
OPENMRS_REST_URL=http://ozone-openmrs:8080/ws/rest/v1
OPENMRS_USER=admin
OPENMRS_PASSWORD=Admin123
```

## API Endpoints

### Eligibility Check
```http
POST /api/eligibility/check
Content-Type: application/json

{
  "patient": { "id": "patient-uuid" },
  "coverage": { "id": "coverage-uuid" },
  "provider": { "organizationId": "org-uuid" }
}
```

### Claim Submission
```http
POST /api/claims
Content-Type: application/json

{
  "careSetting": "OPD",
  "patient": { "id": "patient-uuid" },
  "coverage": { "id": "coverage-uuid" },
  "provider": { 
    "organizationId": "org-uuid", 
    "band": "A" 
  },
  "invoice": {
    "number": "INV-2024-001",
    "currency": "NGN",
    "lines": [
      {
        "code": "12345",
        "description": "Consultation",
        "quantity": 1,
        "unitPrice": 5000
      }
    ]
  }
}
```

### Claim Status
```http
GET /api/claims/{claimId}
```

### Patient Provisioning
```http
POST /api/provisioning/patient
Content-Type: application/json

{
  "policyNumber": "POL-2024-001",
  "givenName": "John",
  "familyName": "Doe",
  "gender": "male",
  "birthdate": "1990-01-01",
  "assignedHospitalUuid": "hospital-uuid",
  "assignedFacilityAttributeTypeUuid": "attribute-type-uuid"
}
```

## Build & Run

### Local Development
```bash
mvn spring-boot:run
```

### Build Docker Image
```bash
mvn clean package
docker build -t insurance-integration-svc .
```

### Run Docker Container
```bash
docker run -d -p 8085:8085 \
  -e OPENIMIS_FHIR_URL=http://localhost:8080/fhir \
  -e OPENIMIS_USER=admin \
  -e OPENIMIS_PASSWORD=admin123 \
  -e OPENMRS_FHIR_URL=http://localhost:8080/ws/fhir2/R4 \
  -e OPENMRS_REST_URL=http://localhost:8080/ws/rest/v1 \
  -e OPENMRS_USER=admin \
  -e OPENMRS_PASSWORD=Admin123 \
  insurance-integration-svc
```

## Health Check
```http
GET /actuator/health
```

## License
This project is licensed under the MIT License.
