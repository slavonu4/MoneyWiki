package org.money.wiki.domain.dao.info;

import java.util.Optional;

public interface CurrencyInfoDAO {
    Optional<Integer> getCodeOf(String currency);
}
