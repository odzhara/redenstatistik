/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.controller;

import java.util.List;
import leo.fashionid.redenstatistik.model.EvaluationResponse;
import leo.fashionid.redenstatistik.services.EvaluateSpeechService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author odzhara-ongom
 */
@RestController
public class RestApi {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    private EvaluateSpeechService service;

    @RequestMapping(value = "/evaluation", method = RequestMethod.GET)
    public EvaluationResponse evaluateSpeech(@RequestParam(value = "url") List<String> urls) {
        EvaluationResponse response;
        try {
            response = service.evaluateSpeeches(urls);
        } catch (Exception e) {
            response = new EvaluationResponse();
            response.setError(e.getMessage());
            LOGGER.error("Exception was thrown while evalating speech statistic", e);
        }
        return response;
    }
}
