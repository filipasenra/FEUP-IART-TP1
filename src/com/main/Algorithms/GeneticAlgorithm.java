package com.main.Algorithms;

import com.main.Model.CityPlan;
import com.main.Model.Problem;

import java.util.Random;

public class GeneticAlgorithm {

    Problem problem;
    CityPlan cityPlan = new CityPlan();

    public GeneticAlgorithm(Problem problem) {
        this.problem = problem;
        cityPlan.initiateGrid(problem);
    }
}
