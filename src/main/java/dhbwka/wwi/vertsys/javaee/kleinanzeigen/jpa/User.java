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
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Datenbankklasse für einen Benutzer.
 */
@Entity
@Table(name = "KLEINANZEIGEN_USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

//Neue Fehlermeldungen bei flascher Edit Eingabe
    @Id
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Der Benutzername muss zwischen 5 und 64 Zeichen lang sein.")
    @NotNull(message = "Der Benutzername darf nicht leer sein.")
    private String username;

    public class Password {

        @Size(min = 6, max = 64, message = "Das Passwort muss zwischen sechs und 64 Zeichen lang sein.")
        public String password = "";
    }

    @Column(name = "NACHNAME", length = 64)
    @Size(min = 2, max = 64, message = "Der Nachname muss zwischen 2 und 64 Zeichen haben!")
    @NotNull(message = "Es muss ein Nachname eingegeben werden ")
    public String nachname;

    @Column(name = "VORNAME", length = 64)
    @Size(min = 2, max = 64, message = "Der Vorname muss zwischen 2 und 64 Zeichen haben!")
    @NotNull(message = "Es muss ein Vorname eingegeben werden ")
    public String vorname;

    @Column(name = "TELEFONNUMMER", length = 64)
    @Size(min = 5, max = 64, message = "Die Telefonnummer muss zwischen 5 und 64 Zeichen haben!")
    @NotNull(message = "Es muss eine Telefonnummer eingegeben werden ")
    public String telefonnummer;

    @Column(name = "EMAIL", length = 64)
    @Size(min = 5, max = 64, message = "Die E-Mail Adresse muss zwischen 5 und 64 Zeichen haben!")
    @NotNull(message = "Es muss eine E-Mail Adresse eingegeben werden ")
    @Pattern(regexp = "^\\w+@\\w+\\..{2,3}(.{2,3})?$", message = "Die E-Mail muss nach der Notation xxx@xxx.xx sein")
    public String email;

    @Column(name = "ADRESSE", length = 64)
    @Size(min = 5, max = 64, message = "Die Adresse muss zwischen 5 und 64 Zeichen haben!")
    @NotNull(message = "Es muss eine Adresse eingegeben werden ")
    public String adresse;

    @Column(name = "POSTLEITZAHL", length = 64)
    @Size(min = 5, max = 64, message = "Die Postleitzahl muss genau 5 Zeichen haben!")
    @NotNull(message = "Es muss eine Postleitzahl eingegeben werden ")
    public String postleitzahl;

    @Column(name = "STADT", length = 64)
    @Size(min = 5, max = 64, message = "Die Stadt muss zwischen 5 und 64 Zeichen haben!")
    @NotNull(message = "Es muss eine Stadt eingegeben werden ")
    public String stadt;

    @Transient
    private final Password password = new Password();

    @Column(name = "PASSWORD_HASH", length = 64)
    @NotNull(message = "Das Passwort darf nicht leer sein.")
    private String passwordHash;

    @ElementCollection
    @CollectionTable(
            name = "KLEINANZEIGEN_USER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
    @Column(name = "GROUPNAME")
    List<String> groups = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Anzeige> anzeigen = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public User() {
    }

    public User(String username, String password,
            String vorname, String nachname, String telefonnummer,
            String email, String adresse, String postleitzahl, String stadt) {

        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
        this.nachname = nachname;
        this.vorname = vorname;
        this.email = email;
        this.telefonnummer = telefonnummer;
        this.postleitzahl = postleitzahl;
        this.stadt = stadt;
        this.adresse = adresse;

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getStadt() {
        return stadt;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public List<Anzeige> getAnzeigen() {
        return anzeigen;
    }

    public void setAnzeigen(List<Anzeige> anzeigen) {
        this.anzeigen = anzeigen;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Passwort setzen und prüfen">
    /**
     * Berechnet der Hash-Wert zu einem Passwort.
     *
     * @param password Passwort
     * @return Hash-Wert
     */
    private String hashPassword(String password) {
        byte[] hash;

        if (password == null) {
            password = "";
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            hash = "!".getBytes(StandardCharsets.UTF_8);
        }

        BigInteger bigInt = new BigInteger(1, hash);
        return bigInt.toString(16);
    }

    /**
     * Berechnet einen Hashwert aus dem übergebenen Passwort und legt ihn im
     * Feld passwordHash ab. Somit wird das Passwort niemals als Klartext
     * gespeichert.
     *
     * Gleichzeitig wird das Passwort im nicht gespeicherten Feld password
     * abgelegt, um durch die Bean Validation Annotationen überprüft werden zu
     * können.
     *
     * @param password Neues Passwort
     */
    public void setPassword(String password) {
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }

    /**
     * Nur für die Validierung bei einer Passwortänderung!
     *
     * @return Neues, beim Speichern gesetztes Passwort
     */
    public Password getPassword() {
        return this.password;
    }

    /**
     * Prüft, ob das übergebene Passwort korrekt ist.
     *
     * @param password Zu prüfendes Passwort
     * @return true wenn das Passwort stimmt sonst false
     */
    public boolean checkPassword(String password) {
        return this.passwordHash.equals(this.hashPassword(password));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Zuordnung zu Benutzergruppen">
    /**
     * @return Eine unveränderliche Liste aller Benutzergruppen
     */
    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        this.groups.forEach((groupname) -> {
            groupsCopy.add(groupname);
        });

        return groupsCopy;
    }

    /**
     * Fügt den Benutzer einer weiteren Benutzergruppe hinzu.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void addToGroup(String groupname) {
        if (!this.groups.contains(groupname)) {
            this.groups.add(groupname);
        }
    }

    /**
     * Entfernt den Benutzer aus der übergebenen Benutzergruppe.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void removeFromGroup(String groupname) {
        this.groups.remove(groupname);
    }
    //</editor-fold>

}
