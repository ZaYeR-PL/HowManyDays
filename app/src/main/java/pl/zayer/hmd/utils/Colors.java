package pl.zayer.hmd.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.HashMap;

import pl.zayer.hmd.R;

/**
 * Created by ZaYeR on 2016-05-07.
 */
public class Colors {

    private static final String TAG = Colors.class.getSimpleName();

    private static HashMap<Integer, String> colorNames;

    public static String getColorName(Context context, int color) {
        if (colorNames == null) {
            colorNames = new HashMap<>();
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayRed), context.getString(R.string.colorNameRed));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayPink), context.getString(R.string.colorNamePink));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayPurple), context.getString(R.string.colorNamePurple));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayDeepPurple), context.getString(R.string.colorNameDeepPurple));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayIndigo), context.getString(R.string.colorNameIndigo));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayBlue), context.getString(R.string.colorNameBlue));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayLightBlue), context.getString(R.string.colorNameLightBlue));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayCyan), context.getString(R.string.colorNameCyan));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayTeal), context.getString(R.string.colorNameTeal));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayGreen), context.getString(R.string.colorNameGreen));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayLightGreen), context.getString(R.string.colorNameLightGreen));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayLime), context.getString(R.string.colorNameLime));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayYellow), context.getString(R.string.colorNameYellow));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayAmber), context.getString(R.string.colorNameAmber));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayOrange), context.getString(R.string.colorNameOrange));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayDeepOrange), context.getString(R.string.colorNameDeepOrange));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayBrown), context.getString(R.string.colorNameBrown));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayGrey), context.getString(R.string.colorNameGrey));
            colorNames.put(ContextCompat.getColor(context, R.color.colorsArrayBlueGrey), context.getString(R.string.colorNameBlueGrey));
            colorNames.put(-1, context.getString(R.string.colorNameUndefined));
        }
        return colorNames.get(color);

    }

}
