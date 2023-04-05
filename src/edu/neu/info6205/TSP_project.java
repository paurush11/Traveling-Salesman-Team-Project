package edu.neu.info6205;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSP_project {

    // Define the coordinates of each city
    private double[][] cities;

    public TSP_project(String fileName) {
        // Read the data from the file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String[]> dataList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                dataList.add(values);
            }
            cities = new double[dataList.size()][2];
            for (int i = 1; i < dataList.size(); i++) {
            	 if (!dataList.get(i)[4].isEmpty() && !dataList.get(i)[5].isEmpty()) {
                cities[i][0] = Double.parseDouble(dataList.get(i)[4]);
                cities[i][1] = Double.parseDouble(dataList.get(i)[5]);
            	 }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TSP solver algorithm goes here
    // ...
    
    public static void main(String[] args) {
        TSP_project tsp = new TSP_project("/Users/Pranavkapoor1/workspace-2022-6-Pranav-Kapoor.zip/TSP project/src/edu/neu/info6205/2023-01-metropolitan-street.csv");
        // Call TSP solver algorithm with parsed data
        
        System.out.println("City " + "city number" + ": " + "X coordinate" + "," + "Y coordinate");
        for (int i = 0; i < tsp.cities.length; i++) {
            System.out.println("City " + (i+1) + ": " + tsp.cities[i][0] + "," + tsp.cities[i][1]);
        }
        
        // ...
    }
}
