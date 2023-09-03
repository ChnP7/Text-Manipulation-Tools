package classes;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;

public class RegExclude {


    /**
     * Removes a string matching a regex from standard input
     * @param regex pattern to match that will be removed
     * @return a string with the resulting matches removed.
     */
    public static String removeFromStandardInput(String regex) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String res =  input.replaceAll(regex,"");
        return res;
    }

    /**
     * Removes a string matching a regex from a string passed as a parameter.
     * @param regex pattern to match that will be removed
     * @param removeFrom String to remove that matches from
     * @return a string with the resulting matches removed
     */
    public static String removeFromString(String regex, String removeFrom) {
        return removeFrom.replaceAll(regex, "");
    }

    /**
     * Reads a file and produces another file as output with all matches based
     * on the regex removed. Does not take into account cases where a regex spans
     * more than one line of a file.
     * @param regex pattern to match that will be removed
     * @param inputPath pathname to the file to read from
     * @param outputPath pathname of the file to output the result
     * @param append whether or not to append to the output file
     * @return true if processing was successful, false if not.
     * @throws IOException
     */
    public static boolean removeFromFile(String regex, String inputPath, String outputPath, boolean append) {

        BufferedReader reader;


        try {
            FileReader inputFR = new FileReader(inputPath);
            reader = new BufferedReader(inputFR);
            FileWriter writer = new FileWriter(outputPath, append);
            String line;

            while ((line = reader.readLine()) != null) {
                writer.write(line.replaceAll(regex, ""));

                if (reader.ready()) {
                    writer.write("\n");
                }
            }

            inputFR.close();
            reader.close();
            writer.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + inputPath + " not found!");
            return false;
        }
        catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
