package com.neu.tsp.SimulatedAnnealing;
import java.util.ArrayList;
import java.util.Arrays;
public class Driver {
	private ArrayList<City> initialCities = new ArrayList<City>(Arrays.asList(
			new City("Boston", 42.3601, -71.0589), 
			new City("Houston", 29.7604, -95.3698), 
			new City("Austin", 30.2672, -97.7431), 
			new City("San Francisco", 37.7749, -122.4194), 
			new City("Denver", 39.7392, -104.9903), 
			new City("Los Angeles", 34.0522, -118.2437), 
			new City("Chicago", 41.8781, -87.6298), 
			new City("New York", 40.7128,-74.0059),
			new City("Dallas", 32.7767, -96.7970),
			new City("Seattle", 47.6062, -122.3321)
			//new City("Sydney", -33.8675,151.2070),
			//new City("Tokyo", 35.6895, 139.6917),
			//new City("Cape Town", -33.9249, 18.4241)
	));
	public static void main(String[] args) {
		Driver driver = new Driver();
		Route route = new Route(driver.initialCities);
		printHeading(route);
		new SimulatedAnnealing().findRoute(SimulatedAnnealing.INITIAL_TEMPERATURE, route);
		driver.printInfo();
	}
	public static void printHeading(Route route) {
    	String headingColumn1 = "Route";
    	String remainingHeadingColumns = "Distance |  Temp  | Func | Random # | Decision                          ";
    	int cityNamesLength = 0;
    	for (int x = 0; x < route.getCities().size(); x++) cityNamesLength += route.getCities().get(x).getName().length();
    	int arrayLength = cityNamesLength + route.getCities().size()*2;
    	int partialLength = (arrayLength - headingColumn1.length())/2;
    	for (int x=0; x < partialLength; x++)System.out.print(" ");
    	System.out.print(headingColumn1);
    	for (int x=0; x < partialLength; x++)System.out.print(" ");
    	if ((arrayLength % 2) == 0)System.out.print(" ");
    	System.out.println(" | "+ remainingHeadingColumns);
    	cityNamesLength += remainingHeadingColumns.length() + 3;
    	for (int x=0; x < cityNamesLength+route.getCities().size()*2; x++)System.out.print("-");
    	System.out.println("");
    }
	public void printInfo() {
    	System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("| Probability Function = exp(-(neighborDistance - currentDistance) / temperature) |");
        System.out.println("| newtemperature = oldtemperature *(1-RATE_OF_COOLING)                            |");
        System.out.print("| RATE_OF_COOLING = "+ SimulatedAnnealing.RATE_OF_COOLING);
        System.out.println("                                                         |");
        System.out.println("----------------------------------------------------------------------------------");
    }
}
