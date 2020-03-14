package com.main.Model;

import java.util.ArrayList;

public class UtilityProject extends Project {
    int u = 0; //service provided by the utility building

    public UtilityProject(int nProject, int rows, int columns, int u, ArrayList<ArrayList<Integer>> occupiedCells) {
        super(nProject, rows, columns, occupiedCells);
        this.u = u;
    }
}
