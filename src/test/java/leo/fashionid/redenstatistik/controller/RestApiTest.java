/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.controller;

import java.util.Arrays;
import leo.fashionid.redenstatistik.model.EvaluationResponse;
import leo.fashionid.redenstatistik.services.EvaluateSpeechService;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author odzhara-ongom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestApi.class})
@EnableWebMvc
@WebAppConfiguration
public class RestApiTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private EvaluateSpeechService service;

    public RestApiTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of evaluateSpeech method, of class RestApi.
     */
    @Test
    public void testEvaluateSpeech() throws Exception {
        System.out.println("testEvaluateSpeech: must return a response with data if ther was no error");
        EvaluationResponse response = new EvaluationResponse();
        response.setMostSecurity("John Dow");
        Mockito.when(service.evaluateSpeeches(Matchers.eq(Arrays.asList("/test")))).thenReturn(response);
        mvc.perform(get("/evaluation?url=/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mostSecurity", equalTo("John Dow")))
                .andReturn();;

    }

    /**
     * Test of evaluateSpeech method, of class RestApi.
     */
    @Test
    public void testEvaluateSpeechWithError() throws Exception {
        System.out.println("testEvaluateSpeech: must return a response with data if ther was no error");
        Mockito.when(service.evaluateSpeeches(Matchers.eq(Arrays.asList("/test")))).thenThrow(new RuntimeException("something happend"));
        mvc.perform(get("/evaluation?url=/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", containsString("Exception was thrown while evalating speech statistic")))
                .andExpect(jsonPath("$.error", containsString("something happend")))
                .andReturn();;

    }
}
