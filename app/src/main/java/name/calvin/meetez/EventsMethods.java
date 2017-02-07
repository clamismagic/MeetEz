package name.calvin.meetez;

import static name.calvin.meetez.Constants.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EventsMethods {
    private static String[] FROM = {_ID, EVENT_NAME, EVENT_DATE, EVENT_TIME, EVENT_VENUE, EVENT_DESCRIPTION, EVENT_PARTICIPANTS, USER_CONTACT, };
    private static String ORDER_BY = _ID + " ASC";

    public void addEvent (String eventName, String eventDate, String eventTime, String eventVenue, String eventDescription, String eventParticipants, String userContact, EventsData events) {
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_NAME, eventName);
        values.put(EVENT_DATE, eventDate);
        values.put(EVENT_TIME, eventTime);
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

    public String showEvents(Cursor cursor) {
        StringBuilder builder = new StringBuilder("");
        while (cursor.moveToNext()) {
            String eventName = cursor.getString(1);
            String eventDate = cursor.getString(2);
            String eventTime = cursor.getString(3);
            String eventVenue = cursor.getString(4);
            String eventDescription = cursor.getString(5);
            String eventParticipants = cursor.getString(6);
            String userContact = cursor.getInt(7) + "";
            builder.append(eventName).append("\t");
            builder.append(eventDate).append("\t");
            builder.append(eventTime).append("\t");
            builder.append(eventVenue).append("\t");
            builder.append(eventDescription).append("\t");
            builder.append(eventParticipants).append("\t");
            builder.append(userContact).append("\t\n");
        }
        return builder.toString();
    }
}
