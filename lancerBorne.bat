@echo off
cd /d "%~dp0"

echo Nettoyage des repertoires
echo Veuillez patienter
call clean.bat
call compilation.bat

echo Lancement du Menu
echo Veuillez patienter

java -cp "." Main

call clean.bat

echo Extinction de la borne dans 30 secondes
timeout /t 30 /nobreak

shutdown /s /t 0
