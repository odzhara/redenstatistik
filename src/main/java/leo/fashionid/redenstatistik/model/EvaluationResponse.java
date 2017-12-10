/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.fashionid.redenstatistik.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author odzhara-ongom
 */
public class EvaluationResponse {

    private String mostSpeeches;
    private String mostSecurity;
    private String leastWordy;

    @JsonInclude(Include.NON_NULL)
    private String error;

    public String getMostSpeeches() {
        return mostSpeeches;
    }

    public void setMostSpeeches(String mostSpeeches) {
        this.mostSpeeches = mostSpeeches;
    }

    public String getMostSecurity() {
        return mostSecurity;
    }

    public void setMostSecurity(String mostSecurity) {
        this.mostSecurity = mostSecurity;
    }

    public String getLeastWordy() {
        return leastWordy;
    }

    public void setLeastWordy(String leastWordy) {
        this.leastWordy = leastWordy;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
