package ddwu.mobile.finalreport

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Binder
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.mobile.finalreport.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    lateinit var searchBinding : ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)

        searchBinding.button.setOnClickListener{
            val searchKeyword = searchBinding.et.text.toString()

            if (searchKeyword == "") {
                Toast.makeText(this, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val result = searchDiary(searchKeyword)
                searchBinding.textView2.text = result
            }
        }
    }

    @SuppressLint("Range")
    fun searchDiary(keyWord: String): String {
        val dbHelper = DBHelper(this)
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DBHelper.COL_TITLE,
            DBHelper.COL_LOCATION,
            DBHelper.COL_DATE,
            DBHelper.COL_WEATHER
        )
        val selection = "${DBHelper.COL_TITLE} = ?"
        val selectionArgs = arrayOf(keyWord)

        var resultString = ""
        val cursor = db.query(DBHelper.TABLE_NAME, columns, selection, selectionArgs,
            null, null, null, null)

        if (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex(DBHelper.COL_TITLE))
            val location = cursor.getString(cursor.getColumnIndex(DBHelper.COL_LOCATION))
            val  date = cursor.getString(cursor.getColumnIndex(DBHelper.COL_DATE))
            val weather = cursor.getString(cursor.getColumnIndex(DBHelper.COL_WEATHER))
            resultString = "제목: $title\n위치: $location\n날짜: $date\n날씨: $weather"

        } else {
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }

        cursor.close()
        dbHelper.close()
        return resultString
    }
}