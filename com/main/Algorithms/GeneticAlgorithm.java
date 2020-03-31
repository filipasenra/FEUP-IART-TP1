package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class GeneticAlgorithm extends Algorithm {

    //variables declaration
    Population population;
    int sizePopulation;

    int nRepeat;
    double percentageToKeep;
    double percentageToMate;
    double percentageToMutate;

    /**
     * Genetic algorithm constructor
     * 
     * @param problem               problem to be solved
     * @param sizePopulation        size of the population
     * @param nRepeat               number of repetitions
     * @param percentageToKeep      percentage of the population to keep
     * @param percentageToMate      percentage of the population to mate
     * @param percentageToMutate    percentage of the population to mutate
     */
    public GeneticAlgorithm(Problem problem, int sizePopulation, int nRepeat, double percentageToKeep, double percentageToMate, double percentageToMutate) {
        super(problem, nRepeat);

        this.nRepeat = nRepeat;
        this.percentageToKeep = percentageToKeep;
        this.percentageToMate = percentageToMate;
        this.percentageToMutate = percentageToMutate;

        this.sizePopulation = sizePopulation;
        this.population = new Population(this.sizePopulation, problem);
        this.population.sortPopulation();
    }

    /**
     * Function that performs the iteration
     * 
     * @return      true if the operation is successfull
     */
    private boolean performIteration() {

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

    /**
     * Function that solves the problem
     */
    public void solve() {

        this.startProgressBar();

        long start = System.nanoTime();

        for(int i = 0; i < this.nRepeat; i++) {
            this.n++;

            this.performIteration();
        }

        this.elapsedTime = System.nanoTime() - start;

        this.solution = this.population.getPopulation().get(0);
        this.endProgressBar();
    }
}
