@echo off

setlocal

echo --------------------------------------------
echo Budowanie projektu za pomoca mvnw.cmd...
echo --------------------------------------------
call mvnw.cmd clean package

if %ERRORLEVEL% neq 0 (
    echo Blad przy budowaniu
    pause
    exit /b 1
)

echo --------------------------------------------
echo Szukam zbudowanego pliku JAR...
echo --------------------------------------------


set JAR_FILE=
for /f "delims=" %%i in ('dir /b /o-d target\*.jar ^| findstr /v "original"') do (
    if not defined JAR_FILE set JAR_FILE=target\%%i
)

if "%JAR_FILE%"=="" (
    echo Nie znaleziono pliku w target
    pause
    exit /b 1
)

echo Znaleziono plik: %JAR_FILE%
echo --------------------------------------------
echo Uruchamianie aplikacji Spring Boot...
echo --------------------------------------------

java -jar "%JAR_FILE%"

endlocal
pause