# Peer API Review
## Part 1
### What about your API/design is intended to be flexible?

* We have an abstract Commands that are very flexible. You can add additional specific commands but just provide the same interfaces. It just calculates the next states, such as coordinates and pen up or not, for the backend models and the frontend listens to these properties for updating graphics.


### How is your API/design encapsulating your implementation decisions?

* The parser always returns a list of commands and whether the commands are valid. The details are encapsulated.
* Command classes are just calculators of the next states
* The front end and back end are pretty much isolated and don't need to know the existence of the other. Back end is keeping tracking of the model and updating using the state class calculated by the command objects. The front end listens to the update of properties and update graphics correspondingly.


### What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?

* Parsing invalid. If caught, would throw an error message display

### Why do you think your API/design is good (also define what your measure of good is)?

* Minimal amount of interfaces so it's clean.

## Part 2

### How do you think Design Patterns are currently represented in the design or could be used to help improve the design?

### What feature/design problem are you most excited to work on?
### What feature/design problem are you most worried about working on?
### Come up with at least five use cases for your part (it is absolutely fine if they are useful for both teams).