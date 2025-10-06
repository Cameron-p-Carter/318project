#!/bin/bash
# Healthcare Platform - Service Test Script

echo "========================================="
echo "Healthcare Platform - Service Tests"
echo "========================================="
echo ""

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Test function
test_endpoint() {
    local service=$1
    local url=$2
    echo -n "Testing $service... "
    response=$(curl -s -o /dev/null -w "%{http_code}" $url)
    if [ $response -eq 200 ]; then
        echo -e "${GREEN}✓ OK${NC}"
    else
        echo -e "${RED}✗ FAILED (HTTP $response)${NC}"
    fi
}

echo "1. HEALTH CHECKS"
echo "-----------------"
test_endpoint "Appointment Service" "http://localhost:8081/api/patients"
test_endpoint "Financial Service" "http://localhost:8084/api/invoices"
test_endpoint "Medication Service" "http://localhost:8082/api/prescriptions"
test_endpoint "Medical Records Service" "http://localhost:8083/api/medical-records"
echo ""

echo "2. GET ALL DATA FROM EACH SERVICE"
echo "-----------------------------------"

echo "Patients (Appointment Service):"
curl -s http://localhost:8081/api/patients | python3 -m json.tool 2>/dev/null || curl -s http://localhost:8081/api/patients
echo ""

echo "Appointments:"
curl -s http://localhost:8081/api/appointments | python3 -m json.tool 2>/dev/null || curl -s http://localhost:8081/api/appointments
echo ""

echo "Invoices (Financial Service):"
curl -s http://localhost:8084/api/invoices | python3 -m json.tool 2>/dev/null || curl -s http://localhost:8084/api/invoices
echo ""

echo "Prescriptions (Medication Service):"
curl -s http://localhost:8082/api/prescriptions | python3 -m json.tool 2>/dev/null || curl -s http://localhost:8082/api/prescriptions
echo ""

echo "Medical Records:"
curl -s http://localhost:8083/api/medical-records | python3 -m json.tool 2>/dev/null || curl -s http://localhost:8083/api/medical-records
echo ""

echo "3. CREATE NEW PATIENT"
echo "----------------------"
curl -X POST http://localhost:8081/api/patients \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "TestUser",
    "lastName": "Demo",
    "email": "test@demo.com",
    "phone": "555-TEST",
    "dateOfBirth": "1990-01-01"
  }'
echo ""
echo ""

echo "4. CREATE NEW APPOINTMENT"
echo "--------------------------"
curl -X POST http://localhost:8081/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 1,
    "doctorName": "Dr. Test",
    "appointmentDateTime": "2024-12-01T10:00:00",
    "type": "CONSULTATION",
    "location": "Room 999"
  }'
echo ""
echo ""

echo "5. CREATE PRESCRIPTION"
echo "-----------------------"
curl -X POST http://localhost:8082/api/prescriptions \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 1,
    "appointmentId": 1,
    "patientName": "John Doe",
    "doctorName": "Dr. Test",
    "medicationName": "Test Medication",
    "dosage": "100mg",
    "frequency": "Once daily",
    "durationDays": 30,
    "refillsRemaining": 2
  }'
echo ""
echo ""

echo "6. CREATE MEDICAL RECORD"
echo "-------------------------"
curl -X POST http://localhost:8083/api/medical-records \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": 99,
    "patientName": "Test Patient"
  }'
echo ""
echo ""

echo "========================================="
echo "Tests Complete!"
echo "========================================="