package org.money.wiki.presentation.exception;

public class BadMnemonicsException extends Exception{
    private final String mnemonics;

    public BadMnemonicsException(String mnemonics) {
        this.mnemonics = mnemonics;
    }

    public String getMnemonics() {
        return mnemonics;
    }
}
