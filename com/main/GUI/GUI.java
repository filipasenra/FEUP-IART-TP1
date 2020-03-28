package com.main.GUI;

import com.main.Algorithms.*;
import com.main.Model.Problem;
import com.main.Model.Project;
import com.main.Model.ResidentialProject;
import com.main.Model.UtilityProject;
import com.main.Parser.Parser;
import javafx.util.Pair;

import java.io.*;
import java.util.Hashtable;

public class GUI {

    BufferedWriter writer;
    Problem problem;

    public GUI(Problem problem) throws IOException {
        File yourFile = new File("index.html");

        yourFile.createNewFile();
        this.writer = new BufferedWriter(new FileWriter("index.html"));
        this.problem = problem;
    }

    public static void main(String[] args) throws IOException {

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

        GUI gui = new GUI(parser.getProblem());

        gui.makeHeader();
        gui.makeLegend();
        gui.makeAlgorithm(geneticAlgorithm, "Genetic Algorithm");
        gui.makeAlgorithm(simulatedAnnealing, "Simulated Annealing");
        gui.makeAlgorithm(hillClimbing, "Hill Climbing");
        gui.makeAlgorithm(tabuSearch, "Tabu Search");

        gui.makeFooter();

        gui.writer.close();

    }

    private void makeHeader() throws IOException {
        this.writer.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "  <title>Output of CityPlan</title>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" +
                "<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet'>" +
                "</head>" +
                "<body>");
    }

    private void makeFooter() throws IOException {

        this.writer.write("</body>\n" +
                "</html>");
    }

    private void makeLegend() throws IOException {
        String html = "";


        html += "<h1>City Plan Optimization</h1>";
        html += "<p>Made by: Filipa Senra, Claudia Martins, Andreia Gouveia | IART | FEUP | 2020 </p>";
        html += "<p id=\"legendText\"><b>Legend:</b></p>";

        html += "<div class=legend>";
        html += "<div class=\"grid\"><small><b> Cell without occupation</b></small></div>\n";
        html += "<div class=\"grid residential\"><small><b> Cell with Residential Project</b></small></div>\n";
        html += "<div class=\"grid utility\"><small><b> Cell with Utility Project</b></small></div>\n";
        html += "</div>";

        this.writer.write(html);

    }

    private void makeAlgorithm(Algorithm algorithm, String title) throws IOException {
        String html = "";

        html += "<h2>"+ title + "</h2>\n";
        html += "<div class=CityPlan>\n";

        Hashtable<Pair<Integer, Integer>, Integer> gridMap = algorithm.getSolution().getGridMap();

        for (int i = 0; i < this.problem.getRows(); i++) {

            html += "<div class=\"row\">";
            for (int j = 0; j < this.problem.getColumns(); j++) {

                Pair<Integer, Integer> location = new Pair<>(i, j);

                if (gridMap.containsKey(location)) {
                    html += "<div ";

                    Project proj = this.problem.getProjects().get(gridMap.get(location));

                    if(proj.getClass() == ResidentialProject.class)
                        html += "class=\"grid residential\"";
                    else if(proj.getClass() == UtilityProject.class)
                        html += "class=\"grid utility\"";

                    html += ">" + gridMap.get(location) + "</div>\n";
                } else
                    html += "<div class=\"grid\"></div>\n";
                ;
            }
            html += "</div>\n";
        }

        html+="<div class=info>";

        html += "<p><b> Fitness: </b>" + algorithm.getSolution().getFitness() + "</p>";
        html += "<p><b>Elapsed Time: </b>" + algorithm.getElapsedTime() + "ns</p>";
        html += "</div>";

        html += "</div>";

        this.writer.write(html);
    }
}
