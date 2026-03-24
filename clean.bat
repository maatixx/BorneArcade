@echo off

del /s /q *.class 2>nul
for /d %%i in (projet\*) do (
    del /s /q "%%i\*.class" 2>nul
)
