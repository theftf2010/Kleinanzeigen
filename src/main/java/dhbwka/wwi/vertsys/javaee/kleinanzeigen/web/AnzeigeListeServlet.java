/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.kleinanzeigen.web;

import dhbwka.wwi.vertsys.javaee.kleinanzeigen.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.ejb.AnzeigeBean;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.Category;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.Anzeige;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.AnzeigeStatus;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Startseite bzw. jede Seite, die eine Liste der Anzeigen
 * zeigt.
 */
@WebServlet(urlPatterns = {"/app/anzeigen/"})
public class AnzeigeListeServlet extends HttpServlet {

    @EJB
    private CategoryBean categoryBean;
    
    @EJB
    private AnzeigeBean anzeigeBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("statuses", AnzeigeStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCategory = request.getParameter("search_category");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Anzeigen suchen
        Category category = null;
        AnzeigeStatus status = null;

        if (searchCategory != null) {
            try {
                category = this.categoryBean.findById(Long.parseLong(searchCategory));
            } catch (NumberFormatException ex) {
                category = null;
            }
        }

        if (searchStatus != null) {
            try {
                status = AnzeigeStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Anzeige> anzeigen = this.anzeigeBean.search(searchText, category, status);
        request.setAttribute("anzeigen", anzeigen);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/anzeige_list.jsp").forward(request, response);
    }
}
