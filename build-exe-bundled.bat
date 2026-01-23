@echo off
REM Build script for Java Unit Converter - EXE with bundled JRE
echo Building Java Unit Converter (EXE with bundled JRE)...

REM First build the JAR
call build-jar.bat

if %errorlevel% neq 0 (
    echo JAR build failed, cannot create EXE!
    exit /b 1
)

echo.
echo Creating EXE with bundled JRE using launch4jc...
echo Note: This requires launch4jc to be installed and in PATH
echo Also requires a JRE 8+ to be placed in dist\jre\ directory

REM Check if launch4jc is available
where launch4jc >nul 2>&1
if %errorlevel% neq 0 (
    echo launch4jc not found in PATH!
    echo Please install launch4jc from: http://launch4jc.sourceforge.net/
    echo Or download and add it to your PATH
    exit /b 1
)

REM Check if JRE directory exists (optional warning)
if not exist dist\jre\ (
    echo Warning: dist\jre\ directory not found!
    echo The EXE will be created but won't run without a JRE.
    echo To bundle a JRE, download Java 8+ JRE and place it in dist\jre\
)

REM Run launch4jc with bundled config
launch4jc config\launch4jc-config-bundled.xml

if %errorlevel% neq 0 (
    echo launch4jc failed!
    exit /b 1
)

echo.
echo Build successful!
echo EXE file created: dist\UnitConverter-bundled.exe
echo Note: This EXE includes a bundled JRE (if dist\jre\ exists).