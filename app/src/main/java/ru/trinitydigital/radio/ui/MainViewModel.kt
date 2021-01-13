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
        val pos = radioList.indexOf(radioLiveData.value)
        if (pos < radioList.size - 1)
            radioLiveData.postValue(radioList[pos + 1])
    }

    fun prevStation() {
        val pos = radioList.indexOf(radioLiveData.value)
        if (pos > 0)
            radioLiveData.postValue(radioList[pos - 1])
    }
}