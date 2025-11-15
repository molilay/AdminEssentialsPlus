@echo off
chcp 65001 >nul
title AdminEssentialsPlus - Gradle Build

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║         AdminEssentialsPlus - Gradle Build Script         ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

REM Проверка Gradle
where gradle >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Gradle не найден!
    echo.
    echo Установите Gradle:
    echo 1. Скачайте: https://gradle.org/releases/
    echo 2. Или используйте: choco install gradle
    echo 3. Или используйте Gradle Wrapper: gradlew.bat
    echo.
    pause
    exit /b 1
)

echo [INFO] Gradle найден!
gradle --version
echo.

echo ════════════════════════════════════════════════════════════
echo  Начинаем сборку плагина...
echo ════════════════════════════════════════════════════════════
echo.

REM Очистка и сборка
gradle clean shadowJar --info

if %errorlevel% equ 0 (
    echo.
    echo ════════════════════════════════════════════════════════════
    echo  ✓ СБОРКА УСПЕШНА!
    echo ════════════════════════════════════════════════════════════
    echo.
    echo JAR файл создан:
    echo build\libs\AdminEssentialsPlus-1.1.0.jar
    echo.
    echo Размер файла:
    for %%A in (build\libs\AdminEssentialsPlus-1.1.0.jar) do echo %%~zA байт
    echo.
) else (
    echo.
    echo ════════════════════════════════════════════════════════════
    echo  ✗ ОШИБКА СБОРКИ!
    echo ════════════════════════════════════════════════════════════
    echo.
    echo Проверьте логи выше для деталей.
    echo.
)

pause
