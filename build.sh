#!/bin/bash

# Write a script to build your optimizer in this file 
# (As required by your chosen optimizer language)

mkdir -p materials/build
find materials/src -name "*.java" > sources.txt
javac -d materials/build @sources.txt
rm sources.txt