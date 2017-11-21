#!/bin/sh

set -eux

./gradlew clean androidGitVersion testProductionReleaseUnitTest publishProductionRelease