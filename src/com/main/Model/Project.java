package com.main.Model;

import javafx.util.Pair;

import java.util.ArrayList;

public class Project {
    int nProject;
    int rows; //number of rows of the building project (1 ≤ hp ≤ min(H, 50))
    int columns; //number of columns of the building project (1 ≤ wp ≤ min(W, 50))
    // lines describing the plan of the building (one row after another from row 0 to row hp hp ­ 1)
    private ArrayList<Pair<Integer, Integer>> occupiedCells = new ArrayList<>();

    public Project(int nProject, int rows, int columns, ArrayList<Pair<Integer, Integer>> occupiedCells) {
        this.nProject = nProject;
        this.rows= rows;
        this.columns = columns;
        this.occupiedCells = occupiedCells;
    }

    public int getnProject() {
        return nProject;
    }

    public ArrayList<Pair<Integer, Integer>> getOccupiedCells() {
        return occupiedCells;
    }
}
