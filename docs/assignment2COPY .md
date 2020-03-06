# Assignment 2

Maximum number of words for this document: 12000

**Format**: establish formatting conventions when  describing your models in this document. For example, you style the name of each class is *Italics*, whereas the attributes, operations, and  associations as <u>underlined</u> text, objects are in italic, etc.

###

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

## Class diagram

Author(s): Eduardo Lira & Theresa Schantz

This chapter contains the specification of the UML class diagram of your system, together with a textual description of all its elements.

```
Class Diagram of VuORK

```

> Figure representing the Class Diagram of VuORK (Diagram 1) **DESCRIPTIVE**

![Class Diagram of VuORK](https://i.postimg.cc/DZX98JfB/Class-diagram-Class-diagram.png)

<h5 id="Item">Item</h5>

The Item class is an abstract type which can be either a _Container_ or a _BasicItem_. The Item class represents an entity the user can interact with. Arbitrary examples may be: *swords, spoons, pens, etc..*. This class is abstract because an Item can be (exclusivly) one of two types: *Container* or *BasicItem*. 

<u>*Name*</u> is a string that gives the entity a name. Pretty self explanatory. It is needed so that the game can tell the user (in words), what the item represents.

<u>*ID*</u> is a uniquie integer which will differentiate all objects. This is needed to tell apart two objects with the same *Name*. 

*<u>getName()</u>* returns the name of the Item

<u>*getID()*</u> returns the ID of the item

Item has a composition relation to (exclusivley) *Area* or a *Player*. This is because an Item can not both exist outside of the *Player's backpack* and in the surrounding area or vice versa. Furthermore, an Item can not exist without an *Area* or *Player's backpack* to exist in. 

<h5 id="BasicItem">BasicItem</h5>

Singelton is a subtype of <a href="#Item">Item</a> that a player can interact with. It is the most basic type of item. It inherits properties from Item, such as the Name and ID and their consequent getter functions. 

An Item can be interracted with, or used to interract. To elaborate, imagine the command ***Attack chair with sword***. Here, the action attack has 2 clauses: the dependent and independent. This means that BasicItems need a way to know what can be done to it (ie be attacked), and what it can be used to do (ie attack). In this case, the BasicItem chair would have the string *Attack*, in its *UsedTo*.

*<u>canBe</u>* is an array of strings. These strings list what can be done to the item. Taking the example of the chair and sword from above, the chair would have the string "Attack", in this array. 

*<u>usedTo</u>* is an array of strings. These strings list what the item can be used to do. Taking the example of the sword from above, the sword would have the string "Attack", in this array.

*<u>isValidAction(string action)</u>* returns a boolean on the condition that the array usedTo contains the string action. If it does contain the string, it returns true. 

*<u>isValidManipulation(string manip)</u>* returns a boolean on the condition that the array usedTo contains the string action. If it does contain the string, it returns true. 

Because BasicItem is a sub-type of *Item*, it follows the same matually exclusive composite relation seen Item has. The aggregate relationship indicates that any number of *BasicItem* objects, may be contained in a *Container*. 

<h5 id="Container">Container</h5>

Container is a subtype of Item. It is different from a BasicItem because the only action that can be performed on a container is the *<u>toggle(state: boolean)</u>* Also, you can see that a container may contain any number of BasicItems. The idea is that a container may be, to bring a realworld example, a (but not limited to) a fridge or drawer. These can be opened and reveal any number of items. 

*<u>isOpen</u>* is a boolean variable that checks wether the contents of the container are available to the surrounding *Area* and, thus, *Player*. The entities within the *Container* can not be accessed unless this condition is true. 

*<u>entities</u>* is an array of *BasicItem*. It lists all the *BasicItem* currently contained in the item. 

*<u>toggle(state:bool)</u>* is a method function which will open/or close the container. If state is TRUE, then *<u>isOpen</u>*, will be set to TRUE.

It is important to note the aggregation from *BasicItem*. This simply indicates that an instance of *Container* may contain any number of *BasicItem* objects, but the existance of *BasicItem* objects does not relay on the existance of the *Container* object.

<h5 id="Area">Area</h5>

Area is a class which represents a region any *Player* can exist in. As a realworld example, this could be a classroom, a bathroom, kitchen etc. We assume that when a *Player* is in a region, he/she can immediatly interact with the objects in the field of view of that room. This means that, if there is a fridge and a pen on the floor, the *Player* can open the fridge for pick up the pen without having to move inside the room.

*<u>items: array <Item\></u>* is an array of items that holds the contents of the room. 

*<u>obstacle: Item</u>* is an *Item* which must first be destroyed before a *Player* can enter and explore the *Area*.

*<u>obstacleNeutrelized: Boolean</u>*:  is a condition to see if the Obstacle was properly handled. Imagine there was a Troll in the room, preventing us from exploring or entering the room. We must first kill the Troll before entering. 

*<u>name: string</u>* is a string that stores the name of the *Area*. 

*<u>description: String</u>* is a description of the *Area*, and example is "it is a dark room. You can't see which way to go, but you feel a breeze north of you."

*<u>getDescription(): String</u>* will print a description.

*<u>canEnter(): boolean</u>* returns the <u>obstacleNeutrelized</u> private member. 

*<u>getName(): String</u>*:  returns the name private member of the room.

*<u>addItem(): Void</u>* adds an *Item* to <u>items</u>

*<u>removeItem(String): Bool</u>* removes an *Item* from *items*, that has a name that matches String

*<u>getItems(): List<String\></u>* returns *items*.

*Area* has two direct relations. It has an (exclusive or) relation from *Item*. Why this is, is further described in <u><a href="#Item">Item</a></u>. An Area also has a composite relation to the class *Map*. This is because the existance of *Area* is entirely dependent of the existance of *Map*.

<h5 id="Map">Map</h5>

The *Map* class holds all the references to *Area* together, in some emposed ordering. The ordering is defined upon instatiation of the class. This is because an *Area* does not, conceptually, know where it is in a *Map*. That is the purpose of the *Map*. It is important to note that the *Map* must be a square.

<u>*gameLayout: Array<Array< Area> >*</u> is a 2-Dimensional array which holds references to *Area* objects. 

*<u>entryPoint: Coordinate</u>* is *Coordinate* which gives the starting point of the *Map*

*<u>mapSize: Int</u>* is the size of the map. This value must be greater than 0. 

*<u>isValidMove(Coord: Coordinate): Boolean</u>* is a simple function to check wether the given position is valid. That is, is the position given by the *Coordinate* not a NULL pointer. 

*<u>getDescription(Coordinate): String</u>* is a getter function that returns the description of the *Area* denoted by *Area*. If Coordinate is not an *Area*, this function will return, "not an Area".

*<u>getEntryPoint(): Coordinate</u>* returns entryPoint.

The relations *Map* has is quite simple. *Map* has a aggregate relation to *Game*. This means that an instance of *Map* can exist a *Game*, but the multiplicity of 1, means that at any time, there can be at most 1 *Map* to a *Game* object. 

<h5 id="Game">Game</h5>

The *Game* class is what runs any instance of a a running game of VuORK. It glues together all of the higher-order class described in this section. It is important to note that VuORK can be multiplayer, which heavily influneced this structure [Futher described in Player].

*<u>users: Player</u>* holds all of the players in the current instance of *Game*. This is an array. 

*<u>time: timestamp</u>* is an object of type timestamp holding the time the game started. timestamp is part of the standard library for Java. 

*<u>layout: Map</u>*: holds the object for *Map*. This is the playarea for *Player* objects to explore. 

*<u>getGameState(): String</u>* is a function which will return a string containing the high-level overview of the game. 

*<u>executeCommand(Instruction)</u>* is a function that will execute a command, that is received from a *Player*. It is important to note that the whole system is multiThreaded, and for every instance of a *Player* in a *Game*, a thread is opened to read and execute instructions from that *Player*. Each thread will handle all of the instructions given by any *Player*. The thread will call *Player.getInstruction()*, and wait for that to execute. Once a *Player* has entered the *Instruction*, the *Game* will execute that instruction, and *Print* to the player what the result was. 

*<u>runPlayer</u>*<u>(Player)</u> is what will handle any player. When a *Player* joins the *Game*, *Game* will open a new socket or thread (depending on wether the game is networked or not), and handle that *Player* in the thread/socket. This function calls *Player.getInstruction()*.

*<u>startGame(): Void</u>* initiates the *Game* by declaring user size, *timestamp* and getting the layout. 

The relationships to *Game* are very specific. However, these relations are further described in *Map* and *Player*. At a highlevel, a *Game* can have at most 1 map, but can have any posotive number greater than 1 of *Players*. 

<h5 id="Player">Player</h5>

A *Player* is the class that a User will take control of. The user interacts to the *Player* using the *Parser* [Further described in *Parser*]. A *Player* is a class which also enables the user to explore the world. 

*<u>name: String</u>* is a string which holds the name the user first put in when logging in to VuORK. This must be unique from all other users in the *Game* object. 

*<u>coord: Coordinate</u>* stores where the player currently is in terms of the *Map*. It should be instatiated to the specified *Map.getEntryPoint()*. 

*<u>backpack: Array<BasicItem\></u>* stores references to all of the *Items* the *Player* currently has in his possesion. Instantiated to NULL. It is important to note that this array is of type *BasicItem* and not its superclass, *Item*, because holding *Containers*, would allow for infinite storage. 

*<u>score: Int</u>* is an integer indicator denoting how well the player is playing. It is calculated by taking the number of <u>moves</u> * (number of minutes played) * *health*

*<u>moves: Int</u>* is an integer which counts the number of commands the user has inputted. 

*<u>health: Int</u>* is an integer holding the health of the *Player*. It is between 0 (dead) and 100 (full health).

<u>instruction: String</u> is a String of the last instruction held by the *Player*.

*<u>getUserName(): String</u>* returns <u>name</u>

*<u>getHealth(): Int</u>* returns *health*

*<u>getBackpack(): String</u>* returns a formatted string of items in <u>backpack</u>

<u>movePlayer(coord: Coordinate): Bool</u> will move a player iff the condition *Map.isValidMove(coord: Coordinate)* is true.

*<u>getCurrentPosition(): Coordinate</u>* returns the value of <u>coord</u>

*<u>getInstruction</u>()*, will return the ask *getInstruction* from *Parser* assosciated to the *Player*. 

*<u>setName(String): Void</u>* sets the value of <u>name</u> to String.

The relations of Player are a little complex. A player must contain a *Parser* and a *Printer* (equally, these 2 must can not exist without a *Player*). The *Player* needs these 2 classes to manage I/O individually in a multiplayer scenario. Much like in *Area*, a *Player* object may contain a reference to an *Item*, iff this *Item* does not already exist in an *Area*

<h5 id="Coordinate">Coordinate</h5>

A *Coordinate* is a very simple class to group together an <u>x</u> and <u>y</u> integer variable that represent a positon on a 2D array of *Map*. 

*<u>xCoord: Int</u>* is the x component

*<u>yCoord: Int</u>* is the y component 

*<u>getx(): Int</u>* returns the x coordinate

*<u>gety(): Int</u>* returns the y coordinate

*Coordinate* has very simple relations. *Coordinate* is contained in exactly 1 *Player*, or exactly 1 *Map*. There can exist any number of coordinates, however, these coordinates must be unique and their references can not be shared upon the *Map* and *Player*.

<h5 id="Parser">Parser</h5>

A *Parser* will take an input from the standardIn, and seperate it into a list of actions and a list of items. This will give a way to convert from the complex semantics of language into a form that the parser can try to parse. The *Parser* expects an input in the form <action\> <items/prepositions...*>. More information on this can be found under features. 

*<u>MAX _WORDS _PER _COMMAND</u>* is a constant that sets the maximum number of words per command.

*<u>START OF ITEMS</u>* is a constant int that indicates at which word of the input begins the serialization of words.

*<u>ACTION WORD POSITION</u>* is a constant int that points to the first word in the input. This indicates the action. For example, in the command "Attack pig with sword", Attack is the action and is in position 0. This is therefor 0.

*<u>getInstruction(): void</u>* will take a line from stdIn, split the words into an action and items as an *Instruction*. 

*<u>getLine(): void</u>* will get a line from the stdin. 

*<u>getLineArray()</u>*: String[] gets input from stdin, and parses the words into an array. It uses whitespace as a delimiter. 

Each *Player* will have their own *Parser*. When the *Player* is terminated, the *Parser* will also be terminated. Each *Player* can have at most 1 *Parser*, and each *Parser*, can have at most 1 *Player*

<h5 id="Printer">Printer</h5>

A printer simply outputs strings to the user. It is a nice class to prevent several objects interfacing with the player. 

*<u>print(output: String): Void</u>* will print the string output to stdout. 

While the *Printer* is assosciated to a *Player*, it is the *Players* interaction that will allow the *Player* to print things. For example, when the *Player* explores the room, and <u>Area.getDescription</u> is called from *Area*, then *Player* will have to pass that string to its *Printer*. Each Player has 1 *Printer* and Vice Versa. 

<h5 id="Instruction">Instruction</h5>

An instruction packages Strings in a format that will make it easier for *Game* to understand. An Instruction holds an Action, and a list of items. An Instruction is immutable, and must therefor, take all of its values during construction of an object. 

*<u>action: String</u>* holds the action word inputed by the user. 

*<u>items: List<String\></u>* holds all of the words after action. This means that it also holds a proposition in between the 2 words. 

*<u>getAction(): String</u>* returns <u>action</u>

*<u>getItemByNumber(Int): String</u>* returns the element at index Int of *items*

*<u>getItems(): List<String\></u>* returns <u>items</u>

It is important to note that the size of <u>items</u> depends on the values of <u>MAX_ WORDS_ PER_COMMAND</u> in *<a href="#parser">Parser</a>*. The *Instruction* has a link to *Parser*, but can be passed to any of the superior composites linked to *Parser*.

<hr />

<h5 id="evolution">Evolution of Class Diagram</h5>

Applause for the final version of the Class Diagram for this assignment. It came quite a long way the last couple of days. The evolution started with collecting different types of classes we would need and trying to find the right and best descriptive connection between them. The first version we obtained contained the following classes: *Game*, *Parser*, *Map*, *Area*, *Connector*, Item, *BasicItem*, Container, Properties, Player. Properties as a subclass of BasicItem, Connector as a subclass of Area and Parser as a class connected to the "Main-class" Game. 

In order to keep the structure the most functional and simultaneously reduce complexity, we included the *Properties* class inside the *BasicItem* class. There will now be a list of all the properties- these could be whether an object is moveable, if we can pick it up, use it, etc. - each item will own in the *BasicItem* class. The second subclass we got rid of is the *Connector* class, as a subclass of *Area*. *Connectors* were supposed to be connectors between Areas -aka rooms or coordinates on the map. Those could be hallways, elevators, stairs. 

After we understood that a hallway can be an Area the same way a room is an *Area*, we completly removed the *Connector* class. The next thing we changed is the *Parser*. Thinking about the bonus assignment later (making VUork a multiplayer game) it is necessary to find a solution for the implementation, that makes it easy to later extend. Consequently, we removed the *Parser* from the *Game* class and connected it to the *Player*. 
Each *Player* (in this case only one) will now have his own *Parser*, that makes it easier to distinguish between the multiple processes that are happening. 

Additionally, we implemented a Printer class. The Printer will output descriptions, moves that were just made, etc (in general: strings) to the user. Having an extra class for this will prevent multiple objects interfacing with the player. 

We then implemented the Coordinate class. Since our map is implemented in the form of a grid and we will move the player by adding or subtracting 1 from the x- or y-coordinate, we thought it would be the easiest to keep track of a position if we are able to access them like we are used to use coordinates. This will keep us from using a 2D-array and adds a cool appearance to our code :). 

Finally, after begenning to write our program, we found that using a 2D array to list Actions and Items was redundany, and be conceptually grouped into a class. This class is *Instruction*.

Overall, we are very content with our class diagram, and its ease of understanding and modularity. If you want to initialize the world from a parsed JSON file, that should be easy to add, by only affecting how *Map* is initialized. 

The largest mental hurdle to jump when desiging a multiplayer, how the requests would be handled. 

## 

## Object diagrams

Author(s): `Eduardo Lira`

This chapter contains the description of a "snapshot" of the status of your system during its execution. This chapter is composed of a UML object diagram of your system, together with a textual description of its key elements.

> Figure representing an instance of VuORK in singleplayer (Diagram 2)

![Diagram 2 Showing instance of Single Player VuORK](https://i.postimg.cc/QCKsn1Dj/Class-diagram-Object-Diagram-2.png)

>  Figure representing an instance of VuORK in multiplayer (Diagram 3)

![Object Diagram of VuORK](https://i.postimg.cc/QCz5XT1q/Class-diagram-Object-Diagram-2.png)



​	Diagram (1) shows the object diagram for VuORK. Diagram (2) is an object diagram for 2´two players who are playing in the same world of VuORK. However, this diagram will only apply when implementing the bonus assignment - multiplayer game. The purpose of these diagrams is to better illustrate, with an example, how the classes described in the **Class Diagram** will interact with each other.
Starting with the left hand side of the diagram, we see the instance of a *Map*. The *Map* holds 2 objects of type Area named Entrance and Lobby. The *Map* object will later in the implementation be extended and, therefore, contain more *Area* objects. The *Area* instances span the whole *Map* in the form of a grid. It is important to note that the gameLayout member is a 2D array, but in this example, we see only 1 dimension being used. The map also has a *Coordinate* indicating where a *Player* begins the game upon joining the *Game*.
​	The 2 *Areas*, Entrance and Lobby, are quite different. In the Entrance, there are no Items the user can interact with, and there is no <u>Obstacle</u> preventing the user from entering the room (There can not be an obstacle at the room pointed to by entryPoint). In the Lobby, we also see 2 a *Container*, Closet, which holds a *BasicItem*, Broom. A Broom can be used Clean and Attack, and a Broom can be Attacked, Pickedup, and Burned. Throughout the game, we see more, nearly identical, Brooms, but these are all distinguished by ID's. An Item with the same ID can not exist in 2 distinct objects at any time, it can however, be moved around *Areas*.
​	On the right hand side of the diagrams, we have objects for the Players. Each *Player* has *Printer*, *Parser*, *Coordinate*, and an array of *BasicItems*. Looking at Jack specifically, he has no BasicItems currently in his possession. (In diagram (2): Mark, on the other hand, does have a Broom and a Sword.) These *BasicItems* have their unique ID's to distinguish them from similar BasicItems. Each *Player* has a *Printer*, which allows the system to output text to the command line of the desired user. Similarly, each *Player* has a *Parser* to allow separate input of the users. This is very important for the notion of Multiplayer.
The job of connecting both sides is done by the object gameState of type *Game*. gameState holds the *Map* that all players interact with, all Players and the timeStamp of when the game was started.
With the figural and textual description of a state our system can have, it should be clear why each class exists, and their relations to one another.

## 

## State machine diagrams

Author(s): `Irene Garcia-Fortea Garcia`, `Eduardo Lira` & `Theresa Schantz`

### Class Game

<<<<<<< HEAD
<<<<<<< HEAD

> State machine diagram of Game (Diagram 3) **DESCRIPTIVE**
> [![State-Machine-Diagram-Page-1.png](https://i.postimg.cc/tTHWn9NV/State-Machine-Diagram-Page-1-1.png)](https://postimg.cc/gwfHpY8M) 
> ||||||| merged common ancestors
> Figure representing the state machine diagram of the class Game (Diagram 3) **DESCRIPTIVE**
>
> [![State-Machine-Diagram-Page-1.png](https://i.postimg.cc/63Qjdq3N/State-Machine-Diagram-Page-1.png)](https://postimg.cc/gwfHpY8M) 
> =======
>
> Figure representing the state machine diagram of the class Game (Diagram 4) **DESCRIPTIVE**
> ||||||| merged common ancestors
>
> Figure representing the state machine diagram of the class Game (Diagram 4) **DESCRIPTIVE**
> =======

> Figure representing the state machine diagram of the Game class (Diagram 4) **DESCRIPTIVE**


>>>>>>> 8311cefd4d7028e8cc5ff79cfea17fcf2ff02dde
>>>>>>> [![State-Machine-Diagram-Page-1.png](https://i.postimg.cc/63Qjdq3N/State-Machine-Diagram-Page-1.png)](https://postimg.cc/gwfHpY8M) 
>>>>>>> 01fecb557dc551bdbef04c59718f63622129ed2b

The diagram above shows the State Machine Diagram of the event that of executing a command. After starting the process the system waits for a user input. After the input has been received, the function *getCommand()* executes. 

In the next step we move to the state that the command has been received. The command needs to be checked, whether it is a valid command that the system accepts in *validateCommand()*. 

If the command i not valid, an error message is printed and the program returns to the idle state, waiting for a command. 

If the command is valid, the command needs to be executed (“Execute Command”). There, changes will be made to the Item or Player (Described in the *Execute Command* figure). After the changes have been made, the system goes back to the idle state, waiting for a new command to process, unless the user types “quit”. Then, the game terminates. 

The *Execute Command* state works as the following: After checking the command for validity, it will be matched matching it with each of the following existing commands: move, help or look. 

If the command is move: the player will be moved into the wanted direction (input by the player). 

If the command is help: a help message will be printed to the user.

If the command is look: the description of the *Area*, in that the *Player* is currently in, will be printed to the user. 


### Class Map

<<<<<<< HEAD

> Figure representing the state machine diagram of the class Map (Diagram 4) **DESCRIPTIVE**
> [![State-Machine-Diagram-Page-2.png](https://i.postimg.cc/FHgLtyV2/State-Machine-Diagram-Page-2-Page-2.png)](https://postimg.cc/PLZ2PpXG)
> ||||||| merged common ancestors
> Figure representing the state machine diagram of the class Map (Diagram 4) **DESCRIPTIVE**
>
> [![State-Machine-Diagram-Page-2.png](https://i.postimg.cc/0NX3XDhN/State-Machine-Diagram-Page-2.png)](https://postimg.cc/PLZ2PpXG)
> =======
>
> Figure representing the state machine diagram of the class Map (Diagram 5) **DESCRIPTIVE**
> [![State-Machine-Diagram-Page-2.png](https://i.postimg.cc/0NX3XDhN/State-Machine-Diagram-Page-2.png)](https://postimg.cc/PLZ2PpXG)
>
> >>>>>> 01fecb557dc551bdbef04c59718f63622129ed2b

Diagram (4) illustrates the machine state diagram of the class map, consisting of a similar structure to the previous state diagram due to the requirements of a command being able to be parsed and the error checking in the case of the command not being valid.
Starting from the initial pseudo state the user will enter its desired direction to move in which the choice pseudostate performs a selection whether the command is valid or not. If the command isn’t one of the possible directions like for example, North-East, it would enter the Error State in which it would return to the Idle State in which the user will be able to input another command which would hopefully be one of the valid options which are North, South, East, West. In the case that this happens the choice pseudostate would then consider the command as valid and thus would enter the next Behaviour State in which, depending on the direction chosen, it would perform the corresponding movement in terms of coordinates of variables x and y and with values of forward being x/y + 1 and backwards being x/y - 1. When the command is selected it is then parsed and the corresponding action is taken.

##

## Sequence diagrams

Author(s): `Marta Anna Jansone, Theresa Schantz`

#### Game Initialisation

> Figure representing the sequence of events when the game is initialised(Diagram 6) **DESCRIPTIVE**

![Sequence-diagram-valid-command-game-initialisation-3.png](https://i.postimg.cc/X7XpC8LH/Sequence-diagram-valid-command-game-initialisation-3.png)



The first possible event described in the sequence diagram above is the Game Initialisation. When initializing a game the first thing that has to happen is declaring a *Game* object. The *Game* object is further responsible for declaring an object of type *Map* and an object of type *Player*. The object *Player*, when initialised, requests the entry point of the game, which is returned by the *Map* object. This happens when *getEntryPoint* is called. The returned value is an object of type *Coordinate*. This value is then stored in the *Player coord* variable. 
When a new *Player* is initialised it further initialises its own *Parser* and *Printer*, and an array of object type *Item*. The object *Parser* is stored in a private variable called *parser* within the object *Player*, the *Printer* is stored in a private variable called *printer* within the object *Player* and the array of *Items* is stored within a private variable called *backpack* within the object *Player*. The game is started by calling *startGame*. 
When the game is started, the *Game* calls a public function *output* of the object *Player* with a welcome message and a request to enter the user's name. The *output* function further calls the function *output* of the private variable printer, which outputs the message by printing it to the Terminal. To add a name to the private variable *name* of the *Player*, the public function *setName* from object *Player* is called by the object *Game*. The *setName* function further calls the private parser of the *Player*, which reads and parses the user input line using the function *getLine*. The value returned by the *Parser* is assigned as the *name*' of the *Player.* Another output message is then sent from *Game* to the *output* function of the *Player*, taking an argument of *getUserName*. The *output* function of the *Player* calls the *output* function of the private printer, which then sends a request back to the *Player* to *getUserName*. When the *getUserName* returns the name of the player the message is printed. The last message printed during the phase of initialising the game is the description of the room where the player is currently located in. This is done through the *Game* calling the *output* function of the *Player* with the argument *map.getPosition(player.position())*. As the argument is a function call then the *Player* calls the *Map* function *getPosition* with the argument *player.position*. As the argument of the function is a call to a function within the object *Player*, the function *position* from object *Player* is called. Then further each of the function calls returns the requested variable till the start of the sequence at the call of *output*, which prints the description of the room to the Terminal.


#### Command Processing


> Figure representing the sequence of events for processing commands after the game has been initialised(Diagram 7) 


[![Sequence-diagram-valid-command-valid-command.png](https://i.postimg.cc/d0qj4c4x/Sequence-diagram-valid-command-valid-command.png)](https://postimg.cc/RWDtZyr7)

The second event described in the sequence diagram above is the command processing. The program will constantly loop, outputting instructions and reading commands from the user. The *gamestate* object of the class *Game* calls getCommand() from the *Player*. The *Player* will read in a line/the instruction from the user input with the *Scanner* with *getLineArray()*. After the input has been returned in *parts*,
the given instruction will be identified by declaring a *new Instruction*. In the *Instruction* class the given input will be split into in the *action* and the *items* and returned in th evariable *command*. The *command* will passed back as the return value of th efunction *getCommand* of *Game* via the *Player* and the functions *getInstruction*. The next necessary step is to check whether the command is valid.
This is done with the function *validate command*. Here, the first step is to obtain the wanted action from the *command*, which is stored in the *Parser* (this will be stored in the variable *action*).
The *action* must then be checked, whether it is a valid direction (north, south, east or west), done in *isDirection*. At this point, if it is not valid, we jump to outputting an error message to th e*Printer* via the *Player* with the *output* property.
If it is a valid direction, *handleMove* is called.  The given direction is matched to a case "north", "east", "west" or "south" and then moved in the corresponding direction by adding or subtracting a 1 to either the x- or y-coordinate. This will be the *newPos* variable. Lastly, it has to be checked whether this is a valid coordinate on the map. If so, the *Player*s coordinate will be set to the new coordinate
and the description will be printed to the user. If it is not a valid position, an error message will be printed to the user, calling *Printer.output* from *Player.output*. We still need to get the description for the new coordinate of the location of the *Player* printed to the Terminal. By passing the *position* with *getDescription* to the *Map* via the *Player.output*, which will return the description for the
corresponding coordinate, *Printer.output* will be called by the *Player*. The will cause that the *Printer* will print the given description to the user (*output* is the *Printer*s print function to the Terminal).
Then, the whole process will start over again.

## 

## Implementation

Author(s): `Eduardo Lira`



It was surprisingly quick to implement our system after having thoroughly thought it out using UML diagrams. However, as discussed in our development of the class diagram, a lot had to be changed as challenges arised, while trying to implement our design. Our strategy was quite simple: once we were satisfied with the design, we tried to write and implement it. As we encountered issues or areas of improvement, we rethought and edited our design in UML, and implemented that.

The greatest challenge of creating a text-adventure game, in my opinion, is how to interpret natural language as structures and objects that exist in a game, and translate them such that the program can understand and handle them. To add to this complexity, having several players on a game at once makes it incredible to reason on how to handle requests. Of course, once you discover a solution, it is obvious, but the mental hurdle of gettin there was significant.

From the above, the 2 greatest challenges are:

- Understanding objects from natural language
- Handling this natural language input for multiple users simultaneously



Our solutions were:

- Use *Strings* as names. Then to identify an object, start searching in the field of view of the player. The Parsing is done by the *Parser*, but it is the *Game* object that makes reason of it and decides what to do.
  We define the field of view as the extent a *Player* can interact with. This is the *Player*'s *backpack* and the *Area* he is in. When an Interaction is needed to be done, the *Game* will need to reason whether this element is in the *Player*'s possession or in the *Area*. If no such *Item* exists that *canBe* or *usedTo* variables match the *Action*, an appropriate error is returned. NB: The latter part about interaction between *Items* is prescriptive and has yet to be implemented.

- The solution to handling multiple *Player* I/O is quite trivial once reasoned. Although not implemented for this assignment, multithreading every socket/instance of a *Player*, which is handled by *Game*. Because the call *Player.getCommand()* is a blocking command, you do not have to worry about several inputs at the same time. This is VERY unlikely. For this assignment, we were unable to create a network version of the game, but aim to do so for the final assignment. Right now, we simply call *handlePlayer()*, which is the function that would create a thread when a player joins.

  

These are the 2 greatest challenges we encountered while implementing movement in our text-based game. For the rest, and more detailed information about the actual project, we suggest looking at our UML descriptions and the code itself. 

The location of the main Java class is:

> src/main/java/Game.java

The location of the Jar file is:

> out/artifacts/software_design_vu_2020_jar/software-design-vu-2020.jar

<hr/>

The video of the gameplay is here: 

![Gif of gamePlay of VuOrk](/Users/eduardolira/Desktop/textAdventure/docs/ass2_demo_gif.gif)

<hr/>

## 

## References

None :)