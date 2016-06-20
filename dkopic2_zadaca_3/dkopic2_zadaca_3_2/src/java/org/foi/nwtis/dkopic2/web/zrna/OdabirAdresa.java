/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.zrna;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import org.foi.nwtis.dkopic2.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.dkopic2.ws.serveri.Adresa;

/**
 *
 * @author domagoj
 */
@Named(value = "odabirAdresa")
@SessionScoped
public class OdabirAdresa implements Serializable {
    private List<String> selectedAddresses;
    private List<Adresa> addressList;
    private Map<String, Object> list;
    private boolean buttonSoapAll;
    private boolean buttonOpenWeather;
    private boolean buttonRest;
    
    /**
     * Kreira novu instancu klase OdabirAdresa
     */
    public OdabirAdresa() {
        list = new LinkedHashMap<>();
        selectedAddresses = new ArrayList<>();
        buttonSoapAll = true;
        buttonOpenWeather = true;
        buttonRest = true;
    }

    public List<String> getSelectedAddresses() {
        return selectedAddresses;
    }

    public void setSelectedAddresses(List<String> selectedAddresses) {
        this.selectedAddresses = selectedAddresses;
    }

    public List<Adresa> getAddressList() {
        addressList = MeteoWSKlijent.dajSveAdrese();
        return addressList;
    }

    public void setAddressList(List<Adresa> addressList) {
        this.addressList = addressList;
    }

    public Map<String, Object> getList() {
        addressList = MeteoWSKlijent.dajSveAdrese();
        list = new LinkedHashMap<>();
        
        for (Adresa address : addressList)
        {
            list.put(address.getAdresa(), address.getAdresa());
        }
        
        return list;
    }

    public void setList(Map<String, Object> list) {
        this.list = list;
    }

    public boolean isButtonSoapAll() {
        return buttonSoapAll;
    }

    public void setButtonSoapAll(boolean buttonSoapAll) {
        this.buttonSoapAll = buttonSoapAll;
    }

    public boolean isButtonOpenWeather() {
        return buttonOpenWeather;
    }

    public void setButtonOpenWeather(boolean buttonOpenWeather) {
        this.buttonOpenWeather = buttonOpenWeather;
    }

    public boolean isButtonRest() {
        return buttonRest;
    }

    public void setButtonRest(boolean buttonRest) {
        this.buttonRest = buttonRest;
    }
    
    /**
     * Omogućuje/neomogućuje akcije određenih gumbiju ovisno o broju izabranih adresa.
     * @param event ValueChangeEvent
     */
    public void valueChanged(ValueChangeEvent event) {
        selectedAddresses = (List<String>) event.getNewValue();
        
        int size = selectedAddresses.size();
        setButtonSoapAll(true);
        setButtonOpenWeather(true);
        setButtonRest(true);
        
        if (size == 1)
        {
           setButtonSoapAll(false);
           setButtonOpenWeather(false); 
        }
        
        else if (size > 1)
        {
            setButtonSoapAll(true);
            setButtonOpenWeather(true);
            setButtonRest(false);
        }
    }
}
