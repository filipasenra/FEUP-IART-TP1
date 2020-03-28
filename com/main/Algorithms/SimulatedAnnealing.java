package com.main.Algorithms;

import com.main.Model.Problem;
import com.main.Model.Project;
import javafx.util.Pair;

import java.util.*;

import java.util.Random;

public class SimulatedAnnealing {

    Problem problem;
    Individual solution;

    // Initial and final temperature
    public static double T = 1;

    // Simulated Annealing parameters

    // Temperature at which iteration terminates
    static final double Tmin = .0001;

    // Decrease in temperature
    static final double alpha = 0.5;

    public SimulatedAnnealing(Problem problem) {
        this.problem = problem;

        solution = new Individual(problem);

        solution.initiateGrid();
    }

    public void solve(int numIterations) {

        Random rand = new Random();

        while (T > Tmin) {

            for (int i = 0; i < numIterations; i++) {
                Individual solutionI = this.solution.clone();
                solutionI.mutate();

                if (solutionI.getFitness() > this.solution.getFitness()) {

                    this.solution = solutionI;

                } else {

                    int delta = solutionI.getFitness() - this.solution.getFitness();

                    if (rand.nextInt(1) > Math.pow(Math.E, (-1*delta)/ T))
                    {
                        this.solution = solutionI;
                    }
                }
            }

            T *= alpha;
        }
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
