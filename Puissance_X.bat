@echo off
cd /d "%~dp0"
cd projet\Puissance_X
type nul >> highscore 2>nul
java -cp ".;..\.." Main
