# Usage

Load the file [PlanetWars-1.01.jar](https://github.com/jaakko-paavola/a-little-software-engineering-project-in-java/releases/download/v1.01/PlanetWars-1.01.jar)

## Configuration

Sqlite database is a pre-requirement. The final release has been tested with Java 17.

## Starting the program

Use the command

```
java -jar PlanetWars-1.01.jar
```

## Signing up a new user or logging in an existing user

The program starts up in the login screen:

<img src="https://github.com/Jakoviz/ot-harjoitustyo/blob/master/dokumentaatio/Screenshot%20from%202019-12-10%2001-12-58.png" width="400">

Either sign up a new user with a password, or sign in an existing user (and the progress of the game will continue were it left off).

After signing in successfully the game scene will open.

## Gameplay

The space ship is controlled using the arrow keys, and space key fires a torpedo. The goal of the game is to fly towards (conquer) or shoot at (destroy) every planet in the game scene before the timer in the upper right corner runs out. If the player succeeds in conquering or destroying every planet on the level, they advance to the next, more difficult, level.
