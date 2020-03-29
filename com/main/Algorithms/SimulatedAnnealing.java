package com.main.Algorithms;

import com.main.Model.Problem;

import java.util.Random;

public class SimulatedAnnealing extends Algorithm {

    int nRepeat;

    // Initial temperature
    double T;
    // Temperature at which iteration terminates
    final double Tmin;
    // Decrease in temperature
    final double alpha;

    public SimulatedAnnealing(Problem problem, int nRepeat, double T, double Tmin, double alpha) {
        super(problem, nRepeat);

        this.nRepeat = nRepeat;
        this.T = T;
        this.Tmin = Tmin;
        this.alpha = alpha;

        solution = new Individual(problem);
        solution.initiateGrid();
    }

    public SimulatedAnnealing(Problem problem, int nRepeat, double T, double Tmin, double alpha, Individual solution) {
        super(problem, nRepeat, solution);

        this.nRepeat = nRepeat;
        this.T = T;
        this.Tmin = Tmin;
        this.alpha = alpha;
    }

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
