/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.dkopic2.ejb.eb.Adrese;
import org.foi.nwtis.dkopic2.ejb.eb.Dnevnik;
import org.foi.nwtis.dkopic2.ejb.sb.AdreseFacade;
import org.foi.nwtis.dkopic2.ejb.sb.DnevnikFacade;
import org.foi.nwtis.dkopic2.ejb.sb.MeteoAdresniKlijent;
import org.foi.nwtis.dkopic2.web.podaci.Lokacija;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPrognoza;
import org.foi.nwtis.dkopic2.web.slusaci.SlusacAplikacije;

/**
 *
 * @author domagoj
 */
@Named(value = "odabirAdresaPrognoza")
@SessionScoped
public class OdabirAdresaPrognoza implements Serializable {

    @EJB
    private DnevnikFacade dnevnikFacade;

    @EJB
    private MeteoAdresniKlijent meteoAdresniKlijent;

    @EJB
    private AdreseFacade adreseFacade;
    
    private String novaAdresa;
    private Map<String, Object> aktivneAdrese;
    private List<String> adreseZaDodavanje;
    private Map<String, Object> kandidiraneAdrese;
    private List<String> adreseZaBrisanje;
    private String azuriranaAdresa;
    private String idAzuriraneAdrese;
    private boolean prikazAzuriranjaAdrese;
    private boolean prikazPrognozaAdrese;
    private List<MeteoPrognoza> prognozeVremena;
    private String odabranaAdresa;
    
    private String poruka;
    private Date pocetak;
    private Date kraj;
    
    private String ipAdresa;
    private String URL;
    private String korisnik;
    private Date vrijeme;
    private int trajanje;
    private int status;
    private String akcija;
    
    /**
     * Kreira novu instancu OdabirAdresaPrognoza
     */
    public OdabirAdresaPrognoza() {
        kandidiraneAdrese = new HashMap<>();
        prikazAzuriranjaAdrese = false;
        prikazPrognozaAdrese = false;
    }

    public MeteoAdresniKlijent getMeteoAdresniKlijent() {
        return meteoAdresniKlijent;
    }

    public void setMeteoAdresniKlijent(MeteoAdresniKlijent meteoAdresniKlijent) {
        this.meteoAdresniKlijent = meteoAdresniKlijent;
    }

    public AdreseFacade getAdreseFacade() {
        return adreseFacade;
    }

    public void setAdreseFacade(AdreseFacade adreseFacade) {
        this.adreseFacade = adreseFacade;
    }

    public String getNovaAdresa() {
        return novaAdresa;
    }

    public void setNovaAdresa(String novaAdresa) {
        this.novaAdresa = novaAdresa;
    }
     
    /**
     * Dohvaća sve adrese iz baze i zapisuje ih u listu
     * @return 
     */
    public Map<String, Object> getAktivneAdrese() {
        List<Adrese> adrese = adreseFacade.getAllDesc();
        aktivneAdrese = new LinkedHashMap<>();
        
        for (Adrese a : adrese) 
        {
            boolean postoji = false;
            Iterator<Map.Entry<String, Object>> iterator = kandidiraneAdrese.entrySet().iterator();
            
            while (iterator.hasNext())
            {
                Map.Entry<String, Object> adresaEntry = iterator.next();
                if (adresaEntry.getValue().toString().compareTo(a.getIdadresa().toString()) == 0)
                {
                    postoji = true;
                    break;
                }
            }
            if (!postoji) 
            {
                aktivneAdrese.put(a.getAdresa(), a.getIdadresa().toString());
            }
        }
        
        return aktivneAdrese;
    }

    public void setAktivneAdrese(Map<String, Object> aktivneAdrese) {
        this.aktivneAdrese = aktivneAdrese;
    }

    public List<String> getAdreseZaDodavanje() {
        return adreseZaDodavanje;
    }

    public void setAdreseZaDodavanje(List<String> adreseZaDodavanje) {
        this.adreseZaDodavanje = adreseZaDodavanje;
    }

    public Map<String, Object> getKandidiraneAdrese() {
        return kandidiraneAdrese;
    }

    public void setKandidiraneAdrese(Map<String, Object> kandidiraneAdrese) {
        this.kandidiraneAdrese = kandidiraneAdrese;
    }

    public List<String> getAdreseZaBrisanje() {
        return adreseZaBrisanje;
    }

    public void setAdreseZaBrisanje(List<String> adreseZaBrisanje) {
        this.adreseZaBrisanje = adreseZaBrisanje;
    }

    public String getAzuriranaAdresa() {
        return azuriranaAdresa;
    }

    public void setAzuriranaAdresa(String azuriranaAdresa) {
        this.azuriranaAdresa = azuriranaAdresa;
    }

    public String getIdAzuriraneAdrese() {
        return idAzuriraneAdrese;
    }

    public void setIdAzuriraneAdrese(String idAzuriraneAdrese) {
        this.idAzuriraneAdrese = idAzuriraneAdrese;
    }

    public boolean isPrikazAzuriranjaAdrese() {
        return prikazAzuriranjaAdrese;
    }

    public void setPrikazAzuriranjaAdrese(boolean prikazAzuriranjaAdrese) {
        this.prikazAzuriranjaAdrese = prikazAzuriranjaAdrese;
    }

    public boolean isPrikazPrognozaAdrese() {
        return prikazPrognozaAdrese;
    }

    public void setPrikazPrognozaAdrese(boolean prikazPrognozaAdrese) {
        this.prikazPrognozaAdrese = prikazPrognozaAdrese;
    }

    public List<MeteoPrognoza> getPrognozeVremena() {
        return prognozeVremena;
    }

    public void setPrognozeVremena(List<MeteoPrognoza> prognozeVremena) {
        this.prognozeVremena = prognozeVremena;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
      
    /**
     * Dodaje novu adresu, ukoliko već postoji izbacuje poruku
     * @return 
     */
    public String dodajNovuAdresu() {
        setPocetak();
        poruka = "";
        
        if(adreseFacade.getByName(novaAdresa) != null)
        {
            poruka = "Adresa već postoji.";
        }
        else
        {
            Lokacija lokacija = meteoAdresniKlijent.getLocation(novaAdresa);
            Adrese dodanaAdresa = new Adrese(Integer.BYTES, novaAdresa, lokacija.getLatitude(), lokacija.getLongitude());
            adreseFacade.create(dodanaAdresa);
        }
        
        setKraj();
        spremiDnevnik();
        novaAdresa = "";
        
        return "";
    }
    
    /**
     * Obavlja akciju premještanja adresi u listu kandidiranih adresa
     * @return 
     */
    public String preuzmiAdrese() {
        setPocetak();
        poruka = "";
        
        if(adreseZaDodavanje.size() < 1)
        {
            poruka = "Treba biti odabrana barem jedna adresa.";
        }
        else
        {
            for (String adresa : adreseZaDodavanje) 
            {
                Iterator<Map.Entry<String, Object>> iterator = aktivneAdrese.entrySet().iterator();

                while (iterator.hasNext())
                {
                    Map.Entry<String, Object> adresaEntry = iterator.next();

                    if (adresaEntry.getValue().toString().compareTo(adresa) == 0) 
                    {
                        kandidiraneAdrese.put(adresaEntry.getKey(), adresa);
                        iterator.remove();
                    }
                }
            }
        }
        setKraj();
        spremiDnevnik();
     
        return "";
    }
    
    /**
     * Obavlja akciju vraćanja adresi iz popisa kandidiranih u početnu listu
     * @return 
     */
    public String vratiAdrese() {
        setPocetak();
        poruka = "";
        
        for (String adresa : adreseZaBrisanje)
        {
            Iterator<Map.Entry<String, Object>> iterator = kandidiraneAdrese.entrySet().iterator();
            
            while (iterator.hasNext())
            {
                Map.Entry<String, Object> adresaEntry = iterator.next();
                
                if (adresaEntry.getValue().toString().compareTo(adresa) == 0) 
                {
                    aktivneAdrese.put(adresaEntry.getKey(), adresa);
                    iterator.remove();
                }
            }
        }
        setKraj();
        spremiDnevnik();
        
        return "";
    }
    
    /**
     * Ažurira postojeću adresu
     * @return 
     */
    public String upisiAdresu() {
        setPocetak();
        poruka = "";
        
        Lokacija l = meteoAdresniKlijent.getLocation(azuriranaAdresa);
        Adrese ispravljenaAdresa = new Adrese(Integer.parseInt(idAzuriraneAdrese), azuriranaAdresa, l.getLatitude(), l.getLongitude());
        Adrese adrese = null;
        adrese = adreseFacade.getByName(azuriranaAdresa);
        
        if(adrese != null)
        {
            if (adrese.getAdresa().equals(odabranaAdresa))
            {
                adreseFacade.edit(ispravljenaAdresa);
                prikazAzuriranjaAdrese = false;
            }
            else if(!adrese.getAdresa().equals(odabranaAdresa))
            {
                poruka = "Adresa već postoji.";
            }
        }
        else
        {
            adreseFacade.edit(ispravljenaAdresa);
            prikazAzuriranjaAdrese = false;
        }
        
        setKraj();
        spremiDnevnik();
        
        return "";
    }
    
    /**
     * Dohvaća id adrese za ažuriranje i prikazuje okvir za ažuriranje
     * @return 
     */
    public String azurirajAdresu() {
        setPocetak();
        poruka = "";
        
        if(adreseZaDodavanje.size() != 1) 
        {
            poruka = "Treba biti odabrana jedna adresa iz popisa aktivnih.";
        }
        else 
        {
            idAzuriraneAdrese = adreseZaDodavanje.get(0);
            Iterator<Map.Entry<String, Object>> iterator = aktivneAdrese.entrySet().iterator();
            
            while (iterator.hasNext()) 
            {
                Map.Entry<String, Object> adresaEntry = iterator.next();
                
                if (adresaEntry.getValue().toString().compareTo(idAzuriraneAdrese) == 0)
                {
                    prikazAzuriranjaAdrese = true;
                    odabranaAdresa = adresaEntry.getKey();
                    azuriranaAdresa = odabranaAdresa;
                    break;
                }
            }
        }
        
        setKraj();
        spremiDnevnik();
        
        return "";
    }
    
    /**
     * Dohvaća prognozu za idućih 5 dana putem API- za odabranu adresu
     * @return 
     */
    public String dohvatiPrognoze() {
        setPocetak();
        
        poruka = "";
        
        if(adreseZaBrisanje.size() != 1)
        {
            poruka = "Treba biti odabrana jedna adresa iz popisa odabranih.";
        }
        else
        {
            String id = adreseZaBrisanje.get(0);
            Iterator<Map.Entry<String, Object>> iterator = kandidiraneAdrese.entrySet().iterator();
            
            while (iterator.hasNext()) 
            {
                Map.Entry<String, Object> adresaEntry = iterator.next();
                
                if (adresaEntry.getValue().toString().compareTo(id) == 0)
                {
                    prikazPrognozaAdrese = true;  
                    meteoAdresniKlijent.postaviKorisnickePodatke(SlusacAplikacije.config.dajPostavku("APPID"));
                    prognozeVremena = new ArrayList<>(meteoAdresniKlijent.getMeteoForecast(adresaEntry.getKey()));
                    break;
                }
            }        
        }
                
        setKraj();
        spremiDnevnik();
        
        return "";
    }
    
    /**
     * Zatvara okvir za prognoze
     * @return 
     */
    public String zatvoriPrognoze() {
        setPocetak();
        
        poruka = "";
        prognozeVremena.clear();
        prikazPrognozaAdrese = false;
        
        setKraj();
        spremiDnevnik();
        
        return "";
    }
   
    /**
     * Postavlja pocetak akcije
     */
    private void setPocetak() {
        pocetak = new Date();
    }

    /**
     * Postavlja kraj akcije i računa vrijeme akcije u ms
     */
    private void setKraj() {
        kraj = new Date();
        
        long sec = (kraj.getTime() - pocetak.getTime());
        trajanje = (int)sec;
    }
    
    /**
     * Dohvaća id odabrane akcije (commandButton)
     * @param event 
     */
    public void dohvatiAkciju(ActionEvent event) {
        akcija = event.getComponent().getId(); 
    }
    
    /**
     * Sprema podatke u dnevnik 
     */
    private void spremiDnevnik() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        URL = request.getRequestURL().toString() + "?metoda=" + request.getMethod() + "&" + akcija;
        
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        
        if (ipAddress == null)
        {
            ipAdresa = request.getRemoteAddr();
        }
        
        //korisnik = request.getRemoteUser();
        korisnik = "dkopic2";
        vrijeme = new Date();
        
        if(poruka.equals("")) 
            status = 1;
        else 
            status = 0;
        
        Dnevnik dnevnik = new Dnevnik(Integer.BYTES, korisnik, URL, ipAdresa, vrijeme, trajanje, status);
        dnevnikFacade.create(dnevnik);
    }
 
}
