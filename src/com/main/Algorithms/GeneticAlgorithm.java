package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneticAlgorithm {

    Problem problem;
    Population population;
    int sizePopulation;

    public GeneticAlgorithm(Problem problem, int sizePopulation) {
        this.problem = problem;
        this.sizePopulation = sizePopulation;
        this.population = new Population(this.sizePopulation, problem);
        this.population.sortPopulation();
    }

    private boolean performIteration() {

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

        //Elitism Keep the percentage of IndividualsToKeep
        for(int i = 0; i < this.sizePopulation*(1-percentageToMutate); i++) {

            int n = rand.nextInt(newPopulation.getPopulation().size());
            Individual individualToMutate = newPopulation.getPopulation().get(n);
            individualToMutate.mutate(10);
        }

        this.population = newPopulation;
        this.population.sortPopulation();

        return false;
    }

    public void performAlgorithm(int nRepeat) {

        System.out.println(this.population.toString());

        for(int i = 0; i < nRepeat; i++) {
            this.performIteration();
        }

        System.out.println(this.population.toString());
    }

}
