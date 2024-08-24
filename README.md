<div align="center">
  <h1 align="center">Stacked Success</h3>
    <img src="https://github.com/user-attachments/assets/45483a8c-2535-4133-98ce-9fc1adadd40a" width="100" height="100">
  <p align="center">
    A faithful Tetris recreation created in Java and JavaFX
  </p>

  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

</div>

## About This Project

<br>

This is a project created for my team's submission of Software Engineering - Software Evolution and Maintenence assignment, 
based of the game Tetris. The objective of the game is to stack blocks and create lines horizontally in order
to get points.

<img src="https://github.com/user-attachments/assets/8927383d-0e41-41e9-b785-52c78903a188" width="450" height="300" align="right">

### My Contributions
* The creation of the back-end game logic for the game, including:
  - Movement and Rotation
  - Changable keybindings
  - Line clearing
  - Game over detection
  - Collision detection
  - Ghost prediction
  - Customisable Tetrimino pieces
    
* The translation from the back-end game logic to the JavaFX front-end
  - This was achieved by taking in the simple 2D integer array and updating the placement of "block" units on a visually 10x20 grid.
    
* Designed and refactored the game to be as portable / decoupled as possible from the rest of the applcation.
  - This was done by excluding game logic inside of JavaFX related classes, and vice versa for JavaFX display logic.
  
In order to get play the game, clone the repository and type in

```
./mvnw javafx:run
```

The default controls of the game are as follows
```
Move Left - Left Arrow
Move Right - Right Arrow
Move Down - Down Arrow
Hard Drop - Spacebar

Rotate Clockwise - Z
Rotate Anticlockwise - X

Hold - C

Pause - Esc
```

In order to get started by contributing the file, make sure to fork the repository and clone onto your own PC. This
project relies on Java 17, JavaFX and the Maven wrapper as the build tool.

To install Java follow this link https://www.geeksforgeeks.org/how-to-download-and-install-java-for-64-bit-machine/

Contributing Guidelines can be found here:

Project acknowledgements: <br />
Quaid Sage - quaidsage<br />
Allan Xu - axu732<br />
Maahir Nafis - MaahirN<br />
Joel Kendall - joelkendall <br />
Albert Sun - asun555 <br />
Max Chen - mche791<br />
