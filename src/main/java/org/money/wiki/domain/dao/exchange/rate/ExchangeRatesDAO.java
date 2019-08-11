package org.money.wiki.domain.dao.exchange.rate;

import org.money.wiki.domain.model.ExchangeRate;

import java.util.Optional;

public interface ExchangeRatesDAO {
    Optional<ExchangeRate> get(ExchangeRate.ExchangeRateId rateId);

    ExchangeRate save(ExchangeRate rate);
}
