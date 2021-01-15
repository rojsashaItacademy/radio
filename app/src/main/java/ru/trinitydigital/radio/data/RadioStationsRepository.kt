package ru.trinitydigital.radio.data

import androidx.lifecycle.MutableLiveData

class RadioStationsRepository {

    var radioLiveData = MutableLiveData<RadioStations>().apply { value = radioList[0] }
    var isBound = false

    fun nextStation(): RadioStations? {
        val index = radioList.indexOf(radioLiveData.value)
        if (index < radioList.size - 1)
            radioLiveData.postValue(radioList[index + 1])
        return radioLiveData.value
    }

    fun prevStation(): RadioStations? {
        val index = radioList.indexOf(radioLiveData.value)
        if (index > 0)
            radioLiveData.postValue(radioList[index - 1])

        return radioLiveData.value
    }

    companion object {
        val radioList = Resources.generate()
    }
}