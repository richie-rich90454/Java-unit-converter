@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo ╔════════════════════════════════════════╗
echo ║      Java Unit Converter Launcher      ║
echo ╚════════════════════════════════════════╝
echo.

:: Detect Java
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH.
    echo Please install JDK 8+ from https://www.java.com/download/
    pause
    exit /b 1
)

:: Check for Maven
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo [INFO] Maven detected -- running via Maven...
    mvn clean compile exec:java -Dexec.mainClass="com.unitconverter.App" -q
    if !errorlevel! equ 0 (
        echo.
        echo Done.
        exit /b 0
    )
)

:: Fallback: compile and run without Maven
echo [INFO] Compiling with javac...
set SRC_DIR=src\main\java
set OUT_DIR=target\classes
set RES_DIR=src\main\resources

if not exist "%OUT_DIR%" mkdir "%OUT_DIR%"

dir /b /s "%SRC_DIR%\*.java" > sources.tmp
javac -d "%OUT_DIR%" -encoding UTF-8 @sources.tmp
if %errorlevel% neq 0 (
    echo [ERROR] Compilation failed.
    del sources.tmp 2>nul
    pause
    exit /b 1
)
del sources.tmp 2>nul

:: Copy resources to output
xcopy /e /y /i "%RES_DIR%" "%OUT_DIR%" >nul 2>&1

echo [INFO] Launching Unit Converter...
start "" javaw -cp "%OUT_DIR%" com.unitconverter.App
exit /b 0
