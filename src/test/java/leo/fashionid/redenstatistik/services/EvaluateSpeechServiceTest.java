/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.services;

import java.util.Arrays;
import java.util.List;
import leo.fashionid.redenstatistik.model.EvaluationResponse;
import leo.fashionid.redenstatistik.utils.SpeechParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author odzhara-ongom
 */
public class EvaluateSpeechServiceTest {

    private SpeechParser parser;
    private HttpService http;
    private EvaluateSpeechService serviceUnderTest;

    public EvaluateSpeechServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        parser = new SpeechParser();
        http = mock(HttpService.class);
        serviceUnderTest = new EvaluateSpeechService(http, parser);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of evaluateSpeeches method, of class EvaluateSpeechService.
     */
    @Test
    public void testEvaluateSpeeches() {
        System.out.println("testEvaluateSpeeches: must calculate statistic for one downloaded file");
        when(http.fetchFile("/exampletext")).thenReturn(exampleText);
        EvaluationResponse result = serviceUnderTest.evaluateSpeeches(Arrays.asList("/exampletext"));
        assertNull(result.getError());
        assertNull(result.getMostSpeeches());
        assertEquals("Alexander Abel", result.getMostSecurity());
        assertEquals("Caesare Collins", result.getLeastWordy());
    }

    @Test
    public void testEvaluateSpeechesWithManyUrls() {
        System.out.println("testEvaluateSpeechesWithManyUrls: must calculate statistic for if there are many files to download");
        when(http.fetchFile("/text1")).thenReturn(text1);
        when(http.fetchFile("/text2")).thenReturn(text2);
        when(http.fetchFile("/text3")).thenReturn(text3);
        when(http.fetchFile("/text4")).thenReturn(text4);
        List<String> urls = Arrays.asList("/text1", "/text2", "/text3", "/text4");
        EvaluationResponse result = serviceUnderTest.evaluateSpeeches(urls);
        assertNull(result.getError());
        assertEquals("Alexander Abel", result.getMostSecurity());
        assertEquals("Bernhard Belling", result.getMostSpeeches());
        assertEquals("Caesare Collins", result.getLeastWordy());
    }

    private String text1 = "Redner, Thema, Datum, Wörter\n"
            + "Alexander Abel, Bildungspolitik, 2012-10-30, 5310";
    private String text2 = "Redner, Thema, Datum, Wörter\n"
            + "Bernhard Belling, Kohlesubventionen, 2013-11-05, 1210";
    private String text3 = "Redner, Thema, Datum, Wörter\n"
            + "Caesare Collins, Kohlesubventionen, 2012-11-06, 1119";
    private String text4 = "Redner, Thema, Datum, Wörter\n"
            + "Alexander Abel, Innere Sicherheit, 2012-12-11, 911";

    private String bigText = "Redner, Thema, Datum, Wörter\n"
            + "Alexander Abel, Bildungspolitik, 2012-10-30, 5310\n"
            + "Bernhard Belling, Kohlesubventionen, 2012-11-05, 1210\n"
            + "Caesare Collins, Kohlesubventionen, 2012-11-06, 1119\n"
            + "Alexander Abel, Innere Sicherheit, 2012-12-11, 911";

    private String exampleText = "Redner, Thema, Datum, Wörter\n"
            + "Alexander Abel, Bildungspolitik, 2012-10-30, 5310\n"
            + "Bernhard Belling, Kohlesubventionen, 2012-11-05, 1210\n"
            + "Caesare Collins, Kohlesubventionen, 2012-11-06, 1119\n"
            + "Alexander Abel, Innere Sicherheit, 2012-12-11, 911";
}
