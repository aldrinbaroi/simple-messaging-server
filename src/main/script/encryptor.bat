@echo off

setlocal
set COMMAND=%1
set PASSWORD=%2
set TEXT=%3
set CLASSPATH=lib/.;lib/jasypt-1.9.3.jar
cd %~dp0%\..
java -cp %CLASSPATH% net.baroi.messaging.server.simple.encryption.EncryptDecryptUtil %COMMAND% %PASSWORD% %TEXT%
endlocal

rem ::END::
