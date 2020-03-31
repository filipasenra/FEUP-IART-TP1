package com.main.Algorithms;

import com.main.Model.Problem;
import java.util.Random;

/**
 * Class that performs the Simulated Annealing Algorithm
 * */
public class SimulatedAnnealing extends Algorithm {

    int nRepeat;
    double T;
    final double alpha;

    /**
     * Simulated Annealing constructor
     * 
     * @param problem       problem to be solved
     * @param nRepeat       number of repetitions
     * @param T             Initial temperature
     * @param alpha         Decrease in temperature
     */
    public SimulatedAnnealing(Problem problem, int nRepeat, double T, double alpha) {
        super(problem, nRepeat);

        this.nRepeat = nRepeat;
        this.T = T;
        this.alpha = alpha;

        solution = new Individual(problem);
        solution.initiateGrid();
    }

    /**
     * Simulated Annealing constructor
     * 
     * @param problem       problem to be solved
     * @param nRepeat       number of repetitions
     * @param T             Initial temperature
     * @param alpha         Decrease in temperature
     * @param solution      solution for the problem
     */
    public SimulatedAnnealing(Problem problem, int nRepeat, double T, double alpha, Individual solution) {
        super(problem, nRepeat, solution);

        this.nRepeat = nRepeat;
        this.T = T;
        this.alpha = alpha;
    }

    /**
     * Solves the problem employing the Simulated Annealing Algorithm
     */
    public void solve() {

        this.startProgressBar();

        long start = System.nanoTime();

        Random rand = new Random();

        for (int i = 0; i < nRepeat; i++) {
            this.n++;

            Individual solutionI = this.solution.clone();
            solutionI.mutate();

            if (solutionI.getFitness() > this.solution.getFitness()) {

                this.solution = solutionI;

            } else {

                int delta = solutionI.getFitness() - this.solution.getFitness();

                if (rand.nextInt(1) > Math.pow(Math.E, (-1 * delta) / T)) {
                    this.solution = solutionI;
                }
            }

            T *= alpha;
        }

        this.elapsedTime = System.nanoTime() - start;

        this.endProgressBar();
    }

}
