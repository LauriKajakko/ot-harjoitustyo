/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lkajakko
 */
public class KassapaateTest {
    
    Kassapaate kassapaate;
    Maksukortti maksukortti;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        maksukortti = new Maksukortti(1000);
    }
    
    @Test
    public void alussaRahaa() {
        assertTrue(kassapaate.maukkaitaLounaitaMyyty()==0 
                    && kassapaate.kassassaRahaa()==100000);
    }
    
    @Test
    public void kateisostoMaukkaidenLounaidenMaaraKasvaa() {
        kassapaate.syoMaukkaasti(400);
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoEdullistenLounaidenMaaraKasvaa() {
        kassapaate.syoEdullisesti(240);
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoEdullinenPalauttaaOikeinRahaa() {
        assertTrue(kassapaate.syoEdullisesti(500)==260);    
    }
    
    @Test
    public void kateisostoMaukasPalauttaaOikeinRahaa() {
        assertTrue(kassapaate.syoMaukkaasti(500)==100);    
    }
    
    @Test
    public void kateisostoEdullinenRahaKasvaaOikein() {
        kassapaate.syoEdullisesti(500);
        assertEquals(100240, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void liianVahanRahaaEdullinenRahaKasvaaOikein() {
        kassapaate.syoEdullisesti(100);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void liianVahanRahaaMaukasRahaKasvaaOikein() {
        kassapaate.syoMaukkaasti(100);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void liianVahanRahaaMaukasLounaatKasvaaOikein() {
        kassapaate.syoMaukkaasti(100);
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void liianVahanRahaaEdullinenLounaatKasvaaOikein() {
        kassapaate.syoEdullisesti(100);
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kortillaTarpeeksiPalauttaaTrue() {
        assertTrue(kassapaate.syoEdullisesti(maksukortti) && kassapaate.syoMaukkaasti(maksukortti));
    }
    
    @Test
    public void kortillaTarpeeksiPalauttaaFalse() {
        kassapaate.syoMaukkaasti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        assertTrue(!kassapaate.syoEdullisesti(maksukortti) && !kassapaate.syoMaukkaasti(maksukortti));
    }
    
    @Test
    public void myytyjenLounaidenMaaraKasvaaKortilla() {
        kassapaate.syoMaukkaasti(maksukortti);
        kassapaate.syoEdullisesti(maksukortti);
        assertTrue(kassapaate.edullisiaLounaitaMyyty()==1 && kassapaate.maukkaitaLounaitaMyyty()==1);
    }
    
    @Test
    public void myytyjenMaaraEiKasvaVirheellisestiKortilla() {
        kassapaate.syoMaukkaasti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        kassapaate.syoEdullisesti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        assertTrue(kassapaate.edullisiaLounaitaMyyty()==0 && kassapaate.maukkaitaLounaitaMyyty()==2);
    }
    
    @Test
    public void kateinenEiMuutuKortillaOstaessa() {
        kassapaate.syoEdullisesti(maksukortti);
        kassapaate.syoMaukkaasti(maksukortti);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void kortilleRahanLatausLisaaRahaa() {
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(2000, maksukortti.saldo());
    }
    
    @Test
    public void kortilleRahanLatausMuuttaaKassapaatteenRahaa() {
        kassapaate.lataaRahaaKortille(maksukortti, 1000);
        assertEquals(101000,kassapaate.kassassaRahaa());
    }
    
    @Test
    public void rahanLatausKortilleEiTeeMitaanNegatiivisella() {
        kassapaate.lataaRahaaKortille(maksukortti, -666);
        assertTrue(maksukortti.saldo()==1000 && kassapaate.kassassaRahaa()==100000);
    }
    
    
    
}
