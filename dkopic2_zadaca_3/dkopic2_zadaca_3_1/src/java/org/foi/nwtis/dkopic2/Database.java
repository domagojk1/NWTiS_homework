package org.foi.nwtis.dkopic2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.foi.nwtis.dkopic2.web.podaci.Adresa;
import org.foi.nwtis.dkopic2.web.podaci.Lokacija;
import org.foi.nwtis.dkopic2.web.podaci.MeteoPodaci;
import org.foi.nwtis.dkopic2.web.slusaci.SlusacAplikacije;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Klasa za komunikaciju sa bazom podataka.
 * @author domagoj
 */
public class Database {
    private String url = SlusacAplikacije.dbConfig.dajPostavku("server.database") + SlusacAplikacije.dbConfig.dajPostavku("user.database");
    private String user = SlusacAplikacije.dbConfig.dajPostavku("user.username");
    private String password = SlusacAplikacije.dbConfig.dajPostavku("user.password");
    private String client = SlusacAplikacije.dbConfig.dajPostavku("driver.database.derby");
    
    /*
    private Connection openConnection() {
        Connection connection = null;
        
        try 
        {
            Class.forName(client);
            connection = DriverManager.getConnection(url, user, password);
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
       
        return connection;
    }
    */
    
    /**
     * Upisuje podatke u tablicu 'adrese'.
     * @param address naziv adrese
     * @param lattitude geografska širina
     * @param longitude geografska dužina
     * @throws SQLException 
     */
    public void insertDataAdrese(String address, String lattitude, String longitude) throws SQLException {
        String insertString = "INSERT INTO ADRESE (ADRESA, LATITUDE, LONGITUDE) VALUES (?,?,?)";
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        
        connection = SlusacAplikacije.dataSource.getConnection();

        if(connection != null)
        {
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, address);
            preparedStatement.setString(2, lattitude);
            preparedStatement.setString(3, longitude);
            preparedStatement.executeUpdate();
            
            System.out.println("Upisana adresa " + address + ".");
        }
        
        if (preparedStatement != null)
            preparedStatement.close();

        if (connection != null)
            connection.close();
        
    }
    
    /**
     * Upisuje podatke u tablicu 'meteo'.
     * @param data MeteoPodaci
     * @param address Adresa
     * @throws SQLException 
     */
    public void insertDataMeteo(MeteoPodaci data, Adresa address) throws SQLException {
        String insertString = "INSERT INTO METEO (IDADRESA, ADRESASTANICE, LATITUDE, LONGITUDE, VRIJEME, VRIJEMEOPIS, TEMP, TEMPMIN, TEMPMAX, VLAGA, TLAK, VJETAR, VJETARSMJER, PREUZETO)" + 
                              " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        
        connection = SlusacAplikacije.dataSource.getConnection();
        
        if (connection != null)
        {
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setInt(1, address.getIdadresa());
            //TODO: pitaj za atribute
            preparedStatement.setString(2, address.getAdresa()); 
            preparedStatement.setString(3, address.getGeoloc().getLatitude());
            preparedStatement.setString(4, address.getGeoloc().getLongitude());
            preparedStatement.setString(5, String.valueOf(data.getWeatherNumber()));
            preparedStatement.setString(6, data.getWeatherValue());
            preparedStatement.setDouble(7, data.getTemperatureValue());
            preparedStatement.setDouble(8, data.getTemperatureMin());
            preparedStatement.setDouble(9, data.getTemperatureMax());
            preparedStatement.setDouble(10, data.getHumidityValue());
            preparedStatement.setDouble(11, data.getPressureValue());
            preparedStatement.setDouble(12, data.getWindSpeedValue());
            preparedStatement.setDouble(13, data.getWindDirectionValue());
            preparedStatement.setTimestamp(14, new Timestamp(data.getLastUpdate().getTime()));

            preparedStatement.executeUpdate();
        }
       
        if (preparedStatement != null)
            preparedStatement.close();

        if (connection != null)
            connection.close();
        
    }
    
    /**
     * Dohvaća sve adrese iz tablice 'adrese'.
     * @return ArrayList objekata klase Adresa
     * @throws SQLException 
     */
    public ArrayList<Adresa> getAddresses() throws SQLException {
        ArrayList<Adresa> addresses = null;
        String query = "SELECT * FROM ADRESE";
        Statement statement = null;
        Connection connection = null;

        connection = SlusacAplikacije.dataSource.getConnection();
            
        if(connection != null)
        {
            addresses = new ArrayList<>();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next())
            {
                Adresa adresa = new Adresa();
                adresa.setIdadresa(resultSet.getInt("IDADRESA"));
                Lokacija lokacija = new Lokacija();
                lokacija.setLatitude(resultSet.getString("LATITUDE"));
                lokacija.setLongitude(resultSet.getString("LONGITUDE"));
                adresa.setGeoloc(lokacija);
                adresa.setAdresa(resultSet.getString("ADRESA"));
                addresses.add(adresa);
            }
        }

        if (statement != null)
            statement.close();

        if (connection != null)
            connection.close();

        return addresses;
    }
    
    /**
     * Dohvaća zadnje zapisane podatke meteo podatke o nekoj adresi.
     * @param address naziv adrese
     * @return MeteoPodaci
     * @throws SQLException 
     */
    public MeteoPodaci getLastMeteo(String address) throws SQLException {
        MeteoPodaci meteoData = null;
        String query = "SELECT * FROM METEO JOIN ADRESE USING(IDADRESA) WHERE ADRESA = ? ORDER BY PREUZETO DESC";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        connection = SlusacAplikacije.dataSource.getConnection();
            
        if (connection != null)
        {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, address);
            preparedStatement.setMaxRows(1);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                meteoData = new MeteoPodaci();
                meteoData.setTemperatureValue(resultSet.getDouble("TEMP"));
                meteoData.setTemperatureMin(resultSet.getDouble("TEMPMIN"));
                meteoData.setTemperatureMax(resultSet.getDouble("TEMPMAX"));
                meteoData.setWeatherNumber(Integer.parseInt(resultSet.getString("VRIJEME")));
                meteoData.setWeatherValue(resultSet.getString("VRIJEMEOPIS"));
                meteoData.setHumidityValue(resultSet.getDouble("TLAK"));
                meteoData.setWindSpeedValue(resultSet.getDouble("VJETAR"));
                meteoData.setWindDirectionValue(resultSet.getDouble("VJETARSMJER"));
                meteoData.setLastUpdate(resultSet.getTimestamp("PREUZETO"));
            }
        }
        
        if (preparedStatement != null)
            preparedStatement.close();

        if (connection != null)
            connection.close();

        return meteoData;
    }
    
    /**
     * Dohvaća meteo podatke prema ID-u adrese.
     * @param id ID adrese u tablici 'adrese'
     * @return JSON String
     * @throws SQLException
     * @throws JSONException 
     */
    public String getMeteo(int id) throws SQLException, JSONException {
        String query = "SELECT * FROM METEO, ADRESE  WHERE ADRESE.IDADRESA = METEO.IDADRESA AND ADRESE.IDADRESA = ? ORDER BY PREUZETO DESC";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String response = null;
       
        connection = SlusacAplikacije.dataSource.getConnection();

        if (connection != null)
        {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setMaxRows(1);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                JSONArray array = new JSONArray();
                JSONObject object = new JSONObject();
                object.put("id", resultSet.getInt("IDADRESA"));
                object.put("adresa", resultSet.getString("ADRESA"));
                object.put("latitude", resultSet.getString("LATITUDE"));
                object.put("longitude", resultSet.getString("LONGITUDE"));
                object.put("stanica", resultSet.getString("ADRESASTANICE"));
                object.put("vrijeme", resultSet.getString("VRIJEMEOPIS"));
                object.put("temperatura", resultSet.getDouble("TEMP"));
                object.put("temperaturamin", resultSet.getDouble("TEMPMIN"));
                object.put("temperaturamax", resultSet.getDouble("TEMPMAX"));
                object.put("vlaga", resultSet.getDouble("VLAGA"));
                object.put("vjetar", resultSet.getDouble("VJETAR"));
                object.put("vjetarsmjer", resultSet.getDouble("VJETARSMJER"));
                object.put("preuzeto", resultSet.getTimestamp("PREUZETO"));
                array.put(object);

                response = array.toString();
            }
        }
        
       if (preparedStatement != null)
                preparedStatement.close();
            
        if (connection != null)
            connection.close();
        
        return response;
    }
    
    /**
     * Dohvaća sve meteo podatke iz tablice 'meteo' prema nekoj adresi.
     * @param address naziv adrese
     * @return ArrayList objekata klase MeteoPodaci
     * @throws SQLException 
     */
    public ArrayList<MeteoPodaci> getAllMeteo(String address) throws SQLException {
        ArrayList<MeteoPodaci> meteoList = null;
        String query = "SELECT * FROM METEO JOIN ADRESE USING(IDADRESA) WHERE ADRESA = ?";
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        
        meteoList = new ArrayList<>();
        connection = SlusacAplikacije.dataSource.getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, address);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next())
        {
            MeteoPodaci meteoData = new MeteoPodaci();
            meteoData.setTemperatureValue(resultSet.getDouble("TEMP"));
            meteoData.setTemperatureMin(resultSet.getDouble("TEMPMIN"));
            meteoData.setTemperatureMax(resultSet.getDouble("TEMPMAX"));
            meteoData.setWeatherNumber(Integer.parseInt(resultSet.getString("VRIJEME")));
            meteoData.setWeatherValue(resultSet.getString("VRIJEMEOPIS"));
            meteoData.setHumidityValue(resultSet.getDouble("TLAK"));
            meteoData.setWindSpeedValue(resultSet.getDouble("VJETAR"));
            meteoData.setWindDirectionValue(resultSet.getDouble("VJETARSMJER"));
            meteoData.setLastUpdate(resultSet.getTimestamp("PREUZETO"));
            meteoList.add(meteoData);
        }
    
        if (preparedStatement != null)
            preparedStatement.close();
        if (connection != null)
            connection.close();

        return meteoList;
    }
}
