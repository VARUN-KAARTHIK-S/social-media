@echo off
echo Downloading MySQL Connector...
echo.

REM Create a simple download script
echo @echo off > download_temp.bat
echo echo Downloading mysql-connector-j-9.4.0.jar... >> download_temp.bat
echo powershell -Command "Invoke-WebRequest -Uri 'https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-9.4.0.jar' -OutFile 'mysql-connector-j-9.4.0.jar'" >> download_temp.bat
echo echo Download completed! >> download_temp.bat
echo pause >> download_temp.bat

echo Running download...
call download_temp.bat

del download_temp.bat

echo.
echo MySQL connector download completed!
pause
