package com.main.Algorithms;

import com.main.Model.Problem;
import com.main.Model.Project;
import javafx.util.Pair;

import java.util.Random;

public class HillClimbing {

    Problem problem;
    Individual solution;

    public HillClimbing(Problem problem){
        this.problem = problem;

        solution = new Individual(problem);

        solution.initiateGrid();
    }


    public void solve(int nRepeat){

        Individual newIndividual = solution.clone();
        Pair<Integer, Integer> location = new Pair<>(0, 0);
        int n = 0;

        for (int i = 0 ; i < nRepeat; i++){

            if(!newIndividual.mutate(location, n)) {
                newIndividual = new Individual(this.problem);
                newIndividual.initiateGrid();
                return;
            }

            if(newIndividual.getFitness() >= solution.getFitness())
            {
                location =  new Pair<>(0, 0);
                n = 0;
                solution = newIndividual.clone();
            } else {

                location = newIndividual.location;
                n = newIndividual.projectNumber;
            }
        }


        //printSolution();
    }

    public void printSolution(){
        System.out.println(this.solution.getMapAbsolutePositionResidential());
        System.out.print(this.solution.toString());
        System.out.println("Final fitness: " + solution.getFitness());
    }

    public Individual getSolution() {
        return solution;
    }
}
