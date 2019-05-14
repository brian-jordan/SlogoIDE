API Review
===

Anshu Dwibhashi (ad353)
Hyunjae Lee (hl285)

## Part 1

* What about your API/Design is intended to be flexible?
    * AD: Our `Command` class is a abstract superclass that each specific `Command` inherits from. This way, creating new commands only requires making a new subclass of `Command` with no changes to any other code.
    * HL: Our general command that the user inputs into the command line will be a string that is then checked against an enum of possible commands. The front end does not have any knowldge on the command or turtle other than the numerical positions and data. Back end is where all parsing takes place, so front end modification would be minimal. 
* How is your API/design encapsulating your implementation decisions?
    * AD: The Model, View and Controller are completely isolated from each other. The Model has no idea that the data it's holding will be used by the View to render things on the screen. Likewise, the Controller has no idea that the computations it's doing are requested by a frontend for rendering purposes. While we're using an observer design-pattern to notify the frontend of changes in the backend on the fly, the backend still has no idea that a frontend exists, because it calls a `notify()` method in a generic `Observer` interface that the View class implements.
    * HL: The front end we designed to know as little as possible. A command will mean nothing to the view of the MVC we are implementing. The front end knows nothing other than the positions and what things to simply put up on the screen. We also discussed the internal interactions between the various parts of the front end such as the GUI, buttons, sliders, etc. Rather than interacting with eachother, the front end components will simply update based on a user input event that the back end will notify the front end of when some thing changes.
* What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
    * AD: Illegal/Undefined command, math errors (division by 0), incompatible datatypes (passing in strings for distances etc.). We handle this by displaying a message to the user and taking no action.
    * HL: Commands that the user inputs that are not in the enum of commands will throw an error of unknown command, bad math such as dividing by 0 will also throw exceptions. All exceptions handled by displaying alert with error message.
* Why do you think your API/design is good (also define what your measure of good is)?
    * AD: Good encapsulation, easily extensible.
    * HL: Same.

## Part 2

* How do you think Design Patterns are currently represented in the design or could be used to help improve the design?
    * AD: We're implementing the MVC class, by having specific View and Model classes. While we do'nt have a Controller class, each instance of the Command class acts as a transient controller that alters the Model class. The Model class updates the frontend using an Observer pattern where it notifies the frontend each time there's new data.
    * HL: Our group also was planning on using the MVC model for the design, and making it so that the user will input some command, it will update the back end data and compute whatever math needs to be done, and then notify the listening controller class that then communicates to the front end what changes must be displayed on the screen. Rinse and repeat.
* What feature/design problem are you most excited to work on?
    * AD: The UI that the user uses to interact with the program.
    * HL: The ability for the turtle to move properly given a user command
* What feature/design problem are you most worried about working on?
    * AD: The parser class.
    * HL: I was most worried about being able to display the path the turtle takes and being able to store all previous commands so that a multiple line program that the user inputs can properly be displayed with the proper paths. We discussed this, and Anshu gave the idea of simply executing a change in the front end after every single command is iputted so that a multiple line command will not be communicated all at once, it will be done in steps command by command.
* Come up with at least five use cases for your part (it is absolutely fine if they are useful for both teams).
    * AD: Accepting/validating user inputs, rendering outputs, saving files corresponding to current state, loading files and re-executing them, error handling, etc.
    * HL: Uploading files, being able to view the turtle's movement and its paths that it takes, changing various characteristics of the GUI such as background color/turtle color, error handling and alert messages for bad inputs, being able to have scrollable lists in the display of previous command history.