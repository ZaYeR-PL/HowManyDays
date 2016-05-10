package pl.zayer.hmd.database.sqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public class DeadlineTable {

    public static final String TABLE_DEADLINE = "deadlines";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ICON = "icon";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_ENDS_AT = "ends_at";

    private static final String CREATE_TABLE_EVENT = "CREATE TABLE " + TABLE_DEADLINE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_ICON + " INTEGER,"
            + COLUMN_COLOR + " INTEGER,"
            + COLUMN_CREATED_AT + " DATETIME,"
            + COLUMN_ENDS_AT + " DATETIME"
            + ")";

    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EVENT);
    }

    public static void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_DEADLINE);
        onCreate(sqLiteDatabase);
    }

}
