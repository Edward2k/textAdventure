
# Assignment 2

Maximum number of words for this document: 12000

**IMPORTANT**: In this assignment you will model the whole system. Within each of your models, you will have a *prescriptive intent* when representing the elements related to the feature you are implementing in this assignment, whereas the rest of the elements are used with a *descriptive intent*. In all your diagrams it is  strongly suggested to used different colors for the prescriptive and  descriptive parts of your models (this helps you in better reasoning on  the level of detail needed in each part of the models and the  instructors in knowing how to assess your models).

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

![Class Diagram of VuORK](https://i.postimg.cc/ZnnwVkP7/Class-diagram-Class-diagram-3.png)

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

> Figure representing an instance of VuORK in singleplayer (Diagram 1)

![Diagram 2 Showing instance of Single Player VuORK](https://i.postimg.cc/QCKsn1Dj/Class-diagram-Object-Diagram-2.png)

>  Figure representing an instance of VuORK in multiplayer (Diagram 2)

![Object Diagram of VuORK](https://i.postimg.cc/QCz5XT1q/Class-diagram-Object-Diagram-2.png)



​	Diagram (1) shows the object diagram for VuORK. Diagram (2) is an object diagram for 2´two players who are playing in the same world of VuORK. However, this diagram will only apply when implementing the bonus assignment - multiplayer game. The purpose of these diagrams is to better illustrate, with an example, how the classes described in the **Class Diagram** will interact with each other.
Starting with the left hand side of the diagram, we see the instance of a *Map*. The *Map* holds 2 objects of type Area named Entrance and Lobby. The *Map* object will later in the implementation be extended and, therefore, contain more *Area* objects. The *Area* instances span the whole *Map* in the form of a grid. It is important to note that the gameLayout member is a 2D array, but in this example, we see only 1 dimension being used. The map also has a *Coordinate* indicating where a *Player* begins the game upon joining the *Game*.
​	The 2 *Areas*, Entrance and Lobby, are quite different. In the Entrance, there are no Items the user can interact with, and there is no <u>Obstacle</u> preventing the user from entering the room (There can not be an obstacle at the room pointed to by entryPoint). In the Lobby, we also see 2 a *Container*, Closet, which holds a *BasicItem*, Broom. A Broom can be used Clean and Attack, and a Broom can be Attacked, Pickedup, and Burned. Throughout the game, we see more, nearly identical, Brooms, but these are all distinguished by ID's. An Item with the same ID can not exist in 2 distinct objects at any time, it can however, be moved around *Areas*.
​	On the right hand side of the diagrams, we have objects for the Players. Each *Player* has *Printer*, *Parser*, *Coordinate*, and an array of *BasicItems*. Looking at Jack specifically, he has no BasicItems currently in his possession. (In diagram (2): Mark, on the other hand, does have a Broom and a Sword.) These *BasicItems* have their unique ID's to distinguish them from similar BasicItems. Each *Player* has a *Printer*, which allows the system to output text to the command line of the desired user. Similarly, each *Player* has a *Parser* to allow separate input of the users. This is very important for the notion of Multiplayer.
The job of connecting both sides is done by the object gameState of type *Game*. gameState holds the *Map* that all players interact with, all Players and the timeStamp of when the game was started.
With the figural and textual description of a state our system can have, it should be clear why each class exists, and their relations to one another.

## 

## State machine diagrams

Author(s): `name of the team member(s) responsible for this section`

This chapter contains the specification of at least 2 UML state  machines of your system, together with a textual description of all  their elements. Also, remember that classes the describe only data  structures (e.g., Coordinate, Position) do not need to have an  associated state machine since they can be seen as simple "data  containers" without behaviour (they have only stateless objects).

For each state machine you have to provide:

- the name of the class for which you are representing the internal behavior;
- a figure representing the part of state machine;
- a textual description of all its states, transitions, activities,  etc. in a narrative manner (you do not need to structure your  description into tables in this case). We expect 3-4 lines of text for  describing trivial or very simple state machines (e.g., those with one  to three states), whereas you will provide longer descriptions (e.g.,  ~500 words) when describing more complex state machines.

The goal of your state machine diagrams is both descriptive and  prescriptive, so put the needed level of detail here, finding the right  trade-off between understandability of the models and their precision.

Maximum number of words for this section: 3000

## 

## Sequence diagrams

Author(s): Marta Jansone, Theresa Schantz

- a title representing the specific situation you want to describe;
- a figure representing the sequence diagram;
- a textual description of all its elements in a narrative manner (you do not need to structure your description into tables in this case). We expect a detailed description of all the interaction partners, their  exchanged messages, and the fragments of interaction where they are  involved. For each sequence diagram we expect a description of about  300-500 words.

Maximum number of words for this section: 3000

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             However, the move has not actually been processed yet. Consequently, 



## 

## Implementation

Author(s): `name of the team member(s) responsible for this section`

In this chapter you will describe the following aspects of your project:

- the strategy that you followed when moving from the UML models to the implementation code;
- the key solutions that you applied when implementing your system  (for example, how you implemented the syntax highlighting feature of  your code snippet manager, how you manage fantasy soccer matches, etc.);
- the location of the main Java class needed for executing your system in your source code;
- the location of the Jar file for directly executing your system;
- the 30-seconds video showing the execution of your system (you can embed the video directly in your md file on GitHub).

IMPORTANT: remember that your implementation must be consistent with  your UML models. Also, your implementation must run without the need  from any other external software or tool. Failing to meet this  requirement means 0 points for the implementation part of your project.

Maximum number of words for this section: 2000

## 

## References

References, if needed.