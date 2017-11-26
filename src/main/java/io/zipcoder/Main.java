package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
//        System.out.println(output);
        // TODO: parse the data in output into items, and display to console.

        String rawSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";
        String rawMultipleItems = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##"
                +"naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##"
                +"NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";
        ItemParser itemParser = new ItemParser();
        ArrayList<String> pleaseWork = itemParser.parseRawDataIntoStringArray(output);

//        for(int i = 0; i < pleaseWork.size(); i++) {
//            if (!(itemParser.checkDataForMatches(pleaseWork.get(i)))) {
//                System.out.println(pleaseWork.get(i) + "-------" + itemParser.checkDataForMatches(pleaseWork.get(i)));
//            }
//        }
//        itemParser.printOneString(rawSingleItem, 1);
        itemParser.printMultiple(output);
    }
}