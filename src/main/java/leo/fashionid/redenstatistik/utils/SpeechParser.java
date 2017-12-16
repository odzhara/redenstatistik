/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import leo.fashionid.redenstatistik.model.RedeMetadaten;
import org.springframework.stereotype.Service;

/**
 *
 * @author odzhara-ongom
 */
@Service
public class SpeechParser {

    private String dateFormat = "yyyy-MM-dd";
    private Locale locale = Locale.GERMAN;

    public RedeMetadaten extractMetadata(String metaDataAsString) {
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
        DateFormat format = new SimpleDateFormat(dateFormat, locale);
        try {
            return format.parse(dateAsString);
        } catch (ParseException ex) {
            throw new RuntimeException("error parsing date format: " + ex.getMessage());
        }
    }
}
