package org.money.wiki.business.unit.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.money.wiki.business.service.exchange.rate.ExchangeRateService;
import org.money.wiki.business.service.exchange.rate.ExchangeRateServiceImpl;
import org.money.wiki.business.service.exchange.rate.remote.RemoteExchangeRateService;
import org.money.wiki.business.service.info.CurrencyInfoService;
import org.money.wiki.domain.dao.exchange.rate.ExchangeRatesDAO;
import org.money.wiki.domain.model.ExchangeRate;
import org.money.wiki.presentation.dto.ExchangeRateResponseDTO;
import org.money.wiki.presentation.exception.BadMnemonicsException;
import org.money.wiki.presentation.exception.RemoteServiceError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ExchangeRateServiceTestSuit {
    @TestConfiguration
    public static class ExchangeRateServiceTestConfig {
        @Bean
        public ExchangeRateService service(CurrencyInfoService infoService, RemoteExchangeRateService remoteService, ExchangeRatesDAO ratesDAO) {
            return new ExchangeRateServiceImpl(infoService, remoteService, ratesDAO);
        }
    }

    @Autowired
    private ExchangeRateService service;

    @MockBean
    private CurrencyInfoService currencyInfoService;
    @MockBean
    private RemoteExchangeRateService remoteService;
    @MockBean
    private ExchangeRatesDAO ratesDAO;

    @Test(expected = BadMnemonicsException.class)
    public void getRateOfNotExistingCurrency() throws RemoteServiceError, BadMnemonicsException {
        String tst = "TST";
        when(currencyInfoService.getCodeOf(eq(tst))).thenReturn(Optional.empty());

        ExchangeRateResponseDTO result = service.getRateOf(tst);
    }

    @Test
    public void getRateFromDB() throws RemoteServiceError, BadMnemonicsException {
        String mnemonics = "USD";
        Integer code = 404;
        ExchangeRate.ExchangeRateId rateId = new ExchangeRate.ExchangeRateId(code);
        ExchangeRate rate = testRate(rateId);

        when(currencyInfoService.getCodeOf(eq(mnemonics))).thenReturn(Optional.of(code));
        when(ratesDAO.get(eq(rateId))).thenReturn(Optional.of(rate));

        ExchangeRateResponseDTO result = service.getRateOf(mnemonics);

        verify(remoteService, never()).load(eq(code));
        assertEquals(rate.getBuyRate(), result.getBuy());
        assertEquals(rate.getSellRate(), result.getSell());
    }

    @Test
    public void getRateFromRemoteService() throws RemoteServiceError, BadMnemonicsException {
        String mnemonics = "USD";
        Integer code = 404;
        ExchangeRate.ExchangeRateId rateId = new ExchangeRate.ExchangeRateId(code);
        ExchangeRate rate = testRate(rateId);

        when(currencyInfoService.getCodeOf(eq(mnemonics))).thenReturn(Optional.of(code));
        when(ratesDAO.get(eq(rateId))).thenReturn(Optional.empty());
        when(remoteService.load(eq(code))).thenReturn(Optional.of(rate));
        when(ratesDAO.save(any())).thenReturn(rate);

        ExchangeRateResponseDTO result = service.getRateOf(mnemonics);

        verify(remoteService).load(eq(code));
        verify(ratesDAO).save(any());
        assertEquals(rate.getBuyRate(), result.getBuy());
        assertEquals(rate.getSellRate(), result.getSell());
    }

    @Test(expected = RemoteServiceError.class)
    public void propagateRemoteServiceError() throws RemoteServiceError, BadMnemonicsException {
        String mnemonics = "USD";
        Integer code = 404;
        ExchangeRate.ExchangeRateId rateId = new ExchangeRate.ExchangeRateId(code);
        ExchangeRate rate = testRate(rateId);

        when(currencyInfoService.getCodeOf(eq(mnemonics))).thenReturn(Optional.of(code));
        when(ratesDAO.get(eq(rateId))).thenReturn(Optional.empty());
        when(remoteService.load(eq(code))).thenReturn(Optional.empty());

        ExchangeRateResponseDTO result = service.getRateOf(mnemonics);
    }

    @Test
    public void getSellRateOnly() throws RemoteServiceError, BadMnemonicsException {
        String mnemonics = "USD";
        Integer code = 404;
        ExchangeRate.ExchangeRateId rateId = new ExchangeRate.ExchangeRateId(code);
        ExchangeRate rate = testRate(rateId);

        when(currencyInfoService.getCodeOf(eq(mnemonics))).thenReturn(Optional.of(code));
        when(ratesDAO.get(eq(rateId))).thenReturn(Optional.of(rate));

        ExchangeRateResponseDTO result = service.getSellRateOf(mnemonics);

        assertEquals(rate.getSellRate(), result.getSell());
        assertNull(result.getBuy());
    }

    @Test
    public void getBuyRateOnly() throws RemoteServiceError, BadMnemonicsException {
        String mnemonics = "USD";
        Integer code = 404;
        ExchangeRate.ExchangeRateId rateId = new ExchangeRate.ExchangeRateId(code);
        ExchangeRate rate = testRate(rateId);

        when(currencyInfoService.getCodeOf(eq(mnemonics))).thenReturn(Optional.of(code));
        when(ratesDAO.get(eq(rateId))).thenReturn(Optional.of(rate));

        ExchangeRateResponseDTO result = service.getBuyRateOf(mnemonics);

        assertEquals(rate.getBuyRate(), result.getBuy());
        assertNull(result.getSell());
    }

    private ExchangeRate testRate(ExchangeRate.ExchangeRateId rateId) {
        ExchangeRate result = new ExchangeRate();

        result.setId(rateId);
        result.setSellRate(11.0);
        result.setBuyRate(10.0);

        return result;
    }
}
