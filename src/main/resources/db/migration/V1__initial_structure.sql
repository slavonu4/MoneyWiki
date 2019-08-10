CREATE TABLE currency_info (
    mnemonics VARCHAR(3) NOT NULL  PRIMARY KEY,
    code INTEGER NOT NULL,
    description VARCHAR
);

CREATE TABLE exchange_rates(
    `date` DATE NOT NULL,
    currency_code INTEGER NOT NULL,
    buy_rate DOUBLE NOT NULL,
    sell_rate DOUBLE NOT NULL,
    PRIMARY KEY(`date`, `currency_code`),
    FOREIGN KEY(currency_code) REFERENCES currency_info(code)
);

INSERT INTO currency_info(mnemonics, code, description) VALUES ('USD', 840, 'Currency of USA and IT sphere');
INSERT INTO currency_info(mnemonics, code, description) VALUES ('EUR', 978, 'Currency of the EU');
