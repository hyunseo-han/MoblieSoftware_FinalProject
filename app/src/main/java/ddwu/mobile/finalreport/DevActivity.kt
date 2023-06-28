package ddwu.mobile.finalreport

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.mobile.finalreport.databinding.ActivityDevBinding

//개발자 소개 페이지
class DevActivity : AppCompatActivity() {

    lateinit var binding : ActivityDevBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profile = binding.ivDev
        profile.setImageResource(R.mipmap.me)

        binding.tvDevGit.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://github.com/hyunseo-han")
            startActivity(intent)
        }
    }
}