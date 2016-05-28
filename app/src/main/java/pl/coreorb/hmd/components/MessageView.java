package pl.coreorb.hmd.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.coreorb.hmd.R;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public class MessageView extends LinearLayout {

    private static String TAG = MessageView.class.getSimpleName();

    private TextView textTV;
    private ImageView iconIV;

    public MessageView(Context context) {
        this(context, null);
    }

    public MessageView(Context context, int id) {
        this(context, null, id);
    }

    public MessageView(Context context, AttributeSet attrs) {
        this(context, attrs, null);
    }

    public MessageView(Context context, AttributeSet attrs, Integer id) {
        super(context, attrs);
        if (id != null) this.setId(id);

        Log.d(TAG, "MessageView() [id: " + getId() + "]");

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.component_message_view, this, true);

        textTV = (TextView) rootView.findViewById(R.id.text_tv);
        iconIV = (ImageView) rootView.findViewById(R.id.icon_iv);
    }

    public void setMessage(Drawable icon, int textResource, Color color) {
        textTV.setText(textResource);
        iconIV.setImageDrawable(icon);
        //TODO: add tining of components
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

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }

}
