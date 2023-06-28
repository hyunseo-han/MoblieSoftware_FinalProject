package ddwu.mobile.finalreport

import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.mobile.finalreport.databinding.ActivityModifyBinding

//수정
class UpdateActivity : AppCompatActivity() {
    lateinit var updateBinding : ActivityModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateBinding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(updateBinding.root)

        val dto = intent.getSerializableExtra("dto") as DiaryDto

        updateBinding.etModTitle.setText(dto.title)
        updateBinding.etModLocation.setText(dto.location)
        updateBinding.etModDate.setText(dto.date)
        updateBinding.etModWeather.setText(dto.weather)
        updateBinding.iv.setImageResource(R.mipmap.rome)

        updateBinding.btnModMod.setOnClickListener{
            dto.title = updateBinding.etModTitle.text.toString()
            dto.location = updateBinding.etModLocation.text.toString()
            dto.date = updateBinding.etModDate.text.toString()
            dto.weather = updateBinding.etModWeather.text.toString()

            if(dto.title.equals("") || dto.location.equals("") || dto.date.equals("") || dto.weather.equals("")){
                Toast.makeText(this, "빈칸 없이 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (DiaryDao(this).updateDiary(dto) > 0) {
                    setResult(RESULT_OK)
                } else {
                    setResult(RESULT_CANCELED)
                }
                finish()
            }
        }

        updateBinding.btnModFin.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

}


