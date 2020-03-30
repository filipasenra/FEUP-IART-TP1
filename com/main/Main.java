package com.main;

import com.main.Algorithms.*;
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
            System.out.println("Starting Genetic Algorithm");
            geneticAlgorithm = new GeneticAlgorithm(parser.getProblem(), size_population, nRepeat, percentage_to_keep, percentage_to_mate, percentage_to_mutate);
            geneticAlgorithm.solve();
        }

        Individual initialSolution = new Individual(parser.getProblem());
        initialSolution.initiateGrid();

        SimulatedAnnealing simulatedAnnealing = null;
        if(typeToPerform == Type.SA || typeToPerform == Type.ALL) {
            System.out.println("Starting Simulated Annealing Algorithm");
            simulatedAnnealing = new SimulatedAnnealing(parser.getProblem(), nRepeat, T, alpha, initialSolution.clone());
            simulatedAnnealing.solve();
        }

        HillClimbing hillClimbing = null;
        if(typeToPerform == Type.HC || typeToPerform == Type.ALL) {
            System.out.println("Starting Hill Climbing Algorithm");
            hillClimbing = new HillClimbing(parser.getProblem(), nRepeat, initialSolution.clone());
            hillClimbing.solve();
        }

        TabuSearch tabuSearch = null;
        if(typeToPerform == Type.TS || typeToPerform == Type.ALL) {
            System.out.println("Starting Tabu Search Algorithm");
            tabuSearch = new TabuSearch(parser.getProblem(), is_tabu_search_random, nRepeat, T, alpha, initialSolution.clone());
            tabuSearch.solve();
        }

        TabuSearch tabuSearchRandom = null;
        if(typeToPerform == Type.ALL) {
            System.out.println("Starting Tabu Search Random Algorithm");
            tabuSearchRandom = new TabuSearch(parser.getProblem(), !is_tabu_search_random, nRepeat, T, alpha, initialSolution.clone());
            tabuSearchRandom.solve();
        }

        GUI gui = new GUI(parser.getProblem(), "results.html");

        gui.makeHeader();
        gui.makeLegend(inputFile);

        if(geneticAlgorithm != null)
            gui.makeAlgorithm(geneticAlgorithm, "Genetic Algorithm");

        if(simulatedAnnealing != null || hillClimbing != null || tabuSearch != null || tabuSearchRandom != null)
            gui.makeIndividual(initialSolution, "Initial Solution");

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

        System.out.println("Checkout the results in: " + gui.getYourFile().getAbsolutePath());

    }

    static private boolean parseArgs(String[] args) {

        if (args.length != 3 && args.length != 5 && args.length != 6 && args.length != 7 && args.length != 8) {

            System.err.println("Usage: Main HillClimbing <inputFile> <nRepeat>");
            System.err.println("Usage: Main SimulatedAnnealing <inputFile> <nRepeat> <T> <alpha>");
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

            if (args.length != 5) {
                System.err.println("Usage: Main SimulatedAnnealing <inputFile> <nRepeat> <T> <alpha>");
                return false;
            }

            typeToPerform = Type.SA;
            inputFile = args[1];
            nRepeat = Integer.parseInt(args[2]);
            T = Double.parseDouble(args[3]);
            alpha = Double.parseDouble(args[4]);
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
                is_tabu_search_random = Boolean.parseBoolean(args[5]);

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
        alpha = Double.parseDouble(args[7]);

        return true;

    }

}
