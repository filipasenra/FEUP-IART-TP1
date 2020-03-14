package com.main.Model;

import java.util.ArrayList;

public class ResidentialProject extends Project {
    int r = 0; //residential capacity of the building

    public ResidentialProject(int h, int w, int r, ArrayList<ArrayList<Integer>> occupiedCells) {
        super(h, w, occupiedCells);
        this.r = r;
    }
}
