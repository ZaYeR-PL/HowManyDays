package pl.zayer.hmd.activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pl.zayer.hmd.R;
import pl.zayer.hmd.components.EditButtonIconHint;
import pl.zayer.hmd.database.Deadline;
import pl.zayer.hmd.utils.Colors;
import pl.zayer.hmd.utils.TimeDate;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditDeadlineActivityFragment extends Fragment {

    private static final String TAG = AddEditDeadlineActivityFragment.class.getSimpleName();

    private EditButtonIconHint dateEB;
    private EditButtonIconHint iconEB;
    private EditButtonIconHint colorEB;

    private Deadline currentDeadline;

    public AddEditDeadlineActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit_deadline, container, false);

        dateEB = (EditButtonIconHint) rootView.findViewById(R.id.date_eb);
        iconEB = (EditButtonIconHint) rootView.findViewById(R.id.icon_eb);
        colorEB = (EditButtonIconHint) rootView.findViewById(R.id.color_eb);

        currentDeadline = new Deadline(getContext());
        populateObject(currentDeadline);
        Log.i(TAG, "current end " + currentDeadline.endsAt);

        dateEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        //TODO: add icon selector
        iconEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIconDialog();
            }
        });
        colorEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });

        return rootView;
    }

    public void showDateDialog2() {
        Calendar now = new GregorianCalendar();
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentDeadline.endsAt.set(year, monthOfYear-1, dayOfMonth);
                dateEB.setButtonText(TimeDate.getFormatedDate(currentDeadline.endsAt));
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
                        dateEB.setButtonText(TimeDate.getFormatedDate(currentDeadline.endsAt));
                    }
                })
                .setPreselectedDate(currentDeadline.endsAt.get(Calendar.YEAR),
                        currentDeadline.endsAt.get(Calendar.MONTH)+1,
                        currentDeadline.endsAt.get(Calendar.DAY_OF_MONTH));
        cdp.show(getFragmentManager(), "DATE_PICKER_TAG");
    }

    public void showIconDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
        builderSingle.setIcon(R.drawable.main_fab_add);
        builderSingle.setTitle("Select One Name:-");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Hardik");
        arrayAdapter.add("Archit");
        arrayAdapter.add("Jignesh");
        arrayAdapter.add("Umang");
        arrayAdapter.add("Gatti");

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                AddEditDeadlineActivityFragment.this.getContext());
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();
    }

    private void showColorDialog() {
        new SpectrumDialog.Builder(getContext())
                .setColors(R.array.colors)
                .setSelectedColor(currentDeadline.color)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            currentDeadline.color = color;
                            colorEB.setButtonIconTint(color);
                            colorEB.setButtonText(Colors.getColorName(getContext(), color));
                        }
                    }
                })
                .build().show(getFragmentManager(), "SPECTRUM_DIALOG");
    }

    private void populateObject(Deadline deadline) {
        dateEB.setButtonText(TimeDate.getFormatedDate(deadline.endsAt));
        //TODO: iconEB
        colorEB.setButtonIconTint(deadline.color);
        colorEB.setButtonText(Colors.getColorName(getContext(), deadline.color));
    }
}
