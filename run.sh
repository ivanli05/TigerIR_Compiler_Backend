#!/bin/bash

# Usage: ./run.sh <input.ir> <output.s> [--naive|--greedy]
DIR="$(cd "$(dirname "$0")" && pwd)"
java -cp "$DIR/materials/build" MipsCompiler "$1" "$2" "${3:---naive}"
