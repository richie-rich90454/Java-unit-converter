@echo off
setlocal

echo ==========================================
echo    Java Unit Converter Builder
echo ==========================================
echo.

where java >nul 2>&1 || (
    echo [ERROR] Java is not installed or not in PATH.
    echo Please install JDK 8+ from https://java.com/download/
    pause
    exit /b 1
)

if not exist "dist" mkdir dist

where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo [INFO] Maven detected -- building with Maven...
    mvn clean package -q -DskipTests && (
        copy /y target\UnitConverter.jar dist\UnitConverter.jar >nul
        echo [OK] Built dist\UnitConverter.jar
        pause
        exit /b 0
    )
    echo [WARN] Maven build failed, trying fallback...
)

set SRC_DIR=src\main\java
set TMP_DIR=target\build-jar
set RES_DIR=src\main\resources

if exist "%TMP_DIR%" rmdir /s /q "%TMP_DIR%"
mkdir "%TMP_DIR%"

echo [INFO] Compiling with javac...
dir /b /s "%SRC_DIR%\*.java" > sources.tmp 2>nul
javac -d "%TMP_DIR%" -encoding UTF-8 @sources.tmp || (
    echo [ERROR] Compilation failed.
    del sources.tmp 2>nul
    pause
    exit /b 1
)
del sources.tmp 2>nul

xcopy /e /y /i "%RES_DIR%" "%TMP_DIR%" >nul 2>&1

copy /y manifest.txt "%TMP_DIR%\manifest.txt" >nul 2>&1

echo [INFO] Packaging JAR...
cd "%TMP_DIR%"
jar cfm ..\..\dist\UnitConverter.jar manifest.txt com\*.class *.png fonts\ 2>nul || (
    jar cf ..\..\dist\UnitConverter.jar com\*.class *.png fonts\ 2>nul
)
cd ..\..\

echo [OK] Built dist\UnitConverter.jar
pause
exit /b 0
