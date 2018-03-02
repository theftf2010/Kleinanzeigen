/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.kleinanzeigen.ejb;

import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.Category;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.Anzeige;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.AnzeigeStatus;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.AnzeigePreisTyp;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
@RolesAllowed("kleinanzeigen-app-user")
public class AnzeigeBean extends EntityBean<Anzeige, Long> { 
   
    public AnzeigeBean() {
        super(Anzeige.class);
    }
    
    /**
     * Alle Aufgaben eines Benutzers, nach Fälligkeit sortiert zurückliefern.
     * @param username Benutzername
     * @return Alle Aufgaben des Benutzers
     */
    public List<Anzeige> findByUsername(String username) {
        return em.createQuery("SELECT t FROM Anzeige t WHERE t.owner.username = :username ORDER BY t.erstelldatum, t.erstellzeit")
                 .setParameter("username", username)
                 .getResultList();
    }
    
    /**
     * Suche nach Aufgaben anhand ihrer Bezeichnung, Kategorie und Status.
     * 
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier
     * mit der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     * 
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param category Kategorie (optional)
     * @param status Status (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Anzeige> search(String search, Category category, AnzeigeStatus status) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Anzeige t
        CriteriaQuery<Anzeige> query = cb.createQuery(Anzeige.class);
        Root<Anzeige> from = query.from(Anzeige.class);
        query.select(from);

        // ORDER BY erstelldatum, erstellzeit
        query.orderBy(cb.asc(from.get("erstelldatum")), cb.asc(from.get("erstellzeit")));
        
        // WHERE t.shortText LIKE :search
        if (search != null && !search.trim().isEmpty()) {
            query.where(cb.like(from.get("shortText"), "%" + search + "%"));
        }
        
        // WHERE t.category = :category
        if (category != null) {
            query.where(cb.equal(from.get("category"), category));
        }
        
        // WHERE t.status = :status
        if (status != null) {
            query.where(cb.equal(from.get("status"), status));
        }
        
        return em.createQuery(query).getResultList();
    }
}
