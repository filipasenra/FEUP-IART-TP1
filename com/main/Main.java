package com.main;

import com.main.Algorithms.GeneticAlgorithm;
import com.main.Algorithms.HillClimbing;
import com.main.Algorithms.SimulatedAnnealing;
import com.main.Algorithms.TabuSearch;
import com.main.GUI.GUI;
import com.main.Parser.Parser;

import java.io.IOException;

enum Type {
    ALL,
    GENETIC,
    SA,
    HC,
    TS
}

public class Main {

    static String inputFile;
    static Type typeToPerform;
    static int nRepeat;
    static int size_population;
    static double percentage_to_keep;
    static double percentage_to_mate;
    static double percentage_to_mutate;
    static double T;
    static double Tmin;
    static double alpha;
    static boolean is_tabu_search_random = false;


    public static void main(String[] args) throws IOException {

        if (!parseArgs(args))
            System.exit(-1);

        Parser parser = new Parser(inputFile);
        parser.parseFile();
        System.out.println("Finish Parsing File");

        GeneticAlgorithm geneticAlgorithm = null;
        if(typeToPerform == Type.GENETIC || typeToPerform == Type.ALL) {
            geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), size_population, nRepeat, percentage_to_keep, percentage_to_mate, percentage_to_mutate);
            geneticAlgorithm.solve();
            geneticAlgorithm.printSolution();
        }

        SimulatedAnnealing simulatedAnnealing = null;
        if(typeToPerform == Type.SA || typeToPerform == Type.ALL) {
            simulatedAnnealing = new SimulatedAnnealing(parser.getProblem(), nRepeat, T, Tmin, alpha);
            simulatedAnnealing.solve();
            simulatedAnnealing.printSolution();
        }

        HillClimbing hillClimbing = null;
        if(typeToPerform == Type.HC || typeToPerform == Type.ALL) {
            hillClimbing = new HillClimbing(parser.getProblem(), nRepeat);
            hillClimbing.solve();
            hillClimbing.printSolution();
        }

        TabuSearch tabuSearch = null;
        if(typeToPerform == Type.TS || typeToPerform == Type.ALL) {
            tabuSearch = new TabuSearch(parser.getProblem(), is_tabu_search_random, nRepeat, T, alpha);
            tabuSearch.solve();
            tabuSearch.printSolution();
        }

        TabuSearch tabuSearchRandom = null;
        if(typeToPerform == Type.ALL) {
            tabuSearchRandom = new TabuSearch(parser.getProblem(), !is_tabu_search_random, nRepeat, T, alpha);
            tabuSearchRandom.solve();
            tabuSearchRandom.printSolution();
        }

        GUI gui = new GUI(parser.getProblem(), "results.html");

        gui.makeHeader();
        gui.makeLegend(inputFile);

        if(geneticAlgorithm != null)
            gui.makeAlgorithm(geneticAlgorithm, "Genetic Algorithm");

        if(simulatedAnnealing != null)
            gui.makeAlgorithm(simulatedAnnealing, "Simulated Annealing");

        if(hillClimbing != null)
            gui.makeAlgorithm(hillClimbing, "Hill Climbing");

        if(tabuSearch != null)
            gui.makeAlgorithm(tabuSearch, "Tabu Search");

        if(tabuSearchRandom != null)
            gui.makeAlgorithm(tabuSearchRandom, "Tabu Search Random");

        gui.makeFooter();
        gui.closeUp();

    }

    static private boolean parseArgs(String[] args) {

        if (args.length != 3 && args.length != 5 && args.length != 6 && args.length != 7 && args.length != 9) {

            System.err.println("Usage: Main HillClimbing <inputFile> <nRepeat>");
            System.err.println("Usage: Main SimulatedAnnealing <inputFile> <nRepeat> <T> <Tmin> <alpha>");
            System.err.println("Usage: Main TabuSearch <inputFile> <nRepeat> <T> <alpha> [<is_tabu_search_random>]");
            System.err.println("Usage: Main Genetic <inputFile> <nRepeat> <size_population> <percentage_to_keep> <percentage_to_mate> <percentage_to_mutate>");
            System.err.println("Usage: Main <inputFile> <nRepeat> <size_population> <percentage_to_keep> <percentage_to_mate> <percentage_to_mutate> <T> <Tmin> <alpha>");
            return false;
        }

        if (args[0].equals("HillClimbing")) {

            if (args.length != 3) {
                System.err.println("Usage: Main HillClimbing <inputFile> <nRepeat>");
                return false;
            }

            typeToPerform = Type.HC;
            inputFile = args[1];
            nRepeat = Integer.parseInt(args[2]);
            return true;
        }


        if (args[0].equals("SimulatedAnnealing")) {

            if (args.length != 6) {
                System.err.println("Usage: Main SimulatedAnnealing <inputFile> <nRepeat> <T> <Tmin> <alpha>");
                return false;
            }

            typeToPerform = Type.SA;
            inputFile = args[1];
            nRepeat = Integer.parseInt(args[2]);
            T = Double.parseDouble(args[3]);
            Tmin = Double.parseDouble(args[4]);
            alpha = Double.parseDouble(args[5]);
            return true;
        }

        if (args[0].equals("TabuSearch")) {

            if (args.length == 5 || args.length == 6) {

                typeToPerform = Type.TS;
                inputFile = args[1];
                nRepeat = Integer.parseInt(args[2]);
                T = Double.parseDouble(args[3]);
                alpha = Double.parseDouble(args[4]);

            } else {
                System.err.println("Usage: Main TabuSearch <inputFile> <nRepeat> <T> <alpha> [<is_tabu_search_random>]");
                return false;
            }

            if (args.length == 6)
                is_tabu_search_random = Boolean.parseBoolean(args[4]);

            return true;
        }

        if (args[0].equals("Genetic")) {

            if (args.length != 7) {
                System.err.println("Usage: Main Genetic <inputFile> <nRepeat> <size_population> <percentage_to_keep> <percentage_to_mate> <percentage_to_mutate>");
                return false;
            }

            typeToPerform = Type.GENETIC;
            inputFile = args[1];
            nRepeat = Integer.parseInt(args[2]);
            size_population = Integer.parseInt(args[3]);
            percentage_to_keep = Double.parseDouble(args[4]);
            percentage_to_mate = Double.parseDouble(args[5]);
            percentage_to_mutate = Double.parseDouble(args[6]);
            return true;
        }

        typeToPerform = Type.ALL;
        inputFile = args[0];
        nRepeat = Integer.parseInt(args[1]);
        size_population = Integer.parseInt(args[2]);
        percentage_to_keep = Double.parseDouble(args[3]);
        percentage_to_mate = Double.parseDouble(args[4]);
        percentage_to_mutate = Double.parseDouble(args[5]);
        T = Double.parseDouble(args[6]);
        Tmin = Double.parseDouble(args[7]);
        alpha = Double.parseDouble(args[8]);

        return true;

    }

}
