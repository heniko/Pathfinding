# User guide

This project works with Java version 8. Gradle project is located in folder pathfinding and all commands should run there. If gradle is not installed on your system then for Windows use

```console
gradlew.bat <command>
```

and for Linux use

```console
./gradlew <command>
```

in place of gradle.

## Running the project

You can run the project with command

```console
gradle run
```

This command will open the GUI version of the project. In the GUI you can create different graphs and see how different algorithms solve them. It should be noted that visualisation of the algorithm can't always be used to tell how efficient the algorithm is (Performance testing document can be found [here](https://github.com/heniko/Pathfinding/blob/master/Documentation/Testing_document.md).

![Main view](https://github.com/heniko/Pathfinding/blob/master/Documentation/Pictures/Main_view.png)

Right side of the GUI contains functionality for selecting node type, selecting pathfinding algorithm, running pathfinding algorithm and cleaning changes made by pathfinding algorithms. Left side of the GUI is reserved for the graph. You can make changes to the graph by first selecting the type of the node on the right and then clicking/dragging mouse on the graph. Walls/empty nodes can't replace or be placed over start/end. Placing start/end over wall will also remove the wall. For visualisation you first have to choose the algorithm under algorithm selector and then click solve. Clean removes all changes made by pathfinding algorithms.

This project has four different pathfinding algorithms you can choose from. The main difference between Djikstra's algorithm, Euclidean distance A* and diagonal distance A* is how they estimate the remaining distance. JPS or jump point search is optimised version of A*. More about the differences or links to better explanations can be found [here](https://github.com/heniko/Pathfinding/blob/master/Documentation/Implementation_document.md).

Graphs uses different colors to represent nodes. White is used for empty node, green for start, red for end and black for wall. Visualising algorithms also requires some different colors. Purple is used for highlighting the path found by algorithm. When algorithm first finds a node it marks it with blue color. Node is given yellow color when algorithm handles is. And for visualising jumps on jump point search we use bisque.

## Testing

Running unit tests and generating tests report with jacoco:

```console
gradle test
gradle jacocoTestReport
```

Running performance tests:

```console
gradle run --args='test'
```

Running performance tests and writing results directly to markdown-file for easier reading:

```console
gradle run --args='test' > filename.md
```