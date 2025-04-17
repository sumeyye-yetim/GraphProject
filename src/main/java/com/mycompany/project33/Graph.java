
package com.mycompany.project33;

import java.io.*;
import java.util.*;

public class Graph {

    private Map<String, Integer> cityToIndex;   
    private List<List<Edge>> adjList;           

    public Graph() {
        this.cityToIndex = new HashMap<>();
        this.adjList = new ArrayList<>();
    }

    public void readGraphFromFile(String filename) throws IOException {
        String filePath = "/Users/sumeyyeyetim/Desktop/graph.txt";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int index = 0;

        while ((line = reader.readLine()) != null) {
            
            line = removeExtraSpaces(line);

            String[] parts = line.split("->");

            String city = removeExtraSpaces(parts[0]);

          
            Integer fromIndex = cityToIndex.get(city);
            if (fromIndex == null) {
                fromIndex = index++;
                cityToIndex.put(city, fromIndex);
                adjList.add(new ArrayList<>()); // empty list for new city 
            }

            if (parts.length > 1) {
                String[] edges = parts[1].split(",");
                for (String edge : edges) {
                    String[] edgeParts = edge.split(":");
                    String toCity = removeExtraSpaces(edgeParts[0]); 
                    int weight = convertToInt(removeExtraSpaces(edgeParts[1])); 
                    // If the destination city is not added yet, we add it
                    Integer toIndex = cityToIndex.get(toCity);
                    if (toIndex == null) {
                        toIndex = index++;
                        cityToIndex.put(toCity, toIndex);
                        adjList.add(new ArrayList<>()); 
                    }

                    adjList.get(fromIndex).add(new Edge(toIndex, weight));
                }
            }
        }

        reader.close();
    }

    private String removeExtraSpaces(String str) {
        char[] chars = str.toCharArray();  
        int length = chars.length;

        int start = 0;  
        int end = length - 1;  

       
        while (start < length && chars[start] == ' ') {
            start++;
        }

        while (end > start && chars[end] == ' ') {
            end--;
        }
        
        if (start > end) {
            return "";
        }

        String result = "";
        boolean inSpaces = false;

        for (int i = start; i <= end; i++) {
            if (chars[i] == ' ') {
                if (!inSpaces) {
                    result += " ";  
                    inSpaces = true;
                }
            } else {
                result += chars[i];  
                inSpaces = false;
            }
        }

        return result;
    }

    private int convertToInt(String str) {
        str = removeExtraSpaces(str);

        
        if (str.isEmpty()) {
            return 0;
        }

        boolean negative = false;
        int startIndex = 0;

        if (str.charAt(0) == '-') {
            negative = true;  
            startIndex = 1;  
        }

        int result = 0;

        for (int index = startIndex; index < str.length(); index++) {  
            char c = str.charAt(index); 
            if (c < '0' || c > '9') {
                return 0;  
            }

            int digit = c - '0';  

            result = result * 10 + digit;  
        }

        
        if (negative) {
            return -result;  
        } else {
            return result;  
        }
    }
    public boolean isThereAPath(String v1, String v2) {
    if (!cityToIndex.containsKey(v1) || !cityToIndex.containsKey(v2)) {
        return false;
    }

    int start = cityToIndex.get(v1);
    int end = cityToIndex.get(v2);
    boolean[] visited = new boolean[adjList.size()];

    return dfsCheckPath(start, end, visited);
}

private boolean dfsCheckPath(int current, int target, boolean[] visited) {
    if (current == target) {
        return true;
    }

    if (visited[current]) {
        return false;
    }

    visited[current] = true;

    for (Edge edge : adjList.get(current)) {
        if (!visited[edge.to] && dfsCheckPath(edge.to, target, visited)) {
            return true;
        }
    }
    return false;
}

    // DFS 
    public void dfsFromTo(String v1, String v2) {
        Integer startIndex = cityToIndex.get(v1);
        Integer endIndex = cityToIndex.get(v2);
        if (startIndex == null || endIndex == null) {
            System.out.println("One or both cities do not exist.");
            return;
        }

        boolean[] visited = new boolean[adjList.size()];
        Map<Integer, Integer> parent = new HashMap<>();

        // Start DFS
        if (dfsRecursive(startIndex, endIndex, visited, parent)) {
            List<String> path = new ArrayList<>();
            int current = endIndex;
            while (current != -1) {
                path.add(getCityName(current));
                current = parent.get(current);
            }

            // Manual reverse
            List<String> reversedPath = new ArrayList<>();
            for (int i = path.size() - 1; i >= 0; i--) {
                reversedPath.add(path.get(i));
            }

            System.out.println("Path: " + String.join(" -> ", reversedPath));
        } else {
            System.out.println("No path found.");
        }
    }

    private String getCityName(int index) {
        for (String city : cityToIndex.keySet()) {
            if (cityToIndex.get(city) == index) {
                return city;
            }
        }
        return null;  
    }

    private boolean dfsRecursive(int current, int target, boolean[] visited, Map<Integer, Integer> parent) {
        if (current == target) {
            return true;
        }

        visited[current] = true;
        List<Edge> neighbors = adjList.get(current);
        bubbleSort(neighbors);  // Sort neighbors from smallest to largest

        for (Edge edge : neighbors) {
            if (!visited[edge.getTo()]) {
                parent.put(edge.getTo(), current);
                if (dfsRecursive(edge.getTo(), target, visited, parent)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void bubbleSort(List<Edge> neighbors) {
        int n = neighbors.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (neighbors.get(j).getWeight() > neighbors.get(j + 1).getWeight()) {
                    Edge temp = neighbors.get(j);
                    neighbors.set(j, neighbors.get(j + 1));
                    neighbors.set(j + 1, temp);
                }
            }
        }
    }

    //BFS
    public void bfsFromTo(String v1, String v2) {
        Integer startIndex = cityToIndex.get(v1);
        Integer endIndex = cityToIndex.get(v2);
        if (startIndex == null || endIndex == null) {
            System.out.println("One or both cities do not exist.");
            return;
        }

        boolean[] visited = new boolean[adjList.size()];
        Map<Integer, Integer> parent = new HashMap<>();
        List<Integer> currentLevel = new ArrayList<>(); 

        visited[startIndex] = true;
        parent.put(startIndex, -1);
        currentLevel.add(startIndex);

        while (!currentLevel.isEmpty()) {
            int current = currentLevel.get(0); 
            currentLevel.remove(0); 

            if (current == endIndex) {
                break; 
            }

            List<Edge> neighbors = adjList.get(current);

            for (int i = 0; i < neighbors.size(); i++) {
                for (int j = i + 1; j < neighbors.size(); j++) {
                    if (neighbors.get(i).getWeight() > neighbors.get(j).getWeight()) {
                        Edge temp = neighbors.get(i);
                        neighbors.set(i, neighbors.get(j));
                        neighbors.set(j, temp);
                    }
                }
            }

            for (Edge edge : neighbors) {
                int neighbor = edge.getTo();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent.put(neighbor, current);
                    currentLevel.add(neighbor); 
                }
            }
        }

        if (!visited[endIndex]) {
            System.out.println("No path found.");
        } else {
            List<String> path = new ArrayList<>();
            int current = endIndex;
            while (current != -1) {
                path.add(getCityName(current));
                current = parent.get(current);
            }

            // Manuel reverse
            List<String> reversedPath = new ArrayList<>();
            for (int i = path.size() - 1; i >= 0; i--) {
                reversedPath.add(path.get(i));
            }

            System.out.println("Path: " + String.join(" -> ", reversedPath));
        }
    }

    public List<Edge> getNeighbors(int cityIndex) {
        List<Edge> neighbors = adjList.get(cityIndex);

        bubbleSort(neighbors);

        return neighbors; 
    }

    public List<String> getHighestDegree() {
        int maxDegree = -1;  
        List<String> citiesWithHighestDegree = new ArrayList<>();  

        for (int i = 0; i < adjList.size(); i++) {
            List<Edge> neighbors = adjList.get(i);  
            int degree = neighbors.size();  

            if (degree > maxDegree) {
                maxDegree = degree;
                List<String> newList = new ArrayList<>();  
                newList.add(getCityName(i));  
                citiesWithHighestDegree = newList;  
            } else if (degree == maxDegree) {
                citiesWithHighestDegree.add(getCityName(i));
            }
        }

        return citiesWithHighestDegree;  
    }

    public boolean isDirected() {
        for (int i = 0; i < adjList.size(); i++) {
            List<Edge> neighbors = adjList.get(i);  
            for (Edge edge : neighbors) {
                int targetCity = edge.getTo();  
                boolean foundReverseEdge = false;

                for (Edge reverseEdge : adjList.get(targetCity)) {
                    if (reverseEdge.getTo() == i) {  
                        foundReverseEdge = true;
                        break;
                    }
                }

                if (foundReverseEdge) {
                    return false; 
                }
            }
        }

        return true;
    }

    public boolean areTheyAdjacent(String v1, String v2) {
        Integer startIndex = cityToIndex.get(v1);
        Integer endIndex = cityToIndex.get(v2);

        if (startIndex == null || endIndex == null) {
            return false;
        }

        List<Edge> neighbors = adjList.get(startIndex);

        for (Edge edge : neighbors) {
            if (edge.getTo() == endIndex) {
                return true;  
            }
        }

        return false;
    }

    public int numberOfSimplePaths(String v1, String v2) {
        Integer startIndex = cityToIndex.get(v1);
        Integer endIndex = cityToIndex.get(v2);

        if (startIndex == null || endIndex == null) {
            return 0;
        }

        boolean[] visited = new boolean[adjList.size()];  
        pathCount = 0;  
        dfsCountPaths(startIndex, endIndex, visited);
        return pathCount;
    }

    private int pathCount;  

    private void dfsCountPaths(int current, int target, boolean[] visited) {
        if (current == target) {
            pathCount++;  
            return;  
        }

        visited[current] = true; 

        List<Edge> neighbors = getNeighbors(current);

        for (Edge edge : neighbors) {
            int neighbor = edge.getTo();
           
            if (!visited[neighbor]) {
                dfsCountPaths(neighbor, target, visited);  
            }
        }

        visited[current] = false;  
    }

    public void WhatIsShortestPathLength(String v1, String v2) {
        Integer startIndex = cityToIndex.get(v1);
        Integer endIndex = cityToIndex.get(v2);
        if (startIndex == null || endIndex == null) {
            System.out.println(v1 + " --x-- " + v2);  
            return;
        }

        boolean[] visited = new boolean[adjList.size()];
        int[] distance = new int[adjList.size()];  
        int[] parent = new int[adjList.size()];    
        List<Integer> currentLevel = new ArrayList<>();  

        visited[startIndex] = true;
        distance[startIndex] = 0;  
        parent[startIndex] = -1;   
        currentLevel.add(startIndex);

        // BFS 
        while (!currentLevel.isEmpty()) {
            int current = currentLevel.get(0); 
            currentLevel.remove(0); 

            // If we have reached the goal, retrace the path
            if (current == endIndex) {
                System.out.println("Shortest path length from " + v1 + " to " + v2 + " is: " + distance[current]);
                return;
            }

            List<Edge> neighbors = adjList.get(current);

            for (int i = 0; i < neighbors.size(); i++) {
                for (int j = i + 1; j < neighbors.size(); j++) {
                    if (neighbors.get(i).getWeight() > neighbors.get(j).getWeight()) {
                        //Change places according to weights
                        Edge temp = neighbors.get(i);
                        neighbors.set(i, neighbors.get(j));
                        neighbors.set(j, temp);
                    }
                }
            }

            for (Edge edge : neighbors) {
                int neighbor = edge.getTo();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distance[neighbor] = distance[current] + 1;  
                    parent[neighbor] = current;
                    currentLevel.add(neighbor); 
                }
            }
        }

        System.out.println(v1 + " --x-- " + v2);  // If there is no path
    }

}
