#!/bin/bash

cd android
ls 
chmod +x ./gradlew
./gradlew assembleDebug
./gradlew connectedAndroidTest