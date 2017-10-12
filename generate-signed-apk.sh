#!/bin/sh

set -eux

./gradlew clean testProductionReleaseUnitTest publishProductionRelease