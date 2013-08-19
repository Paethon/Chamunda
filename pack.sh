#!/bin/sh
# Usage: First command line argument is path to bukkit plugin path
cp plugin.yml bin
cd bin
jar -cfv chamunda.jar *
mv chamunda.jar $1
