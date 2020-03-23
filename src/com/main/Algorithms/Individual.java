package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;
import com.main.Model.Project;
import javafx.util.Pair;

import java.util.*;

public class Individual extends CityPlan {

    public Individual(Problem problem) {
        super(problem);
    }

    public Individual(Hashtable<Pair<Integer, Integer>, Integer> gridMap, Problem problem) {
        super(gridMap, problem);
    }

    public Individual crossOver(Individual individual) {

        Individual mutatedIndividual = new Individual(problem);

        Random rand = new Random();

        Set<Pair<Integer, Integer>> primeKeys = new HashSet<>();
        primeKeys.addAll(this.mapAbsolutePosition.keySet());
        primeKeys.addAll(this.mapAbsolutePosition.keySet());

        for (Pair<Integer, Integer> location : primeKeys) {

            int idProject;
            boolean currentHasLocation = this.mapAbsolutePosition.contains(location);
            boolean individualHasLocation = individual.getMapAbsolutePosition().contains(location);

            if (currentHasLocation && individualHasLocation) {

                if (rand.nextBoolean()) {
                    idProject = this.mapAbsolutePosition.get(location);
                } else {
                    idProject = individual.getMapAbsolutePosition().get(location);
                }

            } else if (currentHasLocation) {
                idProject = this.mapAbsolutePosition.get(location);
            } else if (individualHasLocation) {
                idProject = individual.getMapAbsolutePosition().get(location);
            } else {
                continue;
            }

            if (mutatedIndividual.checkIfCompilable(problem.getProjects().get(idProject), location)) {
                mutatedIndividual.addProject(problem.getProjects().get(idProject), location);
            }

        }

        mutatedIndividual.calculateFitness();
        return mutatedIndividual;

    }

    public void mutate() {

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

}
