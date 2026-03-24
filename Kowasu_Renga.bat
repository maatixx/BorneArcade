@echo off
cd /d "%~dp0"
cd projet\Kowasu_Renga
type nul >> highscore 2>nul
java -cp ".;..\.." Kowasu_Renga
