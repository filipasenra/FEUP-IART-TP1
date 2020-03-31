package com.main.Algorithms;

import com.main.Model.Problem;
import java.util.LinkedList;
import java.util.Random;

public class TabuSearch extends Algorithm{

    LinkedList<Individual> memory = new LinkedList<>();
    int MAX_QUEUE_SIZE = 15;

    int nRepeat;

    boolean random = false;

    // Initial temperature
    double T;
    // Decrease in temperature
    double alpha;

    /**
     * Tabu Search constructor
     * 
     * @param problem       problem to be solved
     * @param nRepeat       number of repetitions
     */
    public TabuSearch(Problem problem, int nRepeat) {
        super(problem, nRepeat);

        solution = new Individual(problem);
        solution.initiateGrid();
    }

    /**
     * Tabu Search constructor
     * 
     * @param problem       problem to be solved
     * @param random        boolean that tells if its random
     * @param nRepeat       number of repetitions
     * @param T             Initial temperature     
     * @param alpha         Decrease in temperature
     */
    public TabuSearch(Problem problem, boolean random, int nRepeat, double T, double alpha) {
        this(problem, nRepeat);
        this.random = random;

        this.nRepeat = nRepeat;
        this.T = T;
        this.alpha = alpha;
    }

    /**
     * Tabu Search constructor
     * 
     * @param problem       problem to be solved
     * @param random        boolean that tells if its random
     * @param nRepeat       number of repetitions
     * @param T             Initial temperature     
     * @param alpha         Decrease in temperature
     * @param solution      solution for the problem
     */
    public TabuSearch(Problem problem, boolean random, int nRepeat, double T, double alpha, Individual solution) {
        super(problem, nRepeat, solution);
        this.random = random;

        this.nRepeat = nRepeat;
        this.T = T;
        this.alpha = alpha;
    }


    /**
     * Function that solves the problem
     */
    public void solve() {

        this.startProgressBar();

        long start = System.nanoTime();

        Random rand = new Random();

        for (int i = 0; i < this.nRepeat; i++) {
            this.n++;

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

        this.elapsedTime = System.nanoTime() - start;
        this.endProgressBar();
    }

}
