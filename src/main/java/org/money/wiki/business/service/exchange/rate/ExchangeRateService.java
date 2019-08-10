package org.money.wiki.business.service.exchange.rate;

import org.money.wiki.presentation.dto.ExchangeRateResponseDTO;
import org.money.wiki.presentation.exception.BadMnemonicsException;
import org.money.wiki.presentation.exception.RemoteServiceError;

public interface ExchangeRateService {
    ExchangeRateResponseDTO getRateOf(String currency) throws BadMnemonicsException, RemoteServiceError;
    ExchangeRateResponseDTO getBuyRateOf(String currency) throws BadMnemonicsException, RemoteServiceError;
    ExchangeRateResponseDTO getSellRateOf(String currency) throws BadMnemonicsException, RemoteServiceError;
}
