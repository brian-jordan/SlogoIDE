# SLogo Refactoring

Please review our pushes to master during this time, rather than looking for code changes in this merge request.

Some major changes were made to the internals of the code during refactoring.

Notably, a great deal of duplication was reduced in the turtleCommand class, and its related subclasses,
so that running through commands for several turtles would be more streamlined. 

The front end input was rewritten to be more robust to incorrect casing types.

The parser was rewritten to better incorporate more general commands, with a generic list style interface where
possible, instead of a more implementation specific approach. 