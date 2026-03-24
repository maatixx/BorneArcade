@echo off
cd /d "%~dp0"
cd projet\InitialDrift
type nul >> highscore 2>nul
java -cp ".;..\.." Main
