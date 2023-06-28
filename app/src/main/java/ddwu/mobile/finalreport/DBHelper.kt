package ddwu.mobile.finalreport

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class DBHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "DBHelper"

    companion object{
        const val DB_NAME = "diary_db"
        const val TABLE_NAME = "diary_table"
        const val COL_TITLE = "title"
        const val COL_LOCATION = "location"
        const val COL_DATE = "date"
        const val COL_WEATHER = "weather"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE ${TABLE_NAME} (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${COL_TITLE} TEXT, ${COL_LOCATION} TEXT, ${COL_DATE} TEXT, ${COL_WEATHER} TEXT)"
        Log.d(TAG, CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '파리에서의 2023', '프랑스 파리', '2022-12-31', '구름 가득 흐린 날씨')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '늦은 크리스마스', '체코 프라하', '2023-01-10', '오늘도 흐린 날씨... 유럽의 겨울은 흐리구나...')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '바티칸의 노을', '이탈리아 로마', '2023-01-15', '이탈리아 날씨는 맑음!')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '북극에서 오로라를 보다!', '핀란드 로바니에미', '2023-01-19', '날씨가 맑아야만 볼 수 있는 오로라! 다행히 오늘 날씨는 맑았다^^')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '해커톤을 했답니다', '동국대학교', '2023-02-15', '춥다...!')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '봄봄봄 봄이 왔어요', '서울숲', '2023-03-30', '아직 3월인데 이렇게 덥다고?!')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '컴퓨터학과 MT', '가평 대성리', '2023-05-13', '산은 춥다...!')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}