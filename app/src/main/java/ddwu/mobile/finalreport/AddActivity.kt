package ddwu.mobile.finalreport

import android.content.ContentValues
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.mobile.finalreport.databinding.ActivityDetailBinding

//추가
class AddActivity : AppCompatActivity() {

    lateinit var addBinding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(addBinding.root)

        addBinding.imageView.setImageResource(R.mipmap.finland)

        addBinding.btnAddAdd.setOnClickListener{
            val title = addBinding.etAddName.text.toString()
            val location = addBinding.etAddLocation.text.toString()
            val date = addBinding.etAddDate.text.toString()
            val weather = addBinding.etAddName.text.toString()
            val newDto = DiaryDto(0, title, location, date, weather)

            if(title.equals("") || location.equals("") || date.equals("") || weather.equals("")) {
                Toast.makeText(this, "빈칸 없이 입력해주세요", Toast.LENGTH_SHORT).show()
            } else{
                if (DiaryDao(this).addDiary(title, location, date, weather) > 0) {
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }

        /*취소버튼 클릭*/
        addBinding.btnAddFinish.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}