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


    public void solve(int n){

        Individual newIndividual = solution.clone();

        for ( int i = 0 ; i < n; i++){
            newIndividual.mutate(3);

            if(newIndividual.getFitness() > solution.getFitness())
            {
                solution = newIndividual.clone();
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
