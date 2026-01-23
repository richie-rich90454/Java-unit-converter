@echo off
REM Build script for Java Unit Converter - JAR version
echo Building Java Unit Converter (JAR)...

REM Clean dist directory
if exist dist rmdir /s /q dist
mkdir dist

REM Clean build directory
if exist build rmdir /s /q build
mkdir build

REM Compile
echo Compiling...
javac -d build src\main\java\App.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    exit /b 1
)

echo Creating JAR file...
REM Create JAR with manifest
jar cvfm dist\UnitConverter.jar config\manifest.txt -C build . -C src\main\resources .

if %errorlevel% neq 0 (
    echo JAR creation failed!
    exit /b 1
)

echo.
echo Build successful!
echo JAR file created: dist\UnitConverter.jar
echo.
echo To run: java -jar dist\UnitConverter.jar