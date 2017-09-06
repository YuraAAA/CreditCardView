package creditcardview.aizenberg.com.creditcardview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Yuriy Aizenberg
 */

public class CreditCardView extends LinearLayout {

    private boolean showingGray = true;
    private AnimatorSet inSet;
    private AnimatorSet outSet;
    private Card card;
    private TextView textCardNumber;
    private TextView textExpiredDate;
    private TextView textCardHolder;
    private TextView textCvvCode;
    private View iconHelpBlue;
    private TextView labelCardNumber;
    private TextView labelExpiredDate;
    private TextView labelCardHolder;
    private TextView labelCvvCode;
    private View iconHelpGray;
    private WrapContentHeightViewPager viewPager;
    private TextInputLayout inputLayoutCardNumber;
    private TextInputEditText inputEditCardNumber;
    private TextInputLayout inputLayoutExpiredDate;
    private TextInputEditText inputEditExpiredDate;
    private TextInputLayout inputLayoutCardHolder;
    private TextInputEditText inputEditCardHolder;
    private TextInputLayout inputLayoutCvvCode;
    private TextInputEditText inputEditCvvCode;
    private ProgressBar progressCircle;
    private ImageView iconLock;
    private ProgressBar progressHorizontal;
    private TextView labelSecureSubmission;
    private CardView cardGray;
    private CardView cardBlue;
    
    
    public CreditCardView(Context context) {
        super(context);
        init();
    }

    public CreditCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreditCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_credit_card, this);
        textCardNumber = (TextView) findViewById(R.id.text_card_number);
        textExpiredDate = (TextView) findViewById(R.id.text_expired_date);
        textCardHolder = (TextView) findViewById(R.id.text_card_holder);
        textCvvCode = (TextView) findViewById(R.id.text_cvv_code);
        iconHelpBlue = findViewById(R.id.icon_help_blue);
        labelCardNumber = (TextView) findViewById(R.id.label_card_number);
        labelExpiredDate = (TextView) findViewById(R.id.label_expired_date);
        labelCardHolder = (TextView) findViewById(R.id.label_card_holder);
        labelCvvCode = (TextView) findViewById(R.id.label_cvv_code);
        iconHelpGray = findViewById(R.id.icon_help_gray);
        viewPager = (WrapContentHeightViewPager) findViewById(R.id.view_pager);
        inputLayoutCardNumber = (TextInputLayout) findViewById(R.id.input_layout_card_number);
        inputEditCardNumber = (TextInputEditText) findViewById(R.id.input_edit_card_number);
        inputLayoutExpiredDate = (TextInputLayout) findViewById(R.id.input_layout_expired_date);
        inputEditExpiredDate = (TextInputEditText) findViewById(R.id.input_edit_expired_date);
        inputLayoutCardHolder = (TextInputLayout) findViewById(R.id.input_layout_card_holder);
        inputEditCardHolder = (TextInputEditText) findViewById(R.id.input_edit_card_holder);
        inputLayoutCvvCode = (TextInputLayout) findViewById(R.id.input_layout_cvv_code);
        inputEditCvvCode = (TextInputEditText) findViewById(R.id.input_edit_cvv_code);
        progressCircle = (ProgressBar) findViewById(R.id.progress_circle);
        iconLock = (ImageView) findViewById(R.id.icon_lock);
        progressHorizontal = (ProgressBar) findViewById(R.id.progress_horizontal);
        labelSecureSubmission = (TextView) findViewById(R.id.label_secure_submission);
        cardGray = (CardView) findViewById(R.id.card_gray);
        cardBlue = (CardView) findViewById(R.id.card_blue);
        afterInit();
    }

    private void afterInit() {
        View.OnClickListener onHelpClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "The CVV Number (\"Card Verification Value\") is a 3 or 4 digit number on your credit and debit cards", Toast.LENGTH_LONG).show();
            }
        };
        iconHelpBlue.setOnClickListener(onHelpClickListener);
        iconHelpGray.setOnClickListener(onHelpClickListener);
        inputEditCardNumber.addTextChangedListener(new TextWatcher() {
            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    flipToBlue();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock || s.length() > 16) {
                    return;
                }
                lock = true;
                for (int i = 4; i < s.length(); i += 5) {
                    if (s.toString().charAt(i) != ' ') {
                        s.insert(i, " ");
                    }
                }
                lock = false;
            }
        });
        inputEditCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textCardNumber.setTextColor(ContextCompat.getColor(getContext(), s.length() == 0 ? R.color.translucent_white : R.color.light_gray));
                textCardNumber.setText(s.length() == 0 ? getContext().getString(R.string.label_card_number) : s);
            }
        });
        inputEditExpiredDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textExpiredDate.setTextColor(ContextCompat.getColor(getContext(), s.length() == 0 ? R.color.translucent_white : R.color.light_gray));
                textExpiredDate.setText(s.length() == 0 ? getContext().getString(R.string.label_expired_date) : s);
            }
        });
        inputEditCardHolder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textCardHolder.setTextColor(ContextCompat.getColor(getContext(), s.length() == 0 ? R.color.translucent_white : R.color.light_gray));
                textCardHolder.setText(s.length() == 0 ? getContext().getString(R.string.label_card_holder) : s);
            }
        });
        inputEditCvvCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textCvvCode.setTextColor(ContextCompat.getColor(getContext(), s.length() == 0 ? R.color.translucent_white : R.color.light_gray));
                textCvvCode.setText(s.length() == 0 ? getContext().getString(R.string.label_cvv_code) : s);
                textCvvCode.setInputType(s.length() == 0 ? (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS) : (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD));
            }
        });


    }



    private void flipToBlue() {
        if (showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = false;

            cardGray.setCardElevation(0);
            cardBlue.setCardElevation(0);

            outSet.setTarget(cardGray);
            outSet.start();

            inSet.setTarget(cardBlue);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    cardBlue.setCardElevation(convertDpToPixel(12, getContext()));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
        inputEditExpiredDate.addTextChangedListener(new TextWatcher() {

            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock || s.length() > 4) {
                    return;
                }
                lock = true;
                if (s.length() > 2 && s.toString().charAt(2) != '/') {
                    s.insert(2, "/");
                }
                lock = false;
            }
        });
        WindowManager systemService = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        Display display = systemService.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        PagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(width / 4, 0, width / 4, 0);
        viewPager.setPageMargin(width / 14);
        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        updateProgressBar(25);
                        inputEditCardNumber.setFocusableInTouchMode(true);
                        inputEditExpiredDate.setFocusable(false);
                        inputEditCardHolder.setFocusable(false);
                        inputEditCvvCode.setFocusable(false);
                        inputEditCardNumber.requestFocus();
                        return;
                    case 1:
                        updateProgressBar(50);
                        inputEditCardNumber.setFocusable(false);
                        inputEditExpiredDate.setFocusableInTouchMode(true);
                        inputEditCardHolder.setFocusable(false);
                        inputEditCvvCode.setFocusable(false);
                        inputEditExpiredDate.requestFocus();
                        return;
                    case 2:
                        updateProgressBar(75);
                        inputEditCardNumber.setFocusable(false);
                        inputEditExpiredDate.setFocusable(false);
                        inputEditCardHolder.setFocusableInTouchMode(true);
                        inputEditCvvCode.setFocusable(false);
                        inputEditCardHolder.requestFocus();
                        return;
                    case 3:
                        updateProgressBar(100);
                        inputEditCardNumber.setFocusable(false);
                        inputEditExpiredDate.setFocusable(false);
                        inputEditCardHolder.setFocusable(false);
                        inputEditCvvCode.setFocusableInTouchMode(true);
                        inputEditCvvCode.requestFocus();
                        return;
                    case 4:
                        inputEditCardNumber.setFocusable(false);
                        inputEditExpiredDate.setFocusable(false);
                        inputEditCardHolder.setFocusable(false);
                        inputEditCvvCode.setFocusable(false);
                        return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    handled = true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit();
                    handled = true;
                }
                return handled;
            }
        };

        inputEditCardNumber.setOnEditorActionListener(onEditorActionListener);
        inputEditExpiredDate.setOnEditorActionListener(onEditorActionListener);
        inputEditCardHolder.setOnEditorActionListener(onEditorActionListener);
        inputEditCvvCode.setOnEditorActionListener(onEditorActionListener);

        inputEditCardNumber.requestFocus();

        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_out);
    }

    private void submit() {

    }


    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.input_layout_card_number;
                    break;
                case 1:
                    resId = R.id.input_layout_expired_date;
                    break;
                case 2:
                    resId = R.id.input_layout_card_holder;
                    break;
                case 3:
                    resId = R.id.input_layout_cvv_code;
                    break;
                case 4:
                    resId = R.id.space;
                    break;

            }
            return findViewById(resId);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }


        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    private void updateProgressBar(int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressHorizontal, "progress", progress);
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

}
