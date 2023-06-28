package ddwu.mobile.finalreport

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.mobile.finalreport.databinding.ActivityMainBinding

//과제명: 다이어리 앱
//분반: 01 분반
//학번: 20210821, 이름: 한현서
//제출일: 6월 23일
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var diaryAdapter: DiaryAdapter
    lateinit var diaryList: ArrayList<DiaryDto>

    val REQ_ADD = 100
    val REQ_UPDATE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager

        diaryList = getAllDiary()
        diaryAdapter = DiaryAdapter(diaryList)
        binding.recyclerView.adapter = diaryAdapter

        val onClicklistener = object : DiaryAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", diaryList.get(position))

                startActivityForResult(intent, REQ_UPDATE)
            }
        }

        diaryAdapter.setOnItemClickListener(onClicklistener)

        val onLongClicklistener = object : DiaryAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int) {
                val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
                alertDialogBuilder.setTitle("기록 삭제")
                alertDialogBuilder.setMessage("["+ diaryList[position].title + "] 기록을 삭제하시겠습니까?")
                alertDialogBuilder.setPositiveButton("삭제",
                    object: DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1:Int){
                            if ( deleteFood(diaryList.get(position).id) > 0) {
                                refreshList(RESULT_OK)
                                Toast.makeText(this@MainActivity, "삭제 완료", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                alertDialogBuilder.setNegativeButton("취소", null)

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }

        diaryAdapter.setOnItemLongClickListener(onLongClicklistener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_UPDATE -> {
                refreshList(resultCode)
            }
            REQ_ADD -> {
                refreshList(resultCode)
            }
        }
    }

    private fun refreshList (resultCode: Int) {
        if (resultCode == RESULT_OK) {
            diaryList.clear()
            diaryList.addAll(getAllDiary())
            diaryAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this@MainActivity, "취소됨", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    fun getAllDiary(): ArrayList<DiaryDto> {
        val helper = DBHelper(this)
        val db = helper.readableDatabase
        val columns = null
        val selection = null
        val selectionArgs = null
        val cursor: Cursor = db.query(
            DBHelper.TABLE_NAME, columns, selection,
            selectionArgs, null, null, null
        )

        val diarylist = arrayListOf<DiaryDto>() //이게 맞어..?

        with(cursor) {
            while (cursor.moveToNext()) {
                val id = getLong(getColumnIndex(BaseColumns._ID))
                val title = getString(getColumnIndex(DBHelper.COL_TITLE))
                val location = getString(getColumnIndex(DBHelper.COL_LOCATION))
                val date = getString(getColumnIndex(DBHelper.COL_DATE))
                val weather = getString(getColumnIndex(DBHelper.COL_WEATHER))

                val diaryData = DiaryDto(id, title, location, date, weather)

                diarylist.add(diaryData)
            }
        }
        cursor.close()
        helper.close()

        return diarylist
    }

    fun deleteFood(id: Long) : Int {
        val helper = DBHelper(this)
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(id.toString())

        val result = db.delete(DBHelper.TABLE_NAME, whereClause, whereArgs)

        helper.close()
        return result
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.m_search -> {
                val intent0 = Intent(this, SearchActivity::class.java)
                startActivity(intent0)
            }
            R.id.m_add -> {
                val intent1 = Intent(this, AddActivity::class.java)
                startActivityForResult(intent1, REQ_ADD)
            }
            R.id.m_dev -> {
                val intent2 = Intent(this, DevActivity::class.java)
                startActivity(intent2)
            }
            R.id.m_fin -> {
                val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)
                alertDialogBuilder.setTitle("앱 종료")
                alertDialogBuilder.setMessage("앱을 종료 하시겠습니까?")
                alertDialogBuilder.setPositiveButton("종료",
                    object: DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1:Int){
                          finish()
                        }
                    })
                alertDialogBuilder.setNegativeButton("취소", null)

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

