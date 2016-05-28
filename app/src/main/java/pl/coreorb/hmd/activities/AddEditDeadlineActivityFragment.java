package pl.coreorb.hmd.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.coreorb.hmd.R;
import pl.coreorb.hmd.database.Deadline;
import pl.coreorb.selectiondialogs.data.SelectableColor;
import pl.coreorb.selectiondialogs.data.SelectableIcon;
import pl.coreorb.selectiondialogs.dialogs.ColorSelectDialog;
import pl.coreorb.selectiondialogs.dialogs.IconSelectDialog;
import pl.coreorb.selectiondialogs.utils.ColorPalettes;
import pl.coreorb.selectiondialogs.views.SelectedItemView;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditDeadlineActivityFragment extends Fragment implements IconSelectDialog.OnIconSelectedListener,
        ColorSelectDialog.OnColorSelectedListener {

    private static final String TAG = AddEditDeadlineActivityFragment.class.getSimpleName();

    private static final String TAG_DIALOG_FRAGMENT_DATE = "TAG_DIALOG_FRAGMENT_DATE";
    private static final String TAG_DIALOG_FRAGMENT_ICONS = "TAG_DIALOG_FRAGMENT_ICONS";
    private static final String TAG_DIALOG_FRAGMENT_COLORS = "TAG_DIALOG_FRAGMENT_COLORS";


    @BindView(R.id.icon_siv)
    SelectedItemView iconSIV;
    @BindView(R.id.color_siv)
    SelectedItemView colorSIV;
    @BindView(R.id.text_b)
    Button testB;

    private Deadline currentDeadline;

    public AddEditDeadlineActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit_deadline, container, false);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this, rootView);

        testB.setText("test2");

        currentDeadline = new Deadline(getContext());
        populateObject(currentDeadline);
        Log.i(TAG, "current end " + currentDeadline.endsAt);

        return rootView;
    }

    @OnClick(R.id.text_b)
    public void TestButtonClicked(View view) {
        Log.i("TAGG", "BUTTON CLICKED!!!!!!!!!!!!");
    }

    public void showDateDialog2() {
        Calendar now = new GregorianCalendar();
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentDeadline.endsAt.set(year, monthOfYear-1, dayOfMonth);
                //dateEB.setButtonText(TimeDate.getFormatedDate(currentDeadline.endsAt));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(getContext(), callback,
                currentDeadline.endsAt.get(Calendar.YEAR),
                currentDeadline.endsAt.get(Calendar.MONTH)+1,
                currentDeadline.endsAt.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    public void showDateDialog() {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                        currentDeadline.endsAt.set(year, monthOfYear-1, dayOfMonth);
                        //dateEB.setButtonText(TimeDate.getFormatedDate(currentDeadline.endsAt));
                    }
                })
                .setPreselectedDate(currentDeadline.endsAt.get(Calendar.YEAR),
                        currentDeadline.endsAt.get(Calendar.MONTH)+1,
                        currentDeadline.endsAt.get(Calendar.DAY_OF_MONTH));
        cdp.show(getFragmentManager(), "DATE_PICKER_TAG");
    }

    @OnClick(R.id.icon_siv)
    public void showIconDialog() {
        Log.i(TAG, "icons " + sampleIcons());
        new IconSelectDialog.Builder(getContext())
                .setIcons(sampleIcons())
                .setTitle("Test")
                .setOnIconSelectedListener(this)
                .build().show(getFragmentManager(), TAG_DIALOG_FRAGMENT_ICONS);
    }

    @Override
    public void onIconSelected(SelectableIcon selectedItem) {
        currentDeadline.icon = selectedItem.getDrawableResId();
        iconSIV.setSelectedIcon(selectedItem);
    }

    @OnClick(R.id.color_siv)
    public void showColorDialog() {
        new ColorSelectDialog.Builder(getContext())
                .setColors(ColorPalettes.loadMaterialDesignColors500(getContext(), false))
                .setOnColorSelectedListener(this)
                .build().show(getFragmentManager(), TAG_DIALOG_FRAGMENT_COLORS);
    }

    @Override
    public void onColorSelected(SelectableColor selectedItem) {
        currentDeadline.color = selectedItem.getColor();
        colorSIV.setSelectedColor(selectedItem);
    }

    private void populateObject(Deadline deadline) {
        //dateEB.setButtonText(TimeDate.getFormatedDate(deadline.endsAt));
        //TODO: iconEB
        //colorEB.setButtonIconTint(deadline.color);
        //colorEB.setButtonText(Colors.getColorName(getContext(), deadline.color));
    }

    /**
     * Creates sample ArrayList of icons to display in dialog.
     *
     * @return sample icons
     */
    private static ArrayList<SelectableIcon> sampleIcons() {
        ArrayList<SelectableIcon> selectionDialogsColors = new ArrayList<>();
        selectionDialogsColors.add(new SelectableIcon("puzzle", "Puzzle", R.drawable.sample_icon_puzzle));
        selectionDialogsColors.add(new SelectableIcon("android", "Android", R.drawable.sample_icon_android));
        selectionDialogsColors.add(new SelectableIcon("bookmark", "Bookmark", R.drawable.sample_icon_bookmark));
        return selectionDialogsColors;
    }
}
