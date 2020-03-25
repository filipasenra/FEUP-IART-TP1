package com.main;

import com.main.Algorithms.GeneticAlgorithm;
import com.main.Parser.Parser;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        Parser parser = new Parser("./src/com/main/inputFiles/a_example.in");
        parser.parseFile();
        System.out.println("Finish Parsing File");
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), 50);
        System.out.println("Finish Initiliating GeneticAlgorithm");

        geneticAlgorithm.performAlgorithm(100);

        printSolution(geneticAlgorithm.getProblem().getColumns(), geneticAlgorithm.getProblem().getRows(), geneticAlgorithm.getSolution());
    }

    public static void printSolution(int columns, int rows, Hashtable<Pair<Integer, Integer>, Integer> gridMap) {
        String sol[][]= new String[columns][rows];
        Arrays.stream(sol).forEach(a -> Arrays.fill(a, "."));

        Set<Pair<Integer, Integer>> keys = gridMap.keySet();
        for(Pair<Integer, Integer> key: keys){
            sol[key.getKey()][key.getValue()] = gridMap.get(key).toString();
        }

        for (int i=0; i<columns; i++) {
            for (int j=0; j<rows; j++) {
                System.out.print(sol[i][j]);
            }
            System.out.print("\n");
        }

        System.out.println(Arrays.deepToString(sol));
    }
}
