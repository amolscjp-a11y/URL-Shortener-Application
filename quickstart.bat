@echo off
REM URL Shortener Application - Quick Start Script for Windows
REM This script helps you build and run the application

echo.
echo ================================================
echo   URL Shortener Application - Quick Start
echo ================================================
echo.

:menu
echo Choose an option:
echo 1. Build application with Maven
echo 2. Run application (mvn spring-boot:run)
echo 3. Build Docker image
echo 4. Run Docker container
echo 5. Run tests
echo 6. Clean build
echo 7. Exit
echo.
set /p choice="Enter your choice (1-7): "

if "%choice%"=="1" goto build
if "%choice%"=="2" goto run
if "%choice%"=="3" goto docker_build
if "%choice%"=="4" goto docker_run
if "%choice%"=="5" goto tests
if "%choice%"=="6" goto clean
if "%choice%"=="7" goto exit
goto invalid

:build
echo.
echo Building application...
mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo Build failed!
    goto menu
)
echo Build completed successfully!
goto menu

:run
echo.
echo Starting application on http://localhost:8080
echo Press Ctrl+C to stop...
echo.
mvn spring-boot:run
goto menu

:docker_build
echo.
echo Building Docker image...
docker build -t url-shortener:latest .
if %errorlevel% neq 0 (
    echo Docker build failed!
    goto menu
)
echo Docker image built successfully!
goto menu

:docker_run
echo.
echo Running Docker container on http://localhost:8080
echo Press Ctrl+C to stop...
echo.
docker run -p 8080:8080 url-shortener:latest
goto menu

:tests
echo.
echo Running tests...
mvn test
goto menu

:clean
echo.
echo Cleaning build artifacts...
mvn clean
echo Clean completed!
goto menu

:invalid
echo Invalid choice. Please try again.
goto menu

:exit
echo Goodbye!
exit /b 0

