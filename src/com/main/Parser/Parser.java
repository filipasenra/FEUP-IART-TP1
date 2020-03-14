package com.main.Parser;

import com.main.Model.CityPlan;
import com.main.Model.ResidentialProject;
import com.main.Model.UtilityProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    String file;
    CityPlan cityPlan;

    public Parser(String file) {

        this.file = file;

    }

    public void parseFile() {


        try {
            FileReader input = new FileReader(file);
            BufferedReader bufRead = new BufferedReader(input);
            String myLine = null;

            int nBuildingPlans;
            if ((nBuildingPlans = parseCityPlan(bufRead)) == -1) {
                return;
            }

            while (nBuildingPlans > 0) {

                if (!this.parseBuilding(bufRead))
                    return;

                nBuildingPlans--;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int parseCityPlan(BufferedReader bufRead) throws IOException {

        String myLine = null;
        myLine = bufRead.readLine();

        if (myLine == null)
            return -1;

        String[] cityPlanCharacteristics = myLine.split(" ");

        if (cityPlanCharacteristics.length != 4)
            return -1;

        this.cityPlan = new CityPlan(Integer.parseInt(cityPlanCharacteristics[0]), Integer.parseInt(cityPlanCharacteristics[1]), Integer.parseInt(cityPlanCharacteristics[2]));

        return Integer.parseInt(cityPlanCharacteristics[3]);

    }

    public boolean parseBuilding(BufferedReader bufRead) throws IOException {

        String myLine = null;

        //parse main info
        if((myLine = bufRead.readLine()) == null)
            return false;

        String[] buildingCharacteristics = myLine.split(" ");
        String type = buildingCharacteristics[0];

        int rows = Integer.parseInt(buildingCharacteristics[1]);
        int columns = Integer.parseInt(buildingCharacteristics[2]);
        int capacityORtype = Integer.parseInt(buildingCharacteristics[3]);


        //parse occupied cells
        ArrayList<ArrayList<Integer>> occupiedCells = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < rows; i++){

            if((myLine = bufRead.readLine()) == null)
                return false;

            String[] occupiedCellsLine = myLine.split(" ");

            for(int j = 0; j < occupiedCellsLine.length; j++){

                if(occupiedCellsLine[j] == "#")
                    occupiedCells.add(new ArrayList<Integer>(Arrays.asList(i, j)));
            }

        }

        //adding project to city plan
        if(type.equals("R"))
            this.cityPlan.addResidentialProject(new ResidentialProject(rows, columns, capacityORtype, occupiedCells));
        else if(type.equals("U"))
            this.cityPlan.addUtilityProject(new UtilityProject(rows, columns, capacityORtype, occupiedCells));
        else
            return false;

        return true;
    }

    public CityPlan getCityPlan() {
        return cityPlan;
    }
}
