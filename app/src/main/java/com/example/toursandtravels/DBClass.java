package com.example.toursandtravels;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBClass extends SQLiteOpenHelper {

    private static final String DB_NAME = "toursandtravels";
    private static final int DB_VERSION = 5; // Incremented for exact landmark photos per city

    // Table: Users
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_MOBILENO = "mobileno";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_TYPE = "usertype";

    // Table: Packages (Tourist Places & City Tours)
    public static final String TABLE_PACKAGES = "packages";
    public static final String COLUMN_PKG_ID = "id";
    public static final String COLUMN_PKG_NAME = "name";
    public static final String COLUMN_PKG_LOCATION = "location";
    public static final String COLUMN_PKG_DESCRIPTION = "description";
    public static final String COLUMN_PKG_PRICE = "price";
    public static final String COLUMN_PKG_LATITUDE = "latitude";
    public static final String COLUMN_PKG_LONGITUDE = "longitude";
    public static final String COLUMN_PKG_IMAGE = "imageResourceId";
    public static final String COLUMN_PKG_RATING = "rating";
    public static final String COLUMN_PKG_CATEGORY = "category";
    public static final String COLUMN_PKG_DURATION = "duration";
    public static final String COLUMN_PKG_BEST_TIME = "bestTime";
    public static final String COLUMN_PKG_HIGHLIGHTS = "highlights";

    // Table: Favorites (Wishlist)
    public static final String TABLE_FAVORITES = "favorites";
    public static final String COLUMN_FAV_ID = "id";
    public static final String COLUMN_FAV_PKG_ID = "package_id";

    // Table: Bookings
    public static final String TABLE_BOOKINGS = "bookings";
    public static final String COLUMN_BKG_ID = "id";
    public static final String COLUMN_BKG_FORT = "fort";
    public static final String COLUMN_BKG_LOCATION = "location";
    public static final String COLUMN_BKG_USERNAME = "username";
    public static final String COLUMN_BKG_MOBILENO = "mobileno";
    public static final String COLUMN_BKG_ADDRESS = "address";

    // Table: Configuration
    public static final String TABLE_CONFIGURATION = "Configuration";
    public static final String COLUMN_CNAME = "CName";
    public static final String COLUMN_CVALUE = "CValue";

    private static SQLiteDatabase database;

    public DBClass(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_MOBILENO + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT, " +
                COLUMN_USER_TYPE + " TEXT)";
        db.execSQL(createUsers);

        String createPackages = "CREATE TABLE IF NOT EXISTS " + TABLE_PACKAGES + " (" +
                COLUMN_PKG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PKG_NAME + " TEXT, " +
                COLUMN_PKG_LOCATION + " TEXT, " +
                COLUMN_PKG_DESCRIPTION + " TEXT, " +
                COLUMN_PKG_PRICE + " TEXT, " +
                COLUMN_PKG_LATITUDE + " TEXT, " +
                COLUMN_PKG_LONGITUDE + " TEXT, " +
                COLUMN_PKG_IMAGE + " INTEGER, " +
                COLUMN_PKG_RATING + " TEXT, " +
                COLUMN_PKG_CATEGORY + " TEXT, " +
                COLUMN_PKG_DURATION + " TEXT, " +
                COLUMN_PKG_BEST_TIME + " TEXT, " +
                COLUMN_PKG_HIGHLIGHTS + " TEXT)";
        db.execSQL(createPackages);

        String createFavorites = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITES + " (" +
                COLUMN_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FAV_PKG_ID + " TEXT UNIQUE)";
        db.execSQL(createFavorites);

        String createBookings = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKINGS + " (" +
                COLUMN_BKG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BKG_FORT + " TEXT, " +
                COLUMN_BKG_LOCATION + " TEXT, " +
                COLUMN_BKG_USERNAME + " TEXT, " +
                COLUMN_BKG_MOBILENO + " TEXT, " +
                COLUMN_BKG_ADDRESS + " TEXT)";
        db.execSQL(createBookings);

        String createConfig = "CREATE TABLE IF NOT EXISTS " + TABLE_CONFIGURATION + " (" +
                COLUMN_CNAME + " VARCHAR, " +
                COLUMN_CVALUE + " VARCHAR)";
        db.execSQL(createConfig);

        seedData(db);
    }

    private void seedData(SQLiteDatabase db) {
        // Default accounts
        db.execSQL("INSERT INTO " + TABLE_USERS + " (name, email, mobileno, password, usertype) VALUES ('Admin', 'admin@gmail.com', '1234567890', '123456', 'admin')");
        db.execSQL("INSERT INTO " + TABLE_USERS + " (name, email, mobileno, password, usertype) VALUES ('Aarav Sharma', 'user@gmail.com', '9876543210', '123456', 'user')");

        // Rich city-focused tourist places & guided packages
        insertSeedPackage(db, "Gateway of India & Marine Drive", "Mumbai",
                "Iconic waterfront arch monument, ferry ride & sunset promenade walk.",
                "1499", "18.9220", "72.8347", R.drawable.city_mumbai_gateway,
                "4.8", "Heritage", "1 Day", "Oct – Mar (Evening)", "Guided Walk, Harbor Ferry, Photo Stops");

        insertSeedPackage(db, "Elephanta Caves Heritage Tour", "Mumbai",
                "UNESCO World Heritage rock-cut cave temples via scenic harbor boat.",
                "1899", "18.9633", "72.9315", R.drawable.city_mumbai_elephanta,
                "4.7", "UNESCO", "Half Day", "Nov – Feb (Morning)", "Ferry Ticket, Heritage Guide, Cave Entry");

        insertSeedPackage(db, "Hawa Mahal & Pink City Walk", "Jaipur",
                "Palace of Winds, Jantar Mantar observatory & bustling local bazaars.",
                "1799", "26.9239", "75.8267", R.drawable.city_jaipur_hawamahal,
                "4.9", "Royal City", "1 Day", "Oct – Mar", "Entry Passes, Local Guide, Chai Tasting");

        insertSeedPackage(db, "Amber Palace & Jal Mahal", "Jaipur",
                "Grand hilltop sandstone palace overlooking Maota Lake with cultural show.",
                "2299", "26.9855", "75.8513", R.drawable.img_2,
                "4.8", "Palace", "1 Day", "Oct – Mar", "Palace Entry, Jeep Ride, Evening Light Show");

        insertSeedPackage(db, "Baga Beach & Aguada Sunset", "Goa",
                "Golden beaches, water sports, coastal lighthouse & seaside shacks.",
                "2499", "15.5553", "73.7517", R.drawable.city_goa_beach,
                "4.7", "Beach", "2 Days", "Nov – Feb", "Water Sports, Fort Aguada, Sunset Cruise");

        insertSeedPackage(db, "Old Goa Churches & Spice Farm", "Goa",
                "Basilica of Bom Jesus, Portuguese heritage & aromatic spice plantation.",
                "1999", "15.5009", "73.9116", R.drawable.city_goa_churches,
                "4.6", "Culture", "1 Day", "Oct – Mar", "Spice Farm Lunch, Heritage Walk, Transfers");

        insertSeedPackage(db, "Shaniwar Wada & Dagdusheth", "Pune",
                "Historic Maratha citadel, bustling old city lanes & cultural landmarks.",
                "999", "18.5196", "73.8553", R.drawable.city_pune_shaniwar,
                "4.5", "Heritage", "Half Day", "Jul – Feb", "Guided Walk, Entry Ticket, Local Snacks");

        insertSeedPackage(db, "Sinhagad Scenic Hilltop", "Pune",
                "Panoramic Sahyadri mountain views, fresh mountain air & local cuisine.",
                "1199", "18.3663", "73.7559", R.drawable.img_6,
                "4.7", "Hilltop", "1 Day", "Jun – Feb", "Transport, Sunrise Trek, Traditional Lunch");

        insertSeedPackage(db, "City Palace & Lake Pichola", "Udaipur",
                "Royal lakeside palace complex with sunset boat cruise on Lake Pichola.",
                "2599", "24.5764", "73.6835", R.drawable.city_udaipur_lake,
                "4.9", "Lakeside", "2 Days", "Sep – Mar", "Boat Cruise, Palace Museum, Cultural Show");

        insertSeedPackage(db, "Taj Mahal Sunrise Experience", "Agra",
                "Guided sunrise visit to the ivory-white marble wonder & Agra Fort.",
                "2799", "27.1751", "78.0421", R.drawable.city_agra_taj,
                "5.0", "Wonder", "1 Day", "Oct – Mar (Sunrise)", "Skip-the-Line Pass, Historian Guide, Transfers");

        insertSeedPackage(db, "Red Fort & India Gate Tour", "Delhi",
                "Full-day heritage circuit covering Red Fort, India Gate & Qutub Minar.",
                "1699", "28.6562", "77.2410", R.drawable.img_3,
                "4.6", "Capital", "1 Day", "Oct – Mar", "AC Cab, Monument Tickets, City Guide");

        insertSeedPackage(db, "Solang Valley & Mall Road", "Manali",
                "Snow-capped Himalayan peaks, cable car views & cozy mountain cafes.",
                "3299", "32.3161", "77.1570", R.drawable.city_manali_valley,
                "4.8", "Mountains", "2 Days", "Dec – Jun", "Ropeway Ride, Valley Transfers, Campfire");

        // Sample bookings so My Bookings is populated out-of-the-box
        db.execSQL("INSERT INTO " + TABLE_BOOKINGS + " (fort, location, username, mobileno, address) VALUES ('Hawa Mahal & Pink City Walk', 'Jaipur', 'Aarav Sharma', '9876543210', 'MG Road, Pune')");
        db.execSQL("INSERT INTO " + TABLE_BOOKINGS + " (fort, location, username, mobileno, address) VALUES ('Gateway of India & Marine Drive', 'Mumbai', 'Aarav Sharma', '9876543210', 'MG Road, Pune')");

        // Seed 2 initial favorites so Saved filter has items immediately
        db.execSQL("INSERT INTO " + TABLE_FAVORITES + " (package_id) VALUES ('3')");
        db.execSQL("INSERT INTO " + TABLE_FAVORITES + " (package_id) VALUES ('9')");
    }

    private void insertSeedPackage(SQLiteDatabase db, String name, String location, String desc,
                                   String price, String lat, String lon, int imgResId,
                                   String rating, String category, String duration,
                                   String bestTime, String highlights) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PKG_NAME, name);
        cv.put(COLUMN_PKG_LOCATION, location);
        cv.put(COLUMN_PKG_DESCRIPTION, desc);
        cv.put(COLUMN_PKG_PRICE, price);
        cv.put(COLUMN_PKG_LATITUDE, lat);
        cv.put(COLUMN_PKG_LONGITUDE, lon);
        cv.put(COLUMN_PKG_IMAGE, imgResId);
        cv.put(COLUMN_PKG_RATING, rating);
        cv.put(COLUMN_PKG_CATEGORY, category);
        cv.put(COLUMN_PKG_DURATION, duration);
        cv.put(COLUMN_PKG_BEST_TIME, bestTime);
        cv.put(COLUMN_PKG_HIGHLIGHTS, highlights);
        db.insert(TABLE_PACKAGES, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACKAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIGURATION);
        onCreate(db);
    }

    // Helper methods for Backend logic
    public static Cursor loginUser(String email, String password) {
        return database.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=? AND password=?", new String[]{email, password});
    }

    public static boolean registerUser(String name, String email, String mobile, String password) {
        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=?", new String[]{email});
        if (c.getCount() > 0) {
            c.close();
            return false; // Email exists
        }
        c.close();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, name);
        cv.put(COLUMN_USER_EMAIL, email);
        cv.put(COLUMN_USER_MOBILENO, mobile);
        cv.put(COLUMN_USER_PASSWORD, password);
        cv.put(COLUMN_USER_TYPE, "user");
        return database.insert(TABLE_USERS, null, cv) != -1;
    }

    public static Cursor getAllUsers() {
        return database.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE usertype='user'", null);
    }

    public static Cursor getProfile(String id) {
        return database.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE id=?", new String[]{id});
    }

    public static boolean updateProfile(String id, String name, String email, String mobile, String password) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, name);
        cv.put(COLUMN_USER_EMAIL, email);
        cv.put(COLUMN_USER_MOBILENO, mobile);
        cv.put(COLUMN_USER_PASSWORD, password);
        return database.update(TABLE_USERS, cv, "id=?", new String[]{id}) > 0;
    }

    public static boolean addPackage(String name, String location, String desc, String price, String lat, String lon) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PKG_NAME, name);
        cv.put(COLUMN_PKG_LOCATION, location);
        cv.put(COLUMN_PKG_DESCRIPTION, desc);
        cv.put(COLUMN_PKG_PRICE, price);
        cv.put(COLUMN_PKG_LATITUDE, lat);
        cv.put(COLUMN_PKG_LONGITUDE, lon);
        cv.put(COLUMN_PKG_IMAGE, R.drawable.img_4);
        cv.put(COLUMN_PKG_RATING, "4.7");
        cv.put(COLUMN_PKG_CATEGORY, "City Tour");
        cv.put(COLUMN_PKG_DURATION, "1 Day");
        cv.put(COLUMN_PKG_BEST_TIME, "Oct – Mar");
        cv.put(COLUMN_PKG_HIGHLIGHTS, "Guided Tour, Sightseeing, Local Experience");
        return database.insert(TABLE_PACKAGES, null, cv) != -1;
    }

    public static Cursor getPackages() {
        return database.rawQuery("SELECT * FROM " + TABLE_PACKAGES, null);
    }

    public static Cursor findPackages(String location, String lat, String lon) {
        if (location != null && !location.trim().isEmpty() && !location.trim().equalsIgnoreCase("All")) {
            String query = "%" + location.trim() + "%";
            return database.rawQuery("SELECT * FROM " + TABLE_PACKAGES + " WHERE location LIKE ? OR name LIKE ?", new String[]{query, query});
        } else {
            return getPackages();
        }
    }

    public static boolean deletePackage(String id) {
        database.delete(TABLE_FAVORITES, COLUMN_FAV_PKG_ID + "=?", new String[]{id});
        return database.delete(TABLE_PACKAGES, "id=?", new String[]{id}) > 0;
    }

    // Favorites / Wishlist helpers
    public static boolean isFavorite(String packageId) {
        if (packageId == null) return false;
        Cursor c = database.rawQuery("SELECT 1 FROM " + TABLE_FAVORITES + " WHERE " + COLUMN_FAV_PKG_ID + "=?", new String[]{packageId});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    public static boolean toggleFavorite(String packageId) {
        if (isFavorite(packageId)) {
            database.delete(TABLE_FAVORITES, COLUMN_FAV_PKG_ID + "=?", new String[]{packageId});
            return false;
        } else {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_FAV_PKG_ID, packageId);
            database.insert(TABLE_FAVORITES, null, cv);
            return true;
        }
    }

    public static boolean addBooking(String fort, String location, String username, String mobile, String address) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BKG_FORT, fort);
        cv.put(COLUMN_BKG_LOCATION, location);
        cv.put(COLUMN_BKG_USERNAME, username);
        cv.put(COLUMN_BKG_MOBILENO, mobile);
        cv.put(COLUMN_BKG_ADDRESS, address);
        return database.insert(TABLE_BOOKINGS, null, cv) != -1;
    }

    public static Cursor getBookings() {
        return database.rawQuery("SELECT * FROM " + TABLE_BOOKINGS + " ORDER BY id DESC", null);
    }

    public static Cursor getMyBookings(String username) {
        return database.rawQuery("SELECT * FROM " + TABLE_BOOKINGS + " WHERE username=? ORDER BY id DESC", new String[]{username});
    }

    public static boolean deleteBooking(String id) {
        return database.delete(TABLE_BOOKINGS, "id=?", new String[]{id}) > 0;
    }

    public static void execNonQuery(String query) {
        database.execSQL(query);
    }

    public static Cursor getCursorData(String query) {
        return database.rawQuery(query, null);
    }

    public static String getSingleValue(String query) {
        Cursor res = getCursorData(query);
        String value = "";
        if (res.moveToNext()) {
            value = res.getString(0);
        }
        res.close();
        return value;
    }

    public static boolean checkIfRecordExist(String query) {
        Cursor res = getCursorData(query);
        boolean exists = res.getCount() > 0;
        res.close();
        return exists;
    }
}
