/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.services;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author odzhara-ongom
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HttpService service;

    public HttpServiceTest() {
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
     * Test of fetchFile method, of class HttpService.
     */
    @Test
    public void testFetchFile() {
        System.out.println("fetchFile: must return a string if there is a file to download");
        String data = "123";
        String url = "/url";
        ResponseEntity<String> response = new ResponseEntity<String>(data, HttpStatus.ACCEPTED);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Mockito.when(restTemplate.exchange(
                Matchers.eq(url),
                Matchers.eq(HttpMethod.GET),
                Matchers.eq(entity),
                Matchers.eq(String.class)
        )).thenReturn(response);
        String expResult = "123";
        String result = service.fetchFile(url);
        assertEquals(expResult, result);
    }

    @Test
    public void testFetchFileWithException() {
        System.out.println("fetchFile: must throw an exception if there is no file to downlad");
        String data = "123";
        String url = "/url";
        ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Mockito.when(restTemplate.exchange(
                Matchers.eq(url),
                Matchers.eq(HttpMethod.GET),
                Matchers.eq(entity),
                Matchers.eq(String.class)
        )).thenThrow(new RuntimeException("something horrible happens!"));
        String expResult = "123";
        try {
            service.fetchFile(url);
            fail("Should throw an Runtime - exception");
        } catch (RuntimeException e) {
            assertEquals(e.getMessage().contains("error downloading csv-file"), true);
        }
    }
}
