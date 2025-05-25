call mvnw.cmd clean package

if %ERRORLEVEL% neq 0 (
    echo Blad przy budowaniu
    pause
    exit /b 1
)

java -jar target\TPO6_TJ_S31585-0.0.1-SNAPSHOT.jar
pause