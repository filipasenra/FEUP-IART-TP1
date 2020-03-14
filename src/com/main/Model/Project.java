package com.main.Model;

import java.util.ArrayList;

public class Project {
    int rows = 0; //number of rows of the building project (1 ≤ hp ≤ min(H, 50))
    int columns = 0; //number of columns of the building project (1 ≤ wp ≤ min(W, 50))
    // lines describing the plan of the building (one row after another from row 0 to row hp hp ­ 1)
    ArrayList<ArrayList<Integer>> occupiedCells = new ArrayList<ArrayList<Integer>>();

    public Project(int rows, int columns, ArrayList<ArrayList<Integer>> occupiedCells) {
        this.rows= rows;
        this.columns = columns;
        this.occupiedCells = occupiedCells;
    }
}
