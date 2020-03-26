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
    static final double alpha = 0.9;

    // Number of iterations of annealing
    // before decreasing temperature
    static final int numIterations = 1000;

    public SimulatedAnnealing(Problem problem) {
        this.problem = problem;

        solution = new Individual(problem);

        solution.initiateGrid();
    }

    public void solve() {

        Individual currentSolution = solution.clone();
        Individual bestSolution = currentSolution.clone();

        while (T > Tmin) {
            for (int i = 0; i < numIterations; i++) {
                Individual solutionI = currentSolution.clone();
                solutionI.mutate(3);

                if (solutionI.getFitness() > currentSolution.getFitness()) {
                    currentSolution = solutionI.clone();

                    if (currentSolution.getFitness() > bestSolution.getFitness())
                        bestSolution = solutionI.clone();

                } else if (Math.pow(Math.E, (currentSolution.getFitness() - solutionI.getFitness() / T)) > Math.random())
                    currentSolution = solutionI.clone();

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
