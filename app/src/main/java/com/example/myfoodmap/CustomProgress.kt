package com.example.myfoodmap

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myfoodmap.databinding.CustomprogressBinding
import kotlinx.android.synthetic.main.customprogress.*

class CustomProgress(mContext: AppCompatActivity) : Dialog(
    mContext
) {
    lateinit var progressAnimation: AnimationDrawable

    init {
        InitProgress()
    }

    fun InitProgress() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val binding = CustomprogressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progress.apply {
            setBackgroundResource(R.drawable.progressbar_loading_pixel)
            progressAnimation = background as AnimationDrawable
        }

    }

    fun start() {
        progressAnimation.start()
    }
}