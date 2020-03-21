package com.main;

import com.main.Algorithms.GeneticAlgorithm;
import com.main.Parser.Parser;
import com.sun.javafx.scene.traversal.Algorithm;

public class Main {

    public static void main(String[] args) {

        Parser parser = new Parser("./src/com/main/inputFiles/a_example.in");

        parser.parseFile();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(parser.getProblem());

    }
}
