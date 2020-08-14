# Week 2

## Progress report

* GUI for making changes to graph and visualizing algorithms
* Class Graph for handling changes in graph
* A* for pathfinding
* Some unit tests for graph

## Learned/Notes

At first I was planning to use the same cost for diagonal, vertical and horizontal moves. However, after seeing some of the "shortest" paths my A* found I decided to change the cost for diagonal moves to sqrt(2).

## Goals for the next week

I would like to add better visualization of algorithms to GUI and it shouldn't be too hard. At the moment I only have A* with one heutistic so next week I should add different heuristics (Djikstra, Euclidean). Some other changes will need to be made to AStar too. Adding some other algorithm that isn't just A* with different heuristic. GUI should also support running different algorithms and resetting changes made by algorithm.

## Future improvements

Support for running pathfinding algorithms on graphs given as images would be nice for testing. For example some [Moving AI Lab street maps](https://www.movingai.com/benchmarks/street/index.html) would be nice to use.

## Time spent

20h