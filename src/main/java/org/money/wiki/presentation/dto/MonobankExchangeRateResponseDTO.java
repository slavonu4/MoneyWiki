package org.money.wiki.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonobankExchangeRateResponseDTO {
    private Integer codeA;
    private Integer codeB;
    private Double buyRate;
    private Double sellRate;

    @JsonProperty("currencyCodeA")
    public Integer getCodeA() {
        return codeA;
    }

    public void setCodeA(Integer codeA) {
        this.codeA = codeA;
    }

    @JsonProperty("currencyCodeB")
    public Integer getCodeB() {
        return codeB;
    }

    public void setCodeB(Integer codeB) {
        this.codeB = codeB;
    }

    @JsonProperty("rateBuy")
    public Double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(Double buyRate) {
        this.buyRate = buyRate;
    }

    @JsonProperty("rateSell")
    public Double getSellRate() {
        return sellRate;
    }

    public void setSellRate(Double sellRate) {
        this.sellRate = sellRate;
    }
}
