package com.main.Algorithms;

import com.main.Model.Problem;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class TabuSearch {

    Problem problem;
    Individual solution;
    LinkedList<Individual> memory = new LinkedList<>();
    int MAX_QUEUE_SIZE = 15;

    boolean random = false;
    public static double T = 1;

    // Simulated Annealing parameters
    // Decrease in temperature
    static final double alpha = 0.5;

    public TabuSearch(Problem problem) {
        this.problem = problem;

        solution = new Individual(problem);
        solution.initiateGrid();
    }

    public TabuSearch(Problem problem, boolean random) {
        this(problem);
        this.random = random;
    }


    public void solve(int nRepeat) {

        Random rand = new Random();

        for (int i = 0; i < nRepeat; i++) {
            Individual newIndividual;

            do {
                newIndividual = this.solution.clone();
                newIndividual.mutate();
            }while (this.memory.contains(newIndividual));

            if(this.memory.size() > MAX_QUEUE_SIZE)
                this.memory.pop();

            this.memory.add(newIndividual);
            if (newIndividual.getFitness() >= solution.getFitness()) {
                solution = newIndividual.clone();
            } else {
                if(this.random) {
                    int delta = newIndividual.getFitness() - this.solution.getFitness();

                    if (rand.nextInt(1) > Math.pow(Math.E, (-1 * delta) / T)) {
                        this.solution = newIndividual.clone();
                    }
                }
            }

            T *= alpha;
        }


        //printSolution();
    }

    public void printSolution() {
        System.out.println(this.solution.getMapAbsolutePositionResidential());
        System.out.print(this.solution.toString());
        System.out.println("Final fitness: " + solution.getFitness());
    }

    public Individual getSolution() {
        return solution;
    }
}
