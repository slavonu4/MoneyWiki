package org.money.wiki.business.service.info;

import org.money.wiki.domain.dao.info.CurrencyInfoDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CurrencyInfoServiceImpl implements CurrencyInfoService {
    private final CurrencyInfoDAO currencyInfoDAO;

    public CurrencyInfoServiceImpl(CurrencyInfoDAO currencyInfoDAO) {
        this.currencyInfoDAO = currencyInfoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Integer> getCodeOf(String mnemonics) {
        String normalizedMnemonics = normalizeMnemonics(mnemonics);

        return currencyInfoDAO.getCodeOf(normalizedMnemonics);
    }

    // To deal with difference between 'Usd', "USd', etc
    private String normalizeMnemonics(String mnemonics){
        return mnemonics.trim().toUpperCase();
    }
}
