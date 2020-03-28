package com.main.Algorithms;

import com.main.Model.*;
import javafx.util.Pair;

import java.util.*;

public class Individual extends CityPlan implements Cloneable {

    public Individual(Problem problem) {
        super(problem);
    }

    public Individual(Problem problem, Hashtable<Pair<Integer, Integer>, Integer> gridMap, Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionUtility, Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionResidential, int fitness) {
        super(problem, gridMap, mapAbsolutePositionUtility, mapAbsolutePositionResidential, fitness);
    }

    public Individual crossOver(Individual individual) {


        Individual mutatedIndividual = new Individual(problem);

        crossOverSpecificType(mutatedIndividual, this.mapAbsolutePositionUtility, individual.mapAbsolutePositionUtility);
        crossOverSpecificType(mutatedIndividual, this.mapAbsolutePositionResidential, individual.mapAbsolutePositionResidential);

        mutatedIndividual.calculateFitness();

        return mutatedIndividual;

    }

    public void crossOverSpecificType(Individual mutatedIndividual, Hashtable<Pair<Integer, Integer>, Integer> currentAbsoluteLocation, Hashtable<Pair<Integer, Integer>, Integer> individualAbsoluteLocation) {

        Random rand = new Random();

        Set<Pair<Integer, Integer>> primeKeys = new HashSet<>();

        primeKeys.addAll(currentAbsoluteLocation.keySet());
        primeKeys.addAll(individualAbsoluteLocation.keySet());

        for (Pair<Integer, Integer> location : primeKeys) {

            int idProject;
            boolean currentHasLocation = currentAbsoluteLocation.containsKey(location);
            boolean individualHasLocation = individualAbsoluteLocation.containsKey(location);

            if (currentHasLocation && individualHasLocation) {

                if (rand.nextBoolean()) {
                    idProject = currentAbsoluteLocation.get(location);
                } else {
                    idProject = individualAbsoluteLocation.get(location);
                }

            } else if (currentHasLocation) {
                idProject = currentAbsoluteLocation.get(location);
            } else if (individualHasLocation) {
                idProject = individualAbsoluteLocation.get(location);
            } else {
                continue;
            }

            if (mutatedIndividual.checkIfCompatible(problem.getProjects().get(idProject), location)) {
                mutatedIndividual.addProject(problem.getProjects().get(idProject), location);
            }

        }
    }

    public void mutate() {

        Random rand = new Random();
        int nTries = 10;

        while (nTries > 0) {

            nTries--;

            int x = rand.nextInt(this.problem.getRows());
            int y = rand.nextInt(this.problem.getColumns());

            int nProject = rand.nextInt(this.problem.getProjects().size());
            Project project = this.problem.getProjects().get(nProject);

            Pair<Integer, Integer> location = new Pair<>(x, y);

            if (addProjectWithCollisions(project, location)) {
                break;
            }
        }

        this.calculateFitness();

    }

    public boolean addProjectWithCollisions(Project project, Pair<Integer, Integer> location) {

        int maxRow = location.getKey()+project.getRows();
        int maxCol = location.getValue()+project.getColumns();

        if(maxCol > this.problem.getColumns())
            return false;

        if(maxRow > this.problem.getRows())
            return false;

        if (project.getClass() != ResidentialProject.class && project.getClass() != UtilityProject.class)
            return false;

        ArrayList<Pair<Integer, Integer>> occupiedCells = project.getOccupiedCells();

        for (Pair<Integer, Integer> locationOfOccupiedCell : occupiedCells) {

            Pair<Integer, Integer> newLocation = new Pair<>
                    (location.getKey() + locationOfOccupiedCell.getKey(),
                            location.getValue() + locationOfOccupiedCell.getValue());

            if (this.gridMap.containsKey(newLocation))
            {
                eraseCollisions(newLocation);
            }

            this.gridMap.put(newLocation, project.getnProject());

        }

        if (project.getClass() == ResidentialProject.class)
            this.mapAbsolutePositionResidential.put(location, project.getnProject());
        else
            this.mapAbsolutePositionUtility.put(location, project.getnProject());

        return true;
    }

    void eraseCollisions(Pair<Integer, Integer> location) {

        Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePosition;

        int nProject = this.gridMap.get(location);
        Project collisionProject = this.problem.getProjects().get(nProject);

        if(collisionProject.getClass() == ResidentialProject.class)
            mapAbsolutePosition = this.mapAbsolutePositionResidential;
        else if(collisionProject.getClass() == UtilityProject.class)
            mapAbsolutePosition = this.mapAbsolutePositionUtility;
        else
            return;

        for (Map.Entry<Pair<Integer, Integer>, Integer> entry : mapAbsolutePosition.entrySet()) {
            Pair<Integer, Integer> absoluteLocation = entry.getKey();
            Integer idProject = entry.getValue();
            if (idProject != nProject)
                continue;

            Pair<Integer, Integer> relativePosition = new Pair<>(
                    location.getKey() - absoluteLocation.getKey(),
                    location.getValue() - absoluteLocation.getValue());

            if (collisionProject.getOccupiedCells().contains(relativePosition)) {
                eraseProject(collisionProject, absoluteLocation);
                break;
            }

        }

    }

    @Override
    public Individual clone() {

        return new Individual(this.problem, (Hashtable<Pair<Integer, Integer>, Integer>) this.gridMap.clone(), (Hashtable<Pair<Integer, Integer>, Integer>) this.mapAbsolutePositionUtility.clone(), (Hashtable<Pair<Integer, Integer>, Integer>) this.mapAbsolutePositionResidential.clone(), this.fitness);
    }

}