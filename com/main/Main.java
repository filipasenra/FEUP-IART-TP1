package com.main;

import com.main.Algorithms.*;
import com.main.Parser.Parser;
import javafx.util.Pair;

import javax.swing.*;

public class Main {

    private static Individual solution;

    public static void main(String[] args) {

        Parser parser = new Parser("./src/com/main/inputFiles/b_short_walk.in");
        parser.parseFile();
        System.out.println("Finish Parsing File");

        /*
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), 200);
        geneticAlgorithm.performAlgorithm(400);
        geneticAlgorithm.printSolution();*/

       /* SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(parser.getProblem());
        simulatedAnnealing.solve(1000);

        simulatedAnnealing.printSolution();*/


       /* HillClimbing hillClimbing = new HillClimbing(parser.getProblem());
        hillClimbing.printSolution();
        hillClimbing.solve(500000);

        hillClimbing.printSolution();*/

       TabuSearch tabuSearch = new TabuSearch(parser.getProblem(), true);
        tabuSearch.printSolution();
       tabuSearch.solve(100000);
       tabuSearch.printSolution();
    }
}
