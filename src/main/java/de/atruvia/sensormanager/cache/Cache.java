package de.atruvia.sensormanager.cache;

import de.atruvia.sensormanager.app.services.EntryService;
import de.atruvia.sensormanager.app.services.sql.DatabaseService;

public class Cache {

    public static final DatabaseService DATABASE_SERVICE = new DatabaseService();

    public static final EntryService ENTRY_SERVICE = new EntryService(DATABASE_SERVICE);
}
