package aufg_4_1_1;

public interface Maschine {

    
    /**
     * berechnet die optimale Kombination aus allen Auftraegen, 
     * damit die Maschine eine maximale Belastungszeit hat
     * dafuer werden zwei Schleifen benutzt:
     * die erste nimmt den Auftrag.
     * Dieser Auftrag wird dann  mittels der zweiten Schleife
     * mit jedem anderen Auftrag versucht zu kombinieren.
     * Fuer jede gefunden Kombination wird die Dauer gespeichert,
     * um letztendlich alle Dauern miteinander zu vergleichen und die 
     * laengste zurueckzugeben.
     *
     * @param auftraege alle Autraege
     * @return optimale Kombination
     */
    Auftrag[] getOptimum(Auftrag[] auftraege);

    /**
     * ermittelt die Auftraege, die von der Maschine nicht ausgefuehrt wurden.
     *
     * @param auftraege alle Auftraege
     * @param optimierterAuftrag optimale Kombination
     * @return Array, das alle Auftraege enthaelt, die nicht ausgefuehrt wurden
     */
    Auftrag[] excludedJobs(Auftrag[] auftraege, Auftrag[] optimierterAuftrag);
    
    /**
     * returned die Belastungszeit der Maschine
     * f�r die optimale Kombination an Auftr�gen
     * (Belastungszeit ist maximal).
     *
     * @return Belastungszeit
     */
    int getDuration();

}
