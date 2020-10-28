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
    public void saldoAlussaOikein() {
        assertEquals(kortti.saldo(), 10);
    }
    
    @Test
    public void saldonKasvattaminenToimii() {
        kortti.lataaRahaa(100);
        assertEquals(kortti.saldo(), 110);
    }
    
    @Test
    public void negatiivisenLisaaminenEiOnnistu() {
        kortti.lataaRahaa(-1);
        assertEquals(kortti.saldo(), 10);
    }
    
    @Test
    public void saldoVaheneeOtettaessa() {
        kortti.otaRahaa(5);
        assertEquals(kortti.saldo(), 5);
    }
    
    @Test
    public void saldoEiVaheneOtettaessaLiikaa() {
        kortti.otaRahaa(100500);
        assertEquals(kortti.saldo(), 10);
    }
    
    @Test
    public void saldoEiKasvaOtettaessaNeg() {
        kortti.otaRahaa(-100500);
        assertEquals(kortti.saldo(), 10);
    }
    
    @Test
    public void metodiPalauttaaTrue() {
        assertEquals(kortti.otaRahaa(1), true);
    }
    
    @Test
    public void metodiPalauttaaFalse() {
        assertEquals(kortti.otaRahaa(100500), false);
    }
}
