@echo off
cd /d "%~dp0"
cd projet\Pacman
type nul >> highscore 2>nul
java -cp ".;..\.." Main
