@echo off
cd /d "%~dp0"
cd projet\DinoRail
type nul >> highscore 2>nul
java -cp ".;..\.." DinoRail
