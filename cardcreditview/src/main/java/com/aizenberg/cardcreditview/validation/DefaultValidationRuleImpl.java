package com.aizenberg.cardcreditview.validation;

import android.text.TextUtils;

import com.aizenberg.cardcreditview.model.Card;

/**
 * Created by Yuriy Aizenberg
 */

public class DefaultValidationRuleImpl implements IValidationRule {

    private Card card;
    private static final int MIN_CHARS_IN_NUMBER = 12;
    private static final int MAX_CHARS_IN_NUMBER = 19;
    private static final int MIN_CVV_COUNT = 3;
    private static final int MAX_CVV_COUNT = 4;

    public DefaultValidationRuleImpl(Card card) {
        this.card = card;
    }

    @Override
    public boolean isCardNumberValid() {
        String cardNumber = card.getCardNumber();
        if (cardNumber == null) return false;
        cardNumber = sanitize(cardNumber);
        return cardNumber.length() >= MIN_CHARS_IN_NUMBER && cardNumber.length() <= MAX_CHARS_IN_NUMBER;
    }

    @Override
    public boolean isCardExpireValid() {
        String expiredDate = card.getExpiredDate();
        if (expiredDate == null) return false;
        expiredDate = sanitize(expiredDate);
        if (expiredDate.length() != 4) return false;
        String month = String.valueOf(expiredDate.charAt(0)) + expiredDate.charAt(1);
        Integer integer = Integer.valueOf(month);
        if (integer < 1 || integer > 12) return false;
        return true;
    }

    @Override
    public boolean isCardHolderValid() {
        String cardHolder = card.getCardHolder();
        return !TextUtils.isEmpty(cardHolder);
    }

    @Override
    public boolean isCardCVVValid() {
        String cvvCode = card.getCvvCode();
        if (cvvCode == null) return false;
        cvvCode = sanitize(cvvCode);
        return cvvCode.length() >= MIN_CVV_COUNT && cvvCode.length() <= MAX_CVV_COUNT;
    }

    @Override
    public boolean isSummaryDataValid() {
        return isCardCVVValid() && isCardExpireValid() && isCardHolderValid() && isCardNumberValid();
    }


    private String sanitize(String input) {
        return input.replaceAll("[^0-9]", "");
    }
}
