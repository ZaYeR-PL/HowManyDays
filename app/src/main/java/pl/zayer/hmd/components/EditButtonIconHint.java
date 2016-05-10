package pl.zayer.hmd.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pl.zayer.hmd.R;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public class EditButtonIconHint extends LinearLayout {

    private static String TAG = EditButtonIconHint.class.getSimpleName();

    private LinearLayout mainLL;
    private TextView hintTV;
    private RelativeLayout buttonRL;
    private TextView textTV;
    private ImageView iconIV;

    public EditButtonIconHint(Context context) {
        this(context, null);
    }

    public EditButtonIconHint(Context context, int id) {
        this(context, null, id);
    }

    public EditButtonIconHint(Context context, AttributeSet attrs) {
        this(context, attrs, null);
    }

    public EditButtonIconHint(Context context, AttributeSet attrs, Integer id) {
        super(context, attrs);
        if (id != null) this.setId(id);

        Log.d(TAG, "EditButtonIconHint() [id: " + getId() + "]");

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.EditButtonIconHint, 0, 0);
        String hint = a.getString(R.styleable.EditButtonIconHint_hint);
        int icon = a.getResourceId(R.styleable.EditButtonIconHint_buttonIcon, -1);
        int tint = a.getInt(R.styleable.EditButtonIconHint_buttonIconTint, -1);
        String text = a.getString(R.styleable.EditButtonIconHint_buttonText);
        //TODO: research and add onClick
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.component_edit_button_icon_hint, this, true);

        mainLL = (LinearLayout) rootView.findViewById(R.id.main_ll);
        hintTV = (TextView) rootView.findViewById(R.id.hint_tv);
        buttonRL = (RelativeLayout) rootView.findViewById(R.id.button_ll);
        textTV = (TextView) rootView.findViewById(R.id.text_tv);
        iconIV = (ImageView) rootView.findViewById(R.id.icon_iv);

        hintTV.setText(hint);
        iconIV.setImageResource(icon);
        Log.i(TAG, "tint " + tint);
        if (tint != -1) iconIV.setColorFilter(tint);
        textTV.setText(text);

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        buttonRL.setOnClickListener(onClickListener);
    }

    public void setButtonText(String text) {
        textTV.setText(text);
    }

    public void setButtonText(int textResId) {
        textTV.setText(textResId);
    }

    public void setButtonIcon(Drawable icon) {
        iconIV.setImageDrawable(icon);
    }

    public void setButtonIcon(int iconResId) {
        iconIV.setImageResource(iconResId);
    }

    public void setButtonIconTint(int colorResId) {
        iconIV.setColorFilter(colorResId);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Log.d(TAG, "onSaveInstanceState() [id: " + getId() + "]");
        SavedState ss = new SavedState(superState);
        //ss.stateToSave = this.gatherDataToObject();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        Log.d(TAG, "onRestoreInstanceState() [id: " + getId() + "]");
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        //populateDataFromObject(ss.stateToSave);
    }

    static class SavedState extends BaseSavedState {
        //ExampleObject stateToSave;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            //this.stateToSave = in.readParcelable(Condition.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            //out.writeParcelable(this.stateToSave, flags);
        }

        public static final Creator<SavedState> CREATOR =
                new Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

}
