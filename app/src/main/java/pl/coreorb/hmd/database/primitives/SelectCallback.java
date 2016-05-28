package pl.coreorb.hmd.database.primitives;

import java.util.ArrayList;

/**
 * Created by ZaYeR on 2016-05-03.
 */
public interface SelectCallback<T> {

    void onSuccess(ArrayList<T> objects);

    void onFailure(int errorCode);

}
