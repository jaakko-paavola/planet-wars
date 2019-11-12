package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }

    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: " + 10/100 + "." + 10%100, kortti.toString());
    }

    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(90);
        assertEquals("saldo: " + 100/100 + "." + 100%100, kortti.toString());
    }

    @Test
    public void rahanOttaminenToimii() {
        assertTrue(kortti.otaRahaa(1));
        assertEquals("saldo: " + 9/100 + "." + 9%100, kortti.toString());
        assertFalse(kortti.otaRahaa(11));
        assertEquals("saldo: " + 9/100 + "." + 9%100, kortti.toString());
    }

    @Test
    public void saldoPalauttaaSaldon() {
        assertEquals(10, kortti.saldo());
    }

}
