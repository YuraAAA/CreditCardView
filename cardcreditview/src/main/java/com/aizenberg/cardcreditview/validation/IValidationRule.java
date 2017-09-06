package com.aizenberg.cardcreditview.validation;

/**
 * Created by Yuriy Aizenberg
 */

public interface IValidationRule {

    boolean isCardNumberValid();

    boolean isCardExpireValid();

    boolean isCardHolderValid();

    boolean isCardCVVValid();

    boolean isSummaryDataValid();

}
