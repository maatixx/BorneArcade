#!/bin/bash
xdotool mousemove 1280 1024
cd projet/Pacman
touch highscore
java -cp .:../.. Main
