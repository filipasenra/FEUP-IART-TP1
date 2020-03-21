package com.main.Model;

import java.util.ArrayList;

public class ResidentialProject extends Project {
    int r; //residential capacity of the building

    public ResidentialProject(int nProject, int rows, int columns, int r, ArrayList<ArrayList<Integer>> occupiedCells) {
        super(nProject, rows, columns, occupiedCells);
        this.r = r;
    }
}
