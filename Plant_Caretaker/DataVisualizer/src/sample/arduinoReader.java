package sample;

import java.io.BufferedReader;
import java.io.FileReader;

public class arduinoReader implements Reader {

     BufferedReader thisJustIn;

    public String readLine() {

        String lineRead = "";

        try {
            lineRead = thisJustIn.readLine();
            System.out.println("new val " + lineRead);

        } catch (Exception e) {
        }
        return lineRead;
    }

    public boolean init() { // Initialize

        String file = "C:\\Users\\Bavian\\Desktop\\Hum.Tek\\6. Semester\\EC2_2.0\\EbbeGRaph\\IDS_Assignment2\\src\\textData.txt";
        String file2 = "C:\\Users\\Bavian\\Desktop\\Hum.Tek\\6. Semester\\Interaktive Digitale Systemer\\Aflevering1\\sketch_ProcessingDataCollector\\LysData.txt";

        try {
            thisJustIn = new BufferedReader(new FileReader(file));

        } catch (Exception e) {
            System.out.println(e);
        }
        return true;
    }
}
