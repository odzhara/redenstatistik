/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.services;

import java.util.ArrayList;
import java.util.List;
import leo.fashionid.redenstatistik.model.EvaluationResponse;
import leo.fashionid.redenstatistik.model.RedeMetadaten;
import leo.fashionid.redenstatistik.utils.EvaluateUtils;
import leo.fashionid.redenstatistik.utils.SpeechParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class EvaluateSpeechService {

    private HttpService http;
    private SpeechParser parser;

    @Autowired
    public EvaluateSpeechService(HttpService http, SpeechParser parser) {
        this.http = http;
        this.parser = parser;
    }

    public EvaluationResponse evaluateSpeeches(List<String> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            return new EvaluationResponse();
        }
        List<RedeMetadaten> speechList = new ArrayList<>();
        for (String url : urls) {
            String speechData = http.fetchFile(url);
            speechList.addAll(parseMetaDatenFromDataAsString(speechData));
        }
        EvaluationResponse evaluationResponse = new EvaluationResponse();
        evaluationResponse.setMostSpeeches(EvaluateUtils.findMostSpeechesInYear(2013, speechList));
        evaluationResponse.setMostSecurity(EvaluateUtils.findMostSpeechesByTheme("Innere Sicherheit", speechList));
        evaluationResponse.setLeastWordy(EvaluateUtils.findLeastWordySpeakers(speechList));
        return evaluationResponse;
    }

    private List<RedeMetadaten> parseMetaDatenFromDataAsString(String data) {
        if (StringUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        String[] allSpeechesMetadata = data.split("\n");
        if (allSpeechesMetadata == null || allSpeechesMetadata.length < 2) {
            return new ArrayList<>();
        }
        List<RedeMetadaten> speechList = new ArrayList<>();
        for (int i = 1; i < allSpeechesMetadata.length; i++) {
            speechList.add(parser.extractMetadata(allSpeechesMetadata[i]));
        }
        return speechList;
    }

}
