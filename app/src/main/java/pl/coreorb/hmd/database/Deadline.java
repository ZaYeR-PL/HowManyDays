package pl.coreorb.hmd.database;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public class Deadline implements Parcelable {

    public long id;
    public String name;
    public int icon;
    public int color;
    public Calendar createdAt;
    public Calendar endsAt;

    public Deadline() {
        id = -1;
        name = "";
        icon = -1;
        color = -1;
        createdAt = new GregorianCalendar(1970, 0, 1);
        endsAt = new GregorianCalendar(1970, 0, 1);
    }

    public Deadline(Context context) {
        id = -1;
        name = "";
        //TODO: color = ContextCompat.getColor(context, R.color.colorsArrayRed);
        icon = 0;
        createdAt = new GregorianCalendar();
        endsAt = new GregorianCalendar();
    }

    public Deadline(int id, String name, int icon, int color, Calendar createdAt, Calendar endsAt) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.createdAt = createdAt;
        this.endsAt = endsAt;
    }

    public Deadline(Parcel in) {
        id = in.readLong();
        name = in.readString();
        icon = in.readInt();
        color = in.readInt();
        createdAt = new GregorianCalendar();
        createdAt.setTimeInMillis(in.readLong());
        endsAt = new GregorianCalendar();
        endsAt.setTimeInMillis(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Deadline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon=" + icon +
                ", color=" + color +
                ", createdAt=" + createdAt +
                ", endsAt=" + endsAt +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(icon);
        dest.writeInt(color);
        dest.writeLong(createdAt.getTimeInMillis());
        dest.writeLong(endsAt.getTimeInMillis());
    }

    public static final Creator<Deadline> CREATOR = new Creator<Deadline>() {

        @Override
        public Deadline createFromParcel(Parcel source) {
            return new Deadline(source);
        }

        @Override
        public Deadline[] newArray(int size) {
            return new Deadline[size];
        }
    };

}
