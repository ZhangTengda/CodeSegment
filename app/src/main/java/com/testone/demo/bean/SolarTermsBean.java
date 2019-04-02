package com.testone.demo.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class SolarTermsBean extends LitePalSupport {

    /**
     * 节气编号
     */
    private int solarTermsNumber;

    /**
     * 节气
     */
    @Column(unique = true, defaultValue = "unknown")
    private String solarTerms;

    /**
     * 节气介绍
     */
    private String solarTermsDes;

    public SolarTermsBean() {}

    public SolarTermsBean(int solarTermsNumber,String solarTerms,String solarTermsDes) {
        this.solarTermsNumber = solarTermsNumber;
        this.solarTerms = solarTerms;
        this.solarTermsDes = solarTermsDes;
    }

    public int getSolarTermsNumber() {
        return solarTermsNumber;
    }

    public void setSolarTermsNumber(int solarTermsNumber) {
        this.solarTermsNumber = solarTermsNumber;
    }

    public String getSolarTerms() {
        return solarTerms;
    }

    public void setSolarTerms(String solarTerms) {
        this.solarTerms = solarTerms;
    }

    public String getSolarTermsDes() {
        return solarTermsDes;
    }

    public void setSolarTermsDes(String solarTermsDes) {
        this.solarTermsDes = solarTermsDes;
    }
}
