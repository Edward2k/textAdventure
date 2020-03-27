# Assignment 3

###Summary of changes of Assignment 2

The first feedback to address was the introduction (from the feedback of Assignment 1). More information, about the game *Zork* had to be added. Therefore, the first step for us was to further elaborate on  the games mechanics and objectives.

Second, the features, and quality requirements had to be added. ...//TODO

Third, unused variables and functions inside the code were removed, and error messages for the user were added. A newly implemented message is the one telling tha player that the given instruction is too long. 

Fourth, still haning around from the feedback of assignment 1, the use case diagram was renewed. We hope it is correct now :-).

### Introduction									
Author(s): Eduardo Lira

Inspiration: [Original Zork game](https://textadventures.co.uk/games/play/5zyoqrsugeopel3ffhz_vq)

For our software project, we have decided to recreate the game Zork. Zork is one of the first Command Line PlayerClient.Interface (CLI), role playing games (RPG). The game is played through the terminal interface, by typing in text commands. There is no such as a graphical user interface with fancy graphics, only text. 

In Zork, a user assumes the role of a protagonist stuck in a post-apocalyptic world. The game starts at an empty house in the forest, and the player must find a way to get inside. After finding a way, the real adventure begins. Items must be collected, trolls must be fought, and rugs must be moved(, etc.). The commands to do so can be given by instructions, such as "move north", "take sword", "trun on lamp", "move rug", etc. To win the game, the protagonist must fight its way through *Zork* world in your terminal, colored by your imagination, in order to find all 20 treasures, and place them in the trophy case. 
Our recreation of Zork will remodel the game play with: a new setting, a new goal, new commands etcetera. 

After a plethora of discourse with the team, we have decided that our game, titled: **VUORK: Saving Thilo from the Chamber of Secrets**, will take place at the Vrije Universiteit Amsterdam. The user assumes the role of a newly enrolled student (user may choose the name) on his way to a 9AM lecture of Computer Programming. Albeit new, when the user arrives at the main campus of the VU, he notices something is wrong… This is where the game begins. 

After a brief introduction into the game, the user will discover he is alone at the VU, and his lecturer, Thilo, is lost in the Chamber of Secrets. He will then have to gather the puzzle pieces, each with a clue indicating where the next piece of the puzzle is, to find the chamber of secrets; where Thilo is held captive. 

To interact with the VUORK world, the user will use text commands in the CLI to give instructions. The instruction will be in the form “<action> <items…*>”. We will create a parser which will transform that instruction into something our system can understand: If the user types in a command, describing what he is doing/what he wants to do, he/she will get a response from the computer what she/he is now able to see, what has changed or if the move is not valid. Furthermore, instead of having a boring CLI as the realm, we want to add a few Graphical elements into the game. Examples include the players inventory, the current areas explored etc...

With the game briefly explained, we must now consider how to develop this idea in a way that is easily extendable, maintainable and comprehensible. By using the Object-Oriented programming paradigm, we aim to create a system that is easily modifiable, expandable and intuitive for programmers to understand. To help achieve this goal, we will be making use of UML diagrams. 

   				   Figure 1: A use case diagram of VUORK

![VuORK use case diagram](https://i.postimg.cc/SRBV1RM5/Screen-Shot-2020-03-01-at-6-40-11-PM.png)

The remainder of this paper will cover detailed features and quality requirements to help us structure this project (This should be self explanatory). 

**NB**: THE SYSTEM WILL BE INDEPENDENT FROM THE STORY LINE OUTLINED ABOVE. The idea is to create a system that can be *easily* adapted to any other storyline and easy to expand. 


### Implemented feature

| ID   | Short name | Description                                                  |
| ---- | ---------- | ------------------------------------------------------------ |
| F1   | Comands    | The player will interact with the game using the command line interface. It is, thus, intrisically important to have a parser that will  understand some simple semantics of langauge. For example, a command  such as *"north"* should be equivilant to *walk north*. <br/> After careful consideration, we have decided that our parser must have the input in the form of * *. An item may be preceeded by a proposition. For example, *"Put cheese in fridge"* or *"Attack bear with knife"*. The action put, will let the parser know that there will be 2 items,  interupted by some proposition. Other actions, however, may exclude a  propostion. An example is *"Take broom"* or *"Move north"*.<br/>Emposing this structure will make it possible to parse natural language to  something our system can understand. If the user, for any reason,  excludes a second item in a 2-part command (such as attack), then the  system will reply with *"What do you want to attack the  with?"*, if the item is ommited. Or *"What do you want to attack?"*, if an object is omitted. If an invalid propositon is used, for example *"Attack monkey on knife"*, the parser should reply, *"I do not understand how to Attack monkey on knife"* |
| F2   | Movements  | The player must move around the virtual environment using *move* followed by a direction: *North, East, West, South*. These command will move the player to the next virtual area. |
| F3   | Narrator   | The narrator is a way for the system to interact with the user: Let the  user know what is going on. The narrator will describe the *field of view* of the player at any given time when: Moving between rooms, the command info is received. We define *field of view* as what all objects and properties of the immediate area around the  user. That is, the area the user is currently in. For example, if the  player is in a room that is dark, there is only an entrace to the west,  and in this area, there lies a fridge, broom and coffee machine. The  game would narrate *"You are in a dark room. The the west you see an  opening to another area. In this room, you see a fridge, broom and  coffee machine"*.  <br/>There are also other commands available,  which will have a special naration. For example, when picking up and  object, the narrator will reply with a simple confirmation, simply  repeating the action. If the action fails, the system should reply  accordingly. <br/>The command *diagnose* will trigger the narrator to list the health of the player. For example, you are wounded on your  left hand, 80% health remaining<br/>The command *inventory* will list all of the contents, without any specific ordering, the player currently has in their posession. |
| F10  | Rooms      | There is a specific number of rooms that the player can enter, each resembling a room at the VU. |

### 

### Used modeling tool

We are using draw.io

###
