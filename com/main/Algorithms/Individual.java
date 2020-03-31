package com.main.Algorithms;

import com.main.Model.*;
import javafx.util.Pair;

import java.util.*;

public class Individual extends CityPlan implements Cloneable {

    /**
     * Individual constructor
     * 
     * @param problem       problem to be solved
     */
    public Individual(Problem problem) {
        super(problem);
    }

    /**
     * Individual constructor
     * 
     * @param problem                           problem to be solved
     * @param gridMap                           grid map of the individual
     * @param mapAbsolutePositionUtility        map of the absolute position of the utility projects
     * @param mapAbsolutePositionResidential    map of the absolute position of the residential projects
     * @param fitness                           fitness of the solution
     */
    public Individual(Problem problem, Hashtable<Pair<Integer, Integer>, Integer> gridMap, Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionUtility, Hashtable<Pair<Integer, Integer>, Integer> mapAbsolutePositionResidential, int fitness) {
        super(problem, gridMap, mapAbsolutePositionUtility, mapAbsolutePositionResidential, fitness);
    }

    /**
     * Cross over an individual
     * 
     * @param individual    individual to be crossed over
     * @return              crossed over individual
     */
    public Individual crossOver(Individual individual) {


        Individual mutatedIndividual = new Individual(problem);

        crossOverSpecificType(mutatedIndividual, this.mapAbsolutePositionUtility, individual.mapAbsolutePositionUtility);
        crossOverSpecificType(mutatedIndividual, this.mapAbsolutePositionResidential, individual.mapAbsolutePositionResidential);

        mutatedIndividual.calculateFitness();

        return mutatedIndividual;

    }

    /**
     * Function that crosses over two individuals
     * 
     * @param mutatedIndividual             mutated individual
     * @param currentAbsoluteLocation       current absolute location
     * @param individualAbsoluteLocation    individual absolute location
     */
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

    /**
     * Function that mutates an individual
     */
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

    /**
     * Function that adds a project with collisions
     * 
     * @param project       project to be added
     * @param location      location to add the project
     * @return              true if project was successfully added
     */
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

    /**
     * Function that erases collisions
     * 
     * @param location      location of the collision to erase
     */
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

    /**
     * Methods that allows the comparison individuals
     */
    @Override
    public boolean equals(Object obj) {

        if(obj.getClass() != this.getClass())
            return false;

        Individual individual = (Individual) obj;
        return (this.mapAbsolutePositionUtility.equals(individual.mapAbsolutePositionUtility)) && (this.mapAbsolutePositionResidential.equals(individual.mapAbsolutePositionResidential));
    }

    /**
     * Method that allows for one individual to be cloned
     */
    @Override
    public Individual clone() {

        return new Individual(this.problem, (Hashtable<Pair<Integer, Integer>, Integer>) this.gridMap.clone(), (Hashtable<Pair<Integer, Integer>, Integer>) this.mapAbsolutePositionUtility.clone(), (Hashtable<Pair<Integer, Integer>, Integer>) this.mapAbsolutePositionResidential.clone(), this.fitness);
    }

}