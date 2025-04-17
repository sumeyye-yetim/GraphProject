
package com.mycompany.project33;

public class Edge {
    int to; 
    private int weight;   

    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }

 
    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return to + " (" + weight + ")";
    }
}
