
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
    
    public CSVRecord temperaturesPerHourInFile(CSVParser parser) {
        for(CSVRecord currentRow : parser) {
            System.out.println(currentRow.get("DateUTC") + " " + currentRow.get("TemperatureF"));
        }
        return null;
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
                if(currentTemp < 0) {
                    currentTemp = 9999.99;
                }
                
                if(currentTemp < minTemp) {
                    temp = currentRow;
                }
            }
        return temp;
    }
    
    public CSVRecord getSmallestHumidityOfTwo(CSVRecord currentRow, CSVRecord temp) {
        if(temp == null) {
                temp = currentRow;
            } else {
                if(currentRow.get("Humidity").equals("N/A")) {
                    System.out.println("N/A Found..skipping");
                } else {
                    double currentTemp = Double.parseDouble(currentRow.get("Humidity"));
                    double minTemp = Double.parseDouble(temp.get("Humidity"));
                    
                    if(currentTemp < minTemp) {
                        temp = currentRow;
                    }
                }
            }
        return temp;
    } 
            
    public String fileAbsWithColdestTemperature () {
        File tempFile = null;
        CSVRecord temp = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f); 
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            if(temp == null) {
                temp = currentRow;
            } else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double minTemp = Double.parseDouble(temp.get("TemperatureF"));
                
                if(currentTemp < minTemp) {
                    temp = currentRow;
                    tempFile = f;
                }
            }        
        }
        return tempFile.getAbsolutePath();
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord temp = null;
        
        for(CSVRecord currentRow : parser) {
            temp = getSmallestHumidityOfTwo(currentRow, temp); 
        }
        return temp;    
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord temp = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            temp = getSmallestHumidityOfTwo(currentRow, temp); 
            
        }
        return temp;
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
        double count = 0.0;
        double totalTemp = 0.0;
        
        for(CSVRecord currentRow : parser) {
            count++;
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            totalTemp += currentTemp;
        }
        return totalTemp/count;    
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        double count = 0.0;
        double totalTemp = 0.0;
        
        for(CSVRecord currentRow : parser) {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double currentHumid = Double.parseDouble(currentRow.get("Humidity"));
            if(currentHumid >= value) {
                count++;
                totalTemp += currentTemp;
            }
            
        }
        return totalTemp/count;  
    }
        
    public void testColdestInDay() {
        FileResource fr = new FileResource();
        CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest Temperature was " + smallest.get("TemperatureF") +
            " at " + smallest.get("DateUTC"));
    }
    
    public void testColdestInManyDays() {
        CSVRecord smallest = coldestInManyDays();
        System.out.println("Coldest Temperature was " + smallest.get("TemperatureF") +
            " at " + smallest.get("DateUTC"));
    }
    
    public void testFileWithColdestTemperature() {
        String smallestFileAbs = fileAbsWithColdestTemperature();
        String smallestFile = smallestFileAbs;
        int startIndex = smallestFile.indexOf("weather");
        smallestFile = smallestFile.substring(startIndex);
        System.out.println("Coldest day was in file " + smallestFile);
        
        FileResource fr = new FileResource(smallestFileAbs);
        CSVRecord smallest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest Temperature on that day was " + smallest.get("TemperatureF"));
        
        FileResource fr2 = new FileResource(smallestFileAbs);
        System.out.println("All the Temperatures on the coldest day were: ");
        CSVRecord smallestFileTemperatures = temperaturesPerHourInFile(fr2.getCSVParser());
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord smallest = lowestHumidityInManyFiles ();
        System.out.println("Lowest Humidity was " + smallest.get("Humidity") +
            " at " + smallest.get("DateUTC"));
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTemp = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " + avgTemp);
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        int value = 80;
        double avgTemp = averageTemperatureWithHighHumidityInFile(parser, value);
        
        System.out.println(avgTemp);
        
        if(avgTemp == -1) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is " + avgTemp);
        }
    }
}
