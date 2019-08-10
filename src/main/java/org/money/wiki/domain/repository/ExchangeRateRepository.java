package org.money.wiki.domain.repository;

import org.money.wiki.domain.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, ExchangeRate.ExchangeRateId> {
}
