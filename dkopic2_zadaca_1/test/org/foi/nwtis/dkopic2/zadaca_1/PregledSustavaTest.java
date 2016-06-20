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
public class PregledSustavaTest {
    
    /**
     * Test of validateParams method, of class PregledSustava.
     */
    @Test
    public void testValidateParams() {
        System.out.println("validateParams test...");
        
        String command = "-show -s NWTiS_evidencija0001.bin";
        
        PregledSustava instance = new PregledSustava(command);
        
        boolean expResult = true;
        boolean result = instance.validateParams();
        assertEquals(expResult, result);
        
        String commandWrong = "-show s NWTiS_evidencija0001.bin";
        PregledSustava instance2 = new PregledSustava(commandWrong);
        result = instance2.validateParams();
        expResult = false;
        assertEquals(expResult, result);
    }
    
}
