# Assignment 3

Maximum number of words for this document: 18000

**IMPORTANT**: In this assignment you will fully model and impement your system. The idea is that you improve your UML models and Java implementation by (i) applying (a subset of) the studied design patterns and (ii) adding any relevant implementation-specific details (e.g., classes with “technical purposes” which are not part of the domain of the system). The goal here is to improve the system in terms of maintainability, readability, evolvability, etc.    

**Format**: establish formatting conventions when describing your models in this document. For example, you style the name of each class in bold, whereas the attributes, operations, and associations as underlined text, objects are in italic, etc.

### Summary of changes of Assignment 2
Author(s): `Theresa Schantz`

Starting Assignment 3 it was important to look at the given feedback our group received for Assignment 2, in order to improve our project. For this it was very nice to get in touch with the functionality github provides. 
<ul>
    <li>
        Use case diagram. For this, we made a completely new diagram, trying a very different approach. Hopefully, this is correct now :P 
    </li>
    <li>
        Unclear functional requirements: we edited the functional and quality requirements and added more details in order to obtain more clarity. Also, bonus assignment goals were changed and we got rid of the possible function displaying the backpack. New sorting of quality and feature requirements was also done 
    </li>
    <li>
        Undetailed Introduction: We added a more detailed description of the game mechanics, game play and an extra description of the storyline contained in our game
    </li>
    <li>
        Class diagram: in the feedback inconsistency with the UML standards were criticised. These were fixed, multiplicities and new classes were added, since during the implementation new questions arose, that were solved by implementing new classes
    </li>
    <li>State Machine Diagram: the state machine diagram was completely renewed, as it was a lot of work required to adjust it with the game mechanics</li>
    <li>
        Input length checking: if the input given by the user is too long, an error message will now be thrown to the user, telling him that the input is too long. By adding this, the user will know what to look out for when giving the next command and the system won't crash
    </li>
    <li>
        Sequence diagram: besides other changes that had to be made in order to adjust the diagram to the new game implementation, we got rid of the first alt statement, since it did not add any special behavior. 
    </li>
    <li>
        unused variables: variables that were not used during assignment 2 were completely removed or finally implemented. Health and moves now have a purpose and are being used. Health was completely removed as it did not match our expectation of the game anymore
    </li>
</ul>


### Application of design patterns
Author(s): `Florent Brunet de Rochebrune, Eduardo Lira`

![Design Patterns in Class Diagram](https://i.imgur.com/15e2WKT.png)

| ID                     | DP1 |
| ---------------------- | ------------------------------------------------------------ |
| **Design pattern**     | Command Pattern |
| **Problem**            | The game consists of a server and a client. The client will be sending commands to the server and the server will react with a response. These commands consist of objects which then are directly executed by the server. To add another command, the client and the server will both have to be updated, as the objects are hardcoded. Implementing new methods would be very inflexible and difficult, because it couples the class to a particular request. Adding or removing a command would require re-hardwiring the player client and server. |
| **Solution**           | We can solve this by creating an Instruction class. This class does not implement a request directly but refers to the server's command interface to perform a request. The client is then independent of how the request is performed. The server will handle the request and execute the command, not the client directly. Creating an Instruction class makes it possible to configure an object with a single request. The Instruction captures all the information needed to trigger an event or action at a later time. The biggest benefit is that nothing is 'hard-wired' anymore. Changing how Instructions are structured can be done by simply changing the Instruction class. |
| **Intended use**       | This allows the client to send complete Instruction objects (in our case across the network) to be executed on the server. When adding new commands to the server, only the server has to be updated as the client will only be sending requests in a generic textual form, not a specialised object that is directly executed on the server. <br>This is also a security measure, as it removes any client-side processing.|
| **Constraints**        | There is no defined way of sending these commands (yet). The next design pattern will go more in-depth for that. |

| ID                     | DP2 |
| ---------------------- | ------------------------------------------------------------ |
| **Design pattern**     | Interpreter Pattern |
| **Problem**            | With Design Pattern 1 we now have a clear way of packaging requests from a Player to the Game. However, how the requests are structured is another problem. We will need a proper grammar as the server would not know how to interpret a command without this. A grammar or syntax for the language should be specified, preferably easily understandable and extendable. |
| **Solution**           | A solution, of course, is actually making the standardized language for passing commands to the server, but how to structure it? We decided to use the following syntax: `<action> <item> <preposition> <item>`. The command is a single string, the arguments can contain more strings separated by a space. The server can interpret the first word as a command and then immediately forward the arguments to respective method. If the command is in the wrong syntax, an error should be displayed. |
| **Intended use**       | When receiving command by the client, they should be in this defined grammar. An example: The command `give wallet guard` is received by the PlayerThread and then forwarded to the Interpreter. The interpreter then selects the correct handling function, in this case `giveItem` and passes on the arguments. In this function the argument syntax is also checked (if applicable), which in this case is `<item> <proposition <item>`. The `give` command in this case would fail as the second argument is not "to". |
| **Constraints**        | The current code assumes `<action> <item> <preposition> <item>`, which is also like simple commands in natural language. E.g. "give x to y", "drop x", "shout z". If you want to change the command syntax to allow different input, for example more natural language e.g. "I want to give the wallet in my backpack to the guard that is standing at the entrance", this would not be easy. Natural language processing is already a big task on itself, but this would also require a rewrite of both the Interpreter class and the respective methods. |

| ID                     | DP3 |
| ---------------------- | ------------------------------------------------------------ |
| **Design pattern**     | Chain-of-Responsibility Pattern |
| **Problem**            | We have now defined a way of sending commands to the server in a set language. These different commands all have different actions and need to retrieve or alter different parts of the code. The problem now is that you would not want to couple one player client request to a single class of the server. Coupling one request to a single class is very inflexible, rewriting the handler of the request needs code changes on both ends and makes it impossible to support multiple receivers. |
| **Solution**           | We need to define a 'chain-of-responsibility'. This allows for one request to be always sent to the 'head' of the chain, and then find its way through to the correct handler. If at the end of the chain the request did not find a way to get handled, we also should to implement a kind of 'safety net' to catch it. |
| **Intended use**       | When a player sends a command request to the server, it will go through the chain-of-responsibility. Depending on run-time conditions, the correct handler will be chosen. Let's take for example the `shout` command, the arguments of the command should be sent by the server to all other receivers as a broadcasted message. The Player class will receive the command, as it is the head, and forward it to the PlayerThread. It is not their responsibility to handle it, so it will keep forwarding it to the server. The server can then call the respective method to handle this request. <br> Another example is when the player joins the realm for the first time. It is then the PlayerThread's responsibility to receive the request and handle the receiving of the player's username. <br>When an incorrect command is sent over the chain, the request will end up in the last part, in our case the server. The server has a handler for incorrect requests and will reply with an error message. |
| **Constraints**        | N/A |

// TODO adding commands via JSON

| ID                     | DP4 |
| ---------------------- | ------------------------------------------------------------ |
| **Design pattern**     | Decorator Pattern |
| **Problem**            | The current game as-is provides creation of areas, items and obstacles via the input JSON file. Using the built-in commands a player can traverse over the areas, pickup or drop items, use the items on other objects and more. All functionality for the original game design is implemented, the storyline can be altered via the JSON. However, it can be understandable that a different developer or modder wants to add additional functionality – read: commands – to the game, to extend beyond possible in the JSON. Not everyone has the same level of experience with coding, which could be a problem. We should provide a flexible solution to add commands to the game without altering the source code. |
| **Solution**           | A solution to this would be implementing an extended (decorated) instruction parser. This object should forward any current instructions to the default instruction parser in the game, but, when a different instruction arrives, it should parse it on its own. This parsing object could be initialized via the JSON file, making it easy to create functions for non-programmers. |
| **Intended use**       | A use-case could be, for example, the addition of a `destroy` command, with syntax `destroy <item>`. The item would be removed from the backpack and, instead of being dropped, be completely gone. For the current game story that might not be desirable, but that's open and up to the developer. |
| **Constraints**        | This pattern is not yet implemented but could serve as an idea for future versions. The commands are also based on only currently implemented commands. There are a lot of combinations to be made, but to get deeper functionality, the actual code needs to be edited. |

## Class diagram									
Author(s): `Eduardo Lira`

It is important to note that the decisions behind this design were taken to allow a multiplayer platform that can easily be expanded/modified. This design attempts to keep the principle of modularity using well-established interfaces. At times in the code (especially in the **Game**), code can be very complex. However, by keeping a simple interface, we aim to make the comprehendible with plenty of ease. 

**NB**. This project has 2 parts: A server and client side. This allows for the networked multiplayer bonus we have created. The ClientSide is very similar to a simple chat client, where a user sends instruction to the Server. The server has *Playerthreads* which reads *Instructions* from the sockets, and asks the *Game* to validate them. The *Game* will always return a string which is then forwarded to the *ClientSide* as a reply. This is a very high-level overview. 

**NB**. These descriptions only discuss the parts that are most important to get a decent understanding of the functioning of each class. Explaining all methods would not only be very verbose, but quite redundant. We have carefully chosen these as they greatly resemble the ommited methods. 

>ServerSide Class Diagram (Diagram 1)

![Server-Side class diagram](https://i.postimg.cc/BZcCqhkr/Class-diagram-Class-diagram.png)

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

>ClientSide Class Diagram (Diagram 2)

<img src="https://i.postimg.cc/CLxJTqHg/Class-diagram-CLASS-DIAGRAM-2.png" alt="Client-Side Class Diagram" style="zoom:60%;" />

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

<h5 id="whyInterface">Interface
Why in the heck do we want a GUI for a text-based game? Well, we designed this system thinking of how this framework could be used by other developers who want to modify the codebase. If a developer wants to create a feature where all users currently logged into the server and their positions is made in a new window to the left of the text, that is possible by simply adding a new window. The multiplayer aspect of this game made it a non-conventional text game, and thus called for non-conventional features. 

*<u>messages: JTextArea</u>* is the window ontop of *userInput* which displays messages from the *Socket*

*<u>gamePanel: JFrame</u>* is the window created by the GUI to hold *messages* and *userInput*

*<u>userInput: JTextField</u>* is the textbar at the bottom where the user inputs data to send to the server whcih is sent by the **WriteThread**

*<u>getInput(): String</u>* gets data from the JTextField. 

*<u>newInterface(): void</u>* is called by the constructor and creates all the bounds, window etc needed to have the game menu. 

*<u>append(String s): void</u>* this appends s to the messages panel where a player can see Server responses. A diagram of this can be found on the ClientSide Object Diagram section. 

The Interface class is made by the PlayerClient object, and passed down to the 2 Thread objects, read and write. These threads can call getInput() to get input from the interface instead of STDIN, and append(s) to write to the interface instead of STDOUT. 

<hr/>

For a history of the past revision (non-multiplayer), please see Assignmnet2.md. In that document, you will also find revisions prior to final npn-multiplayer verison. 

## Object diagrams								
Author(s): `Eduardo Lira`

Below, you will find 2 diagrams showing a "snapshot" of an instance of a VuORK. There are 2 components: *ServerSide* and *ClientSide*. Below are their figures and descriptions respectivly. 

>SeverSide Object Diagram of 2 Players logged in (Diagram 3)

<img src="https://i.postimg.cc/Z5jC5QhM/Class-diagram-Object-Diagram.png" alt="SeverSide Object Diagram of 2 Players logged in" style="zoom:150%;" />

The diagram above shows an instance of a ServerSide hosting 2 players in a very simple **Map**. The **Map** has 2 **Areas**: *Lobby* and *Entrance*. The *Entrance* has no **Items** or **Obstacles** inside of it, and is thus null. In contrast, the *Lobby* has quite a few entities to it. *Lobby* holds a *Closet*, which then holds a *Broom*. The broom is a basic item and *canBe* pickup, burn etc... In the *Lobby*, there is also one last thing, that will prevent any **Player** from seeing the entities in the room: an **Obstacle**. This **Obstacle** has been given the name "guard", and by reading the information inside guard.*howToNeutralize*, we see that the only way to neutralize this **Obstacle** is by bribing him/her. This entire **Map** has an *entryPoint* of *Coordinate(0,1)*. 

Looking torwards the right hand side of the diagram, or the **PlayerThread** side, we see the 2 players early mentioned. Each **Player** is controlled by an individual *Thread*, instantiated on the **PlayerThread** object. Each thread must have been instatiated with a *Socket*, which will be used to instantiate the **Reader** and **Writer** objects. The **Reader** objects hold the *inputStream* while the **Writer** holds the *outputStream*. Looking at 1 Player specifically, Jack, we see he is currently in the **Area** 0,1 of the **Map**. To better understand how an the *Instructions* work, pay attention to the **Instruction** object associated with *Jack's* **Parser**. To get this object, the *String* inputted must have been "Lift carpet", yield an **Instruction** with "lift" as an *action*, and {"carpet",null,null} as the *items*. The **PlayerThread** will recived this object when calling **Player.getInstruction**(), and pass it to the *game.validateInstruction*(<instruction just recieved>, <player the thread controls>). The **Game** will then decide, using the program logic, wether the move is valid, and if so, adjust the attributes of **Player** (Jack) appropriatly. *Mark* is very similar to *Jack*, except *Mark* was instantiated using its appropriate *Socket*, and *Mark* has a *Broom* and *Sword* in his *backpack*. 

>ClientSide Object Diagram (Diagram 4)

![ClientSide Object Diagram](https://i.postimg.cc/c4zhjvVC/Class-diagram-Object-Diagram-2-1.png)

In the ServerSide, we saw the **Player** recieving input *Strings* from the **Parser**, which held a connection to *Socket*. The otherside of a *Socket* belongs to the system above, a *ClientSide* System. This system is simply a multiThreaded chat-cleint to the ServerSide. When a user inputs a *String* to send to the *Server*, the Server will recieve this *String*, process it when ready (*game.isbusy* == false). Then, the Server will handle the request, and ALWAYS reply with a *String* indicating failure/success of the action taken. The *Server* may also reply with more verbose information such as **Area** description etc. The asynchronus (multithreaded) approach of the **playerClient** allows the *Server* to send information to the *Client*, without the *Client* have sent a request first. This is especially useful when inter-player interaction takes place. An example of inter-player interaction is talking, or trading. 

The Interface is also linked to the read and writer threads. This is needed to direct I/O to the correct panels for the interface. Why we decided to use this interace is better described in <a href="whyInterface">ClassDiagram.Interface</a>. Below is an image of the **Interface** we created: 

<img src="https://i.postimg.cc/vHbZ16dK/Screen-Shot-2020-03-30-at-9-35-39-AM.png" alt="Image of Client Interface" style="zoom:30%;" />

## State machine diagrams									
Author(s): `Irene Garcia-Fortea Garcia, Florent Brunet de Rochebrune`

<h5>State-Machine Diagram for Getting Inputs for Player</h5>

> State machine diagram of Game (Diagram 5) **DESCRIPTIVE**

[![State-Machine-Diagram-game-class.png](https://i.postimg.cc/cHtqYYT2/State-Machine-Diagram-game-class.png)](https://postimg.cc/1nSY18kr)

The diagram above shows the State Machine Diagram of the event that is executing a command. After starting the process, the system waits for a user input. After the input has been received, the function *getCommand()* executes. 

In the next step we move to the state that the command has been received. The command needs to be checked, whether it is a valid command that the system accepts in *validateCommand()*. 

If the command is not valid, an error message is printed and the program returns to the idle state, waiting for a command. 

If the command is valid, the command needs to be executed ("*Execute Command*"). There, changes will be made to the Item or Player (Described in the *Execute Command* figure). After the changes have been made, the system goes back to the idle state, waiting for a new command to process, unless the user types “quit”. Then, the game terminates. 

The *Execute Command* state works as the following: After checking the command for validity, it will be matched matching it with each of the following existing commands: move, help, look, list, drop, take/get/pickup, give, score and text/deliver/broadcast. 

If the command is move: the player will be moved into the wanted direction (input by the player).

If the command is help: a help message will be printed to the user.

If the command is look: the description of the *Area*, in that the *Player* is currently in, will be printed to the user.

If the command is list: a list of available commands will be displayed to the user.

If the command is drop: the player will drop the *Item* (input by the player) from the backpack into the current *Area*.

If the command is take/get/pickup: the object from the current *Area* will be placed into the backpack (if the *Area* has it).

If the command is give: the player will give the *Item* to the *Obstacle* in the current *Area*. E.g. wallet to guard.

If the command is score: the score of the user will be printed.

If the command is text/deliver/broadcast: the input message will be sent to every other *Player* currently in the server.

<h5>State-Machine Diagram for Player Movement</h5>

> Figure representing the state machine diagram of the class Map (Diagram 6) **DESCRIPTIVE**

[![State-Machine-Diagram-map-class.png](https://i.postimg.cc/RCf5Z7Wn/State-Machine-Diagram-map-class.png)](https://postimg.cc/0r8HtJd8)

Diagram (4) illustrates the machine state diagram of the class *Map*, in which the user's command input triggers certain movements or does nothing, which will be described next.

Starting from the initial pseudo state the machine will start in an idle state, waiting for input from the user.. After there is a trigger for input, a selection based on the direction input will be evaluated. The choices are consisting of a set of four directions in a form of simple states with their name compartments determining their directions (*North*, *East*, *South* and *West*) as well as an *Else* state in the form of error-handling given the *Player*’s direction input would not be valid.

In the case of such direction not being one of the possibilities, for example, *North-East*, or if the move is not valid in terms of the *Area* chosen being non-existent as their could be obstacles (a wall) that would make the move into that direction unable to be performed, the function would terminate enclosing the area in that direction doesn’t exist and it would then enter into the final state in which it would be signified that the enclosing region is then completed. After this it would enter the *printInvalidMove* state in which it would return a message to the *Player*, determining their desired direction to take is unfortunate1ly not valid, in which the user will be able to input another command which would hopefully be one of the valid options which are *North, South, East, West*. The machine will also return to the idle state, waiting for input again.

If the command followed such terms the selection of simple states composing the previously stated directions are coordinated by their internal activities compartments showing their coordinates, which will correspondingly be shifted depending on their component type (x or y) and values (+1,-1) due to Coordinate being an immutable object. 

Finally we end up in the updating player position state, which updates the *Player*’s coordinate position with the value set in the previous state. When this is done you reach the final state.

## Sequence diagrams									
Author(s): `Marta Anna Jansone, Theresa Schantz`

### Game Initialization

The first event described using sequence diagrams is the initialisation of a new game map an a new player joining the game. The game map is created when the server is started and, hence, occurs only once (does not repeat when a new player joins). The represented events in the sequence diagram are visualised from the server side - what action occur when a new player joins the game. 
This is further represented within 5 sequence diagrams using references from one diagram to another in order to make it more clear. The sequence diagrams include only the project classes (the library functions are excluded).

<h5 id="gameStart">Sequence Diagram for Server-Side Game Initialisation</h5>

[![Sequence-diagram-valid-command-Server-side-game-initialization-2.png](https://i.postimg.cc/8zt083dQ/Sequence-diagram-valid-command-Server-side-game-initialization-2.png)](https://postimg.cc/NK24mdPp)

When the server is started a new *Game* object is declared. The *Game* object is further responsible for declaring a new object of type Map. The constructor of *Map* calls the function *initMapFile()*, which is responsible for reading in a JSON file that contains all of the attributes (areas, coordinates, items, obstacles) of the map. The next function called is *readCoordinateFromString(string)*, where the function argument is the JSON object denoted by *entryPoint*. The function creates a new object of type *Coordinate* and assigns its' value with the information read from the string. This coordinate is returned to the *initMapFile()* function and assigned to the variable *entryPoint*. Further all of the attributes of the map are assigned, which is described more in detail in the <a href="#readMapJson">Sequence diagram for readMapJson subroutine</a>.
Once the map has been initialised, the *Game* object calls the function *runServer()*. Within the function *runServer()* the function *getEntryPoint()*, which belongs to the object *Map*, is called. The return value from this function call is then passed on to the initialisation of a new object of the class *PlayerThread*. The object of class *PlayerThread* further creates a new object of class *Player*, which in turn creates two new objects - one of class *Parser* and the other of class *Printer*. Now when the new player has been initialised using the *PlayerThread*, the function *welcomePlayer()* is called, which is explained more in detail in the <a href="#welcomePlayer">Sequence diagram for welcomePlayer subroutine</a>.

<h5 id="readMapJson">Sequence Diagram for readMapJson subroutine</h5>

[![Sequence-diagram-valid-command-SD-read-Map-Json-2.png](https://i.postimg.cc/tRd0SBcy/Sequence-diagram-valid-command-SD-read-Map-Json-2.png)](https://postimg.cc/jCjkC4Kk)

Within the function *initMapFile()* shown in the <a href="#gameStart">diagram above</a> the private function of the class *Map* is called. The next function call is also of a private function belonging to the same class - the function *readCoordinateFromString(string)* is again called, however, this time the argument passed is the coordinate of the specific area being initialised. The function returns an object of class *Coordinate* and it is assigned to the variable *areaCoordinate*. Next the two functions *x()* and *y()*, belonging to the object of class *Coordinate* are called. Both return the corresponding *x* and *y* values of the *areaCoordinate* that was just initialised. Using these coordinates a new object of class *Area* is created. Further, using the information within the JSON file a new object of class *Obstacle* is also created. Then using the function *setObstacle(Obstacle)* of the class *Area* the just created obstacle is assigned to the *Area* previously created. 
Next, the private function *addAreaItems(Area, JSONObject)* of the class *Map* is called. The arguments passed to this function are *Area* and a JSONObject, because of that before calling this function the functions *x()* and *y()* are called again in order to get the *Area* corresponding to the coordinate. This function first calls the private subroutine/function *addBasicItems()*, which is explained in more detail in <a href="#readMapJson">Sequence diagram for addBasicItems subroutine</a>. The return value from the *addBasicItems* is a list of all of the items that have to be added to the specific *Area*. Therefore, the next fragment is a loop that iterates the number of times as there are items in the list returned. In each iteration the function *addItem(Item)* of the class *Area* is called. Within the next fragment the function *addContainers()* is called, this is explained in more detail in <a href="#readMapJson">Sequence Diagram for addContainers subroutine</a>. 

<h5 id="addBasicItems">Sequence Diagram for addBasicItems subroutine</h5>

[![Sequence-diagram-valid-command-SD-add-Basic-Items-2.png](https://i.postimg.cc/pTqTKPzc/Sequence-diagram-valid-command-SD-add-Basic-Items-2.png)](https://postimg.cc/cgt0SyDf)

The *addBasicItems(JSONObject, Area)* function is called within the function *addAreaItems()* showed in a <a href="#readMapJson">sequence diagram above</a>. Within this function call the private function *getActions()* is called twice - the arguments of the function are strings from the JSON file corresponding to objects *"canBe"* and *"usedTo"*. The function *getActions()* returns a list of strings where these actions are taken from the JSON file. Using the information read from the file a new object of class *BasicItem*, belonging to an abstract class *Item* is created. The sequence of events just described is repeated in a while loop, while the JSON file still has objects in it. All of the created objects of *BasicItem* are appended to a list of items and this list is the return value from the function call *addBasicItems()*.

<h5 id="addContainers">Sequence Diagram for addContainers subroutine</h5>

[![Sequence-diagram-valid-command-SD-add-Containers-2.png](https://i.postimg.cc/tC75ZTWf/Sequence-diagram-valid-command-SD-add-Containers-2.png)](https://postimg.cc/9r5Tscxt)

The *addContainers(JSONObject, Area)* function is called within the function *addAreaItems()* showed in a <a href="#readMapJson">sequence diagram above</a>. As each container stores a list of basic items within it - the first event that occurs is the function *addBasicItems* is called, which is described in more detail in the <a href="#addBasicItems">sequence diagram above</a>. Further, a similar thing as described before happens again - each container also has a list of strings of *canBe* and *usedTo*. Therefore, the function *getActions()* is again called twice. Using he information gathered from the JSON file and the function calls to retrieve information from it a new object of class *Container*, belonging to an abstract class *Item* is created. The object of class *Container* is then added to the specific object of class *Area* using the function *addItem(Item)*. The sequence of events just described is repeated in a while loop, while the JSON file still has objects in it.


<h5 id="welcomePlayer">Sequence Diagram for welcomePlayer subroutine</h5>

[![Sequence-diagram-valid-command-SD-welcome-Player.png](https://i.postimg.cc/4ycpPKPN/Sequence-diagram-valid-command-SD-welcome-Player.png)](https://postimg.cc/vxQ1HZCp)

The *welcomePlayer()* function is called from the object of class *PlayerThread* once everything (*Parser*, *Printer*, *Player*) have been initialised. The *PlayerThread* starts by calling a function *output()* belonging to *Player*. The function *output()* that belongs to the *Player()* then calls the function with the same name belonging to the class *Printer*, which then further takes care of printing out a welcome statement to the player in the game interface. Further the *PlayerThread* calls a function *getLine()* belonging to the class *Player*, which then further calls a function of the same name from class *Parser*. In the welcome message the player was asked to give their name, so the function calls *getLine()* return the text that the player input within the interface. The return value is further used by *PlayerThread* as a function argument when calling the function *setName()* that belongs to the class *Player*. This call assigns the name to the specific player.
Next, the *PlayerThread* calls the public function *getName()* belonging to the class *Player*, the return value from this function is a string which is used to again perform the sequence of outputting a message to the game interface. The message printed includes the player's name and a description of the game. Further the public function *position()* of class *Player* is called, the function returns an object of class *Coordinate*, which is further passed on as an argument to the next function call - *getAreaDescription()*. *getAreaDescription()* is a public function of class *Game*. This function within the *Game* object further calls the public function belonging to class *Map* - *getDescription()*. The *getDescription()* function first makes two function calls to class *Coordinate* - *x()* and *y()*, which return the corresponding *x* and *y* coordinates of the *Area* of interest. Using the coordinates the specific object *Area* is located within the object *Map*. Then the function *getName()* is called specifically on the *Area* just located. The function call return a string of the name of the area. 
Next, the *Map* calls the public function *getDescription()* belonging to the *Area*. Within the class *Area* function call *getDescription()* the private function *canEnter()* is called. This function further calls the function *isNeutralized()* belonging to the class *Obstacle* to see whether the player can move to this area - whether the obstacle is still present. The function call *canEnter()* returns a boolean variable. After the function return of *canEnter()* an alternative fragment follows - if the *canEnter()* returns false the string that will be returned back to the *PlayerThread* is from the function call *getDescription()* belonging to the class *Obstacle*, if the *canEnter()* returns true - the string returned to the *PlayerThread* will be from the private function call *getItemsDecription()* within the class *Area*. Within this function the function *getName()* belonging to the abstract class *Item* is called within a loop for as many times as there are items within the area. The string is then returned from the *getItemsDescription()* function of *Area* (or the *getDescription()* function of *Obstacle*) to the previous function call of *Map*, which is then returned to *Game*, which is then finally returned back to *PlayerThread*. The final message is again printed using the sequence of *output()* functions discussed 

### Valid Command: Move

The second event described in the sequence diagram above is the command processing, for the case that the *act* variable, given by the user is *move*. The program will constantly loop, outputting instructions and reading commands from the user. 
The PlayerThread class will start the whole process by calling *getInstruction* from the *Player* and the *Player* will then call *getInstruction* from the *Parser*. *Player.getInstrcution* is therefore only a pass-on function here. The Parser gets input with the *Scanner* and produces a new *Instruction* with this input.This *Instruction* will be passed back to the *PlayerThread* via the *Player* and stored in the *currCommand* variable. Then the *validateCommand* function in the *Game* class, called by the *PlayerThread*, is executed. The big process starts here.The *Game* asks the *Instruction* to return the *action* variable from the given command. Next, it is checked whether the *action* is a direction. If it is, the *handleMove* of the *Player* class is executed. Here, a new coordinate (from the class of the same name *Coordinate*) is set. Depending on the given direction the x- or y-coordinate will be in- or decreased. After that, this *newPos* is returned to the *Player* and stored there. Next, it is checked whether there are obstacles in the *newPos* of the *Player*. This is done by calling the function *hasObstacles()* from the *Map*. The *Map* needs to get the position from the *Player*, before it can return something. This, and the next step *oppositeDirection* is checked, that if there is an obstacle in the new position, the player must have the opportunity to go back the way he came. 
If it was checked whether the new possible position would be a valid move in *isValidMove* and if it returns *true*, the player is finally actually being moved to the new position. In the steps before, the position was only theoretically moved and checked whether it is possible. The variable *lastValidDirection* is set to the direction in the function *setLastValidDirection* and stored in the *Player*. Then, the *Game* gets the description of the new position from the *Map* via the *Printer* and the *Player, and returns it back to the *Printer* so it can be printed to the user on the terminal with the *output* function. If the *isValidMove* function returns *false*, an error message will be printed to the user with the *Printer* via the *Player.output* call. 
The last step returns the description from the *Game* back to the *PlayerThread* and stores it as the *result*. The result is then printed to the user screen with the *Player*s *output* function, which in turn passes the *result* to the *Printer*. This process is repeated an undefined number of times, until (hopefully) Thilo can be saved. 
This is only the procedure of the command *move*. Other commands will have a much simpler sequence diagram, as they require less processing.ggi Commands such as *score* or *look* will only return the values of variables or the description of what the player is able to see in the current position, respectively.

>Sequence Diagram Valid Command: Move (Diagram 12)

[![Sequence-diagram-valid-command-valid-command-1.png](https://i.postimg.cc/fbHvdP7v/Sequence-diagram-valid-command-valid-command-1.png)](https://postimg.cc/v1V9F3N1)

## Implementation									
Author(s): `Eduardo Lira, Marta Anna Jansone, Irene Garcia-Fortea Garcia`

It was surprisingly quick to implement our system after having thoroughly thought it out using UML diagrams. However, as discussed in our development of the class diagram, a lot had to be changed as challenges arised, while trying to implement our design. Our strategy was quite simple: once we were satisfied with the design, we tried to write and implement it. As we encountered issues or areas of improvement, we rethought and edited our design in UML, and implemented that.

The greatest challenge of creating a text-adventure game, in our opinion, is how to interpret natural language as structures and objects that exist in a game, and translate them such that the program can understand and handle them. To add to this complexity, having several players on a game at once makes it incredible to reason on how to handle requests. Of course, once you discover a solution, it is obvious, but the mental hurdle of getting there was significant.

For the rest of the game, it was suprising how taking the time to properly formulate ideas saves time in the long time. The multi-threaded server was very quick to implement after having thought it through. The UML diagrams were followed regerously and were the backbone to this entire project. However, there may still be parts that are unclear about the implementation and that will be described here: 

1) **The spinlock:** The spinlock is essential to ensure the integrity of the game at any time. Without, 2 players sending the same request "take wallet" may lead to undefined behavour. There may be 2 wallets now. There may be a single wallet but both players have references to them. This is very dangerous behaviour and must be properly handled to ensure consistency with the game expected behaviour and actual behaviour. To implement this, each playerThread has the following check: 

```Java
while (server.isGameBusy()) {}    
```

The boolean flag, *isGameBusy*, is set to true the instant any thread enters the <u>Game.validateCommand(Player)</u> function. Just before exiting the function, after all modifications have been made, the flag is set to false, letting the Thread that next runs to validate its command. The pattern goes on. 

2) **Items**: When carefully inspecting the {abstract} **Item** class and the **BasicItem** class, you will notice that **BasicItem** is an extension to the **Item** Class, but adds no extra functionality. While this is an odd decision, it is important to remember that we designed this sytem with expandibility in mind. An Item represents any sort of entity and Player can interact with: Hence the canBe and usedTo attributes. This allows the developer to add new sorts of interactibles such as monsters or magic powers. 

3) **PlayerThread**: It was a tough decision; Do we make a new class just to multiThread or do we allow the already existing class **Player** extend the *Runnable* interface? From above, it is obvious we stuck to the prior. We choose this method as it follows the principle of modularity and layers. Much like in a network protocal, changing the implementation of one aspect of the network shouldn't affect the system as long as the interface remains the same to the layers above and below. Equally, if a developer wants to change how the spinlock, works, or how the handshake is created to a **Player**, he/she should be able to do so without needing to change **Player** Object. Why would a **Player** object (conceptually) know how to great him/herself to a server?

4) **Identifying objects:** A big mental hurdle was reasoning how to handle a String input and interpret that as a object. Our solution was as follows: Use *Strings* as names. Then to identify an object, start searching in the field of view of the player. The Parsing is done by the *Parser*, but it is the *Game* object that makes reason of it and decides what to do. We define the field of view as the extent a *Player* can interact with. This is the *Player*'s *backpack* and the *Area* he is in. When an Interaction is needed to be done, the *Game* will need to reason whether this element is in the *Player*'s possession or in the *Area*. If no such *Item* exists that *canBe* or *usedTo* variables match the *Action*, an appropriate error is returned. 

5) **Initialising the map:** Initialising the game map with all of the *Areas* with the corresponding *BasicItems*, *Containers* and *Obstacles* started out as quite a big issue for us. The game has many *Areas*, each *Area* might have multiple *Items* and each *Item* might have multiple actions that it can be used for, therefore, adding this manually line by line and hardcoding the objects was not an option. Our solution: adding all of the object to a JSON file! We made this choice as by keeping the names of the objects in the JSON file consistent the initialisation of the map can be done in a couple of *while loops* and, therefore, the game can easily be extended by just modifying the JSON file. The map in our game is initialised from a JSON file, which consists of: map size (number of rows/columns the map will have), entry point (the coordinate where each new player will be placed at the start of the game) and the map itself. The map object within the JSON file consists of areas, which are denoted by a coordinate. Given the coordinate within the JSON file the corresponding coordinate in the 2D array is initialised with this area. Each of the coordinate objects also contains object for: name (the name of the area), description (the description of the area), items (the basic items within the area), containers (the containers within the area) and obstacles (the obstacles within the area) . The objects for items and containers are further again separated into smaller objects necessary for initialising an item. When initialising the map the objects within each of the coordinates are added to the area in the map. 

The points above should address the most complex parts about our code base. Of course, questions may still linger, and if so, we hope we have explained enough in this document so that looking at the code should make any remaining questions self-evident. 

The location of the runnable Java class for the SERVER is:

> src/main/java/Game.java

The lovation of the runnable Java class for the CLIENT is: 

> src/main/java/PlayerClient/PlayerClient.java

The location of the Jar file for the SERVER is:

> out/artifacts/GameServer/software-design-vu-2020.jar

The location of the Jar file for the CLIENT is: 

> out/artifacts/PlayerClient/software-design-vu-2020.jar

The location of the JSON file for instantiating our game Map can be found it: 

> out/artifacts/GameServer/map.json

<hr/>

The video of the gameplay is here:

<img src="Gifs/demoGame.gif" alt="GIF of final VuORK" style="zoom:80%;" />

## References

References, if needed.

