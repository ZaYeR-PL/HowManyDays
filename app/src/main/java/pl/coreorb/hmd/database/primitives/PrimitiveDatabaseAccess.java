package pl.coreorb.hmd.database.primitives;

import pl.coreorb.hmd.database.Deadline;

/**
 * Created by ZaYeR on 2016-05-03.
 */
public abstract class PrimitiveDatabaseAccess {

    public enum Sorting {
        TITLE_ASC, TITLE_DESC, ENDS_ASC, ENDS_DESC
    }

    protected int pageSize = 10;

    public abstract void getEvents(final int pageNumber, Sorting sorting, final SelectCallback<Deadline> callback);

    public abstract void createEvent(final Deadline deadline, final CreateCallback<Deadline> callback);

    public abstract void updateEvent(final Deadline deadline, final UpdateCallback<Deadline> callback);

    public abstract void deleteEvent(final Deadline deadline, final DeleteCallback<Deadline> callback);

}
