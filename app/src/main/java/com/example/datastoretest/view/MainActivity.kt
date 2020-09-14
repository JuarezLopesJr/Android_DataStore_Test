package com.example.datastoretest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.datastoretest.R
import com.example.datastoretest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.readFromDataStore.observe(this, {
            textView.text = it
        })

        button.setOnClickListener {
            val name = editTextTextPersonName.text.toString()
            mainViewModel.saveToDataStore(name)
        }
    }
}