package com.main.Model;

import javafx.util.Pair;

import java.util.*;

import static java.lang.Math.abs;

public class CityPlan {

    Hashtable<Pair<Integer, Integer>, Integer> gridMap = new Hashtable<>();
    int fitnessOfPopulation = 0;

    public void initiateGrid(Problem problem) {
        Random rand = new Random();


        for(int i = 0; i < problem.getRows(); i++){

            for(int j = 0; j < problem.getColumns(); j++){

                if (gridMap.containsKey(new Pair(i, j))) {
                    continue;
                }

                Project project = problem.getProjects().get(rand.nextInt(problem.getProjects().size()));

                if(checkIfCompilable(project)){
                    addProject(project);
                }


            }
        }

        this.calculateFitnessOfPopulation(problem);
    }

    private boolean checkIfCompilable(Project project) {

        ArrayList<Pair<Integer, Integer>> occupiedCells = project.getOccupiedCells();

        for(int i = 0; i < occupiedCells.size(); i++){

            Pair locationOfOccupiedCell = occupiedCells.get(i);

            if(this.gridMap.containsKey(locationOfOccupiedCell))
                return false;
        }

        return true;
    }

    private void addProject(Project project){

        ArrayList<Pair<Integer, Integer>> occupiedCells = project.getOccupiedCells();

        for(int i = 0; i < occupiedCells.size(); i++){

            Pair locationOfOccupiedCell = occupiedCells.get(i);

            this.gridMap.put(locationOfOccupiedCell, project.getnProject());
        }

    }

    public void calculateFitnessOfPopulation(Problem problem) {
        ArrayList<Pair<Integer, Integer>> typeOfServicesPerResidential = new ArrayList<>();

        this.fitnessOfPopulation = 0;

        int minimumManhattanDistance = problem.getMaximumWalkingDistance();
        this.gridMap.forEach((location, id) -> {

            if(problem.getProjects().get(id).getClass() != ResidentialProject.class)
                return;

            for (int i = 0; i < minimumManhattanDistance; i++) {
                for (int j = 0; j < i; j++) {

                    if(!this.gridMap.contains(new Pair(i, j)))
                        continue;

                    int idOfBuilding = this.gridMap.get(new Pair(i, j));
                    if (problem.getProjects().get(idOfBuilding).getClass() != UtilityProject.class)
                        continue;

                    UtilityProject utilityProject = (UtilityProject) problem.getProjects().get(idOfBuilding);

                    if(!typeOfServicesPerResidential.contains(new Pair(id, utilityProject.getTypeOfService()))) {

                        ResidentialProject residentialProject = (ResidentialProject) problem.getProjects().get(id);
                        this.fitnessOfPopulation += residentialProject.getCapacity();

                    }

                }
            }
        });


    }

    private int manhattanDistance(Pair<Integer, Integer> location1, Pair<Integer, Integer> location2){

        return abs(location1.getKey() + location2.getKey()) + abs(location1.getValue() + location2.getValue());
    }

    public int getFitnessOfPopulation() {
        return fitnessOfPopulation;
    }
}
