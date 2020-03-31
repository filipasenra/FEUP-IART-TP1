package com.main.Algorithms;

import com.main.Model.Problem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class that represents a Population
 *
 * A population is a group of individuals
 *
 * */
public class Population {

    ArrayList<Individual> population = new ArrayList<>();

    /**
     * Population constructor
     * 
     * @param populationSize        size of the population
     * @param problem               problem to solve
     */
    public Population(int populationSize, Problem problem) {

        ExecutorService es = Executors.newCachedThreadPool();
        for(int i = 0; i < populationSize; i++) {

            Individual individual = new Individual(problem);
            es.execute(individual::initiateGrid);
            population.add(individual);
        }
        es.shutdown();

        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Population constructor
     * 
     * @param population        initial population
     */
    public Population(ArrayList<Individual> population) {
        this.population = population;
    }

    /**
     * Sorts the population
     */
    public void sortPopulation() {
        this.population.sort(Comparator.comparing(Individual::getFitness));
        Collections.reverse(this.population);
    }

    /**
     *  Returns the fittest population
     * 
     * @param percentage        percentage of the population to return
     * @return                  fittest population
     */
    public ArrayList<Individual> getFittestPopulation(double percentage) {

        this.sortPopulation();
        return new ArrayList<>(this.population.subList(0, (int) (percentage*this.population.size())));
    }

    /**
     * Adds an individual
     * 
     * @param individual        individual to be added
     */
    public void addIndividual(Individual individual) {

        this.population.add(individual);

    }

    /**
     * Returns the population
     * 
     * @return      population
     */
    public ArrayList<Individual> getPopulation() {
        return population;
    }

    /**
     * Method that converts the individual into a string
     */
    @Override
    public String toString() {

        StringBuilder returnString = new StringBuilder();

        for(Individual individual : this.population){
            returnString.append(individual.toString()).append("\n");
        }

        return returnString.toString();
    }
}
