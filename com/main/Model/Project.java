package com.main.Model;

import javafx.util.Pair;
import java.util.ArrayList;

/**
 * Class that represents a Project
 * */
public class Project {
    int nProject;
    int rows; 
    int columns;
    private ArrayList<Pair<Integer, Integer>> occupiedCells; //Location of occupied cells (row, col)

    /**
     * Project constructor
     * 
     * @param nProject       number of the project
     * @param rows           number of rows of the building project (1 ≤ hp ≤ min(H, 50))
     * @param columns        number of columns of the building project (1 ≤ wp ≤ min(W, 50))
     * @param occupiedCells  array with the occupied cells
     */
    public Project(int nProject, int rows, int columns, ArrayList<Pair<Integer, Integer>> occupiedCells) {
        this.nProject = nProject;
        this.rows= rows;
        this.columns = columns;
        this.occupiedCells = occupiedCells;
    }

    /**
     * Returns the number of the project
     * 
     * @return number of the project
     */
    public int getnProject() {
        return nProject;
    }

    /**
     * Returns the occupied cells
     * 
     * @return occupied cells
     */
    public ArrayList<Pair<Integer, Integer>> getOccupiedCells() {
        return occupiedCells;
    }

    /**
     * Returns the number of rows
     * 
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns
     * 
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }
}
