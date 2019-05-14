SLogo Team 8 API Critique (Team 11 Cell Society)

 Using the word "package" in package names.




Good that there was a generic Cell class, but each subclass in the hierarchy needs to be better organized within packages.

* Good that the Cell class uses generic return types (List, Shape etc.) as opposed to specifics.

* Methods of that should not be in the API of the Abstract Cell Class:
    *  getLenColors() - use a get colors method that returns an array of colors instead of just the length of the array
* Methods of that should be part of the external API of the Abstract Cell Class:
    * updateCell()
    * updateDisplay()
* Methods of that should be part of the internal API of the Abstract Cell Class:
    * setShape(Shape shape)
    * getState()
    * setNextState(int state)
    * addNeighbors(List<? extends Cell> neighbors)
    * getNeighbors()
    * createSpace(CellSpace cellSpace)
    * attach()
    * getCellWidth()
    * getMyPosition()
