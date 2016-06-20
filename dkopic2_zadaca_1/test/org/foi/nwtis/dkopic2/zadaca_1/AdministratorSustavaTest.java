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
public class AdministratorSustavaTest {
    
   
    /**
     * Test of validateParams method, of class AdministratorSustava.
     */
    @Test
    public void testValidateParams() {
        System.out.println("validateParams test...");
        
        String command = "-admin -s localhost -port 8200 -u dkopic -p dkopic -new";
        AdministratorSustava instance = new AdministratorSustava(command);
        boolean expResult = true;
        
        boolean result = instance.validateParams(command);
        assertEquals(expResult, result);
        
        command = "-admin -s localhost -port 8200 -u dkopic -p dkopic -pause";
        result = instance.validateParams(command);
        assertEquals(expResult, result);
        
        command = "-admin -s localhost -port 8200 -u dkopic -p dkopic -start";
        result = instance.validateParams(command);
        assertEquals(expResult, result);
        
        command = "-admin -s localhost -port 8200 -u dkopic -p dkopic -stat";
        result = instance.validateParams(command);
        assertEquals(expResult, result);
        
        command = "-admin -s localhost -port 8200 -u dkopic -p dkopic -stop";
        result = instance.validateParams(command);
        assertEquals(expResult, result);
        
        String commandWrong = "-admin -s localhosts -port 7500 -u dkopic -p dkopic -old";
       
        boolean expResult2 = false;
        boolean result2 = instance.validateParams(commandWrong);
        assertEquals(expResult2, result2);
    }
    
}
