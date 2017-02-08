package name.calvin.meetez;

import static name.calvin.meetez.Constants.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventsData extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mapp.db";
    private static final int DATABASE_VERSION = 1;

    // creating a helper object
    public EventsData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EVENTS_TABLE
                + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EVENT_NAME + " TEXT NOT NULL, "
                + EVENT_DATE + " DATE NOT NULL, "
                + EVENT_TIME + " TEXT NOT NULL, "
                + EVENT_VENUE + " TEXT NOT NULL, "
                + EVENT_DESCRIPTION + " TEXT NOT NULL, "
                + EVENT_PARTICIPANTS + " TEXT NOT NULL,"
                + USER_CONTACT + " INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EVENTS_TABLE);
        onCreate(db);
    }
}
