/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
public class EvaluateUtlsTest {

    public EvaluateUtlsTest() {
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
     * Test of findMostSpeechesInYear method, of class EvaluateUtls.
     */
    @Test
    public void testFindMostSpeechesInYear() {
        System.out.println("findMostSpeechesInYear");
        int year = 2013;
        assertNull(EvaluateUtls.findMostSpeechesInYear(year, null));
        assertNull(EvaluateUtls.findMostSpeechesInYear(year, new ArrayList<>()));
        List<RedeMetadaten> speechList = new ArrayList<>();
        speechList.add(createMetadata("redner1", "thema1", "2014-10-01", 1024L));
        assertNull(EvaluateUtls.findMostSpeechesInYear(year, speechList));
        speechList.add(createMetadata("redner2", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner3", "thema1", "2010-10-01", 1024L));
        speechList.add(createMetadata("redner4", "thema1", "2010-10-01", 1024L));
        speechList.add(createMetadata("redner1", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner2", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner3", "thema1", "2012-10-01", 1024L));
        speechList.add(createMetadata("redner4", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner1", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner2", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner3", "thema1", "2011-10-01", 1024L));

        String expResult = "redner2";
        String result = EvaluateUtls.findMostSpeechesInYear(year, speechList);
        assertEquals(expResult, result);
    }

    private RedeMetadaten createMetadata(String redner, String thema, String date, Long words) {
        RedeMetadaten returnValue = new RedeMetadaten();
        returnValue.setRedner(redner);
        returnValue.setDatum(parseDate(date));
        returnValue.setThema(thema);
        returnValue.setWoerter(words);
        return returnValue;
    }

    private Date parseDate(String dateAsString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        try {
            return format.parse(dateAsString);
        } catch (ParseException ex) {
            throw new RuntimeException("error parsing date format");
        }
    }

    /**
     * Test of findMostSpeechesByTheme method, of class EvaluateUtls.
     */
    @Test
    public void testFindMostSpeechesByTheme() {
        System.out.println("findMostSpeechesByTheme");
        String theme = "Innere Sicherheit";
        assertNull(EvaluateUtls.findMostSpeechesByTheme(theme, null));
        assertNull(EvaluateUtls.findMostSpeechesByTheme(theme, new ArrayList<>()));
        List<RedeMetadaten> speechList = new ArrayList<>();
        speechList.add(createMetadata("redner1", "thema1", "2014-10-01", 1024L));
        assertNull(EvaluateUtls.findMostSpeechesByTheme(theme, speechList));
        speechList.add(createMetadata("redner2", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner3", "Innere Sicherheit", "2010-10-01", 1024L));
        speechList.add(createMetadata("redner4", "Innere Sicherheit", "2010-10-01", 1024L));
        speechList.add(createMetadata("redner1", null, "2013-10-01", 1024L));
        speechList.add(createMetadata("redner2", "Innere Sicherheit", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner3", "Innere Sicherheit", "2012-10-01", 1024L));
        speechList.add(createMetadata("redner4", "Innere Sicherheit", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner1", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner2", "Innere Sicherheit", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner3", "Innere Sicherheit", "2011-10-01", 1024L));
        String expResult = "redner3";
        String result = EvaluateUtls.findMostSpeechesByTheme(theme, speechList);
        assertEquals(expResult, result);
    }

    /**
     * Test of findLeastWordySpeakers method, of class EvaluateUtls.
     */
    @Test
    public void testFindLeastWordySpeakers() {
        System.out.println("findLeastWordySpeakers");
        assertNull(EvaluateUtls.findLeastWordySpeakers(null));
        assertNull(EvaluateUtls.findLeastWordySpeakers(new ArrayList<>()));
        List<RedeMetadaten> speechList = new ArrayList<>();
        speechList.add(createMetadata("redner1", "thema1", "2014-10-01", 1024L));
        speechList.add(createMetadata("redner2", "thema1", "2013-10-01", 2024L));
        speechList.add(createMetadata("redner3", "thema1", "2010-10-01", 3024L));
        speechList.add(createMetadata("redner4", "thema1", "2010-10-01", 1024L));
        speechList.add(createMetadata("redner1", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner2", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner3", "thema1", "2012-10-01", 1024L));
        speechList.add(createMetadata("redner4", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner1", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner2", "thema1", "2013-10-01", 1024L));
        speechList.add(createMetadata("redner3", "thema1", "2011-10-01", 1024L));
        String expResult = "redner4";
        String result = EvaluateUtls.findLeastWordySpeakers(speechList);
        assertEquals(expResult, result);
    }

}
