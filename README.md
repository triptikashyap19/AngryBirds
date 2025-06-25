# Angry Birds Game (libGDX)

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending Game that sets the first screen.

## Platforms

- core: Main module with the application logic shared by all platforms.
- lwjgl3: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

This project is an attempt in recreation of the popular Angry Birds game using the libGDX framework in Java. It includes multiple classes and packages to manage game objects, levels, and screens, providing a fun and engaging gaming experience with well-implemented object-oriented principles.

## Key OOP Concepts

1. **Encapsulation**:
   - Each game entity, such as `Bird`, `Pig`, and `Block`, is encapsulated in its own class with private attributes and public methods, making them modular and easy to manage.
   - Encapsulation keeps each object's state controlled and interaction through well-defined interfaces.

2. **Inheritance**:
   - The class hierarchy makes use of inheritance extensively. For example:
     - The `Bird` base class is extended by specific bird classes (`Red`, `Yellow`, and `Black`), each with unique abilities.
     - The `Block` class is extended by specific material classes (`Wood`, `Glass`, and `Stone`), enabling varied behaviors for each block type.
   - Inheritance provides a foundation for shared behaviors among similar objects while allowing unique characteristics.

3. **Polymorphism**:
   - Polymorphism is achieved through method overriding in subclasses. For instance:
     - Each bird type (`Red`, `Yellow`, `Black`) can interact uniquely with other game objects, even when handled through a common `Bird` interface.
     - The different block types respond differently to collisions based on their material.
   - This enables flexible interaction across various game elements.

4. **Abstraction**:
   - Abstract classes and interfaces like `GameObject` are used to define general properties of entities (e.g., `Bird`, `Pig`, `Block`), simplifying the handling of complex game interactions by hiding specific details.

## Project Structure

The project is organized into packages representing various game elements:

- **GameObjects**: Contains classes for different game objects like birds, blocks, and pigs.
  - **Birds**: `Bird` base class and subclasses (`Red`, `Yellow`, `Black`) represent birds with unique abilities.
  - **Blocks**: The `Block` base class is extended by specific material classes (`Wood`, `Glass`, `Stone`), each with different durability properties.
  - **Pigs**: The `Pig` class has variants like `BigPig`, `KingPig`, and `SmallPig`, each with distinct attributes.

- **Levels**: Manages level-specific data and gameplay mechanics for levels such as `Level1`, `Level2`, and `Level3`. Each level class defines unique configurations of obstacles, pigs, and objectives.

- **Screens**: Manages different screens in the game flow:
  - `HomeScreen`: The main menu screen where players can start or exit the game.
  - `LevelScreen`: Displays available levels and allows players to select a level.
  - `VictoryScreen` and `DefeatScreen`: These screens display when a player wins or loses a level, showing relevant options like replay, next level, or return to home.

## Setup and Run Instructions

### Prerequisites

1. **Java Development Kit (JDK)**: Ensure JDK 8 or higher is installed.
2. **libGDX**: libGDX dependencies should be automatically handled by Gradle.

### Setup

1. Clone this repository to your local machine by running following command in terminal:
   ```bash
   git clone https://github.com/devan191/AngryBirds-AP.git
2. Or if you already have .zip file , unzip it and navigate to it in the terminal.
   Specifically navigate to directory : AngryBirds-AP

3. The project includes the Gradle wrapper, so you don’t need to install Gradle separately. To set up and build the project, use the following commands:
   ```bash
    ./gradlew build       # On Mac/Linux
     gradlew.bat build     # On Windows
4. To run the game, use the Gradle run task. This will automatically build and launch the application:
   ```bash
    ./gradlew lwjgl3:run           # On Mac/Linux
    gradlew.bat lwjgl3:run         # On Windows

### Additional Gradle Commands

1. Clean the Project: To clean the build directory, use:
   ```bash
   ./gradlew clean       # On Mac/Linux
    gradlew.bat clean     # On Windows
   
2. Rebuild the Project: To rebuild everything from scratch, use:
   ```bash
   ./gradlew build       # On Mac/Linux
    gradlew.bat build     # On Windows

## Assets

- Assets folder has all the game assets like fonts, images, skins, and sound.
  
- Following are links/references to all the resources and assets that we have used in our project and don't own any rights to them, which are being used for non commercial purpose:

- Font: https://www.dafont.com/angrybirds.font

- App icon:
https://www.iconarchive.com/show/angry-birds-icons-by-femfoyou/angry-bird-icon.html

- Background Music:
https://instrumentalfx.co/angry-birds-theme-song-download/

- PoachedEgg Theme png:
https://www.deviantart.com/yoshibowserfanatic/art/Angry-Birds-Poached-Eggs-Theme-I-Background-406945305

- Splash Screen Theme png:
https://www.spriters-resource.com/mobile/angrybirds/sheet/147559/

- Catapult png:
https://www.pngwing.com/en/free-png-ncdva

- Blocks and decors:
https://www.spriters-resource.com/pc_computer/angrybirdsrio/sheet/113160/

- Stars png:
https://pngtree.com/freepng/star-rating-steps-concept-with-feedback-rating_3605242.html

- All birds, pig, and other ingame sprites have been mainly taken from:

https://www.spriters-resource.com/mobile/angrybirds/

- Specifically from the following sites:

https://www.spriters-resource.com/mobile/angrybirds/sheet/66439/

https://www.spriters-resource.com/mobile/angrybirds/sheet/59982/

https://www.spriters-resource.com/mobile/angrybirds/sheet/171792/

https://www.spriters-resource.com/mobile/angrybirds/sheet/159180/

- Some sprites were taken from this spritesheet:

https://www.reddit.com/r/angrybirds/comments/ufn5wf/hhh/#lightbox


- Online resources which we referred to while building this project:

https://libgdx.com/wiki/

https://www.youtube.com/playlist?list=PLXY8okVWvwZ0JOwHiH1TntAdq-UDPnC2L

https://www.youtube.com/playlist?list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt

A huge shoutout to all of them to help make this project possible!
