package name.calvin.meetez;

import android.provider.BaseColumns;

public class Constants implements BaseColumns {
    public static final String EVENTS_TABLE = "events";

    // columns in the events table
    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_TIME = "time";
    public static final String EVENT_VENUE = "venue";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_PARTICIPANTS = "participants";
    public static final String USER_CONTACT = "myContact";

}
