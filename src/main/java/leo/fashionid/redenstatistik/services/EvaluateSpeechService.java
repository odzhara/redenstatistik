/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import leo.fashionid.redenstatistik.model.EvaluationResponse;
import leo.fashionid.redenstatistik.model.RedeMetadaten;
import leo.fashionid.redenstatistik.utils.EvaluateUtls;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class EvaluateSpeechService {

    public EvaluationResponse evaluateSpeeches(List<String> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return new EvaluationResponse();
        }
        List<RedeMetadaten> speechList = new ArrayList<>();
        for (String url : urls) {
            String speechData = fetchFile(url);
            speechList.addAll(parseMataDatenFromDataAsString(speechData));
        }
        EvaluationResponse evaluationResponse = new EvaluationResponse();
        evaluationResponse.setMostSpeeches(EvaluateUtls.findMostSpeechesInYear(2013, speechList));
        evaluationResponse.setMostSecurity(EvaluateUtls.findMostSpeechesByTheme("Innere Sicherheit", speechList));
        evaluationResponse.setLeastWordy(EvaluateUtls.findLeastWordySpeakers(speechList));
        return evaluationResponse;
    }

    private String fetchFile(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String returnValue;
            restTemplate.getMessageConverters().add(
                    new ByteArrayHttpMessageConverter());
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET, entity, String.class);
            returnValue = response.getBody();
            return returnValue;
        } catch (Exception e) {
            throw new RuntimeException("error downloading csv-file: " + e.getMessage());
        }
    }

    private RedeMetadaten extractMetadata(String metaDataAsString) {
        try {
            String[] splittedData = metaDataAsString.split(", ");
            RedeMetadaten returnValue = new RedeMetadaten();
            returnValue.setRedner(splittedData[0]);
            returnValue.setThema(splittedData[1]);
            returnValue.setDatum(parseDate(splittedData[2]));
            returnValue.setWoerter(Long.parseLong(splittedData[3]));
            return returnValue;
        } catch (Exception e) {
            throw new RuntimeException("error in data format: " + e.getMessage());
        }
    }

    private Date parseDate(String dateAsString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        try {
            return format.parse(dateAsString);
        } catch (ParseException ex) {
            throw new RuntimeException("error parsing date format: " + ex.getMessage());
        }
    }

    private List<RedeMetadaten> parseMataDatenFromDataAsString(String data) {
        if (StringUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        String[] allSpeechesMetadata = data.split("\n");
        if (allSpeechesMetadata == null || allSpeechesMetadata.length < 2) {
            return new ArrayList<>();
        }
        List<RedeMetadaten> speechList = new ArrayList<>();
        for (int i = 1; i < allSpeechesMetadata.length; i++) {
            speechList.add(extractMetadata(allSpeechesMetadata[i]));
        }
        return speechList;
    }
}
