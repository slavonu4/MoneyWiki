package org.money.wiki.business.service.exchange.rate.remote;

import org.money.wiki.domain.model.ExchangeRate;

import java.util.Optional;

public interface RemoteExchangeRateService {
    Optional<ExchangeRate> load(Integer currencyCode);
}
