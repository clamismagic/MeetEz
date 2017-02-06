package name.calvin.meetez;

import static name.calvin.meetez.Constants.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EventsMethods {
    private static String[] FROM = {_ID, EVENT_NAME, EVENT_DATE, EVENT_VENUE, EVENT_DESCRIPTION, EVENT_PARTICIPANTS, USER_CONTACT, };
    private static String ORDER_BY = _ID + " ASC";

    public void addEvent (String eventName, String eventDate, String eventVenue, String eventDescription, String eventParticipants, String userContact, EventsData events) {
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, eventName);
        values.put(EVENT_DATE, eventDate);
        values.put(EVENT_VENUE, eventVenue);
        values.put(EVENT_DESCRIPTION, eventDescription);
        values.put(EVENT_PARTICIPANTS, eventParticipants);
        values.put(USER_CONTACT, userContact);
        db.insertOrThrow(EVENTS_TABLE, null, values);
    }

    public Cursor getEvents(EventsData events) {
        SQLiteDatabase db = events.getReadableDatabase();
        return db.query(EVENTS_TABLE, FROM, null, null, null, null, ORDER_BY);
    }

    // TODO write showEvents() function
}
