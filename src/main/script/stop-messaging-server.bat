@echo off

where powershell 1> nul 2>&1 
if %errorlevel% neq 0 (
  echo "[powershell] not found.  This script requires [powershell] to send (stop) request to the messaging server."
) else (
  powershell -Command Invoke-WebRequest -URI 'http://localhost:1080/actuator/shutdown' -Method 'POST' -ContentType 'application/json' > NUL 2> NUL
)

rem ::END::