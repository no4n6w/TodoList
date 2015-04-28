package il.ac.huji.todolist.db;

import android.provider.BaseColumns;

public class TodoContract {
    public static final String DB_NAME = "il.ac.huji.todolist.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TODOITEM = "task";
        public static final String _ID = BaseColumns._ID;
    }
}