# SYSC3110DesignProject

<p align="center">
  <img src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn-www.bluestacks.com%2Fbs-images%2Flogo239.png&f=1&nofb=1" />
</p>

### How to Install
User must have java JDK installed to run the program.
Game has been optimized to run on Windows and some features maynot function on Mac & Linux

1. Download ZIP file
2. Extract ZIP
3. Launch SYSC3110DesignProject.jar


The goal of this team project is to reproduce a simplified version of the classic strategy game RISK.


### Group 3

Authors

<p align="left">
  <img style= "vertical-align:middle" src="https://avatars0.githubusercontent.com/u/71227923?s=460&u=7eedc11732df85f6a08674179e0aab7b8496dcfd&v=4" width="50" height="50" />
    <a href="https://github.com/aelsammak">@aelsammak</a>
</p>
<p align="left">
  <img style= "vertical-align:middle" src="https://avatars1.githubusercontent.com/u/71864216?s=460&u=c6e4bb16b43a450708bcbcde6b76ede693bc7090&v=4" width="50" height="50" />
    <a href="https://github.com/Erik-Iuhas">@Erik-Iuhas</a>
</p>
<p align="left">
  <img style= "vertical-align:middle" src="https://avatars3.githubusercontent.com/u/47836939?s=460&u=5c135ae66ac6db71ca7d83a05209cc3db690716a&v=4" width="50" height="50" />
  <a href="https://github.com/nikolaspaterson">@nikolaspaterson</a>
</p>

TA(Carleton's Best)

[@MikeVezina](https://github.com/MikeVezina)


### Project Milestones

- [x] Milestone 1: Create a text-based playable game.
- [x] Milestone 2: Make game GUI based.
- [x] Milestone 3: Implement AI players and many more features!
- [x] Milestone 4: Save/load game and custom maps

### Milestone 1 Deliverables
1. Readme :white_check_mark:
2. Source code + jar executable :white_check_mark:
3. UML Diagram :white_check_mark:
4. Documentation :white_check_mark:

The readme file is used to describe our project and the roadmap ahead.
The source code consists of classes that work work together to make our text based game playable.
The UML diagram displays how our classes interact with one another.
We documented all of our code and provided a Word Document describing our choice in data structures.

### Milestone 2 Deliverables
1. Readme :white_check_mark:
2. Design file :white_check_mark:
3. Unit tests :white_check_mark:
4. Source code :white_check_mark:
5. Documentation :white_check_mark:

### Milestone 3 Deliverables
1. Readme :white_check_mark:
2. Source code :white_check_mark:
3. Unit tests :white_check_mark:
4. Refined design :white_check_mark:
5. Removed 'smelly code' :white_check_mark:


### Milestone 4 Deliverables
1. Readme :white_check_mark:
2. Source code :white_check_mark:
3. Unit tests :white_check_mark:
4. Documentation :white_check_mark:


### Changes Made
#### Milestone 1
There were many changes made to this iteration of the progejct. Our UML was revised several times, classes were created, deleted and refactored and many bugs were squashed. Our group also changed, we unfortuantely had a member drop the course resulting in the remaining members to absorb a little extra work. We were able to fix many bugs and after long hours in voice calls we created a playable textbased version of RISK.

#### Milestone 2
In Milestone 2, we had to update the entire game to be GUI based so we went out to achieve the best looking game of RISK ever made, with 3 playable character in the roster and more to come. This version of the game has an easy to use interface which allows for players to clearly identify their owned territories and enemies with ease. Along with background music and a winning track, the game is bound to get Game of the Year.  

#### Milestone 3
Are you smarter than our AI? Thats right, in the third milestone we added an ai player that you can compete against. We also added implemented unit testing, this means we were able to fix any bugs that I player may abuse to get an unfair advantage.

#### Milestone 4
We have allowed users to save their game so they can resume playing at a later time. We also implemented custom maps, users can create maps as a JSON file which can be loaded into our game and played. An example JSON map can be seen in our source code.

### Known Issues
There still arent any known issues in our code. 

### Deliverables
**Old Scripts**

| Script | Author(s) |
| --- | --- |
| Command | Nikolas Paterson |
| CommandEnum | Nikolas Paterson |
| CommandParser | Nikolas Paterson |
| CommandWord | Nikolas Paterson |
| Game | Erik Iuhas, Nikolas Paterson |

**Models**

| Model | Author(s) |
| --- | --- |
| Dice | Ahmad El-Sammak |
| GameEvent | Ahmad El-Sammak |
| Player | Ahmad El-Sammak, Erik Iuhas |
| Game Model | Erik Iuhas, Nikolas Paterson, Ahmad El-Sammak |
| Territory | Ahmad El-Sammak, Erik Iuhas |
| Continent | Erik Iuhas, Nikolas Paterson, Ahmad El-Sammak |
| AIPlayer | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| AttackResult | Ahmad El-Sammak |
| AITimer | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |


**Views**

| View | Author(s) |
| --- | --- |
| GameView | Erik Iuhas |
| GameSetup | Erik Iuhas |
| StatusBar |	Erik Iuhas |
| AttackPopUp |	Ahmad El-Sammak |
| PlayerView | Erik Iuhas | 
| BackgroundPanel | Erik Iuhas |
| TerritoryButton |	Ahmad El-Sammak, Erik Iuhas |
| FortifyPopUp	| Nikolas Paterson |
| StartUpView |	Nikolas Paterson |
| PlayerSelectView |	Ahmad El-Sammak |
| PlayerSelectPanel	| Ahmad El-Sammak |
| ReinforcePopUp	| Nikolas Paterson |
| WinningScreenFrame | Ahmad El-Sammak|

**Controllers**

| Controller | Author(s) |
| --- | --- |
| AttackPopUpController | Ahmad El-Sammak  |
| FortifyPopUpController | Nikolas Paterson |
| GameController | Erik Iuhas |
| PlayerSelectController | Ahmad El-Sammak  |
| ReinforcePopUpController | Nikolas Paterson |
| StartUpController | Nikolas Paterson |
| TerritoryButtonController | Ahmad El-Sammak, Erik Iuhas|
| JButtonActionCommands | Ahmad El-Sammak |
| MapSelectController | Ahmad El-Sammak |
| SaveController | Erik Iuhas |

**Unit Tests**

| Test | Author(s) |
| --- | --- |
| GameEventTest | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| GameModelTest | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| MockDice | Ahmad El-Sammak |
| MockGameEvent | Ahmad El-Sammak |
| SaveTest | Nikolas Paterson |
| MockSave | Erik Iuhas, Nikolas Paterson |

**Resources**

| Resource | Author(s) |
| --- | --- |
| TerritoryNeighbours.csv | Erik Iuhas |
| Map.png | Erik Iuhas |
| Chizzy.png | Erik Iuhas |
| TA.png | Erik Iuhas |
| Captain.png | Erik Iuhas |

**Event**

| Event | Author(s) |
| --- | --- |
| PlayerEvent | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson | 
| UserStatusEvent | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson | 
| TerritoryEvent | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| SaveEvent | Erik Iuhas, Nikolas Paterson | 

**Listener**

| Interface | Author(s) | 
| --- | --- |
| PlayerListener | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| TerritoryView | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson | 
| UserStatusListener | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| SaveEvent | Erik Iuhas, Nikolas Paterson |

**JSONModels**

|JSON Model | Author(s) |
| --- | --- |
| JSONAIPlayer | Erik Iuhas |
| JSONContinent | Erik Iuhas, Ahmad El-Sammak|
| JSONContinentKeys | Erik Iuhas, Ahmad El-Sammak |
| JSONGameModel | Erik Iuhas |
| JSONGameModelKeys | Erik Iuhas, Ahmad El-Sammak |
| JSONMap | Erik Iuhas |
| JSONMapKeys | Erik Iuhas, Ahmad El-Sammak |
| JSONMapTerritory | Erik Iuhas , Ahmad El-Sammak|
| JSONPlayer | Erik Iuhas |
| JSONPlayerKeys | Erik Iuhas |
| JSONTerritory | Erik Iuhas |

**Documentation**

| Document | Author(s) |
| --- | --- |
| README | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| UML Diagram | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| Sequence Diagrams | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |
| Write Up | Ahmad El-Sammak, Erik Iuhas, Nikolas Paterson |

### Project Roadmap
The project is complete and archived so if you would like to modify the code you must fork the project. This project was a great learning experience and it was a true pleasure to work with such a strong and devoted team. Many skills were learned and applied while designing this game, including but not limited to team work & communication, refining & refactoring code base, testing and documenting code. Many of these skills will be very useful when applied to a real world project.

### Copyright matters
If you are interested in our game please check out its original inspiration, [Risk: Global Domination](https://store.steampowered.com/app/1128810/RISK_Global_Domination/).
We are created this game for educational purposes which falls under fair use.
If you have concerns about copyrighted material please contact one of the project creators.
