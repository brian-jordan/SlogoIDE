CompSci 308: SLogo Addition
===

# Before

### Estimation

 * How long do you think it will take you to complete this new feature?
 
 I expect the task of creating the two new commands to take no longer than an hour.  

 * How many files will you need to add or update? Why?
 
 I intend on creating two new files for the Commands.  The Stamp command Class will extend TurtleCommand so that it can
 be applied to all active turtles.  The ClearStamps command Class will extend the Command class.  These classes will
 control the actual logic for adding stamps and removing them.  I will need to update the TurtleCommand class to call 
 the execute method which is empty now but can be overridden for the specific implementation of the Stamp command.  I 
 will also need to add entries in the NumberOfArguments.properties file as well as the Package.properties file for each 
 respective command being added so the parser can fetch the relevant information.

# After

### Review

 * How long did it take you to complete this new feature?
 
 It took me about an hour to complete making the necessary changes in the backend to implement the new commands.
 Unfortunately there were some aspects of the front-ends information processing from the backend that didn't allow the
 commands to function as expected.  I spent another hour or so trying to figure out how I could potentially change the
 frontends code to account for the addition and removal of stamps but could not find an easy fix that would not
 restructure and potentially break another part of the frontend.

 * How many files did you need to add or update? Why?
 
 I added the two classes for the commands, Stamp and ClearStamps that extended TurtleCommand and Command respectively.
 This addition of classes was as I expected.  I made the necessary change to TurtleCommand by calling the execute method
 after the execute single method so Stamp could extend that method.  I also updated the 2 properties files NumberOfArguments.properties
 and Package.properties, as I had intended to do and remembered that I also needed to update the English.properties file
 with the user entered strings that would call the commands.  I only implemented this in English but the other language
 properties files would also have to be updated to call the commands in the respective languages.

 * Did you get it completely right on the first try? If not, why not?
 
 I did not get it completely right the first try.  Some of the implementation of the frontend was not compatible to the
 necessary changes in the backend to allow for the new commands.  For example, we did not expect the need for Turtle's,
 or stamps, to be instantiated in the frontend in any location other than the center of the screen.  This causes the
 new command to spawn a stamp at the center of the display window rather than at the location of the current active
 turtle.  There was also an issue with implementing the clear stamps method due to the frontend only updating the 
 displayed view of the currently active turtles rather than looping over all of them.  Stamps could not be marked as 
 active turtles given the functioning of the backend, because that was what allowed for multi-turtle commands such as 
 the movement commands to function over multiple turtles at once which is not the desired effect for static stamps. 
 Overall, the elaborate inheritance hierarchy of the backend allowed for the new commands to be implemented efficiently in
 the frontend and even capable of functioning over multiple turtles that are active in the case of Stamp.  The visualization
 part of the code was not able to display the changes as expected but would most likely only require a small amount of
 restructuring to account for these static turtle images.  I attempted to work through changes to the frontend part but
 was unsuccessful and didn't want to mess up the current functionality.


### Analysis

 * What do you feel this exercise reveals about your project's design and documentation --- was it as good (or bad) as you remembered?
 
 I thought this exercise revealed the strength of the design in the backend portion of the code.  Not having looked at
 the code for this project in a while, the inheritance structure of the Command library allowed me to quickly figure out
 where the necessary additions would need to be inserted.  The abstract Command class and the abstract TurtleCommand classes
 implement all of the functionality in terms of updating the state of the turtle window and passing it to the frontend
 to visualize the changes.  Extending these classes to implement the Stamp and ClearStamp commands just required overriding
 two methods in each respective subclass.  As mentioned above, the design of the frontend was not implemented in a way
 that was conducive to how the commands were intended to operate, so the changes did not work completely as expected when
 the commands were run.  The structure of inheritance hierarchy was what helped me to best recall how the backend was
 structured and where I needed to make changes, but the documentation was also helpful.  The comments on the public methods,
 especially the ones that were abstract that needed to be changed were very helpful for re-understanding all the details
 of the functionality.  The last thing that was beneficial to implementing the new methods was the question from the 
 Analysis of the project which asked about the steps to make a new Command.  This helped me formulate the process 
 completely at the time as well as recall the steps as I was making the changes now.
 

 * What about the design or documentation could be improved?
 
 In terms of the design, I thought the structure of the backend classes was very robust as discussed above.  This both
 allowed us to efficiently implement almost every requirement of functionality while working on the project at the time
 and now adding add capabilities after not working with the code for a while.  I did have to make a small change to the
 TurtleCommand class to call the execute method in the calculateNewState method.  This was due to initially only individual
 TurtleState objects being modified when commands were called to allow for a loop over all active turtles.  Since this
 command required editing the State object itself including additional stamps in the Turtles list, this needed to be
 modifiable from the Stamp subclass.  I think an improvement in the frontend design could come in the form of small changes
 based on expectation.  We had only anticipated the addition of Turtles with the same starting state so the need for the
 stamps to be placed in exact locations was not compatible.  In a similar way, we updated the state of the visualized
 objects based on whether or not they were active Turtles which inhibited us from removing Stamps from the view for the
 Clear Stamps command.  Updating all of the visualized objects rather than the active ones each command might be a little
 slower but could help solve the issue.  I tried to make some of these changes but it affected other parts of the frontend
 so I left it as is.  Overall as mentioned above the documentation was adequate for recalling how to make the addition
 but adding some more detail in terms of what each subclass does and specific commands could help out those who are less
 familiar with the code base.

 * What would it have been like if you were not familiar with the code at all?
 
 It would probably require more time for getting to know the codebase for a person that has not worked on the program to
 make a change.  For me and the other members of the group, it is clear when presented with an addition where we would
 start by understanding how the code is structured.  For someone new to the project, this would take a little bit of
 just to get a basic understanding, because there are many parts that make up the three larger sections of code being
 Parsing, Processing, and Visualization.  The DESIGN.md document would be the first resource to reference in order to
 understand how each part of the code is structured and interacts with the other parts.  Starting by looking at the
 Command abstract class and working down the hierarchy would help them understand how state is updated and where additional
 changes could fit based on how similar commands currently fit into the structure.  Overall, it would be more difficult
 immediately making modifications to the code not having worked with it, but after understanding the basic structure it
 is easy to see where each piece fits into place.