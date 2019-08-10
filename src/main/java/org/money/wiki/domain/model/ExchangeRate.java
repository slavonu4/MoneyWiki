package org.money.wiki.domain.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = ExchangeRate.TABLE_NAME)
public class ExchangeRate {
    @Embeddable
    public static class ExchangeRateId implements Serializable {
        public static final long serialVersionUID = 1L;

        private static final String COLUMN_DATE = "date";
        private static final String COLUMN_CURRENCY_CODE = "currency_code";

        public ExchangeRateId(){}

        public ExchangeRateId(Integer currencyCode){
            this.currencyCode = currencyCode;
        }

        private LocalDate date = LocalDate.now();
        private Integer currencyCode;

        @Column(name = COLUMN_DATE)
        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        @Column(name = COLUMN_CURRENCY_CODE)
        public Integer getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(Integer currencyCode) {
            this.currencyCode = currencyCode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ExchangeRateId rateId = (ExchangeRateId) o;
            return Objects.equals(date, rateId.date) &&
                    Objects.equals(currencyCode, rateId.currencyCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date, currencyCode);
        }
    }

    static final String TABLE_NAME = "exchange_rates";

    private static final String COLUMN_BUY_RATE = "buy_rate";
    private static final String COLUMN_SELL_RATE = "sell_rate";

    private ExchangeRateId id;
    private Double buyRate = 0.0;
    private Double sellRate = 0.0;

    @EmbeddedId
    public ExchangeRateId getId() {
        return id;
    }

    public void setId(ExchangeRateId id) {
        this.id = id;
    }

    @Column(name = COLUMN_BUY_RATE)
    public Double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(Double buyRate) {
        this.buyRate = buyRate;
    }

    @Column(name = COLUMN_SELL_RATE)
    public Double getSellRate() {
        return sellRate;
    }

    public void setSellRate(Double sellRate) {
        this.sellRate = sellRate;
    }
}
