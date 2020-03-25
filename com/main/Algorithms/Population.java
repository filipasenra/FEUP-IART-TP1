package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Population {

    ArrayList<Individual> population = new ArrayList<>();

    public Population(int populationSize, Problem problem) {

        ExecutorService es = Executors.newCachedThreadPool();
        for(int i = 0; i < populationSize; i++) {

            Individual individual = new Individual(problem);
            es.execute(() -> {individual.initiateGrid();});
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

    public Population(ArrayList<Individual> population) {
        this.population = population;
    }

    public void sortPopulation() {
        Collections.sort(this.population, Comparator.comparing(Individual::getFitness));
        Collections.reverse(this.population);
    }

    public void setPopulation(ArrayList<Individual> population) {
        this.population = population;
    }

    public ArrayList<Individual> getFittestPopulation(double percentage) {

        this.sortPopulation();
        return new ArrayList<Individual>(this.population.subList(0, (int) (percentage*this.population.size())));
    }

    public void addIndividual(Individual individual) {

        this.population.add(individual);

    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    @Override
    public String toString() {

        String returnString = "";

        for(Individual individual : this.population){
            returnString += individual.toString() + "\n";
        }

        return returnString;
    }
}
