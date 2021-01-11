package ru.trinitydigital.radio.ui

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import ru.trinitydigital.radio.data.RadioService
import ru.trinitydigital.radio.data.RadioStations
import ru.trinitydigital.radio.data.Resources
import ru.trinitydigital.radio.databinding.ActivityMainBinding
import ru.trinitydigital.radio.util.viewBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var serviceConnection: ServiceConnection
    private val intentService by lazy { Intent(this, RadioService::class.java) }
    private var radioService: RadioService? = null
    private var radio: RadioStations? = null

    private val adapter by lazy {
        RadioAdapter {
            bindService(intentService, serviceConnection, BIND_AUTO_CREATE)
            radio = it
            binding.viewPlayer.tvTitle.text = it.name
            radio?.station?.let { radioService?.play(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupServiceConnection()
        setupListeners()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvRadio.adapter = adapter
        adapter.update(Resources.generate())
    }

    private fun setupServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Timber.d("onServiceConnected")
                radioService = (service as RadioService.RadioBinder).getService()
                radio?.station?.let { radioService?.play(it) }
                binding.viewPlayer.imgPlayPause.isActivated = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Timber.d("onServiceDisconnected")
                binding.viewPlayer.imgPlayPause.isActivated = false
            }
        }
    }

    private fun setupListeners() {
        binding.viewPlayer.imgPlayPause.setOnClickListener {
            if (it.isActivated) {
                unbindService(serviceConnection)
                binding.viewPlayer.imgPlayPause.isActivated = false
            } else {
                binding.viewPlayer.imgPlayPause.isActivated = true
            }
        }
//        binding.btnStart.setOnClickListener {
//            bindService(intentService, serviceConnection, BIND_AUTO_CREATE)
//        }
//
//        binding.btnStop.setOnClickListener {
//            unbindService(serviceConnection)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //For destroy service when activity is destroyed
        unbindService(serviceConnection)
    }
}