/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import leo.fashionid.redenstatistik.model.RedeMetadaten;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 *
 * @author odzhara-ongom
 */
public class EvaluateUtils {

    public static String findMostSpeechesInYear(int year, List<RedeMetadaten> speechList) {
        if (CollectionUtils.isEmpty(speechList)) {
            return null;
        }
        List<RedeMetadaten> allSpeechesInYear = speechList.stream()
                .filter(speech -> !StringUtils.isEmpty(speech.getRedner()))
                .filter(speech -> speech.getDatum() != null)
                .filter(speech -> isInSameYear(speech.getDatum(), year))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allSpeechesInYear)) {
            return null;
        }
        Map<String, Long> speechStat = countSpeeches(allSpeechesInYear);
        Map.Entry<String, Integer> speakerWithMostSpeeches = null;
        return findMostValuableEntry(speechStat);
    }

    public static String findMostSpeechesByTheme(String theme, List<RedeMetadaten> speechList) {
        if (CollectionUtils.isEmpty(speechList)) {
            return null;
        }
        List<RedeMetadaten> allSpeechesInYear = speechList.stream()
                .filter(speech -> !StringUtils.isEmpty(speech.getRedner()))
                .filter(speech -> !StringUtils.isEmpty(speech.getThema()))
                .filter(speech -> speech.getThema().equals(theme))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allSpeechesInYear)) {
            return null;
        }
        Map<String, Long> speechStat = countSpeeches(allSpeechesInYear);
        return findMostValuableEntry(speechStat);
    }

    public static String findLeastWordySpeakers(List<RedeMetadaten> speechList) {
        if (CollectionUtils.isEmpty(speechList)) {
            return null;
        }
        List<RedeMetadaten> allSpeechesInYear = speechList.stream()
                .filter(speech -> !StringUtils.isEmpty(speech.getRedner()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allSpeechesInYear)) {
            return null;
        }
        Map<String, Long> speechStat = countWords(allSpeechesInYear);
        return findLessValuableEntry(speechStat);
    }

    private static boolean isInSameYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) == year;
    }

    private static Map<String, Long> countSpeeches(List<RedeMetadaten> speechList) {
        Map<String, Long> returnValue = new HashMap<>();
        Long speechCount = null;
        for (RedeMetadaten speech : speechList) {
            speechCount = returnValue.get(speech.getRedner());
            if (speechCount == null) {
                returnValue.put(speech.getRedner(), 1L);
            } else {
                returnValue.put(speech.getRedner(), speechCount + 1);
            }
        }
        return returnValue;
    }

    private static Map<String, Long> countWords(List<RedeMetadaten> speechList) {
        Map<String, Long> returnValue = new HashMap<>();
        Long wordCount = null;
        for (RedeMetadaten speech : speechList) {
            wordCount = returnValue.get(speech.getRedner());
            if (wordCount == null) {
                returnValue.put(speech.getRedner(), speech.getWoerter());
            } else {
                returnValue.put(speech.getRedner(), wordCount + speech.getWoerter());
            }
        }
        return returnValue;
    }

    private static String findMostValuableEntry(Map<String, Long> data) {
        Map.Entry<String, Long> returnValue = null;
        for (Map.Entry<String, Long> entry : data.entrySet()) {
            if (returnValue == null
                    || entry.getValue().compareTo(returnValue.getValue()) > 0) {
                returnValue = entry;
            }
        }
        return returnValue == null ? null : returnValue.getKey();
    }

    private static String findLessValuableEntry(Map<String, Long> data) {
        Map.Entry<String, Long> returnValue = null;
        for (Map.Entry<String, Long> entry : data.entrySet()) {
            if (returnValue == null
                    || entry.getValue().compareTo(returnValue.getValue()) < 0) {
                returnValue = entry;
            }
        }
        return returnValue == null ? null : returnValue.getKey();
    }

}
