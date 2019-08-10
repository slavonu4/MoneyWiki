package org.money.wiki.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

// Чтобы не писать отдельные классы под курсы покупки\продажи, используем возможности Jackson
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeRateResponseDTO implements Serializable {
    public static final long serialVersionUID = 2L;

    public static class Builder {
        private Double buyRate;
        private Double sellRate;

        private Builder(){}

        public Builder withBuyRate(double buyRate){
            this.buyRate = buyRate;
            return this;
        }

        public Builder withSellRate(double sellRate){
            this.sellRate = sellRate;
            return this;
        }

        public ExchangeRateResponseDTO build(){
            return new ExchangeRateResponseDTO(this.buyRate, this.sellRate);
        }
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    private final Double buy;
    private final Double sell;

    public ExchangeRateResponseDTO(Double buy, Double sell) {
        this.buy = buy;
        this.sell = sell;
    }

    @JsonProperty("buy")
    public Double getBuy() {
        return buy;
    }

    @JsonProperty("sell")
    public Double getSell() {
        return sell;
    }
}
