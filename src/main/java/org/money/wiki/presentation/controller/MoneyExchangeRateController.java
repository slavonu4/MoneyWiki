package org.money.wiki.presentation.controller;

import org.money.wiki.business.service.exchange.rate.ExchangeRateService;
import org.money.wiki.presentation.dto.ExchangeRateResponseDTO;
import org.money.wiki.presentation.exception.BadMnemonicsException;
import org.money.wiki.presentation.exception.RemoteServiceError;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/money/exchange/rate")
public class MoneyExchangeRateController {
    private final ExchangeRateService service;

    public MoneyExchangeRateController(ExchangeRateService service) {
        this.service = service;
    }

    @GetMapping("/{currency}")
    public ResponseEntity<ExchangeRateResponseDTO> getRateOf(
            @PathVariable("currency") String currency
    ) throws BadMnemonicsException, RemoteServiceError {
        ExchangeRateResponseDTO result = service.getRateOf(currency);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{currency}/buy")
    public ResponseEntity<ExchangeRateResponseDTO> getBuyRateOf(
            @PathVariable("currency") String currency
    ) throws BadMnemonicsException, RemoteServiceError {
        ExchangeRateResponseDTO result = service.getBuyRateOf(currency);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{currency}/sell")
    public ResponseEntity<ExchangeRateResponseDTO> getSellRateOf(
            @PathVariable("currency") String currency
    ) throws BadMnemonicsException, RemoteServiceError {
        ExchangeRateResponseDTO result = service.getSellRateOf(currency);
        return ResponseEntity.ok(result);
    }


}
