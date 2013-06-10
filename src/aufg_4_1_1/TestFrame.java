package aufg_4_1_1;

public class TestFrame {

    public static final String filePath = "Auftraege3.txt";

    public static void main(String[] args) {

        Auftrag[] auftraege = Auftrag.readFromFile(filePath);

        Maschine maschine = new MaschineImpl(06, 16);

        /* Auftraege ausgeben */
        System.out.println("TEST DATEI: " + filePath);
        System.out.println("\nBEINHALTETE AUFTRAEGE:\n");
        for (Auftrag auftrag : auftraege) {
            System.out.println(auftrag.toString());
        } // for

        /* Optimalsten Auftrag finden  */
        Auftrag[] optimierterAuftrag = maschine.getOptimum(auftraege);

        /* Exkludierte Auftraege ermitteln */
        Auftrag[] excludedJobs = maschine.excludedJobs(auftraege, optimierterAuftrag);

        System.out.println("\n\nAUSGEFUEHRTE AUFTRAEGE:\n");
        for(Auftrag auftrag : optimierterAuftrag) {
            System.out.println(auftrag);
        } // for

        System.out.println("\nBEARBEITUNGSZEIT: " +  maschine.getDuration() + " MINUTEN\n");

        System.out.println("\nNICHT AUSGEFUEHRTE AUFTRAEGE:\n");
        for(Auftrag auftrag : excludedJobs) {
            System.out.println(auftrag);
        } // for

    } // main

} // public class TestFrame
