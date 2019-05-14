# SLogo Design Review

### Provides the high-level design goals of your project

The problem our team was trying to solve was that of being able to build a SLogo interpreter program that was easily extensible. In other words, our main challenge was to make a program that other people could write new SLogo commands for, and have the program execute them successfully, without having to change any internal workings of the original program. This requires the highest level of abstraction that allows for easy extension.


### Explains, in detail, how to add new features to your project

#### What is needed to add one of the following

* *New command to the language:* only a ".properties" file needs to be added to data/config, which does the translation of the commands' name to the actual regex. To use this language tho, the language choice needs to be set in the main.
* *New component to the front end:* To add a new feature to the frontend, you can add a new screen in the screens package, or a new control in the controls package and use them as you wish in other screens.
* *Feature from the specification that was not implemented:* grouping, recursion (but can be added relatively easy with custom command; this didn't work because the parsing rules were added late but were verified to work once the rules are added earlier). Grouping wasn't added because the premises/assumptions about parsing rules are broken.
* *New User Command Option:* Implementing the addition of more commands was made easy through the inheretance hierarchy of the Model portion of the code.  Adding a new command would involve creating a subclass for the specific command that would extend one of the intermediary abstract command class types if it had similar characteristics of other types of commands or directly extend the Command abstract class.  In general the only two methods that could need to be overriden in the new subclass would be calculateNewState and execute but this would depend on the command itself and how the new subclass fit into the hierarchy.  In addition to this, the properties files would need to be updated with the mapping of the new command subclass to the user calls of the command in each language offered.  The package and argument properties files would also need to be updated to map the subclass to the package it is in and the number of arguments or lists required by the command.
 
### Justifies major design choices, including trade-offs (i.e., pros and cons), made in your project

#### Stream of parsing and execution on the fly vs parsing everything and making arraylist of commands

* Stream of parsing on the fly is more scalable than parsing everything at once. But it might add complexity to the code or object structure.

* Parsing everything to execute at once makes code structure simpler but might require a large chunk of memory if the number of commands is large.

* We went on with the second option.

#### When saving/loading a previous work, whether we are saving all commands history and all the model views or just the command history.

* If we save also save all model views of a previous work, there might to far too many objects to store, given the nature of logo. back_end.command.For example a circle is made of hundreds or thousands of lines, storing this circle results in storing that many lines. It might be really large and re-placing them in the graphics could taking lots of effort as well.

* Storing only the command history is more light-weight. But executing all commands is a little ugly but doable.

* We went on with the second option.

#### Possibility of multiple models
One consideration that we discussed as a team was implementing the possibility of multiple models to increase the flexibility of our code produced for the first sprint.  Not knowing whether or not we are going to need to implement multiple turtles in the next sprint, we decided creating an abstract model that could be implemented to create numerous turtles from the start would give us this flexibility.  

#### Whether we would animate turtle movements

* Animating turtle movements would be a very easy change to make, but it would imply that "actions" taken by SLogo commands actually take real time to execute.
* Not animating turtle movements would imply that all SLogo commands are executed instantaneously, and this atomicity is more consistent with what a scripting language should do, philosophically, in our opinion.
* Given this, we made a conscious decision to not animate turtle movements.

#### Use of lambdas and reflection in commands
Upon reviewing the commands, one will find that commands have some natural groupings (such as arithmetic related commands) which will have similar functionality. This is a prime location for extracting the commonality into an abstract class, thus allowing subclasses to simply contain a lambda, making the class extremely short, and reducing duplication in the project. A similar story exists for reflection, especially when reading State fields. However, there are downsides. For instance, reflection does technically violate the encapsulation of certain project pieces, as it allows for private fields to be exposed. Additionally, sometimes the duplication would be only one or two lines, but forcing the use of reflection/lambdas would take 10 or more lines, unnecesarily crowding the class code, and reducing readability. In general, a good case for lambdas/reflection can be made when a lot of classes can benefit from the refactor.

#### Model and View Interaction with State Data

* State and Turtle State classes could be used to store the data reflecting what should be displayed in the turtle window.  The data would be stored in public variables that represented values and datastructures.  The Model portion of the code would execute user commands and update the current State and TurtleState objects stored in the backend.  It would then copy each of these state classes and add them to a list passed to the front end to render in the View.
* State and Turtle State classes could implement respective immutable interfaces.  The classes could then make the value and data structure variables private to exemplify better encapsulation.  The classes themselves would have get and set methods used by the Model portion of the program to edit the values and datastructures stored in the state objects.  These objects could then be passed as the immutable interfaces they implement to the frontend.  The View portion of the code would then only be able to call the get methods that returned immutable versions of the values and datastructures stored in the classes.  The datastructures would be converted into immutable forms through deep copying and converting them to an immutable version before returning.
* We used the first method through the first and most of the second sprint.  We decided the second option would be the most robust design that would encapsulate the modification and reading of the data in the best way we could think of.  This is why the second method was used in the final Slogo implementation.

### States any assumptions or decisions made to simplify or resolve ambiguities in your the project's functionality

* Parser assumes the input comes in as string array and the comments lines are already taken out.
* Parser assumes the commands can be parsed. Otherwise assumptions are thrown.
* Parser assumes the rules exist for the commands to be parsed.
* Parser assumes that every command have the same parsing rules, that is to have *a* expressions (anything that gives a value, such as number, of commands or vaiable) followed by *b* lists, (what's enclosed in [ ]).
* CommandFactory assumes subparser utilities, which parses the rules and language, only initialize once. So that added rules of custom commands are not overwritten.
* The frontend assumes that the different co-existing environments all have their own independent variable spaces.
* The frontend assumes that the default language is English, default background colour is white, and default pen colour is black.
* The Model assumes that the number of arguments that are parsed and added to each command object will match the expected number of arguments for the specific command. 