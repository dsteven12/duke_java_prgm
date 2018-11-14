
/**
 * Write a description of CSVMax here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
    public CSVRecord hottestHourInFile(CSVParser parser) {
        CSVRecord temp = null;
        
        for(CSVRecord currentRow : parser) {
            temp = getLargestOfTwo(currentRow, temp); 
        }
        return temp;
    }
    
    public CSVRecord hottestInManyDays() {
        CSVRecord temp = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
            temp = getLargestOfTwo(currentRow, temp); 
        }
        return temp;
    }
    
    public CSVRecord getLargestOfTwo(CSVRecord currentRow, CSVRecord temp) {
        if(temp == null) {
                temp = currentRow;
            } else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double maxTemp = Double.parseDouble(temp.get("TemperatureF"));
                
                if(currentTemp > maxTemp) {
                    temp = currentRow;
                }
            }
        return temp;
    }    
        
    public void testHottestInDay() {
        FileResource fr = new FileResource("data/2015/weather-2015-01-02.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("Hottest Temperature was " + largest.get("TemperatureF") +
            " at " + largest.get("TimeEST"));
    }
    
    public void testHottestInManyDays() {
        CSVRecord largest = hottestInManyDays();
        System.out.println("Hottest Temperature was " + largest.get("TemperatureF") +
            " at " + largest.get("DateUTC"));
    }
}
