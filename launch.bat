@echo off
chcp 65001 >nul
setlocal

echo ==========================================
echo    Java Unit Converter Launcher
echo ==========================================
echo.

where java >nul 2>&1 || (
    echo [ERROR] Java is not installed or not in PATH.
    echo Please install JDK 8+ from https://java.com/download/
    pause
    exit /b 1
)

set SRC_DIR=src\main\java
set OUT_DIR=target\classes
set RES_DIR=src\main\resources

where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo [INFO] Maven detected -- running via Maven...
    mvn compile exec:java -q && echo. && echo Done. && exit /b 0
    echo [WARN] Maven execution failed, trying fallback...
)

if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

echo [INFO] Compiling with javac...
dir /b /s "%SRC_DIR%\*.java" > sources.tmp 2>nul
javac -d "%OUT_DIR%" -encoding UTF-8 @sources.tmp || (
    echo [ERROR] Compilation failed.
    del sources.tmp 2>nul
    pause
    exit /b 1
)
del sources.tmp 2>nul

xcopy /e /y /i "%RES_DIR%" "%OUT_DIR%" >nul 2>&1

echo [INFO] Launching Unit Converter...
start "" javaw -cp "%OUT_DIR%" com.unitconverter.App || (
    java -cp "%OUT_DIR%" com.unitconverter.App
)
exit /b 0
