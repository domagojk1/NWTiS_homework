/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.foi.nwtis.dkopic2.ejb.eb.Dnevnik;
import org.foi.nwtis.dkopic2.ejb.sb.DnevnikFacade;

/**
 *
 * @author domagoj
 */
@Named(value = "pregledDnevnika")
@SessionScoped
public class PregledDnevnika implements Serializable {

    @EJB
    private DnevnikFacade dnevnikFacade;
    private List<Dnevnik> dnevnikList;
    
    private String vrijemeOd;
    private String vrijemeDo;
    private String filterIpAdresa;
    private String filterTrajanje;
    private String filterStatus;
    
    private String poruka;
    
    /**
     * Kreira novu instancu PregledDnevnika
     */
    public PregledDnevnika() { 
    }

    public List<Dnevnik> getDnevnikList() {     
       return dnevnikList;
    }

    public void setDnevnikList(List<Dnevnik> dnevnikList) {
        this.dnevnikList = dnevnikList;
    }

    public DnevnikFacade getDnevnikFacade() {
        return dnevnikFacade;
    }

    public void setDnevnikFacade(DnevnikFacade dnevnikFacade) {
        this.dnevnikFacade = dnevnikFacade;
    }

    public String getVrijemeOd() {
        return vrijemeOd;
    }

    public void setVrijemeOd(String vrijemeOd) {
        this.vrijemeOd = vrijemeOd;
    }

    public String getVrijemeDo() {
        return vrijemeDo;
    }

    public void setVrijemeDo(String vrijemeDo) {
        this.vrijemeDo = vrijemeDo;
    }

    public String getFilterIpAdresa() {
        return filterIpAdresa;
    }

    public void setFilterIpAdresa(String filterIpAdresa) {
        this.filterIpAdresa = filterIpAdresa;
    }

    public String getFilterTrajanje() {
        return filterTrajanje;
    }

    public void setFilterTrajanje(String filterTrajanje) {
        this.filterTrajanje = filterTrajanje;
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
   
    /**
     * Dohvaća sve dostupne podatke iz dnevnika u dnevnikList
     */
    @PostConstruct
    private void postaviPodatke() {
        dnevnikList = new ArrayList<>(dnevnikFacade.findAll()); 
    }
    
    /**
     * Postavlja vrijednosti varijabli za filtere na prazan String
     */
    public void reset() {
        vrijemeOd = vrijemeDo = filterIpAdresa = filterTrajanje = filterStatus = "";
    }
    
    /**
     * Provodi filtriranje podataka iz dnevnika prema unesenim elementima
     * @return 
     */
    public String filtriraj() {
        poruka = "";
        dnevnikFacade.initialize();
        
        if (!vrijemeOd.isEmpty() && !vrijemeDo.isEmpty())
        {
            try 
            {
                dnevnikFacade.filterByTime(vrijemeOd, vrijemeDo);
            } 
            catch (ParseException ex) {
                poruka = "Datum nije ispravnog formata (dd-MM-yyyy).";
            }
        }
        else if (!vrijemeOd.isEmpty())
        {
            try 
            {
                dnevnikFacade.filterByStart(vrijemeOd);
            } 
            catch (ParseException ex) {
                poruka = "Datum nije ispravnog formata (dd-MM-yyyy).";
            }
        }
        else if (!vrijemeDo.isEmpty())
        {
            try 
            {
                dnevnikFacade.filterByEnd(vrijemeDo);
            } 
            catch (ParseException ex) {
                poruka = "Datum nije ispravnog formata (dd-MM-yyyy).";
            }
        }
        
        if (!filterIpAdresa.isEmpty())
            dnevnikFacade.filterByIp(filterIpAdresa);
        
        if (!filterTrajanje.isEmpty())
            dnevnikFacade.filterBySeconds(filterTrajanje);
        
        if (!filterStatus.isEmpty())
            dnevnikFacade.filterByStatus(filterStatus);
        
        dnevnikList = dnevnikFacade.getResult();
        
        reset();
        return "";
    }
}
