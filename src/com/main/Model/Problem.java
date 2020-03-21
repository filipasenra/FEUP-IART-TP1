package com.main.Model;

import java.util.ArrayList;

public class Problem {

    int rows;
    int columns;
    int maximumWalkingDistance;

    ArrayList<Project> projects = new ArrayList<>();

    public Problem(int rows, int columns, int maximumWalkingDistance) {
        this.rows = rows;
        this.columns = columns;
        this.maximumWalkingDistance = maximumWalkingDistance;
    }

    public void addProject(Project rp){
        this.projects.add(rp);
    }

    public int getMaximumWalkingDistance() {
        return maximumWalkingDistance;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }
}
