@echo off

echo Compilation du menu de la borne d'arcade
echo Veuillez patienter
javac -cp "." *.java

cd projet

for /d %%i in (*) do (
    cd %%i
    echo Compilation du jeu %%i
    echo Veuillez patienter
    javac -cp ".;..\.." *.java
    cd ..
)

cd ..
