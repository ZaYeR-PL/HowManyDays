package pl.zayer.hmd.database.sqlite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;

import pl.zayer.hmd.database.Deadline;
import pl.zayer.hmd.database.primitives.CreateCallback;
import pl.zayer.hmd.database.primitives.DeleteCallback;
import pl.zayer.hmd.database.primitives.SelectCallback;
import pl.zayer.hmd.database.primitives.UpdateCallback;
import pl.zayer.hmd.database.primitives.PrimitiveDatabaseAccess;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public class DatabaseAccess extends PrimitiveDatabaseAccess {

    private ContentResolver contentResolver;

    public DatabaseAccess(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void getEvents(final int pageNumber, final Sorting sorting, final SelectCallback<Deadline> callback) {
        new AsyncTask<Integer, Void, ArrayList<Deadline>>() {
            @Override
            protected ArrayList<Deadline> doInBackground(Integer[] params) {
                int pageNumber = params[0];

                String[] projection = { DeadlineTable.COLUMN_ID, DeadlineTable.COLUMN_NAME, DeadlineTable.COLUMN_ICON,
                        DeadlineTable.COLUMN_COLOR, DeadlineTable.COLUMN_CREATED_AT, DeadlineTable.COLUMN_ENDS_AT};

                Cursor cursor = contentResolver.query(
                        DeadlineContentProvider.EVENT_CONTENT_URI,
                        projection,
                        null,
                        null,
                        getSorting(sorting));

                if (cursor == null) {
                    return null;
                }

                ArrayList<Deadline> result;
                if (cursor.getCount() == 0) {
                    result = new ArrayList<>();
                } else {
                    result = new ArrayList<>();
                    while (cursor.moveToNext()) {
                        result.add(cursorToEvent(cursor));
                    }
                }
                cursor.close();
                return result;
            }

            protected void onPostExecute(ArrayList<Deadline> result) {
                if (result == null) {
                    callback.onFailure(-1);
                } else {
                    callback.onSuccess(result);
                }
            }
        }.execute(pageNumber);
    }

    private String getSorting(Sorting sorting) {
        String result = "";
        switch (sorting) {
            case TITLE_ASC: result = DeadlineTable.COLUMN_NAME + " ASC"; break;
            case TITLE_DESC: result = DeadlineTable.COLUMN_NAME + " DESC"; break;
            case ENDS_ASC: result = DeadlineTable.COLUMN_ENDS_AT + " ASC"; break;
            case ENDS_DESC: result = DeadlineTable.COLUMN_ENDS_AT + " DESC"; break;
        }
        return result;
    }

    private Deadline cursorToEvent(Cursor cursor) {
        Deadline deadline = new Deadline();
        deadline.id = cursor.getLong(cursor.getColumnIndex(DeadlineTable.COLUMN_ID));
        deadline.name = cursor.getString(cursor.getColumnIndex(DeadlineTable.COLUMN_NAME));
        deadline.icon = cursor.getInt(cursor.getColumnIndex(DeadlineTable.COLUMN_ICON));
        deadline.color = cursor.getInt(cursor.getColumnIndex(DeadlineTable.COLUMN_COLOR));
        deadline.createdAt.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DeadlineTable.COLUMN_CREATED_AT)));
        deadline.endsAt.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DeadlineTable.COLUMN_ENDS_AT)));
        return deadline;
    }

    public void createEvent(final Deadline deadline, final CreateCallback<Deadline> callback) {
        new AsyncTask<Deadline, Void, Long>() {
            @Override
            protected Long doInBackground(Deadline[] params) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DeadlineTable.COLUMN_NAME, params[0].name);
                contentValues.put(DeadlineTable.COLUMN_ICON, params[0].icon);
                contentValues.put(DeadlineTable.COLUMN_COLOR, params[0].color);
                contentValues.put(DeadlineTable.COLUMN_CREATED_AT, params[0].createdAt.getTimeInMillis());
                contentValues.put(DeadlineTable.COLUMN_ENDS_AT, params[0].endsAt.getTimeInMillis());

                long id = -1;
                Uri tempUri = contentResolver.insert(DeadlineContentProvider.EVENT_CONTENT_URI, contentValues);
                if (tempUri != null) {
                    id = Long.parseLong(tempUri.getLastPathSegment());
                }
                return id;
            }

            protected void onPostExecute(Long result) {
                if (result == -1) {
                    callback.onFailure(-1);
                } else {
                    deadline.id = result;
                    callback.onSuccess(deadline);
                }
            }
        }.execute(deadline);
    }

    public void updateEvent(final Deadline deadline, final UpdateCallback<Deadline> callback) {
        new AsyncTask<Deadline, Void, Integer>() {
            @Override
            protected Integer doInBackground(Deadline[] params) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DeadlineTable.COLUMN_NAME, params[0].name);
                contentValues.put(DeadlineTable.COLUMN_ICON, params[0].icon);
                contentValues.put(DeadlineTable.COLUMN_COLOR, params[0].color);
                contentValues.put(DeadlineTable.COLUMN_CREATED_AT, params[0].createdAt.getTimeInMillis());
                contentValues.put(DeadlineTable.COLUMN_ENDS_AT, params[0].endsAt.getTimeInMillis());

                int rowsUpdated = contentResolver.update(
                        DeadlineContentProvider.EVENT_CONTENT_URI,
                        contentValues,
                        DeadlineTable.COLUMN_ID + " = ?",
                        new String[] { params[0].id + "" } );

                return rowsUpdated;
            }

            protected void onPostExecute(Integer result) {
                if (result == 1) {
                    callback.onSuccess(deadline);
                } else {
                    callback.onFailure(-1);
                }
            }
        }.execute(deadline);
    }

    public void deleteEvent(final Deadline deadline, final DeleteCallback<Deadline> callback) {
        new AsyncTask<Deadline, Void, Integer>() {
            @Override
            protected Integer doInBackground(Deadline[] params) {
                int deletedRowsCount = contentResolver.delete(
                        DeadlineContentProvider.EVENT_CONTENT_URI,
                        DeadlineTable.COLUMN_ID + " = ?",
                        new String[] { params[0].id + "" }
                );

                return deletedRowsCount;
            }

            protected void onPostExecute(Integer result) {
                if (result == 1) {
                    callback.onSuccess(deadline);
                } else {
                    callback.onFailure(-1);
                }
            }
        }.execute(deadline);
    }

}
