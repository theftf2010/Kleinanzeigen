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

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
public class Anzeige implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "anzeige_ids")
    @TableGenerator(name = "anzeige_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Die Aufgabe muss einem Benutzer geordnet werden.")
    private User owner;

    @ManyToOne
    private Category category;

    @Column(length = 50)
    @NotNull(message = "Die Bezeichnung darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Die Bezeichnung muss zwischen 1 und 50 Zeichen lang sein.")
    private String shortText;

    @Lob
    @NotNull
    private String longText;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date erstelldatum;

    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    private Time erstellzeit;
    
    @NotNull(message = "Der Preis darf nicht leer sein")
    @Size(min=1, max=20, message = "Der Preis muss zwischen 1 und 20 Zeichen haben ")
    private String preis;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AnzeigeStatus status = AnzeigeStatus.ANZEIGE;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private AnzeigePreisTyp preistyp = AnzeigePreisTyp.FIXED;
    

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Anzeige() {
    }

    public Anzeige(User owner, Category category, String shortText, String longText, 
            Date erstelldatum, Time erstellzeit, String preis) {
        
        this.owner = owner;
        this.category = category;
        this.shortText = shortText;
        this.longText = longText;
        this.erstelldatum = erstelldatum;
        this.erstellzeit = erstellzeit;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public Date getErstelldatum() {
        return erstelldatum;
    }

    public void setErstelldatum(Date erstelldatum) {
        this.erstelldatum = erstelldatum;
    }

    public Time getErstellzeit() {
        return erstellzeit;
    }

    public void setErstellzeit(Time erstellzeit) {
        this.erstellzeit = erstellzeit;
    }

    public String getPreis() {
        return preis;
    }

    public void setPreis(String preis) {
        this.preis = preis;
    }

    public AnzeigePreisTyp getPreistyp() {
        return preistyp;
    }

    public void setPreistyp(AnzeigePreisTyp preistyp) {
        this.preistyp = preistyp;
    }

    public AnzeigeStatus getStatus() {
        return status;
    }

    public void setStatus(AnzeigeStatus status) {
        this.status = status;
    }
    //</editor-fold>

}
