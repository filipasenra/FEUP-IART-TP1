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
        this.gridMap.forEach((location, id) -> {

            if (this.problem.getProjects().get(id).getClass() != ResidentialProject.class)
                return;

            for (int i = 0; i < minimumManhattanDistance; i++) {
                for (int j = 0; j < i; j++) {

                    if (!this.gridMap.contains(new Pair<>(i, j)))
                        continue;

                    int idOfBuilding = this.gridMap.get(new Pair<>(i, j));
                    if (this.problem.getProjects().get(idOfBuilding).getClass() != UtilityProject.class)
                        continue;

                    UtilityProject utilityProject = (UtilityProject) this.problem.getProjects().get(idOfBuilding);

                    if (!typeOfServicesPerResidential.contains(new Pair<>(id, utilityProject.getTypeOfService()))) {

                        ResidentialProject residentialProject = (ResidentialProject) this.problem.getProjects().get(id);
                        this.fitness += residentialProject.getCapacity();
                        typeOfServicesPerResidential.add(new Pair<>(id, utilityProject.getTypeOfService()));

                    }

                }
            }
        });


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