#!/bin/bash

./gradlew test

# build using current git tag as version
tag=$(git describe --exact-match --tags $(git log -n1 --pretty='%h') 2> /dev/null)
if [ -z "$tag" ]; then
    echo "HEAD is not tagged. Please tag before building."; \
    exit 1
fi
./gradlew -Pversion=$tag clean build

cp ./build/libs/tollCalculator-$tag.jar ./build/libs/tollCalculator.jar