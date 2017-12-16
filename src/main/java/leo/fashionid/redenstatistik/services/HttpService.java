/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.services;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class HttpService {

    private RestTemplate restTemplate;

    @Autowired
    public HttpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
    }

    public String fetchFile(String url) {
        try {
            String returnValue;
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
}
