# SLogo README
This file is due when the project is submitted.

## Project Members 
    Lucas Liu (ltl9)
    Anshu Dwibhashi (ad353)
    Brian Jordan (bjj17)
    Yao Yuan (yy123)

## Time Sheet

    Start: February 15th, 2019
    
    End: March 9th, 2019
    
    Estimated Work Hours: 400+ Hours

## Member Roles

### Lucas

Back-end development. Developed high level architecture. Designed framework for recursive tree based Command structure, and back-end external API. Implemented hierarchy and code for Command classes. 

### Anshu

Frontend development, Display/JavaFX. Built parser for propertie files. Came up with the frontend screens/utilities heirarchy for easily adding new screens in the future with not much extra code.

### Brian

Back-end Development.  Worked with Lucas to develop the design of the inheritance  structure of the Slogo commands.  Implemented specific commands subclasses and their logic.  Created the classes and interfaces for State and Turtle State.

### Yao

Parsing mechanism and testing.

Implemented parsing features are:
* Parsing commands with arbitrary number of argument and lists
* Recursive parse and see a completely parsed command as a argument
* Get custom commands and support parsing them later


Classes:
* Parser
* CmdNode
* CommandFactory
* parserTestMain
    
## Resources 

Course material examples used to model reflection, lambdas, and resource file usage. See [Course Repository](https://coursework.cs.duke.edu/compsci308_2019spring)

Course material examples using interfaces to pass immutable classes.  See [Course Repository](https://coursework.cs.duke.edu/compsci308_2019spring/example_encapsulation/tree/master/src)
    


## Files
   Run the program from src/front_end/RunSlogo
   Resource Files/Directory is labelled 'res'
   
   Properties files included in data/config
    
## Program Usage
   Since the update to Java 11, OpenFX hasn't update its `TextArea` component to be automatically exported. Until OpenFX fixes this, in order to run our program (which uses `TextAreas`), one must add the following VM Options to their Run Configurations:
   
`--module-path /path/to/this/project/slogo_team08/lib/javafx/mac --add-modules javafx.controls --add-modules javafx.base --add-exports=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED --add-exports=javafx.base/com.sun.javafx.reflect=ALL-UNNAMED --add-exports=javafx.base/com.sun.javafx.beans=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.glass.utils=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED`
    
## Decisions and Assumptions

In order to encapsulate the modification of state in the backend and the depiction of state in the front-end.  State and TurtleState extended Immutable interfaces.  Only the backend has access to the getters and setters that change the values within State and Turtle State while the frontend only has access to getters of immutable versions of the state values.
    One assumption that is made in the backend is that the user will pass an integer for the argument of an indexing command.  The backend checks if this value is within a range of indexes but if the given value is a decimal, it is rounded down to the next closest integer through casting.
   
* for parsing, all commands takes in a number of arguments and lists. Always parse arguments first and then lists. This defines the rule of parsing.
* parser only takes lower case strings. The front end takes care of it
* the command strings needs to be separated. for example [ as delimiter has to have space between other characters
* for parsing the rules, like the number of arguments and lists exist, otherwise parsing will fail
* Command factory assumes that parser has initialized sub-parsers
* More detailed assumptions are methods based
## Known Issues
A bug in JavaFX (a `NullPointerException` in one of the JavaFX libraries we're using) causes some terminal output when new windows are opened, but it doesn't affect our program's execution at all.
    
## Bonus Features    
    
* Autocomplete: Type commands to see suggestions pop up in the language of your choice.
* History: See commands that you've typed previously in a terminal-style output.
* Highlight to run: Select text in the history terminal using your mouse and hit run to rerun them.
    
    
## Impressions
    
Syntax for commands is still hard to understand, not clear what is wanted, there were a lot of questions about syntax possibilities. The example code files were really good, but it would be helpful if the example code also explained what numbers/actions should result after the code is run. 

The quantity of commands that were required for the basic and final implementations required a well organized back-end inheritance structure.  In this way, the project forced good design in order to implement all of the features.  This improved the groups understanding of the conscept and ability to use it again in the future.
