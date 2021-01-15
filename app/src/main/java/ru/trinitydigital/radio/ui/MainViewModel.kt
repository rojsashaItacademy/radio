package ru.trinitydigital.radio.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.trinitydigital.radio.data.RadioStations
import ru.trinitydigital.radio.data.Resources

class MainViewModel : ViewModel() {

    val radioList = Resources.generate()
    var radioLiveData = MutableLiveData<RadioStations>().apply { value = radioList[0] }
    var isBound = false

    fun nextStation() {
        val index = radioList.indexOf(radioLiveData.value)
        if (index < radioList.size - 1)
            radioLiveData.postValue(radioList[index + 1])
    }

    fun prevStation() {
        val index = radioList.indexOf(radioLiveData.value)
        if (index > 0)
            radioLiveData.postValue(radioList[index - 1])
    }
}