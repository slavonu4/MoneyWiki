package org.money.wiki.domain.repository;

import org.money.wiki.domain.model.CurrencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CurrencyInfoRepository extends JpaRepository<CurrencyInfo, String> {
    @Query("SELECT i.code FROM CurrencyInfo i WHERE i.mnemonics=:mnemonics")
    Optional<Integer> findCodeOf(@Param("mnemonics") String mnemonics);
}
