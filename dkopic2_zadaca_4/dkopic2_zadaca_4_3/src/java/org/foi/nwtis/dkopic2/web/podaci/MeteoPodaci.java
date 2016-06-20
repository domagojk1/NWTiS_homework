/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.web.podaci;

import java.util.Date;

/**
 * Klasa za spremanje podataka o vremenskoj prognozi.
 * @author domagoj
 */
public class MeteoPodaci {
    private String address;
    private Date sunRise;
    private Date sunSet;

    private Double temperatureValue;
    private Double temperatureMin;
    private Double temperatureMax;
    private String temperatureUnit;

    private Double humidityValue;
    private String humidityUnit;

    private Double pressureValue;
    private String pressureUnit;

    private Double windSpeedValue;
    private String windSpeedName;
    private Double windDirectionValue;
    private String windDirectionCode;
    private String windDirectionName;

    private int cloudsValue;
    private String cloudsName;

    private String visibility;

    private Double precipitationValue;
    private String precipitationMode;
    private String precipitationUnit;

    private int weatherNumber;
    private String weatherValue;
    private String weatherIcon;
    private Date lastUpdate;

    /**
     * Kreira novu instancu klase 'MeteoPodaci'.
     */
    public MeteoPodaci() {
    }

    public Date getSunRise() {
        return sunRise;
    }

    public void setSunRise(Date sunRise) {
        this.sunRise = sunRise;
    }

    public Date getSunSet() {
        return sunSet;
    }

    public void setSunSet(Date sunSet) {
        this.sunSet = sunSet;
    }

    public Double getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(Double temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public Double getHumidityValue() {
        return humidityValue;
    }

    public void setHumidityValue(Double humidityValue) {
        this.humidityValue = humidityValue;
    }

    public String getHumidityUnit() {
        return humidityUnit;
    }

    public void setHumidityUnit(String humidityUnit) {
        this.humidityUnit = humidityUnit;
    }

    public Double getPressureValue() {
        return pressureValue;
    }

    public void setPressureValue(Double pressureValue) {
        this.pressureValue = pressureValue;
    }

    public String getPressureUnit() {
        return pressureUnit;
    }

    public void setPressureUnit(String pressureUnit) {
        this.pressureUnit = pressureUnit;
    }

    public Double getWindSpeedValue() {
        return windSpeedValue;
    }

    public void setWindSpeedValue(Double windSpeedValue) {
        this.windSpeedValue = windSpeedValue;
    }

    public String getWindSpeedName() {
        return windSpeedName;
    }

    public void setWindSpeedName(String windSpeedName) {
        this.windSpeedName = windSpeedName;
    }

    public Double getWindDirectionValue() {
        return windDirectionValue;
    }

    public void setWindDirectionValue(Double windDirectionValue) {
        this.windDirectionValue = windDirectionValue;
    }

    public String getWindDirectionCode() {
        return windDirectionCode;
    }

    public void setWindDirectionCode(String windDirectionCode) {
        this.windDirectionCode = windDirectionCode;
    }

    public String getWindDirectionName() {
        return windDirectionName;
    }

    public void setWindDirectionName(String windDirectionName) {
        this.windDirectionName = windDirectionName;
    }

    public int getCloudsValue() {
        return cloudsValue;
    }

    public void setCloudsValue(int cloudsValue) {
        this.cloudsValue = cloudsValue;
    }

    public String getCloudsName() {
        return cloudsName;
    }

    public void setCloudsName(String cloudsName) {
        this.cloudsName = cloudsName;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Double getPrecipitationValue() {
        return precipitationValue;
    }

    public void setPrecipitationValue(Double precipitationValue) {
        this.precipitationValue = precipitationValue;
    }

    public String getPrecipitationMode() {
        return precipitationMode;
    }

    public void setPrecipitationMode(String precipitationMode) {
        this.precipitationMode = precipitationMode;
    }

    public String getPrecipitationUnit() {
        return precipitationUnit;
    }

    public void setPrecipitationUnit(String precipitationUnit) {
        this.precipitationUnit = precipitationUnit;
    }

    public int getWeatherNumber() {
        return weatherNumber;
    }

    public void setWeatherNumber(int weatherNumber) {
        this.weatherNumber = weatherNumber;
    }

    public String getWeatherValue() {
        return weatherValue;
    }

    public void setWeatherValue(String weatherValue) {
        this.weatherValue = weatherValue;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
   
}
