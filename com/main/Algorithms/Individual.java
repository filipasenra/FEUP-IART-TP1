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

    public Individual crossOver(Individual individual) {


        Individual mutatedIndividual = new Individual(problem);

        crossOverSpecificType(mutatedIndividual, this.mapAbsolutePositionUtility, individual.mapAbsolutePositionUtility);
        crossOverSpecificType(mutatedIndividual, this.mapAbsolutePositionResidential, individual.mapAbsolutePositionResidential);

        mutatedIndividual.calculateFitness();

        return mutatedIndividual;

    }

    public void crossOverSpecificType(Individual mutatedIndividual, Hashtable<Pair<Integer, Integer>, Integer> currentAbsoluteLocation, Hashtable<Pair<Integer, Integer>, Integer> individualAbsoluteLocation){

        Random rand = new Random();

        Set<Pair<Integer, Integer>> primeKeys = new HashSet<>();

        primeKeys.addAll(currentAbsoluteLocation.keySet());
        primeKeys.addAll(individualAbsoluteLocation.keySet());

        for(Pair<Integer, Integer> location: primeKeys) {

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

    public void mutate(int nGenes) {

        Random rand = new Random();

        for(int i = 0; i < nGenes; i++){

            int x = rand.nextInt(this.problem.getRows());
            int y = rand.nextInt(this.problem.getColumns());

            if (gridMap.containsKey(new Pair<>(x, y))) {
                continue;
            }

            Project project = this.problem.getProjects().get(rand.nextInt(this.problem.getProjects().size()));

            if (checkIfCompatible(project, new Pair<>(x, y))) {
                addProject(project, new Pair<>(x, y));
            } else {
                i--;
            }
        }

        this.calculateFitness();

    }

}
