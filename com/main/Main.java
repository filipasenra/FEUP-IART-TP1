package com.main;

import com.main.Algorithms.GeneticAlgorithm;
import com.main.Model.CityPlan;
import com.main.Parser.Parser;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Parser parser = new Parser("./src/com/main/inputFiles/b_short_walk.in");
        parser.parseFile();
        System.out.println("Finish Parsing File");
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), 50);
        System.out.println("Finish Initiliating GeneticAlgorithm");

        geneticAlgorithm.performAlgorithm(100);
        //geneticAlgorithm.printSolution();


    }
}
