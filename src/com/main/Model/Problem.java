package com.main.Model;

import java.util.ArrayList;

public class Problem {

    int rows;
    int columns;
    int maximumWalkingDistance;

    ArrayList<ResidentialProject> residentialProjects = new ArrayList<>();
    ArrayList<UtilityProject> utilityProjects = new ArrayList<>();

    public Problem(int rows, int columns, int maximumWalkingDistance) {
        this.rows = rows;
        this.columns = columns;
        this.maximumWalkingDistance = maximumWalkingDistance;
    }

    public void addResidentialProject(ResidentialProject rp){
        this.residentialProjects.add(rp);
    }

    public void addUtilityProject(UtilityProject up){
        this.utilityProjects.add(up);
    }

    public int getMaximumWalkingDistance() {
        return maximumWalkingDistance;
    }

    public ArrayList<ResidentialProject> getResidentialProjects() {
        return residentialProjects;
    }
}
