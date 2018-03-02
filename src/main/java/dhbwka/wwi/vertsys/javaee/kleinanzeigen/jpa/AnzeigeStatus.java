/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa;

/**
 * Statuswerte einer Anzeige.
 */
public enum AnzeigeStatus {
    ANZEIGE, SEEKER;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case ANZEIGE:
                return "Bieter";
            case SEEKER:
                return "Sucher";
            default:
                return this.toString();
        }
    }
}
