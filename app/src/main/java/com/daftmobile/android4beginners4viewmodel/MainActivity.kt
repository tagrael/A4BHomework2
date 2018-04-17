package com.daftmobile.android4beginners4viewmodel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.daftmobile.android4beginners4viewmodel.R.id.fab
import com.daftmobile.android4beginners4viewmodel.R.id.items
import com.daftmobile.android4beginners4viewmodel.viewmodel.ExternalSourceRobotsViewModel
import com.daftmobile.android4beginners4viewmodel.viewmodel.LiveDataRobotsViewModel
import com.daftmobile.android4beginners4viewmodel.viewmodel.SimpleRobotsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ExternalSourceRobotsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this)
                .get(ExternalSourceRobotsViewModel::class.java)

        viewModel.getItems().observe(this, Observer { it: String? ->
            items.text = it
        })

        fab.setOnClickListener {
            addNewItem()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when (item.getItemId()) {
            R.id.name_asc -> {
                viewModel.sortNameAsc()
                return true
            }
            R.id.name_desc -> {
                viewModel.sortNameDesc()
                return true
            }
            R.id.mood_happy -> {
                viewModel.showMoodHappy()
                return true
            }
            R.id.mood_sad -> {
                viewModel.showMoodSad()
                return true
            }
            R.id.mood_all -> {
                viewModel.showMoodAll()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun addNewItem() {
        viewModel.addItem()
    }


}
