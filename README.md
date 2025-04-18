City Connection Project

This project models fictional city-to-city connections using a list-based weighted graph and a hash table to map city names (strings) to integer vertex indices. It implements fundamental graph operations and algorithms to analyze and traverse the network.

Features

Load graph from file: ReadGraphFromFile() reads graph.txt and builds an adjacency list while creating a hash table for city-to-index mapping.

Path existence: IsThereAPath(String v1, String v2) checks if any path exists between two cities.

Breadth-First Search: BFSfromTo(String v1, String v2) performs a BFS, printing the sequence of cities and edge weights from v1 to v2 (visiting edges in increasing weight order).

Depth-First Search: DFSfromTo(String v1, String v2) performs a DFS, printing the traversal path and edge weights.

Shortest path length: WhatIsShortestPathLength(String v1, String v2) computes the minimum total weight between two cities (using a custom algorithm).

Count simple paths: NumberOfSimplePaths(String v1, String v2) returns the number of distinct simple paths from v1 to v2.

Neighbor listing: Neighbors(String v1) returns the direct neighbors of a city.

Highest degree vertex: HighestDegree() finds the city (or cities) with the maximum number of connections.

Directed check: IsDirected() returns whether the graph is directed.

Adjacency check: AreTheyAdjacent(String v1, String v2) checks if two cities share an edge.

Cycle detection: IsThereACycle(String v1) checks for a cycle starting and ending at the same city.

Component size: NumberOfVerticesInComponent(String v1) prints the size of the connected component containing v1.

File Structure

project-root/
├── src/                   # Java source files
│   ├── Graph.java         # Graph implementation
│   ├── Edge.java          # Edge representation (weight and destination)
│   ├── Main.java          # Menu and user interaction
├── graph.txt              # Input file defining city connections
└── README.md              # Project documentation

Requirements

Java SE Development Kit (JDK) 8 or higher

Contributing

Contributions are welcome! Feel free to submit issues or pull requests to enhance features, fix bugs, or improve documentation.

Author

Sümeyye YETİM


