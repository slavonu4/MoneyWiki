package org.money.wiki.domain.dao.info;

import org.money.wiki.domain.repository.CurrencyInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CurrencyInfoDAOImpl implements CurrencyInfoDAO {
    private final CurrencyInfoRepository repository;

    public CurrencyInfoDAOImpl(CurrencyInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Integer> getCodeOf(String currency) {
        return repository.findCodeOf(currency);
    }
}
