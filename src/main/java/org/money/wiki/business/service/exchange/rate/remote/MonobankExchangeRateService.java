package org.money.wiki.business.service.exchange.rate.remote;

import org.money.wiki.domain.model.ExchangeRate;
import org.money.wiki.presentation.dto.MonobankExchangeRateResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MonobankExchangeRateService implements RemoteExchangeRateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonobankExchangeRateService.class);

    private static final Integer UAH_CODE = 980;

    private final RestTemplate restTemplate;

    public MonobankExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<ExchangeRate> load(Integer currencyCode) {
        return sendRequestFor(currencyCode)
                .map(response -> toModel(response, currencyCode));
    }

    private Optional<MonobankExchangeRateResponseDTO> sendRequestFor(Integer currencyCode) {
        try {
            ResponseEntity<List<MonobankExchangeRateResponseDTO>> response = restTemplate.exchange(
                    "https://api.monobank.ua/bank/currency", HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<MonobankExchangeRateResponseDTO>>() {
                    }
            );

            List<MonobankExchangeRateResponseDTO> body = response.getBody();
            if (!response.getStatusCode().is2xxSuccessful() || body == null) {
                LOGGER.error("Unable to load exchange rates from Monobank API. Response code {} ", response.getStatusCode());
                return Optional.empty();
            }

            return body.stream()
                    .filter(rate -> isRateFor(rate, currencyCode))
                    .findAny();
        } catch (Exception e) {      //To deal with exception on 404 response code
            LOGGER.error("Unable to load exchange rates from Monobank API: ", e);
            return Optional.empty();
        }
    }

    private boolean isRateFor(MonobankExchangeRateResponseDTO rate, Integer currencyCode) {
        return (Objects.equals(rate.getCodeA(), currencyCode) && Objects.equals(rate.getCodeB(), UAH_CODE))
                || (Objects.equals(rate.getCodeB(), currencyCode) && Objects.equals(rate.getCodeA(), UAH_CODE));
    }

    private ExchangeRate toModel(MonobankExchangeRateResponseDTO responseDTO, Integer currencyCode){
        ExchangeRate rate = new ExchangeRate();

        rate.setId(new ExchangeRate.ExchangeRateId(currencyCode));
        rate.setBuyRate(responseDTO.getBuyRate());
        rate.setSellRate(responseDTO.getSellRate());

        return rate;
    }
}
