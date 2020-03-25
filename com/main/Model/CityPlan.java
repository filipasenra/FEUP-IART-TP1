package com.main.Model;

import javafx.util.Pair;

import java.util.*;

import static java.lang.Math.abs;

public class CityPlan {

    protected Problem problem;
    protected Hashtable<Pair<Integer, Integer>, Integer> gridMap = new Hashtable<>();
    protected Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionUtility = new Hashtable<>();
    protected Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionResidential = new Hashtable<>();
    protected int fitness = 0;

    //Pair<Row, Col>

    public CityPlan(Problem problem) {
        this.problem = problem;
    }

    public void initiateGrid() {
        Random rand = new Random();

        for (int i = 0; i < this.problem.getRows(); i++) {

            for (int j = 0; j < this.problem.getColumns(); j++) {

                if (gridMap.containsKey(new Pair<>(i, j))) {
                    continue;
                }

                Project project = this.problem.getProjects().get(rand.nextInt(this.problem.getProjects().size()));

                if (checkIfCompatible(project, new Pair<>(i, j))) {
                    addProject(project, new Pair<>(i, j));
                }

            }
        }

        this.calculateFitness();
    }

    public boolean checkIfCompatible(Project project, Pair<Integer, Integer> location) {

        int maxRow = location.getKey()+project.getRows();
        int maxCol = location.getValue()+project.getColumns();

        if(maxCol > this.problem.getColumns())
            return false;

        if(maxRow > this.problem.getRows())
            return false;

        ArrayList<Pair<Integer, Integer>> occupiedCells = project.getOccupiedCells();

        for (Pair<Integer, Integer> locationOfOccupiedCell : occupiedCells) {

            Pair<Integer, Integer> newLocation = new Pair<>
                    (location.getKey() + locationOfOccupiedCell.getKey(),
                            location.getValue() + locationOfOccupiedCell.getValue());

            if (this.gridMap.containsKey(newLocation))
                return false;
        }

        return true;
    }

    public void addProject(Project project, Pair<Integer, Integer> location) {

        if (project.getClass() == ResidentialProject.class)
            this.mapAbsolutePositionResidential.put(location, project.getnProject());
        else if (project.getClass() == UtilityProject.class)
            this.mapAbsolutePositionUtility.put(location, project.getnProject());
        else
            return;

        ArrayList<Pair<Integer, Integer>> occupiedCells = project.getOccupiedCells();

        for (Pair<Integer, Integer> locationOfOccupiedCell : occupiedCells) {

            Pair<Integer, Integer> newLocation = new Pair<>
                    (location.getKey() + locationOfOccupiedCell.getKey(),
                            location.getValue() + locationOfOccupiedCell.getValue());

            this.gridMap.put(newLocation, project.getnProject());
        }

    }

    public void calculateFitness() {
        this.fitness = 0;
        int minimumManhattanDistance = this.problem.getMaximumWalkingDistance();

        //Loop thought all the residential projects in the city
        this.mapAbsolutePositionResidential.forEach((absoluteLocation, idResidential) -> {

            //List to save the amenities available to this residential projects //
            ArrayList<Integer> typeOfServicesPerResidential = new ArrayList<>();

            //Residential Project
            ResidentialProject residentialProject = (ResidentialProject) this.problem.getProjects().get(idResidential);

            //Occupied Cells of Residential Project
            ArrayList<Pair<Integer, Integer>> occupiedCells = residentialProject.getOccupiedCells();

            //Let's check if there is any amenity nearby (less than the manhattan distance)
            for (Pair<Integer, Integer> occupiedCell : occupiedCells) {

                //Location of the occupiedCell in the city
                Pair<Integer, Integer> location = new Pair<>(
                        absoluteLocation.getKey() + occupiedCell.getKey(),
                        (absoluteLocation.getValue() + occupiedCell.getValue()));

                //Check all the cells in the perimeter of the minimum Manhattan Distance
                for (int x = 0; x <= minimumManhattanDistance; x++) {

                    for (int y = 0; y <= x; y++) {

                        Pair<Integer, Integer> newLocation = new Pair<>(x + location.getKey(), y + location.getValue());
                        this.addFitness(newLocation, idResidential, typeOfServicesPerResidential);

                        Pair<Integer, Integer> newLocation1 = new Pair<>(-x + location.getKey(), newLocation.getValue());
                        this.addFitness(newLocation1, idResidential, typeOfServicesPerResidential);

                        Pair<Integer, Integer> newLocation2 = new Pair<>(newLocation.getKey(), -y + location.getValue());
                        this.addFitness(newLocation2, idResidential, typeOfServicesPerResidential);

                        Pair<Integer, Integer> newLocation3 = new Pair<>(-x + location.getKey(), -y + location.getValue());
                        this.addFitness(newLocation3, idResidential, typeOfServicesPerResidential);

                    }

                }
            }
        });
    }

    //Adds the Fitness of this cell (if it has any unique amenity)
    private void addFitness(Pair<Integer, Integer> location, int idResidential, ArrayList<Integer> typeOfServicesPerResidential) {

        if (this.isUtilityInRange(location)) {
            int idUtility = this.gridMap.get(location);
            this.addUtilityToResidential(idResidential, idUtility, typeOfServicesPerResidential);
        }
    }

    //Checks if the location is an Utility Project
    private boolean isUtilityInRange(Pair<Integer, Integer> location) {

        if (!this.gridMap.containsKey(location))
            return false;

        int idOfBuilding = this.gridMap.get(location);
        if (this.problem.getProjects().get(idOfBuilding).getClass() != UtilityProject.class)
            return false;

        return true;

    }

    //Adds Utility to the Residential Building and adds its fitness
    private void addUtilityToResidential(int idResidential, int idUtility, ArrayList<Integer> typeOfServicesPerResidential) {

        UtilityProject utilityProject = (UtilityProject) this.problem.getProjects().get(idUtility);

        //If we already accounted for this type of service, we shouldn't do it again
        if (typeOfServicesPerResidential.contains(utilityProject.typeOfService))
            return;

        ResidentialProject residentialProject = (ResidentialProject) this.problem.getProjects().get(idResidential);
        this.fitness += residentialProject.getCapacity();
        typeOfServicesPerResidential.add(utilityProject.getTypeOfService());
    }

    private int manhattanDistance(Pair<Integer, Integer> location1, Pair<Integer, Integer> location2) {

        return abs(location1.getKey() + location2.getKey()) + abs(location1.getValue() + location2.getValue());
    }

    public int getFitness() {
        return fitness;
    }

    public Hashtable<Pair<Integer, Integer>, Integer> getMapAbsolutePositionUtility() {
        return mapAbsolutePositionUtility;
    }

    public Hashtable<Pair<Integer, Integer>, Integer> getMapAbsolutePositionResidential() {
        return mapAbsolutePositionResidential;
    }

    public Hashtable<Pair<Integer, Integer>, Integer> getGridMap() {
        return gridMap;
    }

    @Override
    public String toString() {

        String output = "";

        for (int i=0; i<this.problem.getRows(); i++) {
            for (int j=0; j<this.problem.getColumns(); j++) {

                Pair<Integer, Integer> location = new Pair<>(i, j);

                if(gridMap.containsKey(location))
                    output += " " + gridMap.get(location) + " ";
                else
                    output += " . ";
            }
            output += "\n";
        }

        return output;
    }
}