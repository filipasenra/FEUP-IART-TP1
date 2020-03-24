package com.main.Model;

import javafx.util.Pair;

import java.util.*;

import static java.lang.Math.abs;

public class CityPlan {

    protected Problem problem;
    protected Hashtable<Pair<Integer, Integer>, Integer> gridMap = new Hashtable<>();
    protected Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePosition = new Hashtable<>();
    protected int fitness = 0;


    public CityPlan(Problem problem) {
        this.problem = problem;
    }

    public CityPlan(Hashtable<Pair<Integer, Integer>, Integer> gridMap, Problem problem) {

        this(problem);
        this.gridMap = gridMap;
    }


    public void initiateGrid() {
        Random rand = new Random();


        for (int i = 0; i < this.problem.getRows(); i++) {

            for (int j = 0; j < this.problem.getColumns(); j++) {

                if (gridMap.containsKey(new Pair<>(i, j))) {
                    continue;
                }

                Project project = this.problem.getProjects().get(rand.nextInt(this.problem.getProjects().size()));

                if (checkIfCompilable(project, new Pair<>(i, j))) {
                    addProject(project, new Pair<>(i, j));
                }


            }
        }

        this.calculateFitness();
    }

    public boolean checkIfCompilable(Project project, Pair<Integer, Integer> location) {

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

        ArrayList<Pair<Integer, Integer>> occupiedCells = project.getOccupiedCells();

        for (Pair<Integer, Integer> locationOfOccupiedCell : occupiedCells) {

            Pair<Integer, Integer> newLocation = new Pair<>
                    (location.getKey() + locationOfOccupiedCell.getKey(),
                            location.getValue() + locationOfOccupiedCell.getValue());

            this.gridMap.put(newLocation, project.getnProject());
        }

        this.mapAbsolutePosition.put(location, project.getnProject());

    }

    public void calculateFitness() {
        ArrayList<Pair<Integer, Integer>> typeOfServicesPerResidential = new ArrayList<>();

        this.fitness = 0;

        int minimumManhattanDistance = this.problem.getMaximumWalkingDistance();
        this.gridMap.forEach((location, idResidential) -> {

            if (this.problem.getProjects().get(idResidential).getClass() != ResidentialProject.class)
                return;

            for (int i = 0; i < minimumManhattanDistance; i++) {

                int j = minimumManhattanDistance - i;
                Pair<Integer, Integer> newLocation = new Pair<>(i + location.getKey(), j + location.getValue());

                this.addFitness(newLocation, idResidential, typeOfServicesPerResidential);

                Pair<Integer, Integer> newLocation1 = new Pair<>(-newLocation.getKey(), newLocation.getValue());
                this.addFitness(newLocation1, idResidential, typeOfServicesPerResidential);

                Pair<Integer, Integer> newLocation2 = new Pair<>(newLocation.getKey(), -newLocation.getValue());
                this.addFitness(newLocation2, idResidential, typeOfServicesPerResidential);


                Pair<Integer, Integer> newLocation3 = new Pair<>(-newLocation.getKey(), -newLocation.getValue());
                this.addFitness(newLocation3, idResidential, typeOfServicesPerResidential);

            }
        });


    }

    private void addFitness(Pair<Integer, Integer> location, int idResidential, ArrayList<Pair<Integer, Integer>> typeOfServicesPerResidential) {

        if (this.isUtilityInRange(location)) {
            int idUtility = this.gridMap.get(location);
            this.addUtilityToResidential(idResidential, idUtility, typeOfServicesPerResidential);
        }
    }

    private boolean isUtilityInRange(Pair<Integer, Integer> location) {

        if (!this.gridMap.contains(location))
            return false;

        int idOfBuilding = this.gridMap.get(location);
        if (this.problem.getProjects().get(idOfBuilding).getClass() != UtilityProject.class)
            return false;

        return true;

    }

    private void addUtilityToResidential(int idResidential, int idUtility, ArrayList<Pair<Integer, Integer>> typeOfServicesPerResidential) {

        UtilityProject utilityProject = (UtilityProject) this.problem.getProjects().get(idUtility);

        if (!typeOfServicesPerResidential.contains(new Pair<>(idResidential, utilityProject.getTypeOfService()))) {

            ResidentialProject residentialProject = (ResidentialProject) this.problem.getProjects().get(idResidential);
            this.fitness += residentialProject.getCapacity();
            typeOfServicesPerResidential.add(new Pair<>(idResidential, utilityProject.getTypeOfService()));

        }

    }

    private int manhattanDistance(Pair<Integer, Integer> location1, Pair<Integer, Integer> location2) {

        return abs(location1.getKey() + location2.getKey()) + abs(location1.getValue() + location2.getValue());
    }

    public int getFitness() {
        return fitness;
    }

    public Hashtable<Pair<Integer, Integer>, Integer> getMapAbsolutePosition() {
        return mapAbsolutePosition;
    }

    @Override
    public String toString() {
        return "CityPlan{" +
                "problem=" + problem +
                ", gridMap=" + gridMap +
                ", mapAbsolutePosition=" + mapAbsolutePosition +
                ", fitness=" + fitness +
                '}';
    }
}