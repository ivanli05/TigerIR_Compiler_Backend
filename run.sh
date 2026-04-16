#!/bin/bash

# Usage: ./run.sh <input.ir> <output.s> [--naive|--greedy]
java -cp materials/build MipsCompiler "$1" "$2" "${3:---naive}"
