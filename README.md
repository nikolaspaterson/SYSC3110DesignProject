# SYSC3110DesignProject

<p align="center">
  <img src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn-www.bluestacks.com%2Fbs-images%2Flogo239.png&f=1&nofb=1" />
</p>

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
- [ ] Milestone 2: Make game GUI based.
- [ ] Milestone 3: Implement AI players and many more features!
- [ ] Milestone 4: Save/load game and custom maps

### Milestone 1 Deliverables
1. Read.me :white_check_mark:
2. Source code + jar executable :white_check_mark:
3. UML Diagram :white_check_mark:
4. Documentaion :white_check_mark:

The read.me file is used to describe our project and the roadmap ahead.
The source code consists of classes that work work together to make our text based game playable.
The UML diagram displays how our classes interact with one another.
We documented all of our code and provided a Word Document describing our choice in data structures.

### Milesstone 2 Deliverables
1. Read.me :x:
2. Design file :x:
3. Unit tests :x:
4. Source code :x:
5. Documentation :x:

### Milestone 3 Deliverables
1. Read.me :x:
2. Source code :x:
3. Unit tests :x:
4. Refined design :x:
5. Removed 'smelly code' :x:


### Milestone 4 Deliverables
1. Read.me :x:
2. Source code :x:
3. Unit tests :x:
4. Documentation :x:


### Changes Made
There were many changes made to this iteration of the progejct. Our UML was revised several times, classes were created, deleted and refactored and many bugs were squashed. Our group also changed, we unfortuantely had a member drop the course resulting in the remaining members to absorb a little extra work. We were able to fix many bugs and after long hours in voice calls we created a playable textbased version of RISK. 

### Known Issues
There arent any know issues in our code BUT we did make a couple assumptions about how the players will play the game.
We assumed that each player will,
1. Deploy all available troops(can be in many territories)
2. Attack, players can attack several times each turn
3. Players should only fortify ONCE.

### Text Based User Input Commands
The user will play the game by typing these commands into the interface.
1. **help** - prints availiable list of commands
2. **fortify** - fortify is for moving troops from one owned territory to another. Example: **fortify Ontario Quebec 4**
3. **worldmap** - prints out the entire state of the map and each players territories
4. **reinforce** - reinforce is used to place deployableTroops on owned territoreis at the start of each turn. Example: **reinforce Ontario 3**
5. **attack** - attack is used for attacking an enemy territory, you can only use 1-3 as dice rolls and you provide your territory first then the one you would like to attack next. Example: **attack Ontario EasternUnitedStates 3**
6. **mymap** - prints out the current player's 
7. **quit** - closes the game
8. **skip** - skips the current player turn to go to the next one

### Deliverables

**Scripts:**<br>
Command - Nikolas Paterson<br>
CommandEnum - Nikolas Paterson<br>
CommandParser - Nikolas Paterson<br>
CommandWord - Nikolas Paterson<br>
Dice - Ahmad El-Sammak<br>
Game - Nikolas Paterson, Erik Iuhas<br>
GameEvent - Ahmad El-Sammak<br>
GameSetup - Erik Iuhas<br>
Player - Ahmad El-Sammak<br>
Territory - Ahmad El-Sammak<br>
**Files:**<br>
TerritoryNeighbours.csv - Erik Iuhas<br>
**Documents:**<br>
README -Ahmad El-Sammak, Nikolas Paterson, Erik Iuhas<br>
UML Diagram - Ahmad El-Sammak, Nikolas Paterson, Erik Iuhas<br>
Sequence Diagrams - Ahmad El-Sammak, Nikolas Paterson, Erik Iuhas<br>
Documentation - Ahmad El-Sammak, Nikolas Paterson, Erik Iuhas<br>


### Project Roadmap
Milestone 2! In the second milestone we are required to convert the game from text based to GUI based and implement Unit Testing. We are looking forward to 
adding GUI because it will make playing the game a lot more enjoyable. Adding unit testing will also help us save a lot of time because the testing will be automated instead of us constanly playing the game while testing for unexpected behaviour.


### Copyright matters
If you are interested in our game please check out its original insperation, [Risk: Global Domination](https://store.steampowered.com/app/1128810/RISK_Global_Domination/).
We are creating this game for educational purposes which falls under fair use.
If you have concerns about copyrighted material please contact one of the project creators.

See you again when we have completed Milestone 2! :metal:


