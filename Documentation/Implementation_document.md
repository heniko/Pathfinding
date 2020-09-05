# Implementation document

## A*

A* implementation is based on information and pseudocode found on [Wikipedia](https://en.wikipedia.org/wiki/A*_search_algorithm).

The main data structure needed for A* is heap for open list. Open list needs to have good time complexity for adding new nodes and finding the node with lowest f value (PriorityNode with lowest priority).

## Djikstra's algorithm

In this project Djikstra's algorithm is implemented as special case of A* where estimation given by heuristic is always 0.

## Jump point search (JPS)

JPS is optimisation to A* that finds and adds "intresting" jump points to open list instead of adding every adjacent node.

Better and more in depths explanations about JPS can be found [here](https://harablog.wordpress.com/2011/08/26/fast-pathfinding-via-symmetry-breaking/), [here](https://zerowidth.com/2013/a-visual-explanation-of-jump-point-search.html) and [here](https://www.gamedev.net/tutorials/programming/artificial-intelligence/jump-point-search-fast-a-pathfinding-for-uniform-cost-grids-r4220/).

## Time and space complexities

Djikstra: Since implementation uses heap as open list the time complexity is O((|V|+|E|)log(|V|)) where |V| is the number of nodes and |E| is the number of edges. Space complexity is O(n).

A*: Worst case time and space complexity for A* is O(b^d).

## Hearistics

Heuristics are used for estimating path length. For A* to find the shortest possible path it is important that heuristic doesn't overestimate the path length. Underestimating the path length will still find the shortest possible path but it will decrease the performance of A* since more nodes are being examined.

DjikstraHeuristic is heuristic that turns A* into Djikstra's algorithm. Djikstra's algorithms can be thought as a special case of A* with heuristic that always returns 0 for every source and destination.

EuclideanHeuristic returns the Euclidean distance between source and destination. Euclidean distance will often be underestimation of the real distance. More information [here](http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#euclidean-distance).

DiagonalHeuristic returns the length of optimal path from source to destination if there are no obstacles. More information [here](http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#diagonal-distance).

## Possible improvements and fixes

GUI has a lot of potential improvemenets. I think visualizing JPS recursion is a bit too slow. It is possible to draw on the canvas while finding path and that may cause some unwanted behaviour. Pausing algorithms/Visualise one step features could be added. However, GUI has most of the features I needed for demoing/debugging.

Pathfinders still have some similiar code (mainly in solve()) that could be moved to class Pathfinder.

Most of the code uses x and y for location. This is a bit misleading since atleast GUI thinks y value 0 is on the top.

Heap could have option to change the priority of node instead of adding new one.