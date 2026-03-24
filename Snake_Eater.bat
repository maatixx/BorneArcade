@echo off
cd /d "%~dp0"
cd projet\Snake_Eater
type nul >> highscore 2>nul
java -cp ".;..\.." Snake_Eater
