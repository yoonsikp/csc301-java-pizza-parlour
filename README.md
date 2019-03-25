# a2-starter

# Quick Run
Clone the repo and run:
```
$ java -jar a2_pair55.jar 
```
You will be presented with the following prompt:
```
   ____ ____   ____ _____  ___  _                         
  / ___/ ___| / ___|___ / / _ \/ |                        
 | |   \___ \| |     |_ \| | | | |                        
 | |___ ___) | |___ ___) | |_| | |                        
  \____|____/ \____|____/ \___/|_|                        
  ____  _              ____            _                  
 |  _ \(_)__________ _|  _ \ __ _ _ __| | ___  _   _ _ __ 
 | |_) | |_  /_  / _` | |_) / _` | '__| |/ _ \| | | | '__|
 |  __/| |/ / / / (_| |  __/ (_| | |  | | (_) | |_| | |   
 |_|   |_/___/___\__,_|_|   \__,_|_|  |_|\___/ \__,_|_|   
                                                          
Type '?' at any time for help. End program by typing 'exit'.
/$ 
```

# Usage

The PizzaParlour has three major states it can be in. The state is always displayed by the prompt:

### 1. No Order Selected (Main Menu)
```
/$ 
```
### 2. Order Selected (Create Dishes)
```
/Order_0$ 
```
### 3. Dish Selected (Modify or Remove Dish)
```
/Order_0/Food_Drink-SPRITE$
```

At any state, either the `?` or `help` command will print out the list of available commands. These are the commands for each state:

### 1. No Order Selected (Main Menu)
```
	menu               	Print out the Current Menu
	menuitem ITEM      	Print out the Price of a Menu Item
	neworder           	Create an Order at the Pizza Parlour
	selorder [ORDER_ID]	Select an Order, can optionally specify ID
	lsorder            	List all Current Orders and their IDs
```

### 2. Order Selected (Create Dishes)
```
	menu              	Print out the Current Menu
	menuitem ITEM     	Print out the Price of a Menu Item
	rmorder           	Cancel the Currently Selected Order
	deliver           	Request for Delivery Service
	rmdeliver         	Cancel Delivery Service
	printdeliver      	View Delivery Details
	printorder        	Details about the Current Order
	newpizza          	Add a Pizza to the Current Order
	newdrink          	Add a Drink to the Current Order
	seldish           	Select a Dish in the Current Order
	lsdish            	List all Dishes in the Current Order
	..                	Deselect Currently Selected Order
```
### 3. Dish Selected (Modify or Remove Dish)
```
	menu              	Print out the Current Menu
	menuitem ITEM     	Print out the Price of a Menu Item
	chdish            	Modify the Current Dish
	printdish         	Print Info about the Current Dish
	rmdish            	Remove the Current Dish from the Order
	..                	Deselect Currently Selected Dish
```
As you can see, the `..` command will allow you to move up a state, while the `selorder` and `seldish` commands allow you to move down into orders and dishes. Other commands are explained in detail.

# Clean code

we used IntelliJ refactoring
We used  GoogleStyle plugin.

# The Project Processes

We first read over the specifications of the assignment paying close attention to details. Then we made a UML diagram
detailing the classes and methods and the interactions between them. We have included an image of our original UML
diagram in the root directory of this project. This proved to be a very helpful artifact, as throughout our coding
experience, we refered back to the diagram many times, making changes to it and sharing the changes with one another.
Making this diagram definitely cut the amount of time we would have spent doing the project in half. It also allowed us
both to be on the same page throughout the process.

Then we split up the functionality into tasks.

yoonsik: terminal reader
Andreea: Menu Loader

We started by pair programming the TerminalReader, where Yoonsik was the driver and Andreea was the navigator.
In TerminalReader, we implemented most of the command line functionalities, i.e. the UI.
We finished most of TerminalReader, taking a break once we hit a roadblock and started rethinking strategies.
At this point, we decided it was a good time to take a break from that particular problem, and we switched gears to
start working on the Menu Loader, where Andreea was the driver and Yoonsik was the navigator.
We implemented this functionality very efficiently and then went back to TerminalReader. We finished TerminalReader and
concluded our first pair programming sprint.
During both iterations of pair programming, we had a clear idea in our minds about how to implement our data structures
and functionalities (in large part due to the UML diagram), and we were able to easily start coding and finish not long
after.
Pair programming worked very well for us, as we are both around a similar technical level, neither of us being
extremely advanced at coding or code design. We have different strengths and weaknesses, and by working
together, we were able to offer each other constructive advice and teach each other new things. Additionally, we were
able to learn some new techniques together and practice using them with each other as support and an extra set of
eyes.
Although pair programming was very effective for us as a method of generating clean and well-designed code, it did
waste programmer hours, since the both of us had to work together on the same problem. When the driver was finishing
relatively simple tasks, and the navigator was just watching, it was a little wasteful in terms of time.
We concluded that pair programming is extremely beneficial for performing difficult tasks that take a lot of thinking
and design, but not so beneficial when the task at hand includes things like programming very simple blocks of code
or documentation.

Once we finished the pair programming exercise, we split the rest of the functionalities up and began coding separately.


# Design Patterns

Builder: <br />
    We implemented two different Builders for construction of Delivery items and Food items.< br/>
    In the Delivery class, we implemented a non-abstract builder that constructs Delivery items and assigns to them
an address, type and orderID. Delivery is extended by three different subclasses, UbereatsDelivery, FoodoraDelivery, and
HouseDelivery. The three subclasses are constructed the same way, and for the most part have the same functionality.
The key difference is method outputDeliveryDetails, which has a different output in each of the three subclasses.
In UbereatsDelivery the output is in JSON format, in FoodoraDelivery the output is in CSV format, and in HouseDelivery
the output is in text format. Since we only wanted to override one method, and the construction is the same
for all three subclasses, we were able to use a non-abstract Builder class to construct them. <br />
    In the Food class, we implemented builders in Pizza and Drink that extend an abstract Builder in the Food class.
We used an abstract Builder in Food and Builder subclasses in Pizza and Drink because Pizza and Drink objects are
both constructed differently and have different behaviors. Specifically, Pizzas have extra attributes that Drinks
don't. Thus for Pizza, the abstract Builder in Food has methods that set attributes type and price, and the Builder in
Pizza first calls the Builder in Food to set type and price, and then itself sets size and toppings. Using an abstract
Builder class allows for flexibility and provides the possibility of having multiple subclasses that are related but
have different construction and functionality. <br />
    We used the builder design pattern in order to break the construction of Delivery and Food objects into two parts:
collecting arguments and creating an instance. Using this pattern allowed us to fix the issue of constructor tunneling
by allowing the constructors to instead collect their arguments one at a time. This way, we decrease the change of
passing incorrect parameters to Delivery and Food constructors. As we've stated above, this pattern also allows
different classes that have some similarities in construction and functionality to share some construction attributes.
Additionally, this make the code much easier to read and understand, and improves the code quality as a whole. <br />
<br />
Dependency Injection: <br />
    For each classes that had dependencies on each other, we implemented dependency injection to keep a class that
has a dependency on another class from instantiate objects of that class, and instead have those objects instantiated
by other classes and passed in. An example of this pattern is the dependency between Order and Delivery. An Order can
have a Delivery, but rather than having Order create a new Delivery when needed, we had a separate DeliveryHandler
that is the only class responsible for making and setting Delivery objects.

# Design Patterns we Debated Using

Factory Design Pattern: <br />
    We considered using a factory design pattern and to have one factory interface that is implemented by a
pizza factory and a drink factory, and another factory that makes Delivery items. Our original solution in fact used a
factory design pattern. Using this pattern would have been beneficial to this project because we would have been able
to delegate responsibility of constructing Delivery and Food objects to a separate factory class. This would fall in
line with Single Responsibility Principle. It would have made our code less coupled and easy to extend.<br />
    We didn't end up using the factory design pattern however, since we started using the Builder design pattern, and
we found that it allowed for much easier construction of Pizza and Drink items that are related but still quite
different. We also felt as if Builders were more complicated and more flexible than the factory design pattern.

# Other Design Features

We made attributes of classes private, and implemented and getters and setters and various other methods in each
class to have low coupling.<br />

# Changes to make in the future for better code

We felt as if our TerminalReader was slightly long even though each method specifically deals with a functionality of
the UI, and the TerminalReader itself has the single job to interact with the command line UI. Future changes to better
our code will include cutting the TerminalReader functions into smaller functions.
<br />
We also felt as if the dependency between Order and Delivery was not ideal, specifically in that printing an Order and
printing a Delivery requires multiple other function calls to other classes within the original function call. For
example, to print delivery information, TerminalReader calls printDeliveryDetails in DeliveryHandler, which in turn
calls outputDeliveryDetails in one of the Delivery subclasses, which in turn calls multiple Order methods to get Order
data. This is something else we would like to fix in the future.
<br />
