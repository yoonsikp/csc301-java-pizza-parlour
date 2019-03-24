# a2-starter

# The Project Processes

We first read over the specifications of the assignment paying close attention to details. Then we made a UML diagram
detailing the classes and methods and the interactions between them. We will insert a photo here. This proved to be
a very helpful artifact, as throughout our coding experience, we refered back to the diagram many times, making changes
to it and sharing the changes with one another. Making this diagram definitely cut the amount of time we would have
spent doing the project in half. It also allowed us both to be on the same page throughout the process.

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

Once we finished the pair programming exercise, we split the rest of the functionalities up and began coding separately.


# Design Patterns

We implemented a few design patterns in this project to make various aspects of the design much more neat and
concise, and altogether make the project structure easier to understand, use, and augment by potential future
developers. The following are the design patterns we have implemented.

Factory Design Pattern
    We used factory to implement a delivery factory, which uses a builder class to make one of a
few different kinds of deliveries. For the time being, the delivery types we have are the HouseDelivery,
UbereatsDelivery and FoodoraDelivery. These three delivery types are classes that extend an abstract Delivery class.
The reason for this is that all three of these delivery types are very similar in that the only difference between
them that merits separation into separate classes is the method of sending delivery details to the company requiring
them. This functionality is implemented by the abstract method <outputDeliveryDetails> in the Delivery class, which
is overridden in HouseDelivery, UbereatsDelivery and FoodoraDelivery depending on the format required.
    We used the factory design pattern to create instances of the different kinds of deliveries behind-the-scenes, so
that other classes wouldn't need to have knowledge about how to create a Delivery and the specifics of the three
different Delivery classes we have currently. This way, we can maintain a single responsibility principle, as different
classes have more strongly defined single responsibilities. Additionally, by delegating responsibility only to one
class whose sole purpose is to have knowledge about the different Delivery types as well as maintaining an abstract
Delivery class that can be extended by specific Delivery types, it will be very easy to add more Delivery types and
have them instantiated by the factory as well.

Builder

