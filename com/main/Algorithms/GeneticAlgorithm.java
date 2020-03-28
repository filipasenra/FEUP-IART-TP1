package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Hashtable;
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

            int individualsToKeep = (int)(this.sizePopulation*percentageToKeep);
            int n = individualsToKeep + rand.nextInt(newPopulation.getPopulation().size() - individualsToKeep);
            Individual individualToMutate = newPopulation.getPopulation().get(n);
            individualToMutate.mutate();
        }

        this.population = newPopulation;
        this.population.sortPopulation();

        return false;
    }

    public void performAlgorithm(int nRepeat) {

        for(int i = 0; i < nRepeat; i++) {
            this.performIteration();
        }
    }

    public Individual getSolution () {
        return this.population.getPopulation().get(0);
    }

    public void printSolution() {

        System.out.println("--------------------------");
        this.population.getPopulation().get(0).calculateFitness();
        System.out.println(this.population.getPopulation().get(0).getMapAbsolutePositionResidential());
        System.out.print(this.population.getPopulation().get(0).toString());
        System.out.println("Fitness: " + this.population.getPopulation().get(0).getFitness() + "\n");
    }
}
