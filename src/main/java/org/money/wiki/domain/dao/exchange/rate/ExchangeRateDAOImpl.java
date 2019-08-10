package org.money.wiki.domain.dao.exchange.rate;

import org.money.wiki.domain.model.ExchangeRate;
import org.money.wiki.domain.repository.ExchangeRateRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ExchangeRateDAOImpl implements ExchangeRateDAO {
    private final ExchangeRateRepository repository;

    public ExchangeRateDAOImpl(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ExchangeRate> get(ExchangeRate.ExchangeRateId rateId) {
        return repository.findById(rateId);
    }

    @Override
    public ExchangeRate save(ExchangeRate rate) {
        return repository.save(rate);
    }
}
