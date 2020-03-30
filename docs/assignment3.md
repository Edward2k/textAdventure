# Assignment 3

Maximum number of words for this document: 18000

**IMPORTANT**: In this assignment you will fully model and impement your system. The idea is that you improve your UML models and Java implementation by (i) applying (a subset of) the studied design patterns and (ii) adding any relevant implementation-specific details (e.g., classes with “technical purposes” which are not part of the domain of the system). The goal here is to improve the system in terms of maintainability, readability, evolvability, etc.    

**Format**: establish formatting conventions when describing your models in this document. For example, you style the name of each class in bold, whereas the attributes, operations, and associations as underlined text, objects are in italic, etc.

### Summary of changes of Assignment 2
Author(s): `name of the team member(s) responsible for this section`

##### This is not a bullet point list. Also, lacking a lot of detail. Ex, 2nd point, we had to change features because of incalrities, not just add them. Point 3 was not feedback? It is important to note that this section is for changes FOR FEEDBACK GIVEN IN ASS2. 

The first feedback to address was the introduction (from the feedback of Assignment 1). More information, about the game *Zork* had to be added. Therefore, the first step for us was to further elaborate on  the games mechanics and objectives.

Second, the features, and quality requirements had to be added. ...//TODO

Third, unused variables and functions inside the code were removed, and error messages for the user were added. A newly implemented message is the one telling tha player that the given instruction is too long. 

Fourth, still haning around from the feedback of assignment 1, the use case diagram was renewed. We hope it is correct now :-).

Maximum number of words for this section: 1000

### Application of design patterns
Author(s): `name of the team member(s) responsible for this section`

`Figure representing the UML class diagram in which all the applied design patterns are highlighted graphically (for example with a red rectangle/circle with a reference to the ID of the applied design pattern`

For each application of any design pattern you have to provide a table conforming to the template below.

| ID                     | DP1                                                          |
| ---------------------- | ------------------------------------------------------------ |
| **Design pattern**     | Name of the applied pattern                                  |
| **Problem**            | A paragraph describing the problem you want to solve         |
| **Solution**           | A paragraph describing why with the application of the design pattern you solve the identified problem |
| **Intended use**       | A paragraph describing how you intend to use at run-time the objects involved in the applied design patterns (you can refer to small sequence diagrams here if you want to detail how the involved parties interact at run-time |
| **Constraints**        | Any additional constraints that the application of the design pattern is imposing, if any |
| **Additional remarks** | Optional, only if needed                                     |

Maximum number of words for this section: 2000

## Class diagram									
Author(s): `Eduardo Lira`

It is important to note that the decisions behind this design were taken to allow a multiplayer platform that can easily be expanded/modified. The design attempts to keep the principle of modularity and interfaces. At times in the code (especially in the **Game**), code can be very complex. However, by keeping a simple interface, we aim to make the VuORK understandble. 

**NB**. This project has 2 parts: A server and client side. This allows for the networked multiplayer bonus we have created. The ClientSide is very similar to a simple chat client, where a user sends instruction to the Server. The server has *Playerthreads* which reads *Instructions* from the sockets, and asks the *Game* to validate them. The *Game* will always return a string which is then forwarded to the *ClientSide* as a reply. This is a very high-level overview. 

**NB**. These descriptions only discuss the parts that are most important to get a decent understanding of the functioning of each class. Explaining all methods would not only be very verbose, but quite redundant. We have carefully chosen these as they greatly resemble the ommited methods. 

`ServerSide Class Diagram`

![Server-Side class diagram](https://i.postimg.cc/dtxPk8P1/Class-diagram-Class-diagram-1.png)

<h5 id="Game">Game</h5>

This class holds all of the object together. All high-level decisions, such as wether a player can grab the desired *Item*, or move to the desired *Area*, is all validated by the **Game**. It should be self explanatory why this class is existential to the system. 

*<u>map: Map:</u>* holds the object for *Map*. This is the playarea for *Player* objects to explore. 

*<u>PORT: Int</u>*: holds the server's portnumber.

*<u>isBusy: Boolean</u>* is used as a spin lock. When a *PlayerThread*gives an *Instruction* to the *Game* for validation, this variable will become true. Before a *PlayerThread* gives an *Instruction*, it will check this condition, and only proceed to validation if the condition is false (not busy). This solves any race-conditions 2 players may have when interacting with the *Item* in a world. 

*<u>isGameBusy(): Boolean</u>* returns the value of *isBusy*.

*<u>validateCommand(Instruction): String</u>* is a function that will execute a command, that is received from a *PlayerThread*. It is important to note that the whole system is multiThreaded, and for every instance of a *Player* in a *Game*, a thread is opened to read and execute instructions from that *Player*. Each thread will call this function to handle all of the *Instructions* given by any *Player*. This function will reply with an adequate response (success or not), and execute anything if necessary. The String is returned to send to the *Printer* or the *Player*. 

*<u>runServer: void</u>* loops until the program is stopped with a keyboard interrupt. This function will accept() incomming connection from the socket interface. For every connection created, it will create a new *PlayerThread*, and run this object in a new *Thread*. 

*<u>handleMove(String direction, Player player): String</u>* Will evaluate if a Player's move is possible, and if so, move the player, returning a String of the description. 

An instance of **Game** can hold exactly one **Map**, but it is logical that the *Map* can be swapped. A **Game** can not hold more than one *Map* at anytime. **Game** also holds (infinetly many) references to *PlayerThreads* (discussed below)

##### PlayerThread

This Class extends **Thread** which allows a new thread to be spawned running the *run()* method. This **PlayerThread** was NOT merged with the **Player** class, and for good reason; Changing things such as the *welcomePlayer()* method which establishes a username etc would be nonsensical. How can a person greet him/herself to another entity? It was then immediatly obvious that this middle-man would help reduce the mental strain of understanding the concurrency of multiple players by making an explicit class that controls how the concurrency happens. This also allows for increased modularity, and ease of understanding if anyone ever wants to edit how we multithread the **Players** 

*<u>player: Player</u>* holds the player

*<u>server: Game</u>* holds a reference to the *Game* so moves can be validated. 

*<u>run(): void</u>* is the implementation of Thread. This is what runs once the Thread is created. This method calls welcomePlayer() and then runPlayer(). When a *NullPointerException* is recieved, it will drop all *Items* currently in the *Players.backpack*, then yield the thread. 

*<u>welcomePlayer(): void</u>* will send some messages to the player, then ask for a username. This will set the UserName of the *P*

<u>runPlayer(): void</u> runs until the *ClientSide* is quit. It will ask for a *Instruction*, check the spinlock of *Game* (to prevent any inconcistencies), then ask *Game* to validate the *Instruction*. It will throw a *NullPointerException* if the *Socket* is broken as nothing can be read when *Player.getInstruction()* can be read but *null*. 

A **PlayerThread** is spawned when a *Game* object recieves a new incomming connection on the designated *IP* and *PORT*. A **PlayerThread** needs a *Game* obeject to exist (hence the aggregate association), and each **PlayerThread** can have, at most, 1 *Game* object it is referenced too. 

<h5 id="Player">Player</h5>

A **Player** is the class that resembles a physical user. It contains information such as the *Players name*, *backpack*, *health* etc, which is essential for the running of the **Game**. 

*<u>name: String</u>* is a string which holds the name the user first put in when logging in to VuORK. This must be unique from all other users in the *Game* object. 

*<u>coord: Coordinate</u>* stores where the player currently is in terms of the *Map*. It should be instatiated to the specified *Map.getEntryPoint()*. 

*<u>backpack: List<BasicItem\></u>* stores references to all of the *Items* the *Player* currently has in his possesion. Instantiated to NULL. It is important to note that this array is of type *BasicItem* and not its superclass, *Item*, because holding *Containers*, would allow for infinite storage. 

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

The relations of Player are a little complex. A player must contain a *Parser* and a *Printer* (equally, these 2 must not exist without a *Player*). The *Player* needs these 2 classes to manage I/O individually in a multiplayer scenario. Much like in **Area**, a *Player* object may contain a reference to an *Item* in *backpack*, iff this *Item* does not already exist in an *Area*

<h5 id="Parser">Parser</h5>

A *Parser* will take an input from the *Socket* it was instantiated with, and seperate it into a list of actions and a list of items (ensembled as an *Instruction*). This will give a way to convert from the complex semantics of language into a form that the parser can try to parse. The *Parser* expects an input in the form <action\> <items/prepositions...*>. More information on this can be found under features. The following 3 constants are not in the Class diagram, but were included to help understand how the VuORK understands text-input as commands. 

*<u>MAX _WORDS _PER _COMMAND</u>* is a constant that sets the maximum number of words per command.

*<u>START OF ITEMS</u>* is a constant int that indicates at which word of the input begins the serialization of words.

*<u>ACTION WORD POSITION</u>* is a constant int that points to the first word in the input. This indicates the action. For example, in the command "Attack pig with sword", Attack is the action and is in position 0. This is therefor 0.

*<u>input: BufferReader</u>* is a the buffer for reading input from the Socket which connects to the *PlayerClient*. 

*<u>getInstruction(): void</u>* will call getLine(), split the words into an action and items as an *Instruction*. 

*<u>getLine(): void</u>* will get a line from the the open Socket. 

*<u>getLineArray()</u>*: call getLine, and parses the words into an array. It uses whitespace as a delimiter. 

Each *Player* will have their own *Parser*. When the *Player* is terminated, the *Parser* will also be terminated. Each *Player* can have at most 1 *Parser*, and each *Parser*, can have at most 1 *Player*.

<h5 id="Printer">Printer</h5>

A printer simply outputs strings to the *Socket* it was instantiated with. It is a nice class to prevent several objects interfacing with the player. 

*<u>sender: BufferReader</u>* sends data onto the Socket which connects to the PlayerClient

*<u>print(output: String): Void</u>* will print the string output to stdout. 

While the *Printer* is assosciated to a *Player*, it is the *Players* interaction that will allow the *Player* to print things. For example, when the *Player* explores the room, and <u>Area.getDescription</u> is called from *Area*, then *Player* will have to pass that string to its *Printer*. Each Player has 1 *Printer* and Vice Versa. 

<h5 id="Instruction">Instruction</h5>

An instruction packages *Strings* in a format that will make it easier for *Game* to understand. An *Instruction* holds an Action, and a list of items. An *Instruction* is immutable, and must therefor, take all of its values during construction of an object.

*<u>action: String</u>* holds the action word inputed by the user. 

*<u>items: List<String\></u>* holds all of the words after action. This means that it also holds a proposition in between the 2 words. 

*<u>getAction(): String</u>* returns <u>action</u>

*<u>getItemByNumber(Int): String</u>* returns the element at index Int of *items*

*<u>getItems(): List<String\></u>* returns <u>items</u>

It is important to note that the size of <u>items</u> depends on the values of <u>MAX_ WORDS_ PER_COMMAND</u> in *<a href="#parser">Parser</a>*. The *Instruction* has a link to *Parser*, but can be passed to any of the superior composites linked to *Parser*.

<h5 id="Item">Item</h5>

The Item class is an abstract type which can be either a _Container_ or a _BasicItem_. The Item class represents an entity the user can interact with. Arbitrary examples may be: *swords, spoons, pens, etc..*. This class is abstract because an Item can be (exclusivly) one of two types: *Container* or *BasicItem*. **We choose to make this class abstract** (instead of implementeing it as the BasicItem) because it allows a developer to easily create new kinds of interactible Items. For example, if a developer may want to create little monsters. So the client may simply implement **Item** and add attributes and methods as needed. 

An Item can be interracted with, or used to interract. To elaborate, imagine the command ***Attack chair with sword***. Here, the action attack has 2 clauses: the dependent and independent. This means that BasicItems need a way to know what can be done to it (ie be attacked), and what it can be used to do (ie attack). In this case, the BasicItem chair would have the string *Attack*, in its *UsedTo*.

<u>*Name*</u> is a string that gives the entity a name. Pretty self explanatory. It is needed so that the game can tell the user (in words), what the item represents.

<u>*ID*</u> is a uniquie integer which will differentiate all objects. This is needed to tell apart two objects with the same *Name*. 

*<u>canBe</u>* is an array of strings. These strings list what can be done to the item. Taking the example of the chair and sword from above, the chair would have the string "Attack", in this array. 

*<u>usedTo</u>* is an array of strings. These strings list what the item can be used to do. Taking the example of the sword from above, the sword would have the string "Attack", in this array.

*<u>isValidAction(string action)</u>* returns a boolean on the condition that the array usedTo contains the string action. If it does contain the string, it returns true. 

*<u>isValidManipulation(string manip)</u>* returns a boolean on the condition that the array usedTo contains the string action. If it does contain the string, it returns true. 

*<u>getName()</u>* returns the name of the Item

<u>*getID()*</u> returns the ID of the item

Item has a composition relation to (exclusivley) *Area* or a *Player*. This is because an Item can not both exist outside of the *Player's backpack* and in the surrounding area or vice versa. Furthermore, an Item can not exist without an *Area* or *Player's backpack* to exist in. 

<h5 id="BasicItem">BasicItem</h5>

Singelton is a subtype of <a href="#Item">Item</a> that a player can interact with. It is the most basic type of item. It simply calls the super constructor and returns the object. As explained above, this allows developers to create as many different types of Items they like, and the **Game** will work through this interface. 

> Does not have any other members/attributes beyond its inheritance from **Item**

Because BasicItem is a sub-type of *Item*, it follows the same matually exclusive composite relation seen Item has. The aggregate relationship indicates that any number of *BasicItem* objects, may be contained in a *Container*. 

<h5 id="Container">Container</h5>

Container is a subtype of Item. It is different from a BasicItem because the only action that can be performed on a container is the *<u>toggle(state: boolean)</u>* Also, you can see that a container may contain any number of BasicItems. The idea is that a container may be, to bring a realworld example, a (but not limited to) a fridge or drawer. These can be opened and reveal any number of items. 

*<u>isOpen</u>* is a boolean variable that checks wether the contents of the container are available to the surrounding *Area* and, thus, *Player*. The entities within the *Container* can not be accessed unless this condition is true. 

*<u>entities</u>* is an array of *BasicItem*. It lists all the *BasicItem* currently contained in the item. 

*<u>toggle(state:bool)</u>* is a method function which will open/or close the container. If state is TRUE, then *<u>isOpen</u>*, will be set to TRUE.

It is important to note the aggregation from *BasicItem*. This simply indicates that an instance of *Container* may contain any number of *BasicItem* objects, but the existance of *BasicItem* objects does not relay on the existance of the *Container* object.

<h5 id="Map">Map</h5>

The *Map* class holds all the references to *Area* together, in some emposed ordering. The ordering is defined upon instatiation of the class. This is because an *Area* does not, conceptually, know where it is in a *Map*. That is the purpose of the *Map*. It is important to note that the *Map* must be a square. Map may be instantiated from a JSON file which describes all the Areas, and the Items they have in them. This is made so it is easy to create world without having to write tons of source code. 

<u>*gameLayout: Array<Array< Area> >*</u> is a 2-Dimensional array which holds references to *Area* objects. 

*<u>entryPoint: Coordinate</u>* is *Coordinate* which gives the starting point of the *Map*

*<u>mapSize: Int</u>* is the size of the map. This value must be greater than 0. 

*<u>isValidMove(Coord: Coordinate): Boolean</u>* is a simple function to check wether the given position is valid. That is, is the position given by the *Coordinate* not a NULL pointer. 

*<u>getDescription(Coordinate): String</u>* is a getter function that returns the description of the *Area* denoted by *Area*. If Coordinate is not an *Area*, this function will return, "not an Area".

*<u>getEntryPoint(): Coordinate</u>* returns entryPoint.

*<u>initMapFile(): Void</u>* Is a very complex funtction. It will read the file map.json, which must be linked with the artifact, and extract all the information needed from that file. The JSON file starts with the *entrypoint* **Coordinate** (X and then Y). Then the size of the map. After this, comes a long list of **Areas**, inside *Areas* there may be **Items**, of which there may be **BasicItems** or **Containers**. If *Containers*, there may be another list of more *BasicItems* inside it. Insides the **Area**, there may also be an **Obstacle**. 

The relations *Map* has is quite simple. *Map* has a aggregate relation to *Game*. This means that an instance of *Map* can exist a *Game*, but the multiplicity of 1, means that at any time, there can be at most 1 *Map* to a *Game* object. 

<h5 id="Area">Area</h5>

Area is a class which represents a region any *Player* can exist in. As a realworld example, this could be a classroom, a bathroom, kitchen etc. We assume that when a *Player* is in a region, he/she can immediatly interact with the objects in the field of view of that room. This means that, if there is a fridge and a pen on the floor, the *Player* can open the fridge for pick up the pen without having to move inside the room.

*<u>items: array <Item\></u>* is an array of items that holds the contents of the room. 

*<u>obstacle: Obstacle</u>* holds the obstacle in the room, (if there is one)

*<u>obstacleNeutrelized: Boolean</u>*: returns obstacle.isNeutralized(). 

*<u>name: string</u>* is a string that stores the name of the *Area*. 

*<u>description: String</u>* is a description of the *Area*, and example is *"it is a dark room. You can't see which way to go, but you feel a breeze north of you."*

*<u>getDescription(): String</u>* will print a description.

*<u>canEnter(): boolean</u>* returns the <u>obstacleNeutrelized</u> private member. 

*<u>getName(): String</u>*:  returns the name private member of the room.

*<u>addItem(): Void</u>* adds an *Item* to <u>items</u>

*<u>removeItem(String): Bool</u>* removes an *Item* from *items*, that has a name that matches String

*<u>getItems(): List<String\></u>* returns *items*.

*Area* has two direct relations. It has an (exclusive or) relation from *Item*. Why this is, is further described in <u><a href="#Item">Item</a></u>. An Area also has a composite relation to the class *Map*. This is because the existance of *Area* is entirely dependent of the existance of *Map*.

##### Obstacle

An obstacle is an object which may block the *Area* to a *Player* if it is not yet neutralized. Blocking a *Player* means preventing a player from seeing anything in the room, or going further than the room If a *Player* enters an *Area* with a non-neutralized *Obstacle*, the only way back is the opposite direction the *Player* entered. An example of an **Obstacle** would be a Troll. A Troll may block you from passing, but the Troll can be bribed with rare mushrooms you must gather from a mountain. Then a *Player* may neutralize the Troll by typing "give mushroom to troll". 

*<u>isNeutralized: Boolean</u>* , true if the obstacle has been neutralized, else false. When an object is created, this is set to false. 

*<u>howToNeutralize: String</u>* is a description which is outputted to the *Player* if isNeutralized is false and the *Player* is in a room that with this *obstacle*. It gives a hint on how to pass the Obstacle.

*<u>name: String</u>* holds the name of the *Obstacle*

*<u>description: String</u>* holds a description of the **Obstacle**. Using the example of the Troll, an example would be, "A big ugly smelly troll is blocking your way".

*<u>attackObstacle(damage: Int): String</u>* is called when a player chooses to attack an *Obstacle*. In this case, the Obstacle loses health depending on the damage given

*<u>getDescription(): String</u>* return description. 

An **Obstacle** can only exist a single *Area*. The composite aggregation shows that without the *Area* that instantiated the *Obstacle*, the *Obstacle* is also destroyed. 

<h5 id="Coordinate">Coordinate</h5>

A *Coordinate* is a very simple class to group together an <u>x</u> and <u>y</u> integer variable that represent a positon on a 2D array of *Map*. 

*<u>xCoord: Int</u>* is the x component

*<u>yCoord: Int</u>* is the y component 

*<u>getx(): Int</u>* returns the x coordinate

*<u>gety(): Int</u>* returns the y coordinate

*Coordinate* has very simple relations. *Coordinate* is contained in exactly 1 *Player*, or exactly 1 *Map*. There can exist any number of coordinates, however, these coordinates must be unique and their references can not be shared upon the *Map* and *Player*.

`ClientSide Class Diagram`

<img src="https://i.postimg.cc/j2s3MLTF/Class-diagram-CLASS-DIAGRAM-2.png" alt="Client-Side Class Diagram" style="zoom:60%;" />

##### PlayerClient

This class holds is where execution of client-side program begins. It is the parent of 2 *Threads*, the reader and writer threads. This allows for asyncronus reading/writing to the socket. Thus the Server may send messages without the PlayerClient having sent anything. This is useful for player interactions such as trading *Items*, or talking to eachother. 

*<u>hostName: String</u>* holds the IP to the server. Set to 127.0.0.1 for testing (Local host)

*<u>port: Int</u>* holds the port for connecting to the ServerSide. 

*<u>gameInterface</u>*: Interface holds the window we created. This window has a little text bar at the bottom, for inputting commands, and the rest is for recieving information from the *Socket*

*<u>execute(): void</u>* is what runs instantiates the read and write threads. It first creates the socket connection. Then, after creating a read and write object, runs the thread. If an error occurs, it is handled according. The handled errors are *UnknownHostException* and *IOException*

*<u>main</u>* is what is run when the program is started. It simply creates a new PlayerClient object on hostname "127.0.0.1" and port "1234" with a new **Interface**. Then calls *execute*(). 

**PlayerClient** holds all of other componets of the client side together. It is the Parent of all other objects. Without this object, no other objects can exist. It therefor contains agregate compositions of exactly 1 to **ReadThread**, **WriteThread** and **Interaface**.

##### ReadThread

This is the thread that handles incomming (from **ServerSide**) data from the *Socket* byte-stream. It extends *Thread* to allow a this class to be run as its own *Thread*

*<u>reader: BufferReader</u>* reads data from the *Socket* it was instantiated with. 

*<u>run()</u>*: Will run indefinitly. it will constantly printlines from the ReaderBuffer using the *reader.readLine*() method, which returns all data from the start until the first '\n' character. It appends this return value to the interface (gui). 

The **ReadThread** is instantiated by **PlayerClient**, and can not exist without it. It is linked with up to 1 **PlayerClient** at a time. 

##### WriteThread

Very similar to ReadThread, but it reads from the Interface text-input and sends every newline to the *Socket* it was instantiated with. 

*<u>writer: PrintWriter</u>* Holds *Socket* output, ready to send any line using the .println() method. 

*<u>run()</u>*: simply gets data from the interface text-input, and calls writer.println(<text just recieved>) to send it to the Server.

Similar to **Readthread**, this is instantiated by **PlayerClient**, and needs to be linked to the **Interface** to read data. 

##### Interface

Why in the heck do we want a GUI for a text-based game. Well, we designed this design thinking of how this framework could be used by other developers who want to mod the codebase. If a developer wants to create a feature where all Users currently logged into the server and their positions is made in a new window to the left of the text, that is possible. The multiplayer aspect of this game made it a non-conventional text game, and thus called for non-conventional features. 

*<u>messages: JTextArea</u>* is the window ontop of *userInput* which displays messages from the *Socket*

*<u>gamePanel: JFrame</u>* is the window created by the GUI to hold *messages* and *userInput*

*<u>userInput: JTextField</u>* is the textbar at the bottom where the user inputs data to send to the server whcih is sent by the **WriteThread**

*<u>getInput(): String</u>* gets data from the JTextField. 

*<u>newInterface(): void</u>* is called by the constructor and creates all the bounds, window etc needed to have the game menu. 

<hr/>

For a history of the past revision (non-multiplayer), please see Assignmnet 2.md. There, we have also included a description of versions even prior to that one. 

## Object diagrams								
Author(s): `name of the team member(s) responsible for this section`

This chapter contains the description of a "snapshot" of the status of your system during its execution. 
This chapter is composed of a UML object diagram of your system, together with a textual description of its key elements.

`Figure representing the UML class diagram`

`Textual description`

Maximum number of words for this section: 1000

## State machine diagrams									
Author(s): `name of the team member(s) responsible for this section`

This chapter contains the specification of at least 2 UML state machines of your system, together with a textual description of all their elements. Also, remember that classes the describe only data structures (e.g., Coordinate, Position) do not need to have an associated state machine since they can be seen as simple "data containers" without behaviour (they have only stateless objects).

For each state machine you have to provide:
- the name of the class for which you are representing the internal behavior;
- a figure representing the part of state machine;
- a textual description of all its states, transitions, activities, etc. in a narrative manner (you do not need to structure your description into tables in this case). We expect 3-4 lines of text for describing trivial or very simple state machines (e.g., those with one to three states), whereas you will provide longer descriptions (e.g., ~500 words) when describing more complex state machines.

The goal of your state machine diagrams is both descriptive and prescriptive, so put the needed level of detail here, finding the right trade-off between understandability of the models and their precision.

Maximum number of words for this section: 4000

## Sequence diagrams									
Author(s): `Marta Anna Jansone & Theresa Schantz`

`Sequence Diagram for Server-Side Game Initialisation`
[![Sequence-diagram-valid-command-Server-side-game-initialization-2.png](https://i.postimg.cc/hjbs0ypT/Sequence-diagram-valid-command-Server-side-game-initialization-2.png)](https://postimg.cc/Fdz0rZtH)
`Sequence Diagram for readMapJson subroutine`
[![Sequence-diagram-valid-command-SD-read-Map-Json-2.png](https://i.postimg.cc/yxCd7nHQ/Sequence-diagram-valid-command-SD-read-Map-Json-2.png)](https://postimg.cc/ZBjZc80p)
`Sequence Diagram for addBasicItems subroutine`
[![Sequence-diagram-valid-command-SD-add-Basic-Items-2.png](https://i.postimg.cc/Znt4zRS6/Sequence-diagram-valid-command-SD-add-Basic-Items-2.png)](https://postimg.cc/0MfLp9rN)
`Sequence Diagram for addContainers subroutine`
[![Sequence-diagram-valid-command-SD-add-Containers-2.png](https://i.postimg.cc/7LXbMSSb/Sequence-diagram-valid-command-SD-add-Containers-2.png)](https://postimg.cc/f3twMt2h)
`Sequence Diagram for welcomePlayer subroutine`
[![Sequence-diagram-valid-command-SD-welcome-Player.png](https://i.postimg.cc/VkJcHnyd/Sequence-diagram-valid-command-SD-welcome-Player.png)](https://postimg.cc/DS3DSWwT)
## Implementation									
Author(s): `name of the team member(s) responsible for this section`

In this chapter you will describe the following aspects of your project:
- the strategy that you followed when moving from the UML models to the implementation code;
- the key solutions that you applied when implementing your system (for example, how you implemented the syntax highlighting feature of your code snippet manager, how you manage fantasy soccer matches, etc.);
- the location of the main Java class needed for executing your system in your source code;
- the location of the Jar file for directly executing your system;
- the 30-seconds video showing the execution of your system (you can embed the video directly in your md file on GitHub).

IMPORTANT: remember that your implementation must be consistent with your UML models. Also, your implementation must run without the need from any other external software or tool. Failing to meet this requirement means 0 points for the implementation part of your project.

Maximum number of words for this section: 2000

## References

References, if needed.

