package pl.coreorb.hmd.database.primitives;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public interface CreateCallback<T> {

    void onSuccess(T object);

    void onFailure(int errorCode);

}
