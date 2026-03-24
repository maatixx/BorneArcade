@echo off
cd /d "%~dp0"
cd projet\JavaSpace
type nul >> highscore 2>nul
java -cp ".;..\.." Main
