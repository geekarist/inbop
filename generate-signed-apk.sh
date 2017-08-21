#!/bin/sh

set -eux

git clone git@github.com:geekarist/android-private-files.git
mkdir -p private
cp android-private-files/me-cpele-android-keystore.jks private/
./gradlew assembleProductionRelease