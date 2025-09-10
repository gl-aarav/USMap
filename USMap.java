/**
 * The USMap class is responsible for reading city data,
 * setting up a canvas, and drawing a map of the US with cities.
 */
public class USMap {
    // Arrays to store latitude, longitude, and city names
    private double[] latCoords;
    private double[] lonCoords;
    private String[] cityNames;
    // Arrays to store biggest city names and their populations
    private String[] bigCities;
    private int[] bigCitiesPop;

    /**
     * Constructor for the USMap class.
     * Initializes all arrays with their respective sizes.
     */
    public USMap() {
        latCoords = new double[1112];
        lonCoords = new double[1112];
        cityNames = new String[1112];
        bigCities = new String[275];
        bigCitiesPop = new int[275];
    }

    /**
     * Main method to run the USMap application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        USMap us = new USMap();

        // Read city information from files
        us.readCityData("cities.txt");
        us.readBigCityData("bigCities.txt");
        
        // Set up the drawing canvas and draw the map elements
        us.setupMapCanvas();
        us.drawMapElements();
    }

    /**
     * Reads city latitude, longitude, and names from a specified file.
     * @param fName The name of the file containing city data.
     */
    public void readCityData(String fName) {
        java.util.Scanner input = FileUtils.openToRead(fName);

        // Fill the arrays with data from the file
        for (int i = 0; i < 1112; i++) {
            lonCoords[i] = input.nextDouble();
            latCoords[i] = input.nextDouble();
            cityNames[i] = input.nextLine().trim();
        }
        input.close(); // Close the scanner after reading
    }

    /**
     * Reads information about the biggest cities, including their names and populations.
     * @param fName The name of the file containing biggest city data.
     */
    public void readBigCityData(String fName) {
        String numStr = "";
        java.util.Scanner input = FileUtils.openToRead(fName);

        // Filling in the arrays with biggest cities data
        for (int i = 0; i < bigCities.length; i++) {
            input.nextInt(); // Read and discard rank (not used currently)
            bigCities[i] = input.nextLine();

            // Extract population number from the string
            numStr = bigCities[i].replaceAll("[a-z,-,---]", "").trim();
            bigCitiesPop[i] = Integer.parseInt(numStr);

            // Remove numbers from the city name string
            bigCities[i] = bigCities[i].replaceAll("\\d","").trim();
        }
        input.close(); // Close the scanner after reading
    }

    /**
     * Sets up the drawing canvas parameters, including title, size, and scales.
     */
    public void setupMapCanvas() { 
        StdDraw.setTitle("USMap"); 
        StdDraw.setCanvasSize(900, 512); 
        StdDraw.setXscale(128.0, 65.0); 
        StdDraw.setYscale(22.0, 52.0);
    }

    /**
     * Draws various map elements, including the biggest cities and all other cities.
     */
    public void drawMapElements() {
        // Draw top 10 biggest cities in red with varying pen radius based on population
        StdDraw.setPenColor(StdDraw.RED);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < cityNames.length; j++) {
                if (bigCities[i].equals(cityNames[j])) {
                    StdDraw.setPenRadius(0.6 * Math.sqrt(bigCitiesPop[i]) / 18500);
                    StdDraw.point(latCoords[j], lonCoords[j]);
                }
            }
        }

        // Draw remaining biggest cities in blue with varying pen radius based on population
        StdDraw.setPenColor(StdDraw.BLUE);
        for (int i = 10; i < bigCities.length; i++) {
            for (int j = 0; j < cityNames.length; j++) {
                if (bigCities[i].equals(cityNames[j])) {
                    StdDraw.setPenRadius(0.6 * Math.sqrt(bigCitiesPop[i]) / 18500);
                    StdDraw.point(latCoords[j], lonCoords[j]);
                }
            }
        }
        
        // Draw all other cities in gray with a fixed pen radius
        StdDraw.setPenRadius(0.006);
        StdDraw.setPenColor(StdDraw.GRAY);
        for (int i = 0; i < latCoords.length; i++) {
            StdDraw.point(latCoords[i], lonCoords[i]);
        }
    }
}