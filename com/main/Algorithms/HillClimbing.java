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
        System.out.println("Initial fitness: " + solution.getFitness());
        solve();
        printSolution();
        System.out.println("percentage:");
        System.out.println("Final fitness: " + solution.getFitness());
    }


    public void solve(){

        Individual oldIndividual = solution.clone();
        Random rand = new Random();

        for ( int i = 0 ; i < (problem.getRows()*problem.getColumns()); i++){
            oldIndividual.mutate(10);

            if(solution.getFitness() < oldIndividual.getFitness())
            {
                solution = oldIndividual;
            }
        }
    }

    public void printSolution(){
        System.out.print(this.solution.toString());
    }
}
