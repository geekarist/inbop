# Inbop

Make climbing easier using an Android application.

## Features

- List places near Paris
- Display useful info 
- Show a map
- Star a place

## Planned

- Describe places
  - Useful info
  - Pictures
  - Directions
  - Map
  - List places in order of proximity
  - Get information from Facebook API

- Follow progress
    - Data input
    - Visualization

- UI
  - Pretty splash screen

- Technical
  - Use Dagger
  - Upgrade play services
  - Extract star controlling logic from `ListActivity`
  - Setup `dependencyUpdates`
  - Run unit tests in pre push hook
  - Run UI tests in pre merge hook
  - Offline mode
  - Use Java 8 language features: https://developer.android.com/guide/platform/j8-jack.html

# Notes

## Jack Toolchain Assessment

Build times:

- With Jack:

        git checkout enable_java8 && ./gradlew clean assembleDevelopmentDebug
        [...]
        Total time: 3 mins 9.839 secs

- Without Jack:

        git checkout develop && ./gradlew clean assembleDevelopmentDebug
        [...]
        Total time: 1 mins 21.446 secs