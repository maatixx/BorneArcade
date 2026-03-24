@echo off
cd /d "%~dp0"
cd projet\Minesweeper
type nul >> highscore 2>nul
java -cp ".;..\.." Minesweeper
