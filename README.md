# BvrForth
An open-source Forth Compiler that will run on all platforms, written in Java by Nico ZS6BVR

This project replaces JEE8Forth and is written using "Clean Code" and built using OpenJDK 17 or 21

## Prerequisites :
You need Java 17 or 21 installed on your computer /
Get it here : [https://adoptium.net/](https://adoptium.net/)

## Run these commands to run BVForth
- In GitBash
- In the Linux terminal (remember to run chmod 775 to make each shell script executable)
- In MAC terminal (same as Linux)

### To build the JAR file:
```sh
./jar-make.sh
```
### To copy the JAR file:
./jar-copy.sh

### To run the BVR Forth:
```sh
./start.sh
```
## To run in an IDE
You need to run this class in your IDE:
```java
za.co.bvr.forth.BvrForth
```
## To use the JAR file as a Forth Compiler Java Library 
```java
za.co.bvr.forth.Forth
```
