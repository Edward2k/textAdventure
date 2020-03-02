

# Assignment 2

Maximum number of words for this document: 12000

**IMPORTANT**: In this assignment you will model the whole system. Within each of your models, you will have a *prescriptive intent* when representing the elements related to the feature you are implementing in this assignment, whereas the rest of the elements are used with a *descriptive intent*. In all your diagrams it is  strongly suggested to used different colors for the prescriptive and  descriptive parts of your models (this helps you in better reasoning on  the level of detail needed in each part of the models and the  instructors in knowing how to assess your models).

**Format**: establish formatting conventions when  describing your models in this document. For example, you style the name of each class in bold, whereas the attributes, operations, and  associations as underlined text, objects are in italic, etc.



### Implemented feature

| ID   | Short name | Description                                                  |
| ---- | ---------- | ------------------------------------------------------------ |
| F1   | Tags       | Code snippets can be tagged via freely-defined labels called tags |

### 

### Used modeling tool

We are using draw.io



## Class diagram

Author(s): Eduardo Lira

```
Class Diagram of VuORK
```

![Class Diagram of VuORK](https://i.ibb.co/9W0LPmq/Class-diagram-Class-diagram-1.png)

<h5 id="Item">Item</h5>

The Item class is an abstract type which can be either a _Container_ or a _Singleton_. The Item class represents an entity the user can interact with. Arbitrary examples may be: *swords, spoons, pens, etc..*. This class is abstract because an Item can be (exclusivly) one of two types: Container or Singleton. 

<u>*Name*</u> is a string that gives the entity a name. Pretty self explanatory. It is needed so that the game can tell the user (in words), what the item represents.

<u>*ID*</u> is a uniquie integer which will differentiate all objects. This is needed to tell apart two objects with the same *Name*. 

*<u>getName()</u>* returns the name of the Item

<u>*getID()*</u> returns the ID of the item

Item has a composition relation to (exclusivley) *Area* or a *Player*. This is because an Item can not both exist outside of the *Player's backpack* and in the surrounding area or vice versa. Furthermore, an Item can not exist without an *Area* or *Player's backpack* to exist in. 

<h5 id="Singleton">Singleton</h5>

Singelton is a subtype of <a href="#Item">Item</a> that a player can interact with. It is the most basic type of item. It inherits properties from Item, such as the Name and ID and their consequent getter functions. 

An Item can be interracted with, or used to interract. To elaborate, imagine the command ***Attack chair with sword***. Here, the action attack has 2 clauses: the dependent and independent. This means that singletons need a way to know what can be done to it (ie be attacked), and what it can be used to do (ie attack). In this case, the singleton chair would have the string *Attack*, in its *UsedTo*.

*<u>canBe</u>* is an array of strings. These strings list what can be done to the item. Taking the example of the chair and sword from above, the chair would have the string "Attack", in this array. 

*<u>usedTo</u>* is an array of strings. These strings list what the item can be used to do. Taking the example of the sword from above, the sword would have the string "Attack", in this array.

*<u>isValidAction(string action)</u>* returns a boolean on the condition that the array usedTo contains the string action. If it does contain the string, it returns true. 

*<u>isValidManipulation(string manip)</u>* returns a boolean on the condition that the array usedTo contains the string action. If it does contain the string, it returns true. 

Because Singleton is a sub-type of *Item*, it follows the same matually exclusive composite relation seen Item has. The aggregate relationship indicates that any number of *Singleton* objects, may be contained in a *Container*. 

<h5 id="Container">Container</h5>

Container is a subtype of Item. It is different from a Singleton because the only action that can be performed on a container is the *<u>toggle(state: boolean)</u>* Also, you can see that a container may contain any number of Singletons. The idea is that a container may be, to bring a realworld example, a (but not limited to) a fridge or drawer. These can be opened and reveal any number of items. 

*<u>isOpen</u>* is a boolean variable that checks wether the contents of the container are available to the surrounding *Area* and, thus, *Player*. The entities within the *Container* can not be accessed unless this condition is true. 

*<u>entities</u>* is an array of *Singleton*. It lists all the *Singleton* currently contained in the item. 

*<u>toggle(state:bool)</u>* is a method function which will open/or close the container. If state is TRUE, then *<u>isOpen</u>*, will be set to TRUE.

It is important to note the aggregation from *Singleton*. This simply indicates that an instance of *Container* may contain any number of *Singleton* objects, but the existance of *Singleton* objects does not relay on the existance of the *Container* object.

<h5 id="Area">Area</h5>

Area is a class which represents a region any *Player* can exist in. As a realworld example, this could be a classroom, a bathroom, kitchen etc. We assume that when a *Player* is in a region, he/she can immediatly interact with the objects in the field of view of that room. This means that, if there is a fridge and a pen on the floor, the *Player* can open the fridge for pick up the pen without having to move inside the room.

*<u>items: array <Item\></u>* is an array of items that holds the contents of the room. 

*<u>obstacle: Item</u>* is an *Item* which must first be destroyed before a *Player* can enter and explore the *Area*.

*<u>obstacleNeutrelized: Boolean</u>*:  is a condition to see if the Obstacle was properly handled. Imagine there was a Troll in the room, preventing us from exploring or entering the room. We must first kill the Troll before entering. 

*<u>name: string</u>* is a string that stores the name of the *Area*. 

*<u>getDescription(): String</u>* will generate a string that will describe the room. It will follows the structure:	

> You are now in <name\>. You can not enter the room because there is a <obstacle.getName()>. First neutralize the <obstacle.getName()> before entering the room. [if obstacleNeutrelized(), continue]: In this area, you see <items\>. 

​	This gives a very clear description of how to structure the description of the room. 

*<u>canEnter(): boolean</u>* returns the obstacleNeutrelized private member. 

*<u>getName(): String</u>*:  returns the name private member of the room.

*Area* has two direct relations. It has an (exclusive or) relation from *Item*. Why this is, is further described in <u><a href="#Item">Item</a></u>. An Area also has a composite relation to the class *Map*. This is because the existance of *Area* is entirely dependent of the existance of *Map*.

<h5 id="Map">Map</h5>

The *Map* class holds all the references to *Area* together, in some emposed ordering. The ordering is defined upon instatiation of the class. This is because an *Area* does not, conceptually, know where it is in a *Map*. That is the purpose of the *Map*. 

<u>*gameLayout: Array<Array< Area> >*</u> is a 2-Dimensional array which holds references to *Area* objects. 

*<u>entryPoint: Coordinate</u>* is *Coordinate* which gives the starting point of the *Map*

*<u>isValidMove(Coord: Coordinate): Boolean</u>* is a simple function to check wether the given position is valid. That is, is the position given by the *Coordinate* not a NULL pointer. 

*<u>getGameLayout(): Array<Array<Area\> ></u>* is a getter function for gameLayout. It will return a **copy** of gameLayout to prevent a reference of <u>gameLayout</u> from ever being editted outside of the Map class. 

*<u>getEntryPoint(): Coordinate</u>* returns entryPoint.

The relations *Map* has is quite simple. *Map* has a aggregate relation to *Game*. This means that an instance of *Map* can exist a *Game*, but the multiplicity of 1, means that at any time, there can be at most 1 *Map* to a *Game* object. 

<h5 id="Game">Game</h5>

The *Game* class is what runs any instance of a a running game of VuORK. It glues together all of the higher-order class described in this section. It is important to note that VuORK can be multiplayer, which heavily influneced this structure [Futher described in Player].

*<u>users: Player</u>* holds all of the players in the current instance of *Game*. This is an array. 

*<u>time: timestamp</u>* is an object of type timestamp holding the time the game started. timestamp is part of the standard library for Java. 

*<u>layout</u>* holds the object for *Map*. This is the playarea for *Player* objects to explore. 

*<u>getGameState(): String</u>* is a function which will return a string containing the high-level overview of the game. 

The relationships to *Game* are very specific. However, these relations are further described in *Map* and *Player*. At a highlevel, a *Game* can have at most 1 map, but can have any posotive number greater than 1 of *Players*. 

<h5 id="Player">Player</h5>

A *Player* is the class that a User will take control of. The user interacts to the *Player* using the *Parser* [Further described in *Parser*]. A *Player* is a class which also enables the user to explore the world. 

*<u>name: String</u>* is a string which holds the name the user first put in when logging in to VuORK. This must be unique from all other users in the *Game* object. 

*<u>coord: Coordinate</u>* stores where the player currently is in terms of the *Map*. It should be instatiated to the specified *Map.getEntryPoint()*. 

*<u>backpack: Array<Item\></u>* stores references to all of the *Items* the *Player* currently has in his possesion. Instantiated to NULL

*<u>score: Int</u>* is an integer indicator denoting how well the player is playing. It is calculated by taking the number of <u>moves</u> * (number of minutes played) * *health*

*<u>moves: Int</u>* is an integer which counts the number of commands the user has inputted. 

*<u>health: Int</u>* is an integer holding the health of the *Player*. It is between 0 (dead) and 100 (full health).

*<u>getUserName(): String</u>* returns <u>name</u>

*<u>getHealth(): Int</u>* returns *health*

*<u>getBackpack(): String</u>* returns a formatted string of items in <u>backpack</u>

<u>movePlayer(coord: Coordinate): Bool</u> will move a player iff the condition *Map.isValidMove(coord: Coordinate)* is true.

*<u>getCurrentPosition(): Coordinate</u>* returns the value of <u>coord</u>

The relations of Player are a little complex. A player must contain a *Parser* and a *Printer* (equally, these 2 must can not exist without a *Player*). Much like in *Area*, a *Player* object may contain a reference to an *Item*, iff this *Item* does not already exist in an *Area*

<h5 id="Coordinate">Coordinate</h5>

A *Coordinate* is a very simple class to group together an <u>x</u> and <u>y</u> integer variable that represent a positon on a 2D array of *Map*. 

*<u>xCoord: Int</u>* is the x component

*<u>yCoord: Int</u>* is the y component 

*<u>getx(): Int</u>* returns the x coordinate

*<u>gety(): Int</u>* returns the y coordinate

*Coordinate* has very simple relations. *Coordinate* is contained in exactly 1 *Player*, or exactly 1 *Map*. There can exist any number of coordinates, however, these coordinates must be unique and their references can not be shared upon the *Map* and *Player*.

<h5 id="Parser">Parser FIXME!</h5>

A *Parser* will take an input from the standardIn, and seperate it into a list of actions and a list of items. This will give a way to convert from the complex semantics of language into a form that the parser can try to parse. The *Parser* expects an input in the form <action\> <items/prepositions...*>. More information on this can be found under features. 

*<u>input: String</u>* holds the last input of the user

*<u>actions: Array<String\></u>* holds a list of actions parsed from <u>input</u>.

*<u>items: Array<String\></u>* holds a list of items the user wants to interact with. 

*<u>getAction(): Array <String\></u>* will return a copy of <u>actions</u>

*<u>getItems(): Array<String\></u>*will return a copy of <u>items</u>

*<u>parseInput(): void</u>* will take input and fill parse the actions and items into <u>actions</u> and <u>items</u>

*<u>getLine(): void</u>* will get a line from the stdin. 

Each *Player* will have their own *Parser*. When the *Player* is terminated, the *Parser* will also be terminated. Each *Player* can have at most 1 *Parser*, and each *Parser*, can have at most 1 *Player*

<h5 id="Printer">Printer FIXME!</h5>

A printer simply outputs strings to the user. It is a nice class to prevent several objects interfacing with the player. 

*<u>print(output: String): Void</u>* will print the string output to stdout. 

While the *Printer* is assosciated to a *Player*, it is the *Players* interaction that will allow the *Player* to print things. For example, when the *Player* explores the room, and <u>Area.getDescription</u> is called from *Area*, then *Player* will have to pass that string to its *Printer*. Each Player has 1 *Printer* and Vice Versa. 

<hr />

Also, you can briefly discuss fragments of previous versions of the  class diagram (with figures) in order to show how you evolved from  initial versions of the class diagram to the final one.

In this document you have to adhere to the following formatting conventions:

- the name of each **class** is in bold
- the *attributes*, *operations*, *associations*, and *objects* are in italic.

Maximum number of words for this section: 3000

## 

## Object diagrams

Author(s): `name of the team member(s) responsible for this section`

This chapter contains the description of a "snapshot" of the status of your system during its execution. This chapter is composed of a UML object diagram of your system, together with a textual description of its key elements.

```
Figure representing the UML class diagram
Textual description
```

Maximum number of words for this section: 1000

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

Author(s): `name of the team member(s) responsible for this section`

This chapter contains the specification of at least 2 UML sequence  diagrams of your system, together with a textual description of all its  elements. Here you have to focus on specific situations you want to  describe. For example, you can describe the interaction of player when  performing a key part of the videogame, during a typical execution  scenario, in a special case that may happen (e.g., an error situation),  when finalizing a fantasy soccer game, etc.

For each sequence diagram you have to provide:

- a title representing the specific situation you want to describe;
- a figure representing the sequence diagram;
- a textual description of all its elements in a narrative manner (you do not need to structure your description into tables in this case). We expect a detailed description of all the interaction partners, their  exchanged messages, and the fragments of interaction where they are  involved. For each sequence diagram we expect a description of about  300-500 words.

The goal of your sequence diagrams is both descriptive and  prescriptive, so put the needed level of detail here, finding the right  trade-off between understandability of the models and their precision.

Maximum number of words for this section: 3000

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