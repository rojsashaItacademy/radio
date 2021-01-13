package ru.trinitydigital.radio.util

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun <T : ViewModel> FragmentActivity.viewModel(clazz: KClass<T>) =
    lazy { ViewModelProviders.of(this).get(clazz.java) }

fun <T> MutableLiveData<T>.forceRefresh() {
    this.value = this.value
}