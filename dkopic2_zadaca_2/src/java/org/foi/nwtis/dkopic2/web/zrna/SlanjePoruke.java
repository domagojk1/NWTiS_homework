/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.util.Locale;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.foi.nwtis.dkopic2.web.slusaci.SlusacAplikacije;

/**
 * Klasa za slanje mail poruke.
 * @author domagoj
 */
@Named(value = "slanjePoruke")
@RequestScoped
public class SlanjePoruke {

    private String posiljatelj;
    private String primatelj;
    private String predmetPoruke;
    private String tekstPoruke;
    private String tipPoruke;
    private String posluzitelj;
    private String poruka;
    /**
     * Creates a new instance of SlanjePoruke
     */
    public SlanjePoruke() {
    }

    public String getPosiljatelj() {
        return posiljatelj;
    }

    public void setPosiljatelj(String posiljatelj) {
        this.posiljatelj = posiljatelj;
    }

    public String getPrimatelj() {
        return primatelj;
    }

    public void setPrimatelj(String primatelj) {
        this.primatelj = primatelj;
    }

    public String getPredmetPoruke() {
        return predmetPoruke;
    }

    public void setPredmetPoruke(String predmetPoruke) {
        this.predmetPoruke = predmetPoruke;
    }

    public String getTekstPoruke() {
        return tekstPoruke;
    }

    public void setTekstPoruke(String tekstPoruke) {
        this.tekstPoruke = tekstPoruke;
    }

    public String getTipPoruke() {
        return tipPoruke;
    }

    public void setTipPoruke(String tipPoruke) {
        this.tipPoruke = tipPoruke;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getPosluzitelj() {
        return posluzitelj;
    }

    public void setPosluzitelj(String posluzitelj) {
        this.posluzitelj = posluzitelj;
    }
    
    /**
     * Akcija za slanje mail poruke iz forme.
     * @return 
     */
    public String saljiPoruku(){
        try
        {
            setPosluzitelj(SlusacAplikacije.config.dajPostavku("posluzitelj"));
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", posluzitelj);

            Session session = Session.getInstance(properties, null);
            MimeMessage message = new MimeMessage(session);

            Address fromAddress = new InternetAddress(posiljatelj);
            message.setFrom(fromAddress);
            
            Address[] toAddresses = InternetAddress.parse(primatelj);
            message.setRecipients(Message.RecipientType.TO, toAddresses);

            message.setSubject(predmetPoruke);
            message.setText(tekstPoruke);
            
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
                       
            Transport.send(message);
            
            if(locale.equals(Locale.ENGLISH))
            {
                poruka = "Message send succesful.";
            }
            else if(locale.equals(Locale.GERMAN))
            {
                poruka = "Nachricht senden erfolgreich.";
            }
            else
            {
                poruka = "Poruka je uspješno poslana.";
            }
            
            return "OK";
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            poruka = e.getMessage();
            return "ERROR";
        }
    }
}
