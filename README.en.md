Here's the English version of the document:

# Libgdx Game Collection

<p align="center"><img src="doc/image/logo.png" alt="logo"></p>

[![Jitpack Badge](https://jitpack.io/v/pama1234/just-some-other-libgdx-game.svg)](https://jitpack.io/#Java-Game-Engine-Merger/Libgdx-Processing-Plus)

## Using Protobuf

You should first download `protoc` from the [latest Protobuf release](https://github.com/protocolbuffers/protobuf/releases/latest) and add it to your `PATH` environment variable. Otherwise, running Gradle tasks like `build` and
`generateProto` will result in errors.

## Getting Started

### Creating a New Sketch

1. Review the [Game Collection](#game-collection).
2. Open the corresponding project (e.g., `game0001`) and locate `pama1234.gdx.launcher.MainApp`.
3. Create a new `Screen0xxx`.
4. Inherit your `Screen0xxx` from either `ScreenCore2D` or `ScreenCore3D`.

## Configuring `settings.gradle`

Gradle can be slow, especially with over 40 subprojects in this repository, which could generate more than 9000 tasks. To improve development speed, we recommend commenting out unused projects in the `settings.gradle` file. Uncomment them
if you need to use them.

## Creating a New Project

If you want to create a new project, add the following line at line 35 of the root directory's `build.gradle` file, where it says `configure(subprojects.findAll {`:

```gradle
|| it.name == 'project-name-android'
```

This line excludes this subproject and others that cannot use the Gradle Java plugin.

## Tutorials and Documentation

(Currently under development) [Link to documentation](https://github.com/Java-Game-Engine-Merger/libgdx-processing-website)

## How to Use Our Framework

Our framework is packaged via Jitpack.

[Framework link](https://jitpack.io/#pama1234/just-some-other-libgdx-game/)

Please note that the diamond dependency issue is not resolved. Therefore, when using `framework0001` and other components in different projects, you might need to configure numerous `exclude` rules, especially when packaging for Android. We
recommend using only the following two basic frameworks:

### Gradle

```gradle
implementation "com.github.pama1234.just-some-other-libgdx-game:server-framework:$pama1234Version"
implementation "com.github.pama1234.just-some-other-libgdx-game:framework:$pama1234Version"
```

Or specify the version directly:

```gradle
implementation 'com.github.pama1234.just-some-other-libgdx-game:server-framework:bf0a359313'
implementation 'com.github.pama1234.just-some-other-libgdx-game:framework:bf0a359313'
```

### Maven

```xml

<dependency>
  <groupId>com.github.pama1234.just-some-other-libgdx-game</groupId>
  <artifactId>framework</artifactId>
  <version>bf0a359313</version>
</dependency>
```

## Game Collection

1. Fantasy World 1
2. Particle System [Maintenance Paused]
3. Geometric Duel [Ported from FAL's student project, now popular]
4. High-Dimensional Tower Defense [3D Game Template]
5. Miscellaneous
6. Central IDE
7. Fill the Square
8. Particle Life: Ascension (Modified 2D Processing Particle System)
9. Journey to an Alien Planet
10. Chat Room
11. Numerical Inflation, Vector Sky, and TRPG System [Moved to ce4kotlin]
12. Sky Encounter 2, Wandering Sphere, Don't Be Absurd [ce4kotlin]
13. Item Management and Similar Games [Galgame]
14. Zenith Language
15. MBTI Simulator

[Chinese](#fantasy-world) | [中文](#空想世界)

## Fantasy World

The repository for the open-source game project series "Fantasy World."

QQ Group: 589219461

### Setting Up the Environment

1. Ensure your computer can directly access the necessary websites. [This solution](https://github.com/getlantern/lantern) is recommended.
2. Download and install `JDK-17`, and configure the `JAVA_HOME` and `PATH` environment variables. [This version (GraalVM 22.1.0)](https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0) is recommended.
3. Download and install `Gradle-8`, and configure the `GRADLE_HOME` and `GRADLE_JAVA_HOME` environment variables. [Download link](https://gradle.org/releases).
4. Download and install `Android Studio Canary build`, and configure the `AndroidSDK` environment variable (this is the beta version; always install the latest version). [Download link](https://developer.android.com/studio/preview).

### Libraries Used

- libgdx
- kryo
- vecmath

### Main Class Location

`pama1234.gdx.launcher.MainApp`

### Project Code Help Documentation

`doc\codeHelp.txt`

### Code Formatting

Please use `doc\eclipse.formatter.xml` for code formatting. If you prefer not to use this formatting, please do not submit code with four-space indentation; other styles are acceptable.

### Local Variable Naming Rules

| Type     | Total Dimensions | Dimensions | Character |
|----------|------------------|------------|-----------|
| Position | 3                | 1          | x         |
| Position | 3                | 2          | y         |
| Position | 3                | 3          | z         |
| Volume   | 3                | 1          | w         |
| Volume   | 3                | 2          | h         |
| Volume   | 3                | 3          | l         |
| Integer  | ?                | 1          | i         |
| Float    | ?                | 1          | f         |
| Boolean  | ?                | 1          | b         |
| Parent   | ?                | 1          | p         |
| Bundle   | ?                | 1          | bd, ld    |

### Abbreviation Examples

| Type     | Example                  |
|----------|--------------------------|
| Position | tx, tx1, tx_1, tx2, tx_2 |
| Parent   | p, pc, pw, pg            |

(`tx` is the abbreviation for `temp-x`)

### To-Do List

Please refer to the `doc\todo.txt` file. After completing a task, move it to `solved.txt`. When compiling the Android project, use your own signature file. You can use any language for to-do items, but do not change the language of existing
items.

### Android Version Notes

When modifying `native` dependencies, remember to manually delete files and folders in `android\libs\`.