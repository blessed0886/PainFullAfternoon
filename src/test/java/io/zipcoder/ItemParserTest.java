package io.zipcoder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ItemParserTest {

    private String rawData = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##naMe:CoOkieS;price:2.25;type:Food*expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;price:1.23;type:Food!expiration:4/25/2016##naMe:apPles;price:0.25;type:Food;expiration:1/23/2016##naMe:apPles;price:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food;expiration:1/04/2016##naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food@expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food@expiration:2/25/2016##naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;priCe:;type:Food;expiration:4/25/2016##naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016##naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food^expiration:1/04/2016##";

    private String nothing = "";

    private String rawSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawSingleItemIrregularSeperatorSample = "naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##";

    private String rawBrokenSingleItem =    "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##";

    private String rawMultipleItems = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##"
                                      +"naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##"
                                      +"NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##";

    private ItemParser itemParser;

    Item item = new Item("MiLk", 1.50, "FooD", "3/8/2016");

    @Before
    public void setUp(){
        itemParser = new ItemParser();
    }

    @Test
    public void numberOfErrorsTest(){
        int expected = 4;
        ArrayList<String> tempArray = itemParser.parseRawDataIntoStringArray(rawData);
        ArrayList<String> temp = itemParser.removeNonMatches(tempArray);

        int actual = itemParser.numberOfErrors(tempArray, temp);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeNonMatchesTest(){
        int expected = 24;
        ArrayList<String> temp = itemParser.parseRawDataIntoStringArray(rawData);

        int actual = itemParser.removeNonMatches(temp).size();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSingeCountTest(){
        int expected = 2;
        Map<String, Integer> temp = itemParser.nameValueCounter(rawMultipleItems);

        int actual = itemParser.getSingleCount(temp, "bread");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nameValueCounterTest(){
        String expected = "{bread=2, milk=1}";

        String actual = itemParser.nameValueCounter(rawMultipleItems).toString();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void allNameValuesTest(){
        String expected = "[milk, bread, bread]";

        String actual = itemParser.allNameValues(rawMultipleItems).toString();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkDataForMatchesFalse(){
        boolean expected = false;

        boolean actual = itemParser.checkDataForMatches(nothing);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkDataForMatchesTrue(){
        boolean expected = true;

        boolean actual = itemParser.checkDataForMatches(rawMultipleItems);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnOneItemTest(){

    }

    @Test
    public void getExpirationValueTest(){
        String expected = "1/25/2016";

        String actual = itemParser.getExpirationValue(rawSingleItem);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTypeValue(){
        String expected = "food";

        String actual = itemParser.getTypeValue(rawSingleItem);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getPriceValueTest(){
        Double expected = 3.23;

        Double actual = itemParser.getPriceValue(rawSingleItem);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getNameValueTest(){
        String expected = "milk";

        String actual = itemParser.getNameValue(rawSingleItem);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void patterMatcherExpirationTestFalse(){
        Boolean expected = false;

        Boolean actual = itemParser.patternMatcherExpiration(nothing);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void patterMatcherExpirationTestTrue(){
        Boolean expected = true;

        Boolean actual = itemParser.patternMatcherExpiration(rawBrokenSingleItem);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void patterMatcherTypeTestFalse(){
        Boolean expected = false;

        Boolean actual = itemParser.patternMaterType(nothing);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void patterMatcherTypeTestTrue(){
        Boolean expected = true;

        Boolean actual = itemParser.patternMaterType(rawBrokenSingleItem);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void patterMatcherPriceTestTrue(){
        Boolean expected = true;

        Boolean actual = itemParser.patternMaterPrice(rawBrokenSingleItem);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void patterMatcherPriceTestFalse(){
        Boolean expected = false;

        Boolean actual = itemParser.patternMaterPrice(nothing);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void patterMatcherNameTestTrue(){
        Boolean expected = true;

        Boolean actual = itemParser.patterMatcherName(rawBrokenSingleItem);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void patterMatcherNameTestFalse(){
        Boolean expected = false;

        Boolean actual = itemParser.patterMatcherName(nothing);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseRawDataIntoStringArrayTest(){
        Integer expectedArraySize = 28;
        ArrayList<String> items = itemParser.parseRawDataIntoStringArray(allData);
        Integer actualArraySize = items.size();
        System.out.println(items);
        assertEquals(expectedArraySize, actualArraySize);
    }

    @Test
    public void parseStringIntoItemTest() throws ItemParseException{
        Item expected = new Item("milk", 3.23, "food","1/25/2016");
        Item actual = itemParser.parseStringIntoItem(rawSingleItem);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test(expected = ItemParseException.class)
    public void parseBrokenStringIntoItemTest() throws ItemParseException{
        itemParser.parseStringIntoItem(rawBrokenSingleItem);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTest(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItem).size();
        assertEquals(expected, actual);
    }

    @Test
    public void findKeyValuePairsInRawItemDataTestIrregular(){
        Integer expected = 4;
        Integer actual = itemParser.findKeyValuePairsInRawItemData(rawSingleItemIrregularSeperatorSample).size();
        assertEquals(expected, actual);
    }

    @Test
    public void testing(){
        Assert.assertEquals(itemParser.parseRawDataIntoStringArray(allData), itemParser.findKeyValuePairsInRawItemData(allData));
    }


    private String allData = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:2/25/2016##naMe:MiLK;price:3.23;type:Food^expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food%expiration:1/25/2016##naMe:CoOkieS;price:2.25;type:Food*expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;price:1.23;type:Food!expiration:4/25/2016##naMe:apPles;price:0.25;type:Food;expiration:1/23/2016##naMe:apPles;price:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food;expiration:1/04/2016##naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food@expiration:1/02/2016##NAMe:BrEAD;price:1.23;type:Food@expiration:2/25/2016##naMe:MiLK;priCe:;type:Food;expiration:1/11/2016##naMe:Cookies;price:2.25;type:Food;expiration:1/25/2016##naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016##naMe:COokIes;price:2.25;type:Food;expiration:3/22/2016##naMe:COOkieS;Price:2.25;type:Food;expiration:1/25/2016##NAME:MilK;price:3.23;type:Food;expiration:1/17/2016##naMe:MilK;priCe:;type:Food;expiration:4/25/2016##naMe:apPles;prIce:0.25;type:Food;expiration:1/23/2016##naMe:apPles;pRice:0.23;type:Food;expiration:5/02/2016##NAMe:BrEAD;price:1.23;type:Food;expiration:1/25/2016##naMe:;price:3.23;type:Food^expiration:1/04/2016##";
}
