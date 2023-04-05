package edu.neu.info6205;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShortestPath {

    // Define the coordinates of each city
    private double[][] cities;
    private int numCities;

    public ShortestPath(String fileName) {
        // Read the data from the file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String[]> dataList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                dataList.add(values);
            }
         
            cities = new double[dataList.size()][2];
            numCities = dataList.size();
            
            for (int i = 1; i <  numCities; i++) {
                if (!dataList.get(i)[4].isEmpty() && !dataList.get(i)[5].isEmpty()) {
                    cities[i][0] = Double.parseDouble(dataList.get(i)[4]);
                    cities[i][1] = Double.parseDouble(dataList.get(i)[5]);
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Brute Force TSP solver algorithm
    public double tspBruteForce() {
        int[] tour = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            tour[i] = i;
        }
        return tspBruteForceHelper(tour, 0);
    }

    private double tspBruteForceHelper(int[] tour, int start) {
        if (start == numCities - 1) {
            return distance(tour, numCities);
        }

        double minDist = Double.POSITIVE_INFINITY;
        for (int i = start; i < numCities; i++) {
            swap(tour, start, i);
            double dist = tspBruteForceHelper(tour, start + 1);
            if (dist < minDist) {
                minDist = dist;
            }
            swap(tour, start, i);
        }
        return minDist;
    }


    private void swap(int[] tour, int i, int j) {
        int temp = tour[i];
        tour[i] = tour[j];
        tour[j] = temp;
    }

    private double distance(int[] tour, int len) {
        double dist = 0;
        for (int i = 0; i < len - 1; i++) {
            int city1 = tour[i];
            int city2 = tour[i+1];
            dist += Math.sqrt(Math.pow(cities[city1][0] - cities[city2][0], 2) + Math.pow(cities[city1][1] - cities[city2][1], 2));
        }
        int lastCity = tour[len-1];
        int firstCity = tour[0];
        dist += Math.sqrt(Math.pow(cities[lastCity][0] - cities[firstCity][0], 2) + Math.pow(cities[lastCity][1] - cities[firstCity][1], 2));
        return dist;
    }

   

    public static void main(String[] args) {
    	ShortestPath tsp = new ShortestPath("/Users/Pranavkapoor1/workspace-2022-6-Pranav-Kapoor.zip/TSP project/src/edu/neu/info6205/2023-01-metropolitan-street.csv");

        double shortestPath = tsp.tspBruteForce();
        System.out.println("Shortest Path Length: " + shortestPath);
    }
}
