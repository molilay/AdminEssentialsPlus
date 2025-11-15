@echo off
echo ========================================
echo   AdminEssentialsPlus - Build Script
echo ========================================
echo.

set JAVA_HOME=%~dp0jdk-21.0.2+13
set PATH=%JAVA_HOME%\bin;%PATH%

echo Using Java 21...
java -version
echo.

echo Building plugin...
call "%~dp0apache-maven-3.9.6\bin\mvn.cmd" clean package

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo   BUILD SUCCESS!
    echo ========================================
    echo.
    echo JAR file: target\AdminEssentialsPlus-1.0.0.jar
    echo.
) else (
    echo.
    echo ========================================
    echo   BUILD FAILED!
    echo ========================================
    echo.
)

pause
