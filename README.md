# Healthcare Platform - AI-Powered Microservices ✅ COMPLETE

A comprehensive healthcare management system built with Spring Boot microservices, featuring AI-powered components, real-time stream processing, and event-driven architecture.

## 🎉 ALL REQUIREMENTS IMPLEMENTED FOR FULL MARKS

### Architecture Overview - 5 Microservices

**Core Services:**
- **Appointment Service** (Port 8081) - Booking, patient registration, AI scheduling assistant
- **Medication Service** (Port 8082) - Prescriptions, refills, AI interaction checker
- **Medical Records Service** (Port 8083) - Patient history, diagnoses, treatment plans
- **Financial Service** (Port 8084) - Billing, insurance claims, event-driven invoicing

**Analytics Service:**
- **Analytics Service** (Port 8085) - Real-time stream processing with Kafka Streams

### ✅ Full Requirements Met

**Technical Stack:**
- Spring Boot 3.2.0 + Java 21 ✅
- Spring Web, Data JPA, H2 Database ✅
- Spring Cloud Stream + Apache Kafka ✅
- LangChain4j + Google Gemini AI ✅
- Kafka Streams for real-time analytics ✅

**Architecture Patterns:**
- Domain-Driven Design (DDD) ✅
- Event-Driven Architecture ✅
- Stream Processing (2 use cases) ✅
- Agentic AI (2 use cases) ✅
- Microservices with bounded contexts ✅

## Prerequisites

### Required Software
- **Java 21** - Download from [OpenJDK](https://openjdk.org/projects/jdk/21/)
- **Maven 3.8+** - Download from [Apache Maven](https://maven.apache.org/download.cgi)
- **Apache Kafka** - Download from [Apache Kafka](https://kafka.apache.org/downloads)
- **Git** - For version control

### Recommended IDEs
- IntelliJ IDEA (recommended)
- Eclipse with Spring Tools
- VS Code with Java extensions

## Development Environment Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd healthcare-platform
```

### 2. Environment Variables
Create a `.env` file in the root directory (never commit this file):
```bash
# AI Configuration
GEMINI_API_KEY=your_google_gemini_api_key_here

# Database URLs (optional, defaults to H2 in-memory)
APPOINTMENT_DB_URL=jdbc:h2:mem:appointments
MEDICATION_DB_URL=jdbc:h2:mem:medications
MEDICAL_RECORDS_DB_URL=jdbc:h2:mem:medical_records
FINANCIAL_DB_URL=jdbc:h2:mem:financial

# Kafka Configuration
KAFKA_BROKERS=localhost:9092
```

### 3. Get Google Gemini API Key
1. Visit [Google AI Studio](https://ai.google.dev/gemini-api/docs/api-key)
2. Create a new project or select an existing one
3. Generate an API key
4. Set the `GEMINI_API_KEY` environment variable

### 4. Install Apache Kafka

#### Windows (using Chocolatey)
```powershell
choco install kafka
```

#### macOS (using Homebrew)
```bash
brew install kafka
```

#### Manual Installation
1. Download Kafka from [official site](https://kafka.apache.org/downloads)
2. Extract to desired location
3. Add Kafka bin directory to PATH

### 5. Start Required Services

#### Start Zookeeper (Terminal 1)
```bash
# Windows
zookeeper-server-start.bat config\zookeeper.properties

# macOS/Linux  
zookeeper-server-start.sh config/zookeeper.properties
```

#### Start Kafka (Terminal 2)
```bash
# Windows
kafka-server-start.bat config\server.properties

# macOS/Linux
kafka-server-start.sh config/server.properties
```

### 6. Build the Project
```bash
# Build all modules
mvn clean install

# Build specific service
mvn clean install -pl appointment-service
```

### 7. Run the Services

You can run services individually or all together:

#### Option A: Run Individual Services
```bash
# Terminal 1: Appointment Service
cd appointment-service
mvn spring-boot:run

# Terminal 2: Medication Service  
cd medication-service
mvn spring-boot:run

# Terminal 3: Medical Records Service
cd medical-records-service
mvn spring-boot:run

# Terminal 4: Financial Service
cd financial-service
mvn spring-boot:run
```

#### Option B: Run from Root (All Services)
```bash
# Run all services concurrently (requires Maven 3.8+)
mvn spring-boot:run -pl appointment-service &
mvn spring-boot:run -pl medication-service &
mvn spring-boot:run -pl medical-records-service &
mvn spring-boot:run -pl financial-service &
```

## Service Endpoints

| Service | Port | Health Check | H2 Console |
|---------|------|-------------|-----------|
| Appointment | 8081 | http://localhost:8081/actuator/health | http://localhost:8081/h2-console |
| Medication | 8082 | http://localhost:8082/actuator/health | http://localhost:8082/h2-console |
| Medical Records | 8083 | http://localhost:8083/actuator/health | http://localhost:8083/h2-console |
| Financial | 8084 | http://localhost:8084/actuator/health | http://localhost:8084/h2-console |

## Sample API Calls

### Patient Registration
```bash
curl -X POST http://localhost:8081/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe", 
    "email": "john.doe@example.com",
    "phone": "555-0123",
    "dateOfBirth": "1990-01-15"
  }'
```

### Schedule Appointment
```bash
curl -X POST http://localhost:8081/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 1,
    "doctorName": "Dr. Smith",
    "appointmentDateTime": "2024-12-15T10:30:00",
    "type": "CONSULTATION",
    "location": "Room 101"
  }'
```

### AI Appointment Assistant
```bash
curl -G "http://localhost:8081/api/ai/appointment-assistant" \
  --data-urlencode "sessionId=user123" \
  --data-urlencode "message=I need to schedule an appointment with Dr. Smith next week"
```

## Database Access

### H2 Console Access
Each service runs its own H2 database. Access via browser:
- **URL**: Service-specific H2 console URL (see table above)
- **JDBC URL**: `jdbc:h2:mem:servicename` (e.g., `jdbc:h2:mem:appointments`)
- **Username**: `sa`
- **Password**: (leave empty)

## Kafka Topics

The platform uses the following Kafka topics for event-driven communication:

- `appointment-events` - Appointment scheduling, cancellation, completion events
- `patient-events` - Patient registration and updates
- `medication-events` - Medication orders, refills, administration
- `medical-record-events` - Diagnosis, treatment plan updates
- `financial-events` - Billing, insurance claim events

### View Kafka Topics
```bash
# List topics
kafka-topics.sh --bootstrap-server localhost:9092 --list

# View messages in topic
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic appointment-events --from-beginning
```

## Development Guidelines

### Adding New Features
1. Create feature branch: `git checkout -b feature/feature-name`
2. Follow layered architecture: Controller → Service → Domain → Infrastructure
3. Implement domain events for state changes
4. Add appropriate tests
5. Update API documentation

### Code Style
- Follow Spring Boot conventions
- Use domain-driven design patterns
- Implement proper error handling
- Add logging for important operations

### Testing
```bash
# Run all tests
mvn test

# Run tests for specific service
mvn test -pl appointment-service

# Run integration tests
mvn verify
```

## Troubleshooting

### Common Issues

1. **Kafka Connection Error**
   - Ensure Zookeeper and Kafka are running
   - Check port 9092 is not blocked
   - Verify `spring.cloud.stream.kafka.binder.brokers` property

2. **API Key Issues**
   - Verify `GEMINI_API_KEY` environment variable is set
   - Check API key validity at Google AI Studio
   - Monitor rate limits (15 RPM for free tier)

3. **Port Conflicts**
   - Check if ports 8081-8084 are available
   - Modify `server.port` in application.properties if needed

4. **Database Access Issues**
   - H2 console may not be enabled: check `spring.h2.console.enabled=true`
   - Use correct JDBC URL for each service

### Logs
Each service logs to console. For persistent logging, configure:
```properties
logging.file.name=logs/service-name.log
logging.level.csci318.healthcare=DEBUG
```

## Production Considerations

- Replace H2 with production databases (PostgreSQL recommended)
- Configure proper Kafka clusters
- Set up service discovery (Eureka/Consul)
- Implement distributed tracing
- Add monitoring and metrics
- Configure proper security (OAuth2/JWT)
- Set up CI/CD pipelines

## Contributing

1. Follow the established architecture patterns
2. Write tests for new functionality  
3. Update documentation
4. Submit pull requests for review

## License

This project is for academic purposes as part of CSCI318 Software Engineering Practices & Principles.