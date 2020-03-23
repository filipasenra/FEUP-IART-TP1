package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Population {

    ArrayList<Individual> population = new ArrayList<>();

    public Population(int populationSize, Problem problem) {

        while(populationSize > 0) {
            populationSize--;

            Individual individual = new Individual(problem);
            individual.initiateGrid();
            population.add(individual);
        }
    }

    public Population(ArrayList<Individual> population) {
        this.population = population;
    }

    public void sortPopulation() {
        Collections.sort(this.population, Comparator.comparing(Individual::getFitness));
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
