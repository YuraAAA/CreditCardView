package com.aizenberg.cardcreditview;

import com.aizenberg.cardcreditview.model.Card;

/**
 * Created by Yuriy Aizenberg
 */

public interface ISubmitCallback {

    void onReady(Card card, boolean isValid);

}
