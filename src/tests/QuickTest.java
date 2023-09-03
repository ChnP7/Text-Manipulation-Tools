package tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import classes.RegExclude;

/**
 * QuickTest:
 * Simple tests being used to check for correct output of the functions in the RegExp class.
 *
 * //TODO: Test standard input method
 */
public class QuickTest {

    /* Test Strings being modified for the tests */
    static String[] testStrings = {
            "YYYyyyHelloY",
            "Soda019Table110293456789GreatYY1",
            "&!&*#&$(!& ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890",
            "There was once an ugly barnicle.\nHe was so ugly that everyone died. \nThe end."
    };

    /* Regular expressions being used on the test strings */
    static String[] regexes = {
            "Y",
            "Y{3}",
            "[0-9]",
            "\\w",
            "There was once an ugly barnicle",
    };

    /* Expected Answers, used for FromStrings() test */
    static String[] answers = {
            "yyyHello",
            "yyyHelloY",
            "YYYyyyHelloY",
            "",
            "YYYyyyHelloY",

            "Soda019Table110293456789Great1",
            "Soda019Table110293456789GreatYY1",
            "SodaTableGreatYY",
            "",
            "Soda019Table110293456789GreatYY1",

            "&!&*#&$(!& ABCDEFGHIJKLMNOPQRSTUVWXZ 1234567890",
            "&!&*#&$(!& ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890",
            "&!&*#&$(!& ABCDEFGHIJKLMNOPQRSTUVWXYZ ",
            "&!&*#&$(!&  ",
            "&!&*#&$(!& ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890",

            "There was once an ugly barnicle.\nHe was so ugly that everyone died. \nThe end.",
            "There was once an ugly barnicle.\nHe was so ugly that everyone died. \nThe end.",
            "There was once an ugly barnicle.\nHe was so ugly that everyone died. \nThe end.",
            "     .\n      . \n .",
            ".\nHe was so ugly that everyone died. \nThe end."
    };


    public static void main(String[] args) {
        testFromStrings();
        testFromFile(false);
    }


    /**
     * Tests the test from strings method in RegExclude.
     * @return true if correct results shown. False otherwise
     */
    private static boolean testFromStrings() {
        String[] results = new String[testStrings.length * regexes.length];
        int loopCount = 0;

        /* Run the regexes for each test string. Add each to the result array */
        for (int testStr_i = 0; testStr_i < testStrings.length; testStr_i++) {
            for (int regex_i = 0; regex_i < regexes.length; regex_i++) {
                results[loopCount] = RegExclude.removeFromString(regexes[regex_i], testStrings[testStr_i]);
                loopCount++;
            }
        }

        /* ------- Comparing results to the answers ------- */
        if (results.length != answers.length) {
            System.out.println("FAIL: results length is not equal to answers length.");
            return false;
        }

        for (int i = 0; i < results.length; i++) {
            if (!results[i].equals(answers[i])) {
                System.out.println("FAILURE AT index: " + i + ". ");
                System.out.println("Output " + results[i] + " does not match answer: " + answers[i]);
                return false;
            }
        }

        System.out.println("testFromString() passed!");
        return true;
    }


    /**
     * Test the RegExclude remove from file method.
     * First it stores all test string together in one file, this will be the input file.
     * all regexes are run through this file, and output (appending) into another file, the output file.
     * The output file is then tested against the expected correct answers.
     * @param deleteProducedFiles true to automatically delete any files produced afterward. False to keep them.
     * @return true if correct results shown. False otherwise
     */
    private static boolean testFromFile(boolean deleteProducedFiles) {

        String testPath = "tests/RegExclude/tests.txt";
        String resultPath = "tests/RegExclude/results.txt";
        String ansPath = "tests/RegExclude/ans.txt";


        /******* Obtain answer ********/
        StringBuilder allTestStrings = new StringBuilder("");
        for (String str : testStrings) {
            allTestStrings.append(str);
        }
        StringBuilder ans = new StringBuilder("");
        for (int i = 0; i < regexes.length; i++) {
            ans.append(allTestStrings.toString().replaceAll(regexes[i], ""));
        }



        try {

            /******* Write answer to file ********/
            FileWriter ansWriter = new FileWriter(ansPath, false);
            ansWriter.write(ans.toString());
            ansWriter.close();

            /******* Write test strings to a file ********/
            FileWriter writer = new FileWriter(testPath, false);
            for (String str : testStrings) {
                writer.write(str);
            }
            writer.close();


            /******* Call function being tested ********/
            for (int i = 0; i < regexes.length; i++) {
                RegExclude.removeFromFile(regexes[i], testPath, resultPath, true);
            }

            /******* Read output file after operation is performed ********/
            File resFile = new File(resultPath);
            Scanner resScanner = new Scanner(resFile);
            StringBuilder resString = new StringBuilder("");
            while (resScanner.hasNextLine()) {
                resString.append(resScanner.nextLine());
            }
            resScanner.close();

            /******* Read answer file ********/
            File ansFile = new File(ansPath);
            Scanner ansScanner = new Scanner(ansFile);
            StringBuilder ansString = new StringBuilder("");
            while (ansScanner.hasNextLine()) {
                ansString.append(ansScanner.nextLine());
            }
            ansScanner.close();


            /******* Delete Produced Files ********/
            if (deleteProducedFiles) {
                File testFile = new File(testPath);
                testFile.delete();
                resFile.delete();
                ansFile.delete();
            }



            /******* Compare the actual answer to the output file content ********/
            if (!ansString.toString().equals(resString.toString())) {
                System.out.println("FAIL: \n" +
                        "result: " + resString.toString() + "\n" +
                        "is not equal to\n" +
                        "answer: " + ansString.toString());
                return false;
            }
        }

        catch(IOException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }

        System.out.println("testFromFile() passed!");
        return true;
    }



}