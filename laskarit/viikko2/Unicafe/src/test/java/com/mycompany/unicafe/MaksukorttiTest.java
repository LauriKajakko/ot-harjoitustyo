package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void saldonKasvattaminenToimii() {
        kortti.lataaRahaa(10000);
        assertEquals(11000, kortti.saldo());
    }
    
//    @Test
//    public void negatiivisenLisaaminenEiOnnistu() {
//        kortti.lataaRahaa(-1);
//        assertEquals(1000, kortti.saldo());
//    }
    
    @Test
    public void saldoVaheneeOtettaessa() {
        kortti.otaRahaa(500);
        assertEquals(500, kortti.saldo());
    }
    
    @Test
    public void saldoEiVaheneOtettaessaLiikaa() {
        kortti.otaRahaa(10050000);
        assertEquals(1000, kortti.saldo());
    }
    
//    @Test
//    public void saldoEiKasvaOtettaessaNeg() {
//        kortti.otaRahaa(-10050000);
//        assertEquals(1000, kortti.saldo());
//    }
    
    @Test
    public void metodiPalauttaaTrue() {
        assertEquals(true, kortti.otaRahaa(100));
    }
    
    @Test
    public void metodiPalauttaaFalse() {
        assertEquals(false, kortti.otaRahaa(10050000));
    }
    
    @Test
    public void toStringToimii() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
   
}
