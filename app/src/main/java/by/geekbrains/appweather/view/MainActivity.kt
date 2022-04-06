package by.geekbrains.appweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.geekbrains.appweather.R
import by.geekbrains.appweather.databinding.ActivityMainBinding
import by.geekbrains.appweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_fragment_container, MainFragment.newInstance())
                .commit()
        }
    }
}