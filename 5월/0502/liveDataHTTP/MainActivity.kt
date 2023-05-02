package com.ssafy.jetpack

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.jetpack.databinding.ActivityMainBinding

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {

    private  val activityViewModel: ActivityViewModel by viewModels()
    lateinit var loadingDialog: LoadingDialog

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDialog = LoadingDialog(this)

        binding.button.setOnClickListener {
            activityViewModel.getPostList()
            loadingDialog.show()
        }

//        activityViewModel.postList.observe(this){
//            binding.text.text = it.toString()
//            Log.d(TAG, "onCreate: ${it.toString()}")
//            loadingDialog.dismiss()
//        }
    }
}