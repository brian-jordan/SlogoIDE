#### SLogo API Review

## Part 1
1. What about your API/design is intended to be flexible?
    Both teams are creating flexible API's for the parser and command classes in order to allow for the ease of 
    extension when new commands want to be added to the library.
    Both API's also allow for flexibility of input from the user.  This comes in the form of being able to have commands
    be the arguments for other commands.  The groups are implementing this flexibility in different ways.  One group is
    using recursion to solve the nested commands issue by looking for the commands that have given arguments based on
    the type of command.  The other group is nesting the internal commands as arguments of the outer commands.
    
2. How is your API/design encapsulating your implementation decisions?
    Both groups are using API's made up of independent classes.  Other classes are not aware of how a certain class
    performs its function, they just know the outputs produced by the class and the inputs required of their class.
    This encapsulation allows for any number of different implementations within a specific class that doesnt effect the
    performance of another class.  A specific example of this is how both groups have command execution classes that
    are not aware of how command objects are created by the parser classes, they just know that it converts user input
    strings to command objects.
    
3. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
    Both groups are handling specific user input exceptions such as incorrect command declaration in terms of the
    the command string not existing or an incorrect number of arguments.  Other input errors include incorrect syntax
    such as '.' or other symbols that arent accounted for and using a variable that has not yet been declared.  We also
    discussed the error of the user presenting commands that would make the turtle leave the window.  Two solutions we
    found to this would be allowing the window to wrap to the other side and saving previous state to allow the user to
    revert to a past position.

4. Why do you think your API/design is good (also define what your measure of good is)?
    One of the biggest parts of good design that we discussed was encapsulating functionality of classes from other
    classes to only allow completely necessary information to be passed between them.  Both groups do this through their
    API design by reducing amount of communication between classes and making as many parts of each class as possible
    private.  In a similar way, only certain classes should be able to modify parts of other classes.  Unwanted
    modification is an example of poor design and could lead to future errors.
    
## Part 2
1. How do you think Design Patterns are currently represented in the design or could be used to help improve the design?
    One of the groups is using the model, view, controller (MVC) design pattern.  This separates the major functionality
    of the program into groups of classes that interact with each other and only pass necessary information to the other
    groups.  Both groups are using Polymorphism in order to have specific implementations of classes while maintaining
    overarching parent class characteristics.