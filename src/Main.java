import classes.RegExclude;

public class Main
{
    public static void main(String[] args) {
        String regexp = "[0-9]+,(male|female),[0-9]+,[A-Za-z0-9 -]+,[A-Za-z0-9 -]+,\\\"(.+)\\\",\\\"[ ]+";
        String inPath = "blogText.csv";
        String outPath = "blogRaw.txt";
        RegExclude.removeFromFile(regexp, inPath, outPath, false);
    }
}
