package com.main;

import com.main.Algorithms.Individual;
import com.main.Algorithms.SimulatedAnnealing;
import com.main.Algorithms.GeneticAlgorithm;
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

        HillClimbing hillClimbing = new HillClimbing(parser.getProblem());
        hillClimbing.solve(10000);

        hillClimbing.printSolution();

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), 500);
        geneticAlgorithm.performAlgorithm(200);
        geneticAlgorithm.printSolution();*/

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(parser.getProblem());
        simulatedAnnealing.solve(1000);

        simulatedAnnealing.printSolution();

        solution = simulatedAnnealing.getSolution();

        drawSolution();
    }

    public static void drawSolution() {
        JFrame frame = new JFrame("CITY PLAN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);

        JTextArea ola = new JTextArea(solution.toString());
        frame.getContentPane().add(ola);


        frame.setVisible(true);
    }
}
