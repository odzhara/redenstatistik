/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.utils;

import java.util.Calendar;
import junit.framework.Assert;
import leo.fashionid.redenstatistik.model.RedeMetadaten;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author odzhara-ongom
 */
public class SpeechParserTest {

    public SpeechParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of extractMetadata method, of class SpeechParser.
     */
    @Test
    public void testExtractMetadata() {
        System.out.println("testExtractMetadata: must parse data from a string");
        String metaDataAsString = "Alexander Abel, Bildungspolitik, 2012-10-30, 5310";
        SpeechParser instance = new SpeechParser();
        RedeMetadaten result = instance.extractMetadata(metaDataAsString);
        assertEquals("Alexander Abel", result.getRedner());
        assertEquals("Bildungspolitik", result.getThema());
        assertEquals(5310L, result.getWoerter());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(result.getDatum());
        assertEquals(2012, calendar.get(Calendar.YEAR));
        assertEquals(Calendar.OCTOBER, calendar.get(Calendar.MONTH));
        assertEquals(30, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testExtractMetadataWithErrors() {
        System.out.println("testExtractMetadataWithErrors: must throw exception if date is not valid");
        String metaDataAsString = "Alexander Abel, Bildungspolitik, 2012-Jun-30, 5310";
        SpeechParser instance = new SpeechParser();
        try {
            instance.extractMetadata(metaDataAsString);
            fail("Should throw an Runtime - exception");
        } catch (RuntimeException e) {
            assertEquals(e.getMessage().contains("error in data format"), true);
            assertEquals(e.getMessage().contains("error parsing date format"), true);
        }
    }
}
