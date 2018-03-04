# Papyrus

An object relational mapper in Java inspired by Django ORM.

The workflow uses the analogy of its name - an ancient material to make scrolls pioneered in ancient Egypt.
A scroll class (class that extends the abstract class 'Scroll') is used to define a table, which we'll call scroll.
This scroll is converted into a table in RDBMS by wrapping it in a 'Mapper' class and data manipulation is achieved by wrapping it up in 'Ruler' class.

Mapper class converts a Scroll class into an SQL query. It supports numeric, int, date and varchar data types.
It also implements primary key and foreign key along with other domain constraints.

Ruler class implements select, update and delete operations.

Custom implementations of many operations like average, sum and ordering can hence, be added by the programmer in Java.
Tables can be made using Object Oriented constructs like inheritance.
Application code becomes much more readable and crammed up SQL queries are avoided.
