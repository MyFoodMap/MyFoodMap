package com.example.myfoodmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_egg_score_test.*
import kotlinx.android.synthetic.main.activity_score.*
import kotlin.math.roundToInt

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        var egg_1=1
        var egg_2=1
        var egg_3=1
        var egg_4=1
        var egg_5=1
        var whatScore="Taste"

        score_TasteRange.setOnClickListener() {
            score_ScoreBackground.visibility=View.VISIBLE
            score_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            score_Score1.setImageResource(R.mipmap.score_zero)
            score_Score2.setImageResource(R.mipmap.score_zero)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            score_Score.text = "0.0"
            whatScore="Taste"
        }
        score_PriceRange.setOnClickListener() {
            score_ScoreBackground.visibility=View.VISIBLE
            score_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            score_Score1.setImageResource(R.mipmap.score_zero)
            score_Score2.setImageResource(R.mipmap.score_zero)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            score_Score.text = "0.0"
            whatScore="Price"
        }
        score_CleanRange.setOnClickListener() {
            score_ScoreBackground.visibility=View.VISIBLE
            score_ScoreRange.visibility= View.VISIBLE
            egg_1=1
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            score_Score1.setImageResource(R.mipmap.score_zero)
            score_Score2.setImageResource(R.mipmap.score_zero)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            score_Score.text = "0.0"
            whatScore="Clean"
        }
        score_Save.setOnClickListener() {
            when(whatScore) {
                "Taste" -> {
                    score_TasteScore.text=score_Score.text
                    score_Taste1.setImageDrawable(score_Score1.drawable)
                    score_Taste2.setImageDrawable(score_Score2.drawable)
                    score_Taste3.setImageDrawable(score_Score3.drawable)
                    score_Taste4.setImageDrawable(score_Score4.drawable)
                    score_Taste5.setImageDrawable(score_Score5.drawable)
                }
                "Price" -> {
                    score_PriceScore.text=score_Score.text
                    score_Price1.setImageDrawable(score_Score1.drawable)
                    score_Price2.setImageDrawable(score_Score2.drawable)
                    score_Price3.setImageDrawable(score_Score3.drawable)
                    score_Price4.setImageDrawable(score_Score4.drawable)
                    score_Price5.setImageDrawable(score_Score5.drawable)
                }
                "Clean" -> {
                    score_CleanScore.text=score_Score.text
                    score_Clean1.setImageDrawable(score_Score1.drawable)
                    score_Clean2.setImageDrawable(score_Score2.drawable)
                    score_Clean3.setImageDrawable(score_Score3.drawable)
                    score_Clean4.setImageDrawable(score_Score4.drawable)
                    score_Clean5.setImageDrawable(score_Score5.drawable)
                }
            }
            score_ScoreRange.visibility= View.INVISIBLE
            score_ScoreBackground.visibility= View.INVISIBLE
            averageSum()
        }
        score_Score1.setOnClickListener() {
            score_Score2.setImageResource(R.mipmap.score_zero)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_3=1
            egg_4=1
            egg_5=1
            when (egg_1) {
                1 -> {
                    score_Score1.setImageResource(R.mipmap.score_half)
                    egg_1 = 2
                    score_Score.text = "0.5"
                }
                2 -> {
                    score_Score1.setImageResource(R.mipmap.score_full)
                    egg_1 = 3
                    score_Score.text = "1.0"
                }
                3 -> {
                    score_Score1.setImageResource(R.mipmap.score_zero)
                    egg_1 = 1
                    score_Score.text = "0.0"
                }
            }
        }
        score_Score2.setOnClickListener() {
            score_Score1.setImageResource(R.mipmap.score_full)
            score_Score3.setImageResource(R.mipmap.score_zero)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            egg_1=1
            egg_3=1
            egg_4=1
            egg_5=1
            when (egg_2) {
                1 -> {
                    score_Score2.setImageResource(R.mipmap.score_half)
                    egg_2 = 2
                    score_Score.text = "1.5"
                }
                2 -> {
                    score_Score2.setImageResource(R.mipmap.score_full)
                    egg_2 = 3
                    score_Score.text = "2.0"
                }
                3 -> {
                    score_Score2.setImageResource(R.mipmap.score_zero)
                    egg_2 = 1
                    score_Score.text = "1.0"
                }
            }
        }
        score_Score3.setOnClickListener() {
            score_Score1.setImageResource(R.mipmap.score_full)
            score_Score2.setImageResource(R.mipmap.score_full)
            score_Score4.setImageResource(R.mipmap.score_zero)
            score_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_1=1
            egg_4=1
            egg_5=1
            when (egg_3) {
                1 -> {
                    score_Score3.setImageResource(R.mipmap.score_half)
                    egg_3 = 2
                    score_Score.text = "2.5"
                }
                2 -> {
                    score_Score3.setImageResource(R.mipmap.score_full)
                    egg_3 = 3
                    score_Score.text = "3.0"
                }
                3 -> {
                    score_Score3.setImageResource(R.mipmap.score_zero)
                    egg_3 = 1
                    score_Score.text = "2.0"
                }
            }
        }
        score_Score4.setOnClickListener() {
            score_Score1.setImageResource(R.mipmap.score_full)
            score_Score2.setImageResource(R.mipmap.score_full)
            score_Score3.setImageResource(R.mipmap.score_full)
            score_Score5.setImageResource(R.mipmap.score_zero)
            egg_2=1
            egg_3=1
            egg_1=1
            egg_5=1
            when (egg_4) {
                1 -> {
                    score_Score4.setImageResource(R.mipmap.score_half)
                    egg_4 = 2
                    score_Score.text = "3.5"
                }
                2 -> {
                    score_Score4.setImageResource(R.mipmap.score_full)
                    egg_4 = 3
                    score_Score.text = "4.0"
                }
                3 -> {
                    score_Score4.setImageResource(R.mipmap.score_zero)
                    egg_4 = 1
                    score_Score.text = "3.0"
                }
            }
        }
        score_Score5.setOnClickListener() {
            score_Score1.setImageResource(R.mipmap.score_full)
            score_Score2.setImageResource(R.mipmap.score_full)
            score_Score3.setImageResource(R.mipmap.score_full)
            score_Score4.setImageResource(R.mipmap.score_full)
            egg_2=1
            egg_3=1
            egg_4=1
            egg_1=1
            when (egg_5) {
                1 -> {
                    score_Score5.setImageResource(R.mipmap.score_half)
                    egg_5 = 2
                    score_Score.text = "4.5"
                }
                2 -> {
                    score_Score5.setImageResource(R.mipmap.score_full)
                    egg_5 = 3
                    score_Score.text = "5.0"
                }
                3 -> {
                    score_Score5.setImageResource(R.mipmap.score_zero)
                    egg_5 = 1
                    score_Score.text = "4.0"
                }
            }
        }
    }
    fun averageSum() {
        val a=score_TasteScore.text.toString().toDouble()
        val b=score_PriceScore.text.toString().toDouble()
        val c=score_CleanScore.text.toString().toDouble()
        val totalScore=(a+b+c)/3
        val averageScore=(totalScore*10.0).roundToInt()/10.0
        score_AverageScore.text="${averageScore}점"
    }
}