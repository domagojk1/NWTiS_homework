<%-- 
    Document   : index
    Created on : Apr 30, 2016, 10:33:10 PM
    Author     : domagoj
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Zadaća 3</title>
    </head>
    <body>
        <form action="dodajAdresu" method="POST">
        <label for="adresa">Adresa</label>
        <input name="adresa" id="adresa" size="100" maxlength="254"><br>
        <input type="submit" name="dohvatGP" value=" Dohvati geo podatke "><br>
        <input type="submit" name="spremiGP" value=" Spremi geo podatke "><br>
        <input type="submit" name="dohvatMP" value=" Dohvati meteo podatke "><br>
    </form
    </body>
</html>
