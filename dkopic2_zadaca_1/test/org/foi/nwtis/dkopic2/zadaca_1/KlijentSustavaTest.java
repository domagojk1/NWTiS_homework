/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.zadaca_1;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author domagoj
 */
public class KlijentSustavaTest {
    
    /**
     * Test of validateParams method, of class KlijentSustava.
     */
    @Test
    public void testValidateParams() {
        System.out.println("validateParams test...");
        
        String command = "-user -s localhost -port 8200 -u dkopic -play";
        KlijentSustava instance = new KlijentSustava(command);
        
        boolean expResult = true;
        boolean result = instance.validateParams(command);
        assertEquals(expResult, result);
        
        command = "-user -s localhost -port 8200 -u dkopic -x 5 -y 3";
        result = instance.validateParams(command);
        assertEquals(expResult, result);
        
        command = "-user -s localhost -port 8200 -u dkopic -stat";
        result = instance.validateParams(command);
        assertEquals(expResult, result);
        
        command = "-user -s localhost -port 8200 -u dkopic -stats";
        expResult = false;
        result = instance.validateParams(command);
        assertEquals(expResult, result);
    }
    
}
