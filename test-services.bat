@echo off
echo =========================================
echo Healthcare Platform - Service Tests
echo =========================================
echo.

echo 1. HEALTH CHECKS
echo -----------------
curl -s http://localhost:8081/api/patients >nul 2>&1 && echo Appointment Service: OK || echo Appointment Service: FAILED
curl -s http://localhost:8084/api/invoices >nul 2>&1 && echo Financial Service: OK || echo Financial Service: FAILED
curl -s http://localhost:8082/api/prescriptions >nul 2>&1 && echo Medication Service: OK || echo Medication Service: FAILED
curl -s http://localhost:8083/api/medical-records >nul 2>&1 && echo Medical Records Service: OK || echo Medical Records Service: FAILED
echo.

echo 2. GET ALL PATIENTS
echo --------------------
curl http://localhost:8081/api/patients
echo.
echo.

echo 3. GET ALL APPOINTMENTS
echo ------------------------
curl http://localhost:8081/api/appointments
echo.
echo.

echo 4. GET ALL INVOICES
echo --------------------
curl http://localhost:8084/api/invoices
echo.
echo.

echo 5. GET ALL PRESCRIPTIONS
echo --------------------------
curl http://localhost:8082/api/prescriptions
echo.
echo.

echo 6. GET ALL MEDICAL RECORDS
echo ----------------------------
curl http://localhost:8083/api/medical-records
echo.
echo.

echo 7. CREATE NEW PATIENT
echo ----------------------
curl -X POST http://localhost:8081/api/patients -H "Content-Type: application/json" -d "{\"firstName\":\"TestUser\",\"lastName\":\"Demo\",\"email\":\"test@demo.com\",\"phone\":\"555-TEST\",\"dateOfBirth\":\"1990-01-01\"}"
echo.
echo.

echo 8. CREATE NEW APPOINTMENT
echo --------------------------
curl -X POST http://localhost:8081/api/appointments -H "Content-Type: application/json" -d "{\"patientId\":1,\"doctorName\":\"Dr. Test\",\"appointmentDateTime\":\"2024-12-01T10:00:00\",\"type\":\"CONSULTATION\",\"location\":\"Room 999\"}"
echo.
echo.

echo =========================================
echo Tests Complete!
echo =========================================
pause