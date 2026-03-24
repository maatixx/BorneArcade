@echo off
cd /d "%~dp0"
cd projet\Columns
type nul >> highscore 2>nul
java -cp ".;..\.." Main
