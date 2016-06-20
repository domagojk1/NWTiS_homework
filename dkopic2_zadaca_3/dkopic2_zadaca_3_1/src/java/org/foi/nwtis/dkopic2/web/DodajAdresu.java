package org.foi.nwtis.dkopic2.web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.dkopic2.Database;
import org.foi.nwtis.dkopic2.rest.klijenti.GMKlijent;
import org.foi.nwtis.dkopic2.rest.klijenti.OWMKlijent;
import org.foi.nwtis.dkopic2.web.podaci.Lokacija;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPodaci;

/**
 * Prima podatke od index.jsp i vrsi akcije.
 * @author domagoj
 */
@WebServlet(name = "DodajAdresu", urlPatterns = {"/dodajAdresu"})
public class DodajAdresu extends HttpServlet {
 
    private Lokacija location;
    /**
     * Prosljeđuje zahtjeve za HTTP <code>GET</code> and <code>POST</code>
     * metode.
     * Ispisuje na ekran.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) 
        { 
            String output = "";
            
            String address = request.getParameter("adresa");
           
            if(address != null)
            {
                output = getGeoLocation(address);
                
                if(request.getParameter("spremiGP") != null)
                {
                    output += storeGeoLocation(address);
                }
                else if(request.getParameter("dohvatMP") != null)
                {
                    output += getMeteoData();
                }
            }
            else
                output = "<p>Nije unesena adresa.</p>";
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DodajAdresu</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println(output);
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    /**
     * Dohvaća geolokaciju neke adrese.
     * @param address naziv adrese
     * @return output
     */
    private String getGeoLocation(String address) {
        GMKlijent client = new GMKlijent();
        location = client.getGeoLocation(address);
        String output = "<h1>Traženi podaci</h1>";
        output += "<p>Lattitude: " + location.getLatitude() + "\n" + "Longitude: " + location.getLongitude()+ "</p>";
        return output;
    }
    
    /**
     * Pohranjuje geolokaciju neke adrese u bazu.
     * @param address naziv adrese
     * @return output
     */
    private String storeGeoLocation(String address) {
        Database database = new Database();
        String output = "";
        try 
        {
            database.insertDataAdrese(address, location.getLatitude(), location.getLongitude());
            output = "Podaci uspješno uneseni.";
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(DodajAdresu.class.getName()).log(Level.SEVERE, null, ex);
            output = "Došlo je do pogreške/adresa već zapisana.";
        }
        
        return output;
    }
    
    /**
     * Dohvaća vremensku prognozu proslijeđene adrese.
     * @return output
     */
    private String getMeteoData() {
        String output = "";
        OWMKlijent client = new OWMKlijent();
        MeteoPodaci data = client.getRealTimeWeather(location.getLatitude(), location.getLongitude());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        output += "<p><b>Vrijeme: </b>" + data.getWeatherValue() + "</p>";
        output += "<p><b>Temperatura: </b>" + data.getTemperatureValue() + "</p>";
        output += "<p><b>Minimalna temperatura: </b>" + data.getTemperatureMin() + "</p>";
        output += "<p><b>Maksimalna temperatura: </b>" + data.getTemperatureMax() + "</p>";
        output += "<p><b>Jedinica temperature: </b>" + data.getTemperatureUnit() + "</p>";
        output += "<p><b>Vlaga: </b>" + data.getHumidityValue() + "</p>";
        output += "<p><b>Jedinica vlage: </b>" + data.getHumidityUnit() + "</p>";
        output += "<p><b>Pritisak: </b>" + data.getPressureValue() + "</p>";
        output += "<p><b>Jedinica pritiska: </b>" + data.getPressureUnit() + "</p>";
        output += "<p><b>Brzina vjetra: </b>" + data.getWindSpeedValue() + "</p>";
        output += "<p><b>Smjer vjetra: </b>" + data.getWindDirectionValue()+ "</p>";
        output += "<p><b>Vidljivost: </b>" + data.getVisibility() + "</p>";
        output += "<p><b>Izlazak sunca: </b>" + formatter.format(data.getSunRise()) + "</p>";
        output += "<p><b>Zalazak sunca: </b>" + formatter.format(data.getSunSet()) + "</p>";
        output += "<p><b>Zadnja izmjena: </b>" + formatter.format(data.getLastUpdate()) + "</p>";
        return output;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}