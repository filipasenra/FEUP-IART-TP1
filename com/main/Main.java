package com.main;

import com.main.Algorithms.*;
import com.main.Parser.Parser;

public class Main {

    private static Individual solution;

    public static void main(String[] args) {

        Parser parser = new Parser("./src/com/main/inputFiles/b_short_walk.in");
        parser.parseFile();
        System.out.println("Finish Parsing File");


        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), 200, 400, 0.1, 0.5, 0.5);
        geneticAlgorithm.solve();
        geneticAlgorithm.printSolution();

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(parser.getProblem(), 100000, 1, .0001, 0.5);
        simulatedAnnealing.solve();
        simulatedAnnealing.printSolution();


        HillClimbing hillClimbing = new HillClimbing(parser.getProblem(), 1000000);
        hillClimbing.solve();
        hillClimbing.printSolution();

        TabuSearch tabuSearch = new TabuSearch(parser.getProblem(), true, 100000, 1, 0.5);
        tabuSearch.solve();
        tabuSearch.printSolution();

    }
}
