package com.emma_ea.dogify.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.emma_ea.dogify.model.Breed
import com.emma_ea.dogify.model.FetchBreedUsecase
import com.emma_ea.dogify.model.GetBreedsUsecase
import com.emma_ea.dogify.model.ToggleFavouriteStateUsecase
import kotlinx.coroutines.launch

suspend fun greet() =
    "${FetchBreedUsecase().invoke()}\n" +
            "${GetBreedsUsecase().invoke()}\n" +
            "${ToggleFavouriteStateUsecase().invoke(Breed("toggle favorite state test", ""))}\n"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        lifecycleScope.launch {
            tv.text = greet()
        }
    }
}
