@echo off
REM Build script for Java Unit Converter - EXE version (requires JRE)
echo Building Java Unit Converter (EXE - requires JRE)...

REM First build the JAR
call build-jar.bat

if %errorlevel% neq 0 (
    echo JAR build failed, cannot create EXE!
    exit /b 1
)

echo.
echo Creating EXE with launch4jc...
echo Note: This requires launch4jc to be installed and in PATH

REM Check if launch4jc is available
where launch4jc >nul 2>&1
if %errorlevel% neq 0 (
    echo launch4jc not found in PATH!
    echo Please install launch4jc from: http://launch4jc.sourceforge.net/
    echo Or download and add it to your PATH
    exit /b 1
)

REM Run launch4jc
launch4jc config\launch4jc-config.xml

if %errorlevel% neq 0 (
    echo launch4jc failed!
    exit /b 1
)

echo.
echo Build successful!
echo EXE file created: dist\UnitConverter.exe
echo Note: This EXE requires Java 8+ to be installed on the system.