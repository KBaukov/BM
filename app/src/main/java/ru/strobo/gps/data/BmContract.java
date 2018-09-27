package ru.strobo.gps.data;

import android.provider.BaseColumns;
import java.util.Date;

public class BmContract {

    public BmContract() {
    }

    public static final class ConfigEntry implements BaseColumns {
        public static final String TABLE_NAME = "config";

        public static final String _ID = BaseColumns._ID;
        public static final String SERVER_HOST = "host";
        public static final String SERVER_PORT = "port";
        public static final String DEVICE_ID = "dev_id";

    }

    public static final class PointsEntry implements BaseColumns {
        public static final String TABLE_NAME = "points";

        public static final String _ID = BaseColumns._ID;
        public static final String DATE_TIME = "date_time";
        public static final String POINT = "point";
        public static final String IS_SENF_FLAG = "is_send";

    }
}
