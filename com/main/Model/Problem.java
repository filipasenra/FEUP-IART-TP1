package com.main.Model;

import java.util.ArrayList;

/**
 * Class that represents the problem to be resolved by a CityPlan
 * */
public class Problem {

    int rows;
    int columns;
    int maximumWalkingDistance;

    ArrayList<Project> projects = new ArrayList<>();

    /**
     * Problem constructor
     * 
     * @param rows                      number of rows of the problem
     * @param columns                   number of columns of the problem
     * @param maximumWalkingDistance    maximum walking distance
     */
    public Problem(int rows, int columns, int maximumWalkingDistance) {
        this.rows = rows;
        this.columns = columns;
        this.maximumWalkingDistance = maximumWalkingDistance;
    }

    /**
     * Adds a project to the problem
     * 
     * @param rp    project to be added
    */
    public void addProject(Project rp){
        this.projects.add(rp);
    }

    /**
     * Returns the maximum walking distance
     * 
     * @return      maximum walking distance
     */
    public int getMaximumWalkingDistance() {
        return maximumWalkingDistance;
    }

    /**
     * Returns the number of rows
     * 
     * @return      number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns
     * 
     * @return      number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns the projects added to the problem
     * 
     * @return      projects
     */
    public ArrayList<Project> getProjects() {
        return projects;
    }
}
