package com.main.Parser;

import com.main.Model.Problem;
import com.main.Model.ResidentialProject;
import com.main.Model.UtilityProject;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    String file;
    Problem problem;

    /**
    * Parser constructor. 
    *
    * @param  file   file to be parsed
    */
    public Parser(String file) {

        this.file = file;

    }

    /**
    * Function that parses the file. 
    */
    public void parseFile() throws IOException {


        FileReader input = new FileReader(file);
        BufferedReader bufRead = new BufferedReader(input);

        int nBuildingPlans;
        if ((nBuildingPlans = parseProblem(bufRead)) == -1) { //get number of building plans
            return;
        }

        for (int i = 0; i < nBuildingPlans; i++) {//parse all build plans

            if (!this.parseBuilding(bufRead, i))
                return;

        }

    }

    /**
    * Function responsible for the parsing of the problem. 
    *
    * @param  bufRead   buffer containing the file
    * @return           the number of building plans, returns -1 in case of ERROR
    */
    public int parseProblem(BufferedReader bufRead) throws IOException {

        String myLine;
        myLine = bufRead.readLine();

        if (myLine == null)
            return -1;

        String[] problemCharacteristics = myLine.split(" ");

        if (problemCharacteristics.length != 4)
            return -1;

        this.problem = new Problem(Integer.parseInt(problemCharacteristics[0]), Integer.parseInt(problemCharacteristics[1]), Integer.parseInt(problemCharacteristics[2]));

        return Integer.parseInt(problemCharacteristics[3]);

    }

    /**
    * Function responsible for the parsing of a building. 
    *
    * @param  bufRead   buffer containing the file
    * @param  nProject  number of the project
    * @return           the number of building plans, returns -1 in case of ERROR
    */
    public boolean parseBuilding(BufferedReader bufRead, int nProject) throws IOException {

        String myLine;

        //parse main info
        if ((myLine = bufRead.readLine()) == null)
            return false;

        String[] buildingCharacteristics = myLine.split(" ");
        String type = buildingCharacteristics[0];

        int rows = Integer.parseInt(buildingCharacteristics[1]);
        int columns = Integer.parseInt(buildingCharacteristics[2]);
        int capacityORtype = Integer.parseInt(buildingCharacteristics[3]);


        //parse occupied cells
        ArrayList<Pair<Integer, Integer>> occupiedCells = new ArrayList<>();
        for (int i = 0; i < rows; i++) {

            if ((myLine = bufRead.readLine()) == null)
                return false;

            String[] occupiedCellsLine = myLine.split("");

            for (int j = 0; j < occupiedCellsLine.length; j++) {

                if (occupiedCellsLine[j].equals("#")) {
                    occupiedCells.add(new Pair(i, j));
                }
            }

        }

        //adding project to city plan
        if (type.equals("R"))
            this.problem.addProject(new ResidentialProject(nProject, rows, columns, capacityORtype, occupiedCells));
        else if (type.equals("U"))
            this.problem.addProject(new UtilityProject(nProject, rows, columns, capacityORtype, occupiedCells));
        else
            return false;

        return true;
    }

    /**
    * Function responsible for returning the problem 
    *
    * @return           problem
    */
    public Problem getProblem() {
        return problem;
    }
}
