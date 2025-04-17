
package com.mycompany.project33;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        
        Graph graph = new Graph();

        
        System.out.println("Starting to read the graph from the file...");
        graph.readGraphFromFile("/Users/sumeyyeyetim/Desktop/graph.txt");  
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Is there a path between two cities?");
            System.out.println("2. Find the path between two cities using DFS");
            System.out.println("3. Find the path between two cities using BFS");
            System.out.println("4. Check if the graph is directed");
            System.out.println("5. Show cities with the highest degree");
            System.out.println("6. Find the shortest path length between two cities");
            System.out.println("7. Exit");

            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    // Check if there is a path between two cities
                    System.out.print("Enter the starting city: ");
                    String city1 = scanner.nextLine();
                    System.out.print("Enter the destination city: ");
                    String city2 = scanner.nextLine();

                    if (graph.isThereAPath(city1, city2)) {
                        System.out.println("There is a path between " + city1 + " and " + city2 + ".");
                    } else {
                        System.out.println("There is no path between " + city1 + " and " + city2 + ".");
                    }
                    break;

                case 2:
                    // Find the path using DFS
                    System.out.print("Enter the starting city: ");
                    city1 = scanner.nextLine();
                    System.out.print("Enter the destination city: ");
                    city2 = scanner.nextLine();

                    graph.dfsFromTo(city1, city2);
                    break;

                case 3:
                    // Find the path using BFS
                    System.out.print("Enter the starting city: ");
                    city1 = scanner.nextLine();
                    System.out.print("Enter the destination city: ");
                    city2 = scanner.nextLine();

                    graph.bfsFromTo(city1, city2);
                    break;

                case 4:
                    // Check if the graph is directed
                    if (graph.isDirected()) {
                        System.out.println("The graph is directed.");
                    } else {
                        System.out.println("The graph is undirected.");
                    }
                    break;

                case 5:
                    // Show cities with the highest degree
                    System.out.println("Cities with the highest degree: ");
                    for (String city : graph.getHighestDegree()) {
                        System.out.println(city);
                    }
                    break;

                case 6:
                    // Find the shortest path length between two cities
                    System.out.print("Enter the starting city: ");
                    city1 = scanner.nextLine();
                    System.out.print("Enter the destination city: ");
                    city2 = scanner.nextLine();

                    graph.WhatIsShortestPathLength(city1, city2);
                    break;

                case 7:
                    // Exit the program
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 7.");
            }
        }

        scanner.close();
    }
    
}
