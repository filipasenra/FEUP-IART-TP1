package com.main.GUI;

import com.main.Algorithms.*;
import com.main.Model.Problem;
import com.main.Model.Project;
import com.main.Model.ResidentialProject;
import com.main.Model.UtilityProject;
import javafx.util.Pair;

import java.io.*;
import java.util.Hashtable;

public class GUI {

    File yourFile;
    BufferedWriter writer;
    Problem problem;

    public GUI(Problem problem, String outputfile) throws IOException {
        yourFile = new File(outputfile);

        yourFile.createNewFile();
        this.writer = new BufferedWriter(new FileWriter(outputfile));
        this.problem = problem;
    }

    public void makeHeader() throws IOException {
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

    public void makeFooter() throws IOException {

        this.writer.write("</body>\n" +
                "</html>");
    }

    public void makeLegend(String inputfile) throws IOException {
        String html = "";


        html += "<h1>City Plan Optimization</h1>";
        html += "<p>Made by: Filipa Senra, Claudia Martins, Andreia Gouveia | IART | FEUP | 2020 </p>";
        html += "<p id=\"inputFile\"><b>InputFile:</b> " + inputfile + "</p>";
        html += "<p id=\"legendText\"><b>Legend:</b></p>";

        html += "<div class=legend>";
        html += "<div class=\"grid\"><small><b> Cell without occupation</b></small></div>\n";
        html += "<div class=\"grid residential\"><small><b> Cell with Residential Project</b></small></div>\n";
        html += "<div class=\"grid utility\"><small><b> Cell with Utility Project</b></small></div>\n";
        html += "</div>";

        this.writer.write(html);

    }

    public void makeAlgorithm(Algorithm algorithm, String title) throws IOException {
        StringBuilder html = new StringBuilder();

        html.append("<h2>").append(title).append("</h2>\n");
        html.append("<div class=CityPlan>\n");

        Hashtable<Pair<Integer, Integer>, Integer> gridMap = algorithm.getSolution().getGridMap();

        for (int i = 0; i < this.problem.getRows(); i++) {

            html.append("<div class=\"row\">");
            for (int j = 0; j < this.problem.getColumns(); j++) {

                Pair<Integer, Integer> location = new Pair<>(i, j);

                if (gridMap.containsKey(location)) {
                    html.append("<div ");

                    Project proj = this.problem.getProjects().get(gridMap.get(location));

                    if (proj.getClass() == ResidentialProject.class)
                        html.append("class=\"grid residential\"");
                    else if (proj.getClass() == UtilityProject.class)
                        html.append("class=\"grid utility\"");

                    html.append(">").append(gridMap.get(location)).append("</div>\n");
                } else
                    html.append("<div class=\"grid\"></div>\n");
            }
            html.append("</div>\n");
        }

        html.append("<div class=info>");

        html.append("<p><b> Fitness: </b>").append(algorithm.getSolution().getFitness()).append("</p>");
        html.append("<p><b>Elapsed Time: </b>").append(algorithm.getElapsedTime()).append("ns or " + (algorithm.getElapsedTime()/(10e-9)) + "s</p>");
        html.append("</div>");

        html.append("</div>");

        this.writer.write(html.toString());
    }

    public void closeUp() throws IOException {

        this.writer.close();

    }

    public File getYourFile() {
        return yourFile;
    }
}
