# Assignment 1

Word count: 1827 words

## Introduction									
Author(s): Eduardo Lira

Inspiration: [Original Zork game](https://textadventures.co.uk/games/play/5zyoqrsugeopel3ffhz_vq)

For our software project, we have decided to recreate the game Zork. Zork is one of the first Command Line Interface (CLI), role playing games (RPG). In Zork, a user assumes the role of a protagonist stuck in a post-apocalyptic world. To win the game, the protagonist must find all 20 treasures, and place them in the trophy case. Our recreation of Zork will remodel the game play with: a new setting, a new goal, new commands etcetera. 

After a plethora of discourse with the team, we have decided that our game, titled: **VUORK: Saving Thilo from the Chamber of Secrets**, will take place at the Vrije Universiteit Amsterdam. The user assumes the role of a newly enrolled student (user may choose the name) on his way to a 9AM lecture of Computer Programming. Albeit new, when the user arrives at the main campus of the VU, he notices something is wrong… This is where the game begins. 

After a brief introduction into the game, the user will discover he is alone at the VU, and his lecturer, Thilo, is lost in the Chamber of Secrets. He will then have to gather the puzzle pieces, each with a clue indicating where the next piece of the puzzle is, to find the chamber of secrets; where Thilo is held captive. 

To interact with the VUORK world, the user will use text commands in the CLI to give instructions. The instruction will be in the form “<action> <items…*>”. We will create a parser which will transform that instruction into something our system can understand: If the user types in a command, describing what he is doing/what he wants to do, he/she will get a response from the computer what she/he is now able to see, what has changed or if the move is not valid. Furthermore, instead of having a boring CLI as the realm, we want to add a few Graphical elements into the game. Examples include the players inventory, the current areas explored etc...

With the game briefly explained, we must now consider how to develop this idea in a way that is easily extendable, maintainable and comprehensible. By using the Object-Oriented programming paradigm, we aim to create a system that is easily modifiable, expandable and intuitive for programmers to understand. To help achieve this goal, we will be making use of UML diagrams. 

   				   Figure 1: A use case diagram of VUORK

![VuORK use case diagram](https://i.postimg.cc/SRBV1RM5/Screen-Shot-2020-03-01-at-6-40-11-PM.png)

The remainder of this paper will cover detailed features and quality requirements to help us structure this project (This should be self explanatory). 

**NB**: THE SYSTEM WILL BE INDEPENDENT FROM THE STORY LINE OUTLINED ABOVE. The idea is to create a system that can be *easily* adapted to any other storyline and easy to expand. 

## Features
Author(s): Marta Jansone, Theresa Schantz, Eduardo Lira, Irene Garcia-Fortea Garcia, Florent Brunet de Rochebrune 

### Functional features

As a preamble to the table, you can discuss the main line of reasoning you applied for defining the functional features provided in the table.

| ID   | Short name | Description                                                  |
| ---- | ---------- | :----------------------------------------------------------- |
| F1   | Commands   | The player will interact with the game using the command line interface. It is, thus, intrisically important to have a parser that will understand some simple semantics of langauge. For example, a command such as *"north"* should be equivilant to *walk north*. <br> After careful consideration, we have decided that our parser must have the input in the form of *<action\> <item(s)/objects(s) [proposition]...\*>*. An item may be preceeded by a proposition. For example, *"Put cheese in fridge"* or *"Attack bear with knife"*. The action put, will let the parser know that there will be 2 items, interupted by some proposition. Other actions, however, may exclude a propostion. An example is *"Take broom"* or *"Move north"*.<br>Emposing this structure will make it possible to parse natural language to something our system can understand. If the user, for any reason, excludes a second item in a 2-part command (such as attack), then the system will reply with *"What do you want to attack the <object\> with?"*, if the item is ommited. Or *"What do you want to attack?"*, if an object is omitted. If an invalid propositon is used, for example *"Attack monkey on knife"*, the parser should reply, *"I do not understand how to Attack monkey on knife"*<br> |
| F2   | Movements  | The player must move around the virtual environment using *move* followed by a direction: *North, East, West, South*. These command will move the player to the next virtual area. |
| F3   | Narrator   | The narrator is a way for the system to interact with the user: Let the user know what is going on. The narrator will describe the *field of view* of the player at any given time when: Moving between rooms, the command info is received. We define *field of view* as what all objects and properties of the immediate area around the user. That is, the area the user is currently in. For example, if the player is in a room that is dark, there is only an entrace to the west, and in this area, there lies a fridge, broom and coffee machine. The game would narrate *"You are in a dark room. The the west you see an opening to another area. In this room, you see a fridge, broom and coffee machine"*.  <br>There are also other commands available, which will have a special naration. For example, when picking up and object, the narrator will reply with a simple confirmation, simply repeating the action. If the action fails, the system should reply accordingly. <br>The command *diagnose* will trigger the narrator to list the health of the player. For example, you are wounded on your left hand, 80% health remaining<br>The command *inventory* will list all of the contents, without any specific ordering, the player currently has in their posession. |
| F4   | Game-info  | The player can get the statistics with the command *stats*. The statistics include, time being played, current score (see F7). Furthermore, information about the current gamestate can be achieved with the use of special commands. See F3 for more info on these. |
| F5   | Map        | A list that keeps track of the areas already visited. Quite self explanatory. **Bonus:** Include an option to add a graphic for each item which can then be displayed as a backpack. |
| F6   | Backpack   | A list that keeps track of the items that have been picked up already. Also quite self explanatory. **Bonus:** Include an option to add a graphic for each item which can then be displayed as a backpack. |
| F7   | Score      | The score will be an integer value which will be a numeric representation as to how well the player will is playing. It is calculated by summing the move with the total damage taken. Lower is better, much like in golf. |
| F8   | Moves      | An integer that shows the number of moves at the top of the game as part of the text-based GUI. Always updated after an action. This is used immediatly display to the user how many steps the user has taken (and thus, how efficiently he is completing the game in). |
| F9   | Timer      | A clock that shows the time that has already been passed since the start of the game. This will be in real time, just a little gimmick to add :) |
| F10  | Rooms      | There is a specific number of rooms that the player can enter, each resembling a room at the VU. |
|      |            |                                                              |

### Quality requirements

Author(s): Eduardo Lira, Irene Garcia-Fortea Garcia, Florent Brunet de Rochebrune

| ID  | Short name  | Quality attribute | Description  |
|---|---|---|---|
| QR1  | Check valid input | Correctness | Check if the move the user wants to do is possible at that time of the game and check whether the input was given in the correct syntax. If the command does not meet the requirement: an error message must be printed and/or the user must be asked to give a new input. |
| QR2 | Check valid command | Correctness | If the user gives a command that is legal, but is not applicable at the current state, the sytem should ALWAYS respond, in some form, that the input was correct, just not allowed in the current state of the game. |
| QR3 | Quick     responses to user input     | Responsiveness | The system must always respond to any input. It shall, thus, not be possible for the user to be able to input 2 seperate commands without a response from the system. The system should respond immediatly. |
| QR4 | Extensible world | Maintainability | The code should be well structured and organized; and the universe should not be closed. It should be an open story plot so more levels can be added later. Readable code will make it easier to implement such. |
| QR5 | User friendly | Usability | An introduction should explain how to play the game and how to give commands. The syntax shall be intuitive for any player. The help of text-based GUI will help give a visual display of current inventory, current layout of the explored map etc. |
| QR6 | Always respond to user inputs | Reliability | All commands shall be idempotent; If a player drops an item, that item shall be dropped. |
| QR7 | Available actions | Availability | When an action is possible, it shall be able to be executed when requested by the user. |
| QR8 | User privacy & System security | Security | Not collecting any personal userdata. GDPR compliant. |

### Java libraries
Author(s): Marta Anna Jansone, Irene Garcia-Fortea Garcia

| Name (with link) | Description  |
|---|:-:|
| [JFoenix](http://www.jfoenix.com/)  | Graphical interface. Used for styling and creating the user interface window. We are choosing it as it supports material design and is easy to customize. It includes such features as input fields, buttons and text fields, all of which are required in our game implementation. |
| [StringUtils](https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html) | (STANDARD LIBRARY) Comparing strings. We will use this library to process user input and compare the input commands to the allowed commands stored in our game database (JSON). We choose to use this library as the strings can easily be compared using the ”==” sign. ... |
| [fastjson ](https://github.com/alibaba/fastjson) | We will use it for reading JSON files that will contain the room descriptions in the game and the allowed commands, which the player can make. We chose this library as it provides functions to parse JSON objects to string for further string comparisons. The library also provides good documentation on GitHub. |
| [Scanner](https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html) | We will use this library for acquiring user input. User input is required for the player to make moves and pick up objects when traversing the game. |
| [JUnit ](https://junit.org/junit5/) | This library will be used to perform testing on our game. We choose this library as it is possible to easily set-up unit tests to test the performance of small parts of the game, for example, acquiring and comparing user input. |

