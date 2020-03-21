package com.main.Model;

import javafx.util.Pair;

import java.util.ArrayList;

public class ResidentialProject extends Project {
    int capacity; //residential capacity of the building

    public ResidentialProject(int nProject, int rows, int columns, int capacity, ArrayList<Pair<Integer, Integer>> occupiedCells) {
        super(nProject, rows, columns, occupiedCells);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
