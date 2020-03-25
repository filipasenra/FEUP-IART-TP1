package com.main.Model;

import javafx.util.Pair;

import java.util.ArrayList;

public class UtilityProject extends Project {
    int typeOfService; //service provided by the utility building

    public UtilityProject(int nProject, int rows, int columns, int typeOfService, ArrayList<Pair<Integer, Integer>> occupiedCells) {
        super(nProject, rows, columns, occupiedCells);
        this.typeOfService = typeOfService;
    }

    public int getTypeOfService() {
        return typeOfService;
    }
}
