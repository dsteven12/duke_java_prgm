
/**
 * Write a description of CountryExports here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;

public class CountryExports {
    public void countryExports(CSVParser parser, String exportOfInterest) {
        int count = 0;
        for(CSVRecord record: parser) {
            String export = record.get("Exports");
            
            if(export.contains(exportOfInterest)) {
                count++;
                System.out.println("Country: " + record.get("Country") + ". ");
                System.out.println("Exports: " + record.get("Exports") + ". ");
                System.out.println("Value: " + record.get("Value (dollars)") + ". ");
                System.out.println("=========================================");
            }
        }
        System.out.println(count);
    }
    
    public String countryInfo(CSVParser parser, String countryCheck) {
        String info = "+++++Not Found+++++";       
        for(CSVRecord record: parser) {
            String country = record.get("Country"); 
            
            if(country.contains(countryCheck)) {
                info = country + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");            
            } 
        }
        return info;
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportitem1, String exportitem2) {
        for(CSVRecord record: parser) {
            String export = record.get("Exports");
            
            if((export.contains(exportitem1)) && (export.contains(exportitem2))) {
                System.out.println("Country: " + record.get("Country") + ". ");
                System.out.println("Exports: " + record.get("Exports") + ". ");
                System.out.println("Value: " + record.get("Value (dollars)") + ". ");
                System.out.println("=========================================");
            }
        }
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        for(CSVRecord record: parser) {
            String value = record.get("Value (dollars)");
            String country = record.get("Country");            
            if(value.length() > amount.length()) {
                System.out.println(country + " " + value);
            }
        
        }
    }
    
       
    public void tester() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
                
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "cotton", "flowers");
        
        System.out.println("*****************************************");
        parser = fr.getCSVParser();
        countryExports(parser, "cocoa");
        System.out.println("=========================================");
        
        System.out.println("*****************************************");
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999,999");
    }

}
