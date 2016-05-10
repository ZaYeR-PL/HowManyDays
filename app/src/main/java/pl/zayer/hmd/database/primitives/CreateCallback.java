package pl.zayer.hmd.database.primitives;

/**
 * Created by ZaYeR on 2016-05-02.
 */
public interface CreateCallback<T> {

    public void onSuccess(T object);

    public void onFailure(int errorCode);

}
