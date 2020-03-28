package com.main.Algorithms;

import com.main.Model.Problem;

import java.text.DecimalFormat;

public class Algorithm {

    protected Problem problem;
    protected Individual solution;

    long elapsedTime = 0;

    public Algorithm(Problem problem) {
        this.problem = problem;
    }

    public void printSolution() {

        System.out.println("--------------------------------------");
        System.out.print(this.solution.toString());
        System.out.println("Fitness: " + this.solution.getFitness());
        System.out.println("Elapsed Time: " + this.elapsedTime + "ns or " +  (this.elapsedTime/(10e-9)) + "s.");

    }
}
