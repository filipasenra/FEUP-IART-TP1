package com.main.Model;

import javafx.util.Pair;

import java.util.ArrayList;

public class ResidentialProject extends Project {
    int capacity; //residential capacity of the building

    /**
    * Utility Project constructor 
    *
    * @param  nProject   number of the project
    * @param  rows  number of rows
    * @param  columns   number of columns
    * @param  capacity  residential capacity of the building
    * @param  occupiedCells   array with the occupied cells
    *
    */
    public ResidentialProject(int nProject, int rows, int columns, int capacity, ArrayList<Pair<Integer, Integer>> occupiedCells) {
        super(nProject, rows, columns, occupiedCells);
        this.capacity = capacity;
    }

    /**
     * Function that returns the residential capacity of the building
     * 
     * @return residential capacity of the building
     */
    public int getCapacity() {
        return capacity;
    }
}
