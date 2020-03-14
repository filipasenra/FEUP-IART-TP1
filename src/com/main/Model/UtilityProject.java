package com.main.Model;

import java.util.ArrayList;

public class UtilityProject extends Project {
    int u = 0; //service provided by the utility building

    public UtilityProject(int h, int w, int u, ArrayList<ArrayList<Integer>> occupiedCells) {
        super(h, w, occupiedCells);
        this.u = u;
    }
}
