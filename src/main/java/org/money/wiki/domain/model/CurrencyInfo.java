package org.money.wiki.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = CurrencyInfo.TABLE_NAME)
public class CurrencyInfo {
    static final String TABLE_NAME = "currency_info";

    private static final String COLUMN_MNEMONICS = "mnemonics";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_DESCRIPTION = "description";

    private String mnemonics;
    private Integer code;
    private String description;

    @Id
    @Column(name = COLUMN_MNEMONICS)
    public String getMnemonics() {
        return mnemonics;
    }

    public void setMnemonics(String mnemonics) {
        this.mnemonics = mnemonics;
    }

    @Column(name = COLUMN_CODE)
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Column(name = COLUMN_DESCRIPTION)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
