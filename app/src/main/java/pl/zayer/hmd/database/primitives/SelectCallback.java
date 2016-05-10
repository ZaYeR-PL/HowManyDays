package pl.zayer.hmd.database.primitives;

import java.util.ArrayList;

/**
 * Created by ZaYeR on 2016-05-03.
 */
public interface SelectCallback<T> {

    public void onSuccess(ArrayList<T> objects);

    public void onFailure(int errorCode);

}
