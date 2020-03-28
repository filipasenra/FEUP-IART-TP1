package com.main.Algorithms;

import com.main.Model.Problem;
import com.main.Model.Project;
import javafx.util.Pair;

import java.util.Random;

public class HillClimbing extends Algorithm{

    int nRepeat;

    public HillClimbing(Problem problem, int nRepeat){
        super(problem);

        this.nRepeat = nRepeat;

        solution = new Individual(problem);
        solution.initiateGrid();
    }


    public void solve(){

        long start = System.nanoTime();

        Individual newIndividual = solution.clone();

        for (int i = 0 ; i < this.nRepeat; i++){

           newIndividual.mutate();

            if(newIndividual.getFitness() >= solution.getFitness())
            {
                solution = newIndividual.clone();
            }
        }

        this.elapsedTime = System.nanoTime() - start;

    }

}
