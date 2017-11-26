package io.zipcoder;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    Integer counter;

    public void printMultiple(String rawData)throws  NullPointerException{
        ArrayList<String> temp = parseRawDataIntoStringArray(rawData);
        int errors = 0;
        try{
        for(int i = 0; i < temp.size(); i++) {
            Item tempItem = parseStringIntoItem(temp.get(i));
            if (!(checkDataForMatches(temp.get(i)))) {
                temp.remove(i);
                errors++;
            }
            Map<String, Integer> nameCounter = nameValueCounter(rawData);
            printOneString(temp.get(i), getSingleCount(nameCounter,tempItem.getName()));
        }
        }catch (NullPointerException npe){
            errors++;
        }
        System.out.printf("\n\nErrors \t\t\t\t seen: %d times", errors);
    }

    public void printOneString(String rawData, int count){
        Item item = parseStringIntoItem(rawData);
        System.out.printf("\n\nname:%8s\t\t seen: %d times\n", item.getName().substring(0,1).toUpperCase() + item.getName().substring(1), count);
        System.out.print("============= 	 	 =============");
        System.out.printf("\nPrice:%7.2f", item.getPrice());
        System.out.printf("\t\t seen: %d times\n", count);
        System.out.print("-------------\t\t -------------");
    }

    public boolean checkDataForMatches(String rawData){
            if(patternMatcherExpiration(rawData) && patterMatcherName(rawData) && patternMaterPrice(rawData) && patternMaterType(rawData).equals(true)){
                return true;
        }
        counter++;
        return false;
    }

    public ArrayList<String> allNameValues(String rawData){
        ArrayList<String> nameValue = new ArrayList<>();
        ArrayList<String> temp = parseRawDataIntoStringArray(rawData);
        for(String name: temp){
            ArrayList<String> forLoop = findKeyValuePairsInRawItemData(name);
            nameValue.add(forLoop.get(1).toLowerCase());
        }
        return nameValue;
    }

    public Map<String, Integer> nameValueCounter(String rawData){
        Integer counter = 0;
        ArrayList<String> nameOccurences = allNameValues(rawData);
        Map<String, Integer> wordCount = new HashMap<String, Integer>();

        for(String word: nameOccurences) {
            counter = wordCount.get(word);
            wordCount.put(word, (counter==null) ? 1 : counter+1);
        }
        return wordCount;
    }

    public int getSingleCount(Map<String, Integer> data, String singleString){
        return new Integer(data.get(singleString));
    }

    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem){
        if(checkDataForMatches(rawItem) == true) {
            return new Item(getNameValue(rawItem), getPriceValue(rawItem), getTypeValue(rawItem), getExpirationValue(rawItem));
        }return null;
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^|:|*|&|%|#|!]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }


    public Boolean patterMatcherName(String rawData){
        Pattern namePattern = Pattern.compile("(^[n|N][a|A][m|M][e|E]):([a-z|A-Z|0-9]+)");
        Matcher name = namePattern.matcher(rawData);
        return name.find();
    }

    public Boolean patternMaterPrice(String rawData){
        Pattern pricePattern = Pattern.compile("([p|P][r|R][i|I][c|C][e|E]):([0-9]+\\.[0-9]+)");
        Matcher priceMatcher = pricePattern.matcher(rawData);
        return priceMatcher.find();
    }

    public Boolean patternMaterType(String rawData){
        Pattern typePattern = Pattern.compile("([p|P][r|R][i|I][c|C][e|E]):([0-9]+\\.[0-9]+)");
        Matcher typeMatcher = typePattern.matcher(rawData);
        return typeMatcher.find();
    }

    public Boolean patternMatcherExpiration(String rawData){
        //        Pattern expirationPattern = Pattern.compile("e........n:\\d.......6", Pattern.CASE_INSENSITIVE);
        Pattern expirationPattern = Pattern.compile("([e|E][x|X][p|P][i|I][r|R][a|A][t|T][i|I][o|O|0][n|N]):([0-9]+/[0-9]+/[0-9]+)");
        Matcher expirationMatcher = expirationPattern.matcher(rawData);
        return expirationMatcher.find();
    }

    public String getNameValue(String rawData){
        ArrayList<String> toFind = findKeyValuePairsInRawItemData(rawData);
        String toReturn = toFind.get(1).toLowerCase();
        return toReturn;
    }

    public Double getPriceValue(String rawData){
        ArrayList<String> toFind = findKeyValuePairsInRawItemData(rawData);
        String toReturn = toFind.get(3).toLowerCase();
        return Double.parseDouble(toReturn);
    }

    public String getTypeValue(String rawData){
        ArrayList<String> toFind = findKeyValuePairsInRawItemData(rawData);
        String toReturn = toFind.get(5).toLowerCase();
        return toReturn;
    }

    public String getExpirationValue(String rawData){
        ArrayList<String> toFind = findKeyValuePairsInRawItemData(rawData);
        String toReturn = toFind.get(7).toLowerCase();
        return toReturn;
    }











//    private String compareListItems (Pattern pattern, String rawData) throws ItemParseException {
//        if(pattern.matcher(rawData).matches()){
//            ArrayList<String> arrayToReturn = splitStringWithRegexPattern(":", rawData);
//            rawData = arrayToReturn.get(1).toUpperCase();
//        }else {
//            throw new ItemParseException("This doesn't work");
//            }
//        return rawData;
//    }
}
