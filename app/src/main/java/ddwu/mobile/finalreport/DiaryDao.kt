package ddwu.mobile.finalreport

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class DiaryDao (context: Context){
    private val dbHelper: DBHelper = DBHelper(context)
    private val db: SQLiteDatabase = dbHelper.writableDatabase

    fun addDiary(title: String, location: String, date: String, weather: String): Long {
        val values = ContentValues().apply {
            put(DBHelper.COL_TITLE, title)
            put(DBHelper.COL_LOCATION, location)
            put(DBHelper.COL_DATE, date)
            put(DBHelper.COL_WEATHER, weather)
        }

        return db.insert(DBHelper.TABLE_NAME, null, values)
    }

    fun updateDiary(diary: DiaryDto): Int {
        val values = ContentValues().apply {
            put(DBHelper.COL_TITLE, diary.title)
            put(DBHelper.COL_LOCATION, diary.location)
            put(DBHelper.COL_DATE, diary.date)
            put(DBHelper.COL_WEATHER, diary.weather)
        }

        val selection = BaseColumns._ID + " = ?"
        val selectionArgs = arrayOf(diary.id.toString())

        return db.update(DBHelper.TABLE_NAME, values, selection, selectionArgs)
    }
}