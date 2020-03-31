package com.main.Algorithms;

import com.main.Model.Problem;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Algorithm {

    protected Problem problem;
    protected Individual solution;

    int n = 0;
    int total_n;

    long elapsedTime = 0;

    ScheduledThreadPoolExecutor scheduler;
    ScheduledFuture<?> advertiser;

    /**
     * Algorithm constructor
     * 
     * @param problem       problem to be solved
     * @param total_n
     */
    public Algorithm(Problem problem, int total_n) {
        this.problem = problem;
        this.total_n = total_n;
    }

    /**
     * Algorithm constructor
     * 
     * @param problem       problem to be solved
     * @param total_n       
     * @param solution      solution for the problem     
     */
    public Algorithm(Problem problem, int total_n, Individual solution) {
        this(problem, total_n);
        this.solution = solution;
    }

    /**
     * Function that prints the solution
     */
    public void printSolution() {

        System.out.println("--------------------------------------");
        System.out.print(this.solution.toString());
        System.out.println("Fitness: " + this.solution.getFitness());
        System.out.println("Elapsed Time: " + this.elapsedTime + "ns or " +  (this.elapsedTime/(10e9)) + "s.");

    }

    /**
     * Function that prints the progress bar
     */
    public void printProgressBar() {
        char[] animationChars = new char[]{'|', '/', '-', '\\'};

        System.out.print("\rProcessing: " + (this.n*100/this.total_n) + "% " + animationChars[(this.n*100/this.total_n) % 4]);
    }

    /**
     * Function that starts the progress bar
     */
    public void startProgressBar() {
        this.scheduler = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        this.advertiser = scheduler.scheduleAtFixedRate((new Thread(() -> this.printProgressBar())), 0, 500, TimeUnit.MILLISECONDS);
    }

    /**
     * Function that ends the progress bar
     */
    public void endProgressBar() {
        this.advertiser.cancel(true);
        this.scheduler.shutdown();
        this.printProgressBar();
        System.out.println("");
    }

    /**
     * Function that returns the solution
     * 
     * @return      solution
     */
    public Individual getSolution() {
        return solution;
    }

    /**
     * Function that returns the elapsed time
     * 
     * @return      elapsed time
     */
    public long getElapsedTime() {
        return elapsedTime;
    }
}
