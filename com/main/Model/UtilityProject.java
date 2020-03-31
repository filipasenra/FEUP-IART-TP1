package com.main.Model;

import javafx.util.Pair;
import java.util.ArrayList;

/**
 * Class that represents a Utility Project
 * */
public class UtilityProject extends Project {
    int typeOfService; //service provided by the utility building

    /**
    * Utility Project constructor 
    *
    * @param  nProject   number of the project
    * @param  rows       number of rows
    * @param  columns    number of columns
    * @param  typeOfService  the type of service of the project
    * @param  occupiedCells   array with the occupied cells
    *
    */
    public UtilityProject(int nProject, int rows, int columns, int typeOfService, ArrayList<Pair<Integer, Integer>> occupiedCells) {
        super(nProject, rows, columns, occupiedCells);
        this.typeOfService = typeOfService;
    }

    /**
    * Returns the type of service of this utility building provides
    *
    * @return type of service 
    */
    public int getTypeOfService() {
        return typeOfService;
    }
}
