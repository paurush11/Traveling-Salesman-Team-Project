package com.neu.tsp.antcolony;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Driver {
	static final int NUMBER_OF_ANTS = 50;
	static final double PROCESSING_CYCLE_PROBABILITY = 0.8;
	
	static ArrayList<City> initialRoute = new ArrayList<City>();
	
	
    public static void fetchData(String fileName) {
        // Read the data from the file
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String[]> dataList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                dataList.add(values);
            }
           
            initialRoute = new ArrayList<City>();
            
            for (int i = 1; i < dataList.size(); i++) {
            	 if (!dataList.get(i)[4].isEmpty() && !dataList.get(i)[5].isEmpty()) {

                     
                     initialRoute.add(new City(dataList.get(i)[0], Double.parseDouble(dataList.get(i)[4]), Double.parseDouble(dataList.get(i)[5])));
            	 }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	static ExecutorCompletionService<Ant> executorCompletionService = new ExecutorCompletionService<Ant>(executorService);
	private  Route shortestRoute = null;
	private int activeAnts = 0;
	
	public static void main(String[] args) throws IOException {
		
		
        fetchData("/Users/Pranavkapoor1/workspace-2022-6-Pranav-Kapoor.zip/TSP project/src/edu/neu/info6205/2023-01-metropolitan-street.csv");
        // Call TSP solver algorithm with parsed data
		
		System.out.println("> "+NUMBER_OF_ANTS + " Artificial Ants ...");
		Driver driver = new Driver();
		driver.printHeading();
		AntColonyOptimization aco = new AntColonyOptimization();
		IntStream.range(1, NUMBER_OF_ANTS).forEach(x -> {
			executorCompletionService.submit(new Ant(aco, x));
			driver.activeAnts++;
			if (Math.random() > PROCESSING_CYCLE_PROBABILITY) driver.processAnts();
		});
		driver.processAnts();
		executorService.shutdownNow();
		System.out.println("\nOptimal Route : "+Arrays.toString(driver.shortestRoute.getCities().toArray()));
		System.out.println("w/ Distance   : " + driver.shortestRoute.getDistance());
	}
	private void processAnts() {
		while (activeAnts > 0) {
			try { 
				Ant ant = executorCompletionService.take().get();
				Route currentRoute =  ant.getRoute();
				if (shortestRoute == null || currentRoute.getDistance() < shortestRoute.getDistance()) {
					shortestRoute = currentRoute;
					StringBuffer distance = new StringBuffer("       "+String.format("%.2f", currentRoute.getDistance()));
					IntStream.range(0, 21 - distance.length()).forEach(k -> distance.append(" "));
					System.out.println(Arrays.toString(shortestRoute.getCities().toArray()) + " |" + distance + "| "+ ant.getAntNumb());
				}
			} catch (Exception e) { e.printStackTrace(); }
			activeAnts--;
		}
	}
	private void printHeading() {
    	String headingColumn1 = "Route";
    	String remainingHeadingColumns = "Distance (in miles) | ant #";
    	int cityNamesLength = 0;
    	for (int x = 0; x < initialRoute.size(); x++) cityNamesLength += initialRoute.get(x).getName().length();
    	int arrayLength = cityNamesLength + initialRoute.size()*2;
    	int partialLength = (arrayLength - headingColumn1.length())/2;
    	for (int x=0; x < partialLength; x++)System.out.print(" ");
    	System.out.print(headingColumn1);
    	for (int x=0; x < partialLength; x++)System.out.print(" ");
    	if ((arrayLength % 2) == 0)System.out.print(" ");
    	System.out.println(" | "+ remainingHeadingColumns);
    	cityNamesLength += remainingHeadingColumns.length() + 3;
    	for (int x=0; x < cityNamesLength+initialRoute.size()*2; x++)System.out.print("-");
    	System.out.println("");
    }
}
