#!/bin/bash

noSpaces=$(echo "$@" | sed 's/ //g')

if [ "$#" -ne 1 ]; then
    printf "Invalid Argument:\n\t./computor 8 * X^0 - 6 * X^1 + 0 * X^2 - 5.6 * X^3 = 3 * X^0\n"
    exit
fi


 input=""
    for a in "$noSpaces"
    do
        input+="$a"
    done
    echo "$input"

# find . -name "*.java" > sources.txt
# javac @sources.txt
# java Main "$input"
# find . -name "*.class" -delete
# rm sources.txt
