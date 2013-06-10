package aufg_4_1_1;

import java.util.*;

public class MaschineImpl implements Maschine {

    /* OBJEKTVARIABLEN */
    int startTime;
    int endTime;
    int duration;
    boolean[] timeTable;


    /**
     * Instantiates a new maschine.
     *
     * @param startTime die Startzeit der Maschine in Stunden
     * @param endTime die Endzeit der Maschine in Stunden
     */
    public MaschineImpl(int startTime, int endTime) {
        super();
        this.startTime = startTime * 60;
        this.endTime = endTime * 60;
        this.timeTable = new boolean[endTime - startTime];
        this.duration = 0;
    } // Maschine Konstruktor

    
    @Override
    public int getDuration() {
        return duration;
    }


    /**
     * Ermittelt die Gesamtlaufzeit einer Liste von Auftraegen.
     * 
     * @param list      Eine Liste von Auftraegen
     * @return          Laufzeit der gesamten Auftraege
     */
    public int getDuration(List<Auftrag> list) {

        int duration = 0;

        for (int i = 0; i < list.size(); i++) {
            duration = duration + list.get(i).getLength();
        } // for

        return duration;
    } // getDuration

    /**
     * Boolesches Array, das den Minuten der Arbeitszeit entspricht.
     * Wird mit dem Startwert false vorinitialisiert, bzw. resettet.
     */
    public void resetTimetable() {
        this.timeTable = new boolean[this.endTime - this.startTime];
    } // resetTimetable

    /**
     * Timetable ist ein boolesches Array, das den Minuten der Arbeitszeit zugrunde
     * liegt. Die vom ausgewaehlten Auftrag benutzen Minuten werden dort reserviert,
     * indem die Felder des Array auf TRUE gesetzt werden.
     * 
     * @param auftrag   Auftrag, fuer den die Zeit reserviert werden soll
     */
    public void reserveTimetable(Auftrag auftrag) {

        int startIndex = auftrag.getStartTime() - this.startTime;
        for (int i = startIndex; i < (startIndex + auftrag.getLength()); i++) {
            this.timeTable[i] = true;
        } // for
    } // reserveTimetable

    /**
     * Prueft, ob ein Auftrag innerhalb seiner Zeit auch auf freie Betriebszeit
     * zugreifen kann, oder ob diese Zeit bereits reserviert ist.
     * 
     * @param entry         Auftrag, der bereits besteht
     * @param candidate     Auftrag, fuer den die Zeitreservierung geprueft werden soll
     * @return              TRUE oder FALSE
     */
    public boolean invalidTime(Auftrag entry, Auftrag candidate) {

        int startTime = candidate.getStartTime() - this.startTime;
        int endTime = startTime + candidate.getLength();

        /* Falls sich der Start von candidate mit dem Ende von exist ueberschneidet */
        if (candidate.getStartTime() == (entry.getStartTime()) + entry.getLength()) {
            startTime++; // startTime in der Timetable um einen Index spaeter starten lassen
        } // if

        /* Pruefen, ob man auf einen belegten Zeitplatz trifft und deshalb abbrechen*/
        for (int i = startTime; i < endTime; i++) {
            if (timeTable[i] == true) {
                return true;
            } // if
        } // for

        return false;
    } // validTime

    /**
     * Prueft, ob ein Auftrag innerhalb der Betriebszeit liegt.
     * 
     * @param auftrag   Ein einzelner Auftrag
     * @return          TRUE oder FALSE
     */
    public boolean isValid(Auftrag auftrag) {
        if ((auftrag.getStartTime() >= this.startTime)
                && (((auftrag.getStartTime() + auftrag.getLength()) <= this.endTime))) {
            return true;
        } else {
            return false;
        } // else
    } // isValid

    /**
     * Prueft, ob sich zwei Auftraege in ihrer Laufzeit ueberschneiden.
     * 
     * @param auftrag1  Erster zu pruefender Auftrag
     * @param auftrag2  Zweiter zu pruefender Auftrag
     * @return          TRUE oder FALSE
     */
    public boolean isIntersected(Auftrag auftrag1, Auftrag auftrag2) {

        /* Pruefen, ob beide Auftrage gueltig sind */
        if ((auftrag1 != null) && (auftrag2 != null)) {

            /* Pruefen, ob beide Auftraege innerhalb der Betriebszeit liegen und nicht dieselben Auftraege sind */
            if ((isValid(auftrag1) && isValid(auftrag2)) && (auftrag1 != auftrag2)) {

                /* Pruefen, ob auftrag1 vor auftrag2 liegt, bzw. umgekehrt */
                if ( (((auftrag1.getStartTime() < auftrag2.getStartTime())) && (((auftrag1.getStartTime() + auftrag1.getLength()) <= (auftrag2.getStartTime()))))                        
                  || (((auftrag2.getStartTime() < auftrag1.getStartTime()) && ((auftrag2.getStartTime() + auftrag2.getLength()) <= (auftrag1.getStartTime())))) ) {
                    return false;
                } // if
            } // if
        } // ff

        return true;
    } // isIntersected

    /**
     * Nimmt ein Array von Auftraegen entgegen und optimierte deren Reihenfolge
     * so, dass sie sich nicht ueberlappen und eine maximale Maschinenlaufzeit
     * entsteht.
     * 
     * @param auftraege     Array ueber Auftrag
     * @return              Array ueber Auftrag (optimiert)
     */
    @Override
    public Auftrag[] getOptimum(Auftrag[] auftraege) {

        /* Array fuer optimalen Auftrag vorinitialisieren */
        Auftrag[] optimalerAuftrag = sortStartTime(auftraege);

        /* ArrayList fuer eine Kombination von Auftraegen */
        List<Auftrag> auftragsKombination;

        /* ArrayList ueber alle moeglichen Auftragskombinationen */
        List<List<Auftrag>> auftragsArray = new ArrayList<List<Auftrag>>();

        /* Auftraege zum Zwischenspeichern */
        Auftrag tempAuftrag1;
        Auftrag tempAuftrag2;

        int tempDuration = 0; // Speichern der Berechnung ueber die laengste Dauer
        int tempIndex = 0; // Speichern des zugehoerigen Index in auftragsArray


        /*
         * Zuerst alle Auftraege durchlaufen und dabei fuer jeden Auftrag eine
         * Kombination mit allen anderen moeglichen Auftraegen erstellen, die
         * sich weder ueberschneiden, noch ausserhalb der Betriebszeiten der
         * Maschine liegen.
         * 
         * Jede Kombination wird als Array in einer ArrayList gespeichert.
         * 
         * Am Ende wird jede Kombination geprueft und die Betriebszeit
         * ermittelt und die Objektvariable der Maschine geschrieben.
         * 
         * Zurueckgegeben wird die Kombination, mit der laengsten Betriebszeit.
         */

        for (int i = 0; i < optimalerAuftrag.length; i++) {

            /* Timetable resetten, bevor ein neuer Auftrag hinzugefuegt wird und sich dort seine Zeit reserviert */
            resetTimetable();

            /* Pruefen, ob der zu holende Auftrag innerhalb der Betriebszeiten liegt */
            if(isValid(optimalerAuftrag[i])) {

                tempAuftrag1 = optimalerAuftrag[i]; // Hole Auftrag
                auftragsKombination = new ArrayList<Auftrag>(); // Initialisiere Array fuer alle moeglichen Kombinationen des geholten Auftrags
                auftragsKombination.add(tempAuftrag1); // Fuege den geholten Auftrag zu den Auftragskombinationen ein
                reserveTimetable(tempAuftrag1); // Zeit fuer den geholten Auftrag im Timetable reservieren

                /* Uebrige Auftraege durchlaufen und pruefen, ob sie zum geholten Auftrag passen */
                for (int j = 0; j < optimalerAuftrag.length; j++) {

                    /* Pruefen, ob der zu holende Auftrag innerhalb der Betriebszeiten liegt */
                    if (isValid(optimalerAuftrag[j])) {

                        /* Pruefen, ob sich die beiden Auftraege ueberlappen */
                        if (!(isIntersected(tempAuftrag1, optimalerAuftrag[j]))) {

                            /* Pruefen, ob der zu holende Auftrag innerhalb eines freien Zeitfensters liegt */
                            if (!(invalidTime(tempAuftrag1, optimalerAuftrag[j]))) {

                                /* tempAuftrag2 ist gueltig und wird zur auftragsKombination hinzugefuegt */
                                tempAuftrag2 = optimalerAuftrag[j];
                                auftragsKombination.add(tempAuftrag2);
                                reserveTimetable(tempAuftrag2); // Timetable mit der Laufzeit von tempAuftrag2 reservieren

                            } // if
                        } // if
                    } // if
                } // for j

                /* Innere for-schleife verlassen und alle Auftrags-Kombinationen zum auftragsArray adden */
                auftragsArray.add(auftragsKombination);

            } // if
        } // for i

        /* Nachdem alle Kombinationen ermittelt worden sind, kann mit Hilfe der
         * getDuration Methode fuer jede Kombination die Betriebszeit ermittelt
         * werden. Die Kombination mit der laengsten Betriebszeit und deren
         * Position im auftragsArray wird gespeichert. */
        for (int i = 0; i < auftragsArray.size(); i++) {
            auftragsKombination = auftragsArray.get(i);

            /* Wenn die aktuelle Betriebsdauer laenger ist, als die alte */
            if(getDuration(auftragsKombination) >= tempDuration) {
                tempDuration = getDuration(auftragsKombination); // Dauer speichern
                tempIndex = i; // Zugehoerigen Index in auftragsArray speichern
            } // if
        } // for

        /* Dauer der Betriebszeit in Minuten an die Maschine uebermitteln */
        this.duration = tempDuration;

        /* Rueckgabe Array anlegen und mit den Auftragen mit der laengsten Betriebszeit aus dem auftragsArray befuellen */
        optimalerAuftrag = new Auftrag[auftragsArray.get(tempIndex).size()];
        for (int i = 0; i < optimalerAuftrag.length; i++) {
            optimalerAuftrag[i] = auftragsArray.get(tempIndex).get(i);
        } // for

        /* Array nach zeitlich aufsteigender Reihenfolge sortieren und zurueckgeben */
        return sortStartTime(optimalerAuftrag);

    } // getOptimum

    /**
     * Sortiert ein Array von Auftraegen nach zeitlich aufsteigender Reihenfolge
     * und gibt dieses zurueck.
     * 
     * @param auftraege     Array aller Auftraege
     * @return              Sortiertes Array ueber Auftraege
     */
    public Auftrag[] sortStartTime(Auftrag[] auftraege) {

        boolean unsortiert = true;
        Auftrag tempAuftrag;

        while (unsortiert) {
            unsortiert = false;

            for (int i = 0; i < auftraege.length - 1; i++) {

                /* Sortiere nach Startzeit */
                if (auftraege[i].getStartTime() > auftraege[i + 1].getStartTime()) {
                    tempAuftrag = auftraege[i];
                    auftraege[i] = auftraege[i + 1];
                    auftraege[i + 1] = tempAuftrag;
                    unsortiert = true;
                } // if

                /* Bei gleicher Startzeit absteigend nach Dauer sortieren */
                else if (auftraege[i].getStartTime() == auftraege[i + 1].getStartTime()) {

                    if (auftraege[i].getLength() < auftraege[i + 1].getLength()) {
                        tempAuftrag = auftraege[i];
                        auftraege[i] = auftraege[i + 1];
                        auftraege[i + 1] = tempAuftrag;
                        unsortiert = true;
                    } // if
                } // else if
            } // for

        } // while

        return auftraege;
    } // sortStartTime

    /**
     * Entfernt aus der Liste aller Auftraege jene, die ausgefuehrt wurden.
     * Gibt ein nach zeitlicher aufsteigender Reihenfolge sortiertes
     * Array von Auftraegen zurueck. 
     * 
     * @param auftraege     Liste aller Auftraege
     * @param executed      Liste der ausgefuehrten Auftraege
     * @return              Array ueber Auftraege
     */
    @Override
    public Auftrag[] excludedJobs(Auftrag[] auftraege, Auftrag[] executed) {

        /* ArrayList zur Ermittlung der exkludierten Auftraege */
        List<Auftrag> tempJobs = new ArrayList<Auftrag>();

        /* Fuer das einfache Entfernen der bearbeiteten Auftraege kommt das auftraege Array in die excludedJobs ArrayList */
        for(Auftrag element : auftraege) {
            tempJobs.add(element);
        } // for

        /* Bearbeitete Auftraege aus der ArrayList entfernen */
        for(Auftrag element : executed) {
            tempJobs.remove(element);
        } // for

        /* Array fuer die Rueckgabe erstellen, mit den exkludierten Auftraegen befuellen, sortieren und zurueckgeben */
        Auftrag[] excludedJobs  = new Auftrag [tempJobs.size()];
        for(int i = 0; i < tempJobs.size(); i++) {
            excludedJobs[i] = tempJobs.get(i); 
        } // for

        return sortStartTime(excludedJobs);
    } // excludedJobs

} // public class Maschine
