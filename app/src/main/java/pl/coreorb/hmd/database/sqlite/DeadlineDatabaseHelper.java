package pl.coreorb.hmd.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public class DeadlineDatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "DeadlineDatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eventCatcher";

    public DeadlineDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.v(LOG_TAG, "onCreate");
        DeadlineTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.v(LOG_TAG, "onUpgrade (from: " + oldVersion + " to: " + newVersion);
        DeadlineTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
