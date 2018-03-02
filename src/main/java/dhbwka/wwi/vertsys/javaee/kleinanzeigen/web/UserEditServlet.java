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

import dhbwka.wwi.vertsys.javaee.kleinanzeigen.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.Anzeige;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.AnzeigePreisTyp;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.AnzeigeStatus;
import dhbwka.wwi.vertsys.javaee.kleinanzeigen.jpa.User;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/app/useredit/"})
public class UserEditServlet extends HttpServlet {

    @EJB
    ValidationBean validationBean;

    @EJB
    UserBean userBean;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        User benutzer = this.userBean.getCurrentUser();
        request.setAttribute("benutzer",benutzer);
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/user_edit.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("login_form");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben auslesen
        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("login_username");
        String vorname = request.getParameter("login_vorname");
        String nachname = request.getParameter("login_nachname");
        String telefonnummer = request.getParameter("login_telefonnummer");
        String adresse = request.getParameter("login_adresse");
        String email = request.getParameter("login_email");
        String stadt = request.getParameter("login_stadt");
        String postleitzahl = request.getParameter("login_postleitzahl");

        // Eingaben prüfen
        User benutzer = this.userBean.getCurrentUser();

        //sets
        benutzer.setVorname(vorname);
        benutzer.setNachname(nachname);
        benutzer.setStadt(stadt);
        benutzer.setEmail(email);
        benutzer.setAdresse(adresse);
        benutzer.setPostleitzahl(postleitzahl);
        benutzer.setTelefonnummer(telefonnummer);
        benutzer.setUsername(username);

        List<String> errors = this.validationBean.validate(benutzer);

        //wenn keine Fehler vorliegen wird der User upgedatet <--kleinanzeigen
        if (errors.isEmpty()) {
            this.userBean.update(benutzer);
            response.sendRedirect(WebUtils.appUrl(request, "/app/anzeigen/"));
        }else{
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("login_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }
}
