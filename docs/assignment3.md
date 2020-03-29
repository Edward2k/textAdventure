# Assignment 3

###Summary of changes of Assignment 2

The first feedback to address was the introduction (from the feedback of Assignment 1). More information, about the game *Zork* had to be added. Therefore, the first step for us was to further elaborate on  the games mechanics and objectives.

Second, the features, and quality requirements had to be added. ...//TODO

Third, unused variables and functions inside the code were removed, and error messages for the user were added. A newly implemented message is the one telling tha player that the given instruction is too long. 

Fourth, still haning around from the feedback of assignment 1, the use case diagram was renewed. We hope it is correct now :-).


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
