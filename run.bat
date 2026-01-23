@echo off
REM Run script for Java Unit Converter
echo Running Java Unit Converter...

REM Check if JAR exists
if not exist dist\UnitConverter.jar (
    echo JAR file not found. Building first...
    call build-jar.bat
    if %errorlevel% neq 0 (
        echo Build failed!
        exit /b 1
    )
)

echo.
echo Starting Unit Converter...
java -jar dist\UnitConverter.jar