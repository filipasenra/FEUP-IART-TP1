package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneticAlgorithm {

    Problem problem;
    Population population;
    int sizePopulation = 100;

    public GeneticAlgorithm(Problem problem) {
        this.problem = problem;
        this.population = new Population(this.sizePopulation, problem);
    }

    public boolean performAlgorithm() {

        double percentageToKeep = 0.1;
        double percentageToMate = 0.5;
        double percentageToMutate = 0.5;

        Random rand = new Random();

        ArrayList<Individual> fittestIndividuals = this.population.getFittestPopulation(percentageToKeep);

        Population newPopulation = new Population(fittestIndividuals);

        for(int i = 0; i < this.sizePopulation*(1-percentageToKeep); i++) {

            int bound = (int)(this.sizePopulation*percentageToMate);

            Individual parent1 = this.population.getFittestPopulation(percentageToMate).get(rand.nextInt(bound));
            Individual parent2 = this.population.getFittestPopulation(percentageToMate).get(rand.nextInt(bound));
            Individual offspring = parent1.crossOver(parent2);
            newPopulation.addIndividual(offspring);
        }

        for(int i = 0; i < this.sizePopulation*(1-percentageToMutate); i++) {

            Individual individualToMutate = newPopulation.getPopulation().get(rand.nextInt(newPopulation.getPopulation().size()));
            individualToMutate.mutate();
        }

        //System.out.println(this.population.toString());
        return false;
    }

}
