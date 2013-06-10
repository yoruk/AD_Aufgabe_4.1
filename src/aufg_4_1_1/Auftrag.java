package aufg_4_1_1;

import java.util.*;
import java.io.*;

public class Auftrag {

    private String name;    // Name des Auftrags
    private int startTime;  // Startzeit des Auftrags
    private int length;     // Dauer des Auftrags

    public Auftrag(String name, int startTime, int length) {
        super();
        this.name = name;
        this.startTime = startTime;
        this.length = length;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Gets the length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    protected String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AUFTRAG: " + name
                + "\tSTART:\t" + (startTime / 60) + ":" + (startTime % 60 < 10 ? "0" + (startTime % 60) : startTime % 60) + "\tLENGTH:\t" + length;
    }

    public static Auftrag[] readFromFile(String path) {

        //Liste fuer temporaeres Speichern der Auftraege
        List<Auftrag> auftragsList = new ArrayList<Auftrag>();

        // storage / manipulation variables
        String line;
        String AuftragName;
        int AuftragStartTime;
        int AuftragDuration;
        StringBuilder manipulator = new StringBuilder();

        //temporary indices for Substrings
        int tmpISubstingAuftragName = 0;
        int tmpISubstringStartTime = 0;
        int tmpISubstringDuration = 0;

        //debug / validation variables
        int lineCount=0;

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                lineCount++;
                line = line.trim(); // remove whitespaces
//                System.out.println("line: "+line+"\n");
                // read Auftragname
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ':') {
                        tmpISubstingAuftragName = i;
                        break;
                    }
                }
                AuftragName = line.substring(0, tmpISubstingAuftragName);
                //System.out.println("\tAuftragName: "+AuftragName+"\n");

                //add Offset
                tmpISubstringStartTime = tmpISubstingAuftragName+1;


                //read AuftragStartTime
                manipulator = new StringBuilder();
                manipulator.append(line.substring(tmpISubstringStartTime, tmpISubstringStartTime + 5));
                //System.out.println("\t\tmanipulator: "+manipulator+"\n");
                manipulator.deleteCharAt(2);
                //AuftragStartTime = manipulator.toString();
                AuftragStartTime = (Integer.parseInt(manipulator.substring(0, 2)) * 60 + Integer.parseInt(manipulator.substring(2, 4)));
                //System.out.println("\tAuftragStartTime: "+AuftragStartTime+"\n");

                //add Offset a 2nd time
                tmpISubstringDuration = tmpISubstingAuftragName + 7;

                //read AuftragDuration
                manipulator = new StringBuilder();
                manipulator.append(line);
                manipulator.delete(0, tmpISubstringDuration);
                manipulator.reverse();
                manipulator.delete(0, 3);
                AuftragDuration = Integer.parseInt(manipulator.reverse().toString());
                //System.out.println("\tAuftragDuration: "+AuftragDuration+"\n");

                //System.out.println("AuftragName: "+AuftragName+"\tAuftragStartTime: "+AuftragStartTime+"\tAuftragDuration: "+AuftragDuration);

                auftragsList.add(new Auftrag(AuftragName, AuftragStartTime, AuftragDuration));
                tmpISubstingAuftragName = 0;
                tmpISubstringStartTime = 0;
                tmpISubstringDuration = 0;
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Auftrag[] auftraege = new Auftrag[auftragsList.size()];
        for (int i = 0; i < auftraege.length; i++) {
            auftraege[i] = auftragsList.get(i);
        }
        if (lineCount == auftraege.length) {
            return auftraege;
        } else {
            System.err.println("Data inconstency. Number of data entries in input differs from number of Auftrags.\n");
            return null;
        }
    }
}
