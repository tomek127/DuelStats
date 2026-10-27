# Duel Stats — Fabric 1.21.4

Client-side Minecraft Java mod project for **Minecraft 1.21.4**, **Fabric**, and **Java 21**.

## Features
- Press **L** to open the GUI.
- **START / RESET** begins a fresh measurement.
- **STOP** ends the measurement; stats remain visible.
- Shows attack attempts, confirmed hits, accuracy percentage, current combo, and longest combo.

## Build
1. Install JDK 21.
2. Install Gradle (or open this project in IntelliJ IDEA with Gradle support).
3. From the project directory run:
   - Windows: `gradle build`
   - Linux/macOS: `gradle build`
4. The distributable mod jar should be in `build/libs/` (use the jar without `-dev` in its name).

## Install
Copy the built `duel-stats-1.0.0.jar` into your Minecraft instance's `mods` folder. Fabric API must also be installed.

## Important accuracy note
Minecraft's client does not expose one universal, perfect "hit landed" event for every server. This starter implementation counts an attempt when the client sends an attack action and correlates the target's hurt status / health change to detect a hit. Server plugins, unusual combat mechanics, multiple attacks in the same tick, and invulnerability frames can affect accuracy. Treat the first build as a starter project and test it on your target server.

The project source is provided here; this archive is **not a precompiled or verified `.jar`**. Gradle must download Minecraft/Fabric dependencies and compile it on your machine.
