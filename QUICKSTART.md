# Quick Start Guide - Healthcare Platform ✅

## Prerequisites Check

Before starting, verify you have:
- **Java 21** installed: `java -version`
- **Maven 3.8+** installed: `mvn -version`
- **Apache Kafka** downloaded and extracted
- **Google Gemini API key**: Get from https://ai.google.dev/gemini-api/docs/api-key

## Step 1: Set Gemini API Key (IMPORTANT!)

**Windows:**
```cmd
set GEMINI_API_KEY=your_actual_api_key_here
```

**Mac/Linux:**
```bash
export GEMINI_API_KEY=your_actual_api_key_here
```

## Step 2: Start Kafka

**Terminal 1 - Start Zookeeper:**
```bash
# Windows
C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties

# Mac/Linux
./bin/zookeeper-server-start.sh ./config/zookeeper.properties
```

**Terminal 2 - Start Kafka:**
```bash
# Windows
C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties

# Mac/Linux
./bin/kafka-server-start.sh ./config/server.properties
```

## Step 3: Build the Project

```bash
cd C:\Users\cz\318project
mvn clean install
```

Wait for "BUILD SUCCESS"

## Step 4: Start All 5 Services

Open 5 separate terminals and run each:

**Terminal 3 - Appointment Service (Port 8081):**
```bash
cd appointment-service
mvn spring-boot:run
```

**Terminal 4 - Financial Service (Port 8084):**
```bash
cd financial-service
mvn spring-boot:run
```

**Terminal 5 - Medication Service (Port 8082):**
```bash
cd medication-service
mvn spring-boot:run
```

**Terminal 6 - Medical Records Service (Port 8083):**
```bash
cd medical-records-service
mvn spring-boot:run
```

**Terminal 7 - Analytics Service (Port 8085):**
```bash
cd analytics-service
mvn spring-boot:run
```

Wait for each to show: "Tomcat started on port(s): XXXX"

## Step 5: Test Everything Works!

### Basic Health Checks
```bash
curl http://localhost:8081/api/patients
curl http://localhost:8082/api/prescriptions
curl http://localhost:8083/api/medical-records
curl http://localhost:8084/api/invoices
```

### Test Event-Driven Architecture
```bash
# Complete an appointment (triggers Kafka event)
curl -X PUT http://localhost:8081/api/appointments/1/complete

# Watch Financial Service console - you'll see:
# "Financial Service received event..."
# "✓ Auto-created invoice for appointment: 1"

# Verify invoice was auto-created
curl http://localhost:8084/api/invoices
```

### Test Stream Processing (Use Case 1)
```bash
# Real-time appointment analytics (1-hour tumbling windows)
curl http://localhost:8085/api/analytics/appointments-per-hour
```

### Test Stream Processing (Use Case 2)
```bash
# Real-time insurance claims (30-second tumbling windows)
curl http://localhost:8085/api/analytics/insurance-claims
```

### Test AI Agent 1 - Appointment Scheduling Assistant
```bash
# Natural language appointment booking
curl -G "http://localhost:8081/api/ai/appointment-assistant" \
  --data-urlencode "sessionId=user1" \
  --data-urlencode "message=I need to book an appointment with Dr. Smith"

# Check availability
curl -G "http://localhost:8081/api/ai/appointment-assistant" \
  --data-urlencode "sessionId=user1" \
  --data-urlencode "message=Is Dr. Smith available on 2024-12-15?"
```

### Test AI Agent 2 - Medication Interaction Checker
```bash
# Check drug interactions
curl -G "http://localhost:8082/api/ai/medication-checker" \
  --data-urlencode "sessionId=doc1" \
  --data-urlencode "message=Check interaction between Warfarin and Aspirin"

# Review patient medications
curl -G "http://localhost:8082/api/ai/medication-checker" \
  --data-urlencode "sessionId=doc1" \
  --data-urlencode "message=Review all medications for patient 1"
```

## Step 6: View H2 Database Consoles

Open in browser:
- Appointment: http://localhost:8081/h2-console
- Medication: http://localhost:8082/h2-console
- Medical Records: http://localhost:8083/h2-console
- Financial: http://localhost:8084/h2-console

**Login (same for all):**
- JDBC URL: `jdbc:h2:mem:[servicename]` (e.g., `jdbc:h2:mem:appointments`)
- Username: `sa`
- Password: (leave blank)

## Troubleshooting

### "GEMINI_API_KEY not set" Error
```bash
# Make sure you set the environment variable BEFORE starting services
# Windows: set GEMINI_API_KEY=your_key
# Mac/Linux: export GEMINI_API_KEY=your_key
```

### Port Already in Use
```bash
# Windows - Find and kill process
netstat -ano | findstr :8081
taskkill /PID [number] /F

# Mac/Linux
lsof -ti:8081 | xargs kill -9
```

### Maven Build Fails
```bash
# Clean everything and rebuild
mvn clean
mvn install
```

## ✅ All Features Working!

**What's Running:**
- ✅ 5 Microservices with DDD patterns
- ✅ Event-driven architecture (Kafka)
- ✅ Stream processing (windowed queries)
- ✅ 2 AI Agents (LangChain4j + Gemini)
- ✅ 40+ REST endpoints
- ✅ Auto-invoicing on appointment completion

**See Also:**
- **COMPLETE.md** - Full testing guide & demonstration script
- **test-services.bat** - Automated Windows tests
- **test-services.sh** - Automated Mac/Linux tests
- **FINAL-SUMMARY.md** - Quick reference