@echo off

setlocal
set JASYPT_ENCRYPTOR_PASSWORD=%1
set CLASSPATH=config;lib
cd %~dp0%\..
java -cp %CLASSPATH% -jar lib/simple-messaging-server-1.0.jar
endlocal

rem ::END::