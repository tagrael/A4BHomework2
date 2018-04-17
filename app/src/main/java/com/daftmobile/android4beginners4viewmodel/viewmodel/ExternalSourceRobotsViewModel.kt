package com.daftmobile.android4beginners4viewmodel.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.daftmobile.android4beginners4viewmodel.log
import com.daftmobile.android4beginners4viewmodel.model.Robot
import com.daftmobile.android4beginners4viewmodel.model.RobotDataSource
import com.daftmobile.android4beginners4viewmodel.model.RobotGenerator


class ExternalSourceRobotsViewModel: ViewModel(), RobotDataSource.Observer {


    private val mutableLiveData: MutableLiveData<String> = MutableLiveData()
    private val robotDataSource = RobotDataSource()
    private var sortHelper: Int = 0
    private var filterHelper: Int = 0

    init {
        robotDataSource.observer = this
    }

    fun getItems(): LiveData<String> = mutableLiveData

    fun addItem() = robotDataSource.addNew()

    override fun onChanged(robotDataSource: RobotDataSource) {
        var temp = robotDataSource.getRobots()
        when(sortHelper) {
            1 -> {
                temp = temp.sortedWith(compareBy({ it.name }))
            }
            2 -> {
               temp = temp.sortedWith(compareByDescending({ it.name }))
            }
        }
        when(filterHelper){
            1 -> {
                temp = temp.filter{ it.mood.toString() == "HAPPY" }
            }
            2 -> {
                temp = temp.filter{ it.mood.toString() == "SAD" }
            }
        }
        mutableLiveData.value = temp.joinToString("\n")
    }

    override fun onCleared() {
        robotDataSource.observer = null
        log("Cleared")
    }

    fun sortNameAsc() {
        sortHelper = 1
        onChanged(robotDataSource)
    }

    fun sortNameDesc() {
        sortHelper = 2
        onChanged(robotDataSource)
    }

    fun showMoodHappy() {
        filterHelper = 1
        onChanged(robotDataSource)
    }

    fun showMoodSad() {
        filterHelper = 2
        onChanged(robotDataSource)
    }

    fun showMoodAll() {
        filterHelper = 0
        onChanged(robotDataSource)
    }
}
