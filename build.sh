#!/bin/bash

DIR="$(cd "$(dirname "$0")" && pwd)"
mkdir -p "$DIR/materials/build"
find "$DIR/materials/src" -name "*.java" > "$DIR/sources.txt"
javac -d "$DIR/materials/build" @"$DIR/sources.txt"
rm "$DIR/sources.txt"
