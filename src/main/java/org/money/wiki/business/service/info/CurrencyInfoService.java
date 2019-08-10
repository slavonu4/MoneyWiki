package org.money.wiki.business.service.info;

import java.util.Optional;

public interface CurrencyInfoService {
    Optional<Integer> getCodeOf(String mnemonics);
}
