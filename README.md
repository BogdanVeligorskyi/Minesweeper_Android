# Minesweeper (Android)

![alt text](https://github.com/BogdanVeligorskyi/Minesweeper_Android/blob/main/screens/screen_1.png?raw=true)
![alt text](https://github.com/BogdanVeligorskyi/Minesweeper_Android/blob/main/screens/screen_2.png?raw=true)
![alt text](https://github.com/BogdanVeligorskyi/Minesweeper_Android/blob/main/screens/screen_3.png?raw=true)
![alt text](https://github.com/BogdanVeligorskyi/Minesweeper_Android/blob/main/screens/screen_4.png?raw=true)
![alt text](https://github.com/BogdanVeligorskyi/Minesweeper_Android/blob/main/screens/screen_5.png?raw=true)

My implementation of iconic Minesweeper game for Android devices.

## Features

1. Classic Minesweeper rules.
2. Cnaggeable board size and number of mines.
3. Support for smartphones and tablets.
4. Best results window (something like Highscores).
5. White and dark themes.

## Available languages

English

## Used technologies

Java 8, Android Studio (2021.3)

## How to run application

In order to run this application you need to download correponding APK-file and run it on your Android device.
After that select board size in settings (number of rows and columns) and number of mines. Rows and cols possible values are from 1 to 25, mines possible values are from 1 to rows*cols/3. 
The main goal is to open all cells without mines. However, if you meet mine, your game stops. 
A digit (from 1 to 8) in cell shows exact number of mines in all 8 neighbour cells. 
To mark cell as a one containing a mine you need to use flag (long click). 

PLEASE NOTE: your Android device must have at least OS Android 4.2.

## Demonstrational video

[YouTube](https://www.youtube.com/watch?v=2eXyqi0Ayos)