/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jaakkpaa
 */
public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti kortti; 

    public KassapaateTest() {
    }
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void luodunKassapaatteenRahamaaraJaMyytyjenLounaidenMaaraOikein() {
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void kateisostoMaukkaatJaEdullisetLounaatToimii() {
        int syoEdullisestiVaihtoraha = kassapaate.syoEdullisesti(1000);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(760, syoEdullisestiVaihtoraha);
        assertEquals(100240, kassapaate.kassassaRahaa());
        int syoMaukkaastiVaihtoraha = kassapaate.syoMaukkaasti(1000);
        assertEquals(600, syoMaukkaastiVaihtoraha);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100640, kassapaate.kassassaRahaa());

        kassapaate.syoEdullisesti(1);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(100640, kassapaate.kassassaRahaa());
        kassapaate.syoMaukkaasti(1);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(100640, kassapaate.kassassaRahaa());
    }


    @Test
    public void korttiostoMaukkaatJaEdullisetLounaatToimii() {
        kassapaate.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());

        kassapaate.syoMaukkaasti(kortti);
        assertEquals(360, kortti.saldo());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());

        kortti.otaRahaa(300);
        kassapaate.syoEdullisesti(kortti);
        assertEquals(60, kortti.saldo());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
            
        kassapaate.syoMaukkaasti(kortti);
        assertEquals(60, kortti.saldo());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
        
        assertEquals(100000, kassapaate.kassassaRahaa());
    }


    @Test
    public void kortilleRahaaLadattaessaKortinSaldoMuuttuuJaKassaKasvaaLadatullaSummalla() {
        kassapaate.lataaRahaaKortille(kortti, 100);
        assertEquals(1100, kortti.saldo());
        assertEquals(100100, kassapaate.kassassaRahaa());
        
        kassapaate.lataaRahaaKortille(kortti, -1);
        assertEquals(1100, kortti.saldo());
        assertEquals(100100, kassapaate.kassassaRahaa());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
