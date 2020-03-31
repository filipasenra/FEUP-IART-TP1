package com.main.Model;

import javafx.util.Pair;
import java.util.*;
import static java.lang.Math.abs;

/**
 * Class that represents a City Plan
 * */
public class CityPlan {

    protected Problem problem;
    protected Hashtable<Pair<Integer, Integer>, Integer> gridMap = new Hashtable<>();  //Holds the occupied cells (key = location, value = number of the project)
    protected Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionUtility = new Hashtable<>(); //Holds the position of left up corner of the utility projects in the plan
    protected Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionResidential = new Hashtable<>(); //Holds the position of left up corner of the residential projects in the plan
    protected int fitness = 0;

    //Pair<Row, Col>

    /**
     * City Plan constructor
     * 
     * @param problem       problem to be solved
     */
    public CityPlan(Problem problem) {
        this.problem = problem;
    }

    /**
     * City Plan constructor
     * 
     * @param problem                           problem to be solved
     * @param gridMap                           hash table with the grid map
     * @param mapAbsolutePositionUtility        map with the absolute position of the utility buildings
     * @param mapAbsolutePositionResidential    map with the absolute position of the residential buildings
     * @param fitness                           fitness of the problem
     */
    public CityPlan(Problem problem, Hashtable<Pair<Integer, Integer>, Integer> gridMap, Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionUtility, Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionResidential, int fitness) {
        this.problem = problem;
        this.gridMap = gridMap;
        this.mapAbsolutePositionUtility = mapAbsolutePositionUtility;
        this.mapAbsolutePositionResidential = mapAbsolutePositionResidential;
        this.fitness = fitness;
    }

    /**
     * Initializes the plan (randomly generated) (initializes the grid and the other maps)
     */
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

    /**
     * Checks if a project in a certain position is compatible with the plan
     *  
     * @param project   project to check
     * @param location  desired location to insert the project on
     * @return          returns true if the project can be added and false if it can't be added 
     */
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

    /**
     * Adds the project to the plan (must be called after checkIfCompatible)
     * 
     * @param project       project to be added 
     * @param location      desired location to insert the project on
     */
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

    /**
     * Erases a project in a certain location from the plan (the project must exist in the location (location is the coordinates of the left up corner)
     * 
     * @param project   project to be erased
     * @param location  desired location to erase the project
     */
    public void eraseProject(Project project, Pair<Integer, Integer> location) {

        if (project.getClass() == ResidentialProject.class)
            this.mapAbsolutePositionResidential.remove(location);
        else if (project.getClass() == UtilityProject.class)
            this.mapAbsolutePositionUtility.remove(location);
        else
            return;

        ArrayList<Pair<Integer, Integer>> occupiedCells = project.getOccupiedCells();

        for (Pair<Integer, Integer> locationOfOccupiedCell : occupiedCells) {

            Pair<Integer, Integer> newLocation = new Pair<>
                    (location.getKey() + locationOfOccupiedCell.getKey(),
                            location.getValue() + locationOfOccupiedCell.getValue());

            this.gridMap.remove(newLocation);
        }

    }

    /**
     * Calculates the fitness of the plan
     */
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

                        if (manhattanDistance(newLocation, location) > minimumManhattanDistance)
                            continue;

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

    /**
     * Adds the Fitness of this cell (if it has any unique amenity to the project we are analyzing)
     * 
     * @param location                          location of the cell 
     * @param idResidential                     id of the residential
     * @param typeOfServicesPerResidential      type of services per residential
     */
    private void addFitness(Pair<Integer, Integer> location, int idResidential, ArrayList<Integer> typeOfServicesPerResidential) {

        if (this.isUtilityInRange(location)) {
            int idUtility = this.gridMap.get(location);

            this.addUtilityToResidential(idResidential, idUtility, typeOfServicesPerResidential);
        }
    }

    /**
     * Checks if there is a utility in the location
     * 
     * @param location      location to be checked
     * @return              if the location inserted is an utility project
     */
    private boolean isUtilityInRange(Pair<Integer, Integer> location) {

        if (!this.gridMap.containsKey(location))
            return false;

        int idOfBuilding = this.gridMap.get(location);

        return this.problem.getProjects().get(idOfBuilding).getClass() == UtilityProject.class;

    }

    /**
     * Adds a Utility to a Residential Building and updates the fitness of the plan
     * 
     * @param idResidential                     residential to add utility into
     * @param idUtility                         utility to be added
     * @param typeOfServicesPerResidential      type of services per residential
     */
    private void addUtilityToResidential(int idResidential, int idUtility, ArrayList<Integer> typeOfServicesPerResidential) {

        UtilityProject utilityProject = (UtilityProject) this.problem.getProjects().get(idUtility);

        //If we already accounted for this type of service, we shouldn't do it again
        if (typeOfServicesPerResidential.contains(utilityProject.typeOfService))
            return;

        ResidentialProject residentialProject = (ResidentialProject) this.problem.getProjects().get(idResidential);
        this.fitness += residentialProject.getCapacity();
        typeOfServicesPerResidential.add(utilityProject.getTypeOfService());

    }

    /**
     * Returns the manhattan distance between two locations
     * 
     * @param location1     first location
     * @param location2     second location
     * @return              returns the manhattan distance between the first and second location
     */
    private int manhattanDistance(Pair<Integer, Integer> location1, Pair<Integer, Integer> location2) {

        return abs(location1.getKey() - location2.getKey()) + abs(location1.getValue() - location2.getValue());
    }

    /**
     * Returns the fitness of the plan
     * 
     * @return  fitness
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * Returns the problem of the cityPlan
     * 
     * @return      problem
     */
    public Problem getProblem() {
        return problem;
    }

    /**
     * Returns the grid map
     * 
     * @return      grid map
     */
    public Hashtable<Pair<Integer, Integer>, Integer> getGridMap() {
        return gridMap;
    }

    /**
     * Method that converts the grid into a string
     */
    @Override
    public String toString() {

        StringBuilder output = new StringBuilder();

        for (int i=0; i<this.problem.getRows(); i++) {
            for (int j=0; j<this.problem.getColumns(); j++) {

                Pair<Integer, Integer> location = new Pair<>(i, j);

                if(gridMap.containsKey(location))
                    output.append(" ").append(gridMap.get(location)).append(" ");
                else
                    output.append(" . ");
            }
            output.append("\n");
        }

        return output.toString();
    }
}