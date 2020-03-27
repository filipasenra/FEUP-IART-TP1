package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;
import com.main.Model.Project;
import javafx.util.Pair;

import java.util.*;

public class Individual extends CityPlan implements Cloneable {

    Pair<Integer, Integer> location = new Pair<>(0, 0);
    int projectNumber = 0;

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

    public boolean mutate(Pair<Integer, Integer> location, int projectNumber) {

        if(location.getValue() >= this.problem.getRows())
            location = new Pair<>(location.getKey() + 1, 0);

        if(location.getKey() >= this.problem.getColumns())
            location = new Pair<>(0, 0);

        for (int y = location.getKey(); y < this.problem.getColumns(); y++) {
            for (int x = location.getValue(); x < this.problem.getRows(); x++) {

                Project oldProject = null;
                if (this.mapAbsolutePositionResidential.containsKey(location)) {

                    Integer nOldProject = this.mapAbsolutePositionResidential.get(location);
                    oldProject = this.problem.getProjects().get(nOldProject);
                    this.eraseProject(oldProject, location);

                } else if (this.mapAbsolutePositionUtility.containsKey(location)) {

                    Integer nOldProject = this.mapAbsolutePositionUtility.get(location);
                    oldProject = this.problem.getProjects().get(nOldProject);
                    this.eraseProject(oldProject, location);
                } else if (this.gridMap.containsKey(new Pair<>(x, y))) {
                    continue;
                }

                System.out.println(location);

                for (int n = projectNumber; n < this.problem.getProjects().size(); n++) {

                    if (oldProject != null && n == oldProject.getnProject())
                        continue;

                    Project project = this.problem.getProjects().get(n);

                    if (checkIfCompatible(project, new Pair<>(x, y))) {
                        addProject(project, new Pair<>(x, y));
                        this.location = new Pair<>(x, y);
                        this.projectNumber = n+1;
                        this.calculateFitness();
                        return true;
                    }
                }
            }
        }

        //Could not find a neighbor
        return false;
    }

    @Override
    public Individual clone() {

        return new Individual(this.problem, (Hashtable<Pair<Integer, Integer>, Integer>) this.gridMap.clone(), (Hashtable<Pair<Integer, Integer>, Integer>) this.mapAbsolutePositionUtility.clone(), (Hashtable<Pair<Integer, Integer>, Integer>) this.mapAbsolutePositionResidential.clone(), this.fitness);
    }

}