package com.main;

import com.main.Parser.Parser;

public class Main {

    public static void main(String[] args) {

        Parser parser = new Parser("./src/com/main/inputFiles/a_example.in");

        parser.parseFile();

        System.out.println(parser.getCityPlan().getResidentialProjects());
    }
}
