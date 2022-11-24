package com.example.myfoodmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_egg_score_test.*

class EggScoreTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_egg_score_test)
        var egg_1=1
        var egg_2=1
        var egg_3=1
        var egg_4=1
        var egg_5=1
        var whatScore="Taste"

        eggScoreTest_TasteRange.setOnClickListener {
            eggScoreTest_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            eggScoreTest_1.setImageResource(R.mipmap.score_zero)
            eggScoreTest_2.setImageResource(R.mipmap.score_zero)
            eggScoreTest_3.setImageResource(R.mipmap.score_zero)
            eggScoreTest_4.setImageResource(R.mipmap.score_zero)
            eggScoreTest_5.setImageResource(R.mipmap.score_zero)
            eggScoreTest_score.text = "0.0"
            whatScore="Taste"
        }
        eggScoreTest_PriceRange.setOnClickListener {
            eggScoreTest_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            eggScoreTest_1.setImageResource(R.mipmap.score_zero)
            eggScoreTest_2.setImageResource(R.mipmap.score_zero)
            eggScoreTest_3.setImageResource(R.mipmap.score_zero)
            eggScoreTest_4.setImageResource(R.mipmap.score_zero)
            eggScoreTest_5.setImageResource(R.mipmap.score_zero)
            eggScoreTest_score.text = "0.0"
            whatScore="Price"
        }
        eggScoreTest_CleanRange.setOnClickListener {
            eggScoreTest_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            eggScoreTest_1.setImageResource(R.mipmap.score_zero)
            eggScoreTest_2.setImageResource(R.mipmap.score_zero)
            eggScoreTest_3.setImageResource(R.mipmap.score_zero)
            eggScoreTest_4.setImageResource(R.mipmap.score_zero)
            eggScoreTest_5.setImageResource(R.mipmap.score_zero)
            eggScoreTest_score.text = "0.0"
            whatScore="Clean"
        }
        eggScoreTest_Save.setOnClickListener {
            when(whatScore) {
                "Taste" -> {
                    eggScoreTest_TasteScore.text=eggScoreTest_score.text
                    eggScoreTest_Taste1.setImageDrawable(eggScoreTest_1.drawable)
                    eggScoreTest_Taste2.setImageDrawable(eggScoreTest_2.drawable)
                    eggScoreTest_Taste3.setImageDrawable(eggScoreTest_3.drawable)
                    eggScoreTest_Taste4.setImageDrawable(eggScoreTest_4.drawable)
                    eggScoreTest_Taste5.setImageDrawable(eggScoreTest_5.drawable)
                }
                "Price" -> {
                    eggScoreTest_PriceScore.text=eggScoreTest_score.text
                    eggScoreTest_Price1.setImageDrawable(eggScoreTest_1.drawable)
                    eggScoreTest_Price2.setImageDrawable(eggScoreTest_2.drawable)
                    eggScoreTest_Price3.setImageDrawable(eggScoreTest_3.drawable)
                    eggScoreTest_Price4.setImageDrawable(eggScoreTest_4.drawable)
                    eggScoreTest_Price5.setImageDrawable(eggScoreTest_5.drawable)
                }
                "Clean" -> {
                    eggScoreTest_CleanScore.text=eggScoreTest_score.text
                    eggScoreTest_Clean1.setImageDrawable(eggScoreTest_1.drawable)
                    eggScoreTest_Clean2.setImageDrawable(eggScoreTest_2.drawable)
                    eggScoreTest_Clean3.setImageDrawable(eggScoreTest_3.drawable)
                    eggScoreTest_Clean4.setImageDrawable(eggScoreTest_4.drawable)
                    eggScoreTest_Clean5.setImageDrawable(eggScoreTest_5.drawable)
                }
            }
            eggScoreTest_ScoreRange.visibility=View.INVISIBLE
        }
        eggScoreTest_1.setOnClickListener {
            eggScoreTest_2.setImageResource(R.mipmap.score_zero)
            eggScoreTest_3.setImageResource(R.mipmap.score_zero)
            eggScoreTest_4.setImageResource(R.mipmap.score_zero)
            eggScoreTest_5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            when (egg_1) {
                1 -> {
                    eggScoreTest_1.setImageResource(R.mipmap.score_half)
                    egg_1 = 2
                    eggScoreTest_score.text = "0.5"
                }
                2 -> {
                    eggScoreTest_1.setImageResource(R.mipmap.score_full)
                    egg_1 = 3
                    eggScoreTest_score.text = "1.0"
                }
                3 -> {
                    eggScoreTest_1.setImageResource(R.mipmap.score_zero)
                    egg_1 = 1
                    eggScoreTest_score.text = "0.0"
                }
            }
        }
        eggScoreTest_2.setOnClickListener {
            eggScoreTest_1.setImageResource(R.mipmap.score_full)
            eggScoreTest_3.setImageResource(R.mipmap.score_zero)
            eggScoreTest_4.setImageResource(R.mipmap.score_zero)
            eggScoreTest_5.setImageResource(R.mipmap.score_zero)
            egg_1=1
            egg_3=1
            egg_4=1
            egg_5=1
            when (egg_2) {
                1 -> {
                    eggScoreTest_2.setImageResource(R.mipmap.score_half)
                    egg_2 = 2
                    eggScoreTest_score.text = "1.5"
                }
                2 -> {
                    eggScoreTest_2.setImageResource(R.mipmap.score_full)
                    egg_2 = 3
                    eggScoreTest_score.text = "2.0"
                }
                3 -> {
                    eggScoreTest_2.setImageResource(R.mipmap.score_zero)
                    egg_2 = 1
                    eggScoreTest_score.text = "1.0"
                }
            }
        }
        eggScoreTest_3.setOnClickListener {
            eggScoreTest_1.setImageResource(R.mipmap.score_full)
            eggScoreTest_2.setImageResource(R.mipmap.score_full)
            eggScoreTest_4.setImageResource(R.mipmap.score_zero)
            eggScoreTest_5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_1=1
            egg_4=1
            egg_5=1
            when (egg_3) {
                1 -> {
                    eggScoreTest_3.setImageResource(R.mipmap.score_half)
                    egg_3 = 2
                    eggScoreTest_score.text = "2.5"
                }
                2 -> {
                    eggScoreTest_3.setImageResource(R.mipmap.score_full)
                    egg_3 = 3
                    eggScoreTest_score.text = "3.0"
                }
                3 -> {
                    eggScoreTest_3.setImageResource(R.mipmap.score_zero)
                    egg_3 = 1
                    eggScoreTest_score.text = "2.0"
                }
            }
        }
        eggScoreTest_4.setOnClickListener {
            eggScoreTest_1.setImageResource(R.mipmap.score_full)
            eggScoreTest_2.setImageResource(R.mipmap.score_full)
            eggScoreTest_3.setImageResource(R.mipmap.score_full)
            eggScoreTest_5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_3=1
            egg_1=1
            egg_5=1
            when (egg_4) {
                1 -> {
                    eggScoreTest_4.setImageResource(R.mipmap.score_half)
                    egg_4 = 2
                    eggScoreTest_score.text = "3.5"
                }
                2 -> {
                    eggScoreTest_4.setImageResource(R.mipmap.score_full)
                    egg_4 = 3
                    eggScoreTest_score.text = "4.0"
                }
                3 -> {
                    eggScoreTest_4.setImageResource(R.mipmap.score_zero)
                    egg_4 = 1
                    eggScoreTest_score.text = "3.0"
                }
            }
        }
        eggScoreTest_5.setOnClickListener {
            eggScoreTest_1.setImageResource(R.mipmap.score_full)
            eggScoreTest_2.setImageResource(R.mipmap.score_full)
            eggScoreTest_3.setImageResource(R.mipmap.score_full)
            eggScoreTest_4.setImageResource(R.mipmap.score_full)
            egg_2=1
            egg_3=1
            egg_4=1
            egg_1=1
            when (egg_5) {
                1 -> {
                    eggScoreTest_5.setImageResource(R.mipmap.score_half)
                    egg_5 = 2
                    eggScoreTest_score.text = "4.5"
                }
                2 -> {
                    eggScoreTest_5.setImageResource(R.mipmap.score_full)
                    egg_5 = 3
                    eggScoreTest_score.text = "5.0"
                }
                3 -> {
                    eggScoreTest_5.setImageResource(R.mipmap.score_zero)
                    egg_5 = 1
                    eggScoreTest_score.text = "4.0"
                }
            }
        }
    }
}