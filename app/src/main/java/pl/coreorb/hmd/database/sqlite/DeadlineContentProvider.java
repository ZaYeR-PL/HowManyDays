package pl.coreorb.hmd.database.sqlite;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public class DeadlineContentProvider extends ContentProvider {

    private DeadlineDatabaseHelper database;

    private static final int EVENTS = 10;
    private static final int EVENT_ID = 20;

    private static final String AUTHORITY = "pl.coreorb.hmd.database.sqlite";
    private static final String EVENT_BASE_PATH = "events";

    public static final Uri EVENT_CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + EVENT_BASE_PATH);
    public static final String EVENT_CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/events";
    public static final String EVENT_CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/event";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, EVENT_BASE_PATH, EVENTS);
        sURIMatcher.addURI(AUTHORITY, EVENT_BASE_PATH + "/#", EVENT_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DeadlineDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch(sURIMatcher.match(uri)) {
            case EVENTS:
                checkColumnsEvent(projection);
                queryBuilder.setTables(DeadlineTable.TABLE_DEADLINE);
                break;
            case EVENT_ID:
                checkColumnsEvent(projection);
                queryBuilder.setTables(DeadlineTable.TABLE_DEADLINE);
                queryBuilder.appendWhere(DeadlineTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = database.getWritableDatabase();
        Uri _uri;
        long id;
        switch(sURIMatcher.match(uri)) {
            case EVENTS:
                id = db.insert(DeadlineTable.TABLE_DEADLINE, null, contentValues);
                _uri = Uri.parse(EVENT_BASE_PATH + "/" + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return _uri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsDeleted;
        String id;
        switch(sURIMatcher.match(uri)) {
            case EVENTS:
                rowsDeleted = db.delete(DeadlineTable.TABLE_DEADLINE, selection, selectionArgs);
                break;
            case EVENT_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(DeadlineTable.TABLE_DEADLINE, DeadlineTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = db.delete(DeadlineTable.TABLE_DEADLINE, DeadlineTable.COLUMN_ID + "=" + id
                    + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    public long count(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = database.getReadableDatabase();

        String table;
        switch(sURIMatcher.match(uri)) {
            case EVENTS:
                table = DeadlineTable.TABLE_DEADLINE;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return DatabaseUtils.queryNumEntries(db, table, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = database.getWritableDatabase();
        int rowsUpdated;
        String id;
        switch(sURIMatcher.match(uri)) {
            case EVENTS:
                rowsUpdated = db.update(DeadlineTable.TABLE_DEADLINE, contentValues, selection,
                        selectionArgs);
                break;
            case EVENT_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(DeadlineTable.TABLE_DEADLINE, contentValues,
                            DeadlineTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = db.update(DeadlineTable.TABLE_DEADLINE, contentValues,
                            DeadlineTable.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    private void checkColumnsEvent(String[] projection) {
        String[] available = {
                DeadlineTable.COLUMN_CREATED_AT,  DeadlineTable.COLUMN_ENDS_AT,
                DeadlineTable.COLUMN_ICON, DeadlineTable.COLUMN_COLOR,
                DeadlineTable.COLUMN_ID, DeadlineTable.COLUMN_NAME };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
