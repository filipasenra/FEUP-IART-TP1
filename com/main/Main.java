package com.main;

import com.main.Algorithms.*;
import com.main.GUI.GUI;
import com.main.Parser.Parser;

import java.io.IOException;

//Type of algorithm to use
enum Type {
    ALL,
    GENETIC,
    SA,
    HC,
    TS
}

//  Genetic ./src/com/main/inputFiles/c_going_green.in 500 100 0.1 0.5 0.5
//  TabuSearch ./src/com/main/inputFiles/d_wide_selection.in 20000 1 0.1
//  ./src/com/main/inputFiles/a_example.in 400 100 0.1 0.5 0.5 1 0.5
//  ./src/com/main/inputFiles/b_short_walk.in 1000 500 0.1 0.5 0.5 1 0.5
//  ./src/com/main/inputFiles/a_example.in 1000 100 0.1 0.5 0.5 1 0.5

public class Main {

    static String inputFile;    //Path to the input file
    static Type typeToPerform;  //Type of algorithm to perform
    static int nRepeat;         //Number of iterations
    static int size_population; //Population Size (Genetic)
    static double percentage_to_keep;  //Percentage of the population to remain unaltered to the next generation (Genetic)
    static double percentage_to_mate;  //Percentage of the population to mate to create the next generation (Genetic)
    static double percentage_to_mutate;//Percentage of the population that should be mutated (introduces diversity into the population) (Genetic)
    static double T;      //Initial Temperature (Simulated Annealing or/and Tabu Search)
    static double alpha;  //Rate to which the temperature decreases (Simulated Annealing or/and Tabu Search)
    static boolean is_tabu_search_random = false;  //Whether Tabu Search should introduce random solutions or not


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

        //Creating the output file
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

    //Parsing input arguments
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
