
/**
 * Write a description of CSVMin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMin {
    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord temp = null;
        
        for(CSVRecord currentRow : parser) {
            temp = getSmallestOfTwo(currentRow, temp); 
        }
        return temp;
    }
    
    public CSVRecord coldestInManyDays() {
        CSVRecord temp = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            temp = getSmallestOfTwo(currentRow, temp); 
        }
        return temp;
    }
    
    public CSVRecord getSmallestOfTwo(CSVRecord currentRow, CSVRecord temp) {
        if(temp == null) {
                temp = currentRow;
            } else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double minTemp = Double.parseDouble(temp.get("TemperatureF"));
                
                if(currentTemp < minTemp) {
                    temp = currentRow;
                }
            }
        return temp;
    }    
        
    public void testColdestInDay() {
        FileResource fr = new FileResource("data/2015/weather-2015-01-02.csv");
        CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest Temperature was " + smallest.get("TemperatureF") +
            " at " + smallest.get("TimeEST"));
    }
    
    public void testColdestInManyDays() {
        CSVRecord smallest = coldestInManyDays();
        System.out.println("Coldest Temperature was " + smallest.get("TemperatureF") +
            " at " + smallest.get("DateUTC"));
    }

}
