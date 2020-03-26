package com.main;

import com.main.Algorithms.GeneticAlgorithm;
import com.main.Algorithms.HillClimbing;
import com.main.Algorithms.SimulatedAnnealing;
import com.main.Model.CityPlan;
import com.main.Parser.Parser;
import com.sun.javafx.scene.text.HitInfo;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Parser parser = new Parser("./src/com/main/inputFiles/b_short_walk.in");
        parser.parseFile();
        System.out.println("Finish Parsing File");
/*
        HillClimbing hillClimbing;
        int n = 0;

        do {
        hillClimbing = new HillClimbing(parser.getProblem());
        hillClimbing.solve(1000);
        n++;
        }while (hillClimbing.getSolution().getFitness() < 100);

        hillClimbing.printSolution();
        System.out.println(n);

        n = 0;
        GeneticAlgorithm geneticAlgorithm;
        do {
            geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), 500);
            geneticAlgorithm.performAlgorithm(15);
            n++;
        }while (geneticAlgorithm.getSolution().getFitness() <= 100);

        geneticAlgorithm.printSolution();
        System.out.println(n);
*/

        HillClimbing hillClimbing = new HillClimbing(parser.getProblem());
        hillClimbing.solve(10000);

        hillClimbing.printSolution();

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), 500);
        geneticAlgorithm.performAlgorithm(200);
        geneticAlgorithm.printSolution();

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(parser.getProblem());
        simulatedAnnealing.solve(1000);

        simulatedAnnealing.printSolution();


    }
}
