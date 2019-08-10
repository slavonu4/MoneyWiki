package org.money.wiki.business.service.exchange.rate;

import org.money.wiki.business.service.exchange.rate.remote.RemoteExchangeRateService;
import org.money.wiki.business.service.info.CurrencyInfoService;
import org.money.wiki.domain.dao.exchange.rate.ExchangeRateDAO;
import org.money.wiki.domain.model.ExchangeRate;
import org.money.wiki.presentation.dto.ExchangeRateResponseDTO;
import org.money.wiki.presentation.exception.BadMnemonicsException;
import org.money.wiki.presentation.exception.RemoteServiceError;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final CurrencyInfoService infoService;
    private final RemoteExchangeRateService remoteExchangeRateService;
    private final ExchangeRateDAO rateDAO;

    public ExchangeRateServiceImpl(CurrencyInfoService infoService, RemoteExchangeRateService remoteExchangeRateService, ExchangeRateDAO rateDAO) {
        this.infoService = infoService;
        this.remoteExchangeRateService = remoteExchangeRateService;
        this.rateDAO = rateDAO;
    }

    @Override
    @Cacheable(value = "rates", key = "#currency")
    public ExchangeRateResponseDTO getRateOf(String currency) throws BadMnemonicsException, RemoteServiceError {
        return getRates(currency, true, true);
    }

    @Override
    @Cacheable(value = "buyRates", key = "#currency")
    public ExchangeRateResponseDTO getBuyRateOf(String currency) throws BadMnemonicsException, RemoteServiceError {
        return getRates(currency, true, false);
    }

    @Override
    @Cacheable(value = "sellRates", key = "#currency")
    public ExchangeRateResponseDTO getSellRateOf(String currency) throws BadMnemonicsException, RemoteServiceError {
        return getRates(currency, false, true);
    }

    private ExchangeRateResponseDTO getRates(String currency, boolean buyRate, boolean sellRate) throws BadMnemonicsException, RemoteServiceError {
        Integer currencyCode = infoService.getCodeOf(currency)
                .orElseThrow(() -> new BadMnemonicsException(currency));
        LocalDate currentDate = LocalDate.now();
        ExchangeRate.ExchangeRateId rateId = new ExchangeRate.ExchangeRateId(currencyCode);
        Optional<ExchangeRate> rateOpt = rateDAO.get(rateId);

        ExchangeRate rate = null;
        if (rateOpt.isPresent()) rate = rateOpt.get();
        else rate = loadRateFor(rateId);

        return toDto(rate, buyRate, sellRate);
    }

    private ExchangeRate loadRateFor(ExchangeRate.ExchangeRateId rateId) throws RemoteServiceError {
        ExchangeRate loadedRate = remoteExchangeRateService.load(rateId.getCurrencyCode())
                .orElseThrow(RemoteServiceError::new);

        return rateDAO.save(loadedRate);
    }

    private ExchangeRateResponseDTO toDto(ExchangeRate rate, boolean buyRate, boolean sellRate) {
        ExchangeRateResponseDTO.Builder builder = ExchangeRateResponseDTO.newBuilder();

        if (buyRate)
            builder.withBuyRate(rate.getBuyRate());
        if (sellRate)
            builder.withSellRate(rate.getSellRate());

        return builder.build();
    }
}
