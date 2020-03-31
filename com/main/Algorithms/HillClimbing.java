package com.main.Algorithms;

import com.main.Model.Problem;
import com.main.Model.Project;
import javafx.util.Pair;

import java.util.Random;

public class HillClimbing extends Algorithm{

    int nRepeat;

    /**
     * Hill Climbing constructor
     * 
     * @param problem       problem to be solves
     * @param nRepeat       number of repetitions
     */
    public HillClimbing(Problem problem, int nRepeat){
        super(problem, nRepeat);

        this.nRepeat = nRepeat;

        solution = new Individual(problem);
        solution.initiateGrid();
    }

    /**
     * Hill Climbing constructor
     * 
     * @param problem       problem to be solves
     * @param nRepeat       number of repetitions
     * @param solution      solution of the problem
     */
    public HillClimbing(Problem problem, int nRepeat, Individual solution){
        super(problem, nRepeat, solution);

        this.nRepeat = nRepeat;
    }

    /**
     * Function that solves the problem
     */
    public void solve(){

        this.startProgressBar();

        long start = System.nanoTime();

        Individual newIndividual = solution.clone();

        for (int i = 0 ; i < this.nRepeat; i++){
            this.n++;

           newIndividual.mutate();

            if(newIndividual.getFitness() >= solution.getFitness())
            {
                solution = newIndividual.clone();
            }
        }

        this.elapsedTime = System.nanoTime() - start;

        this.endProgressBar();

    }

}
