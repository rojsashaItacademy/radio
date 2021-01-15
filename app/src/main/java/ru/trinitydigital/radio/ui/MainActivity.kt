package ru.trinitydigital.radio.ui

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import ru.trinitydigital.radio.data.RadioService
import ru.trinitydigital.radio.data.RadioStations
import ru.trinitydigital.radio.data.RadioStationsRepository
import ru.trinitydigital.radio.databinding.ActivityMainBinding
import ru.trinitydigital.radio.util.viewBinding
import ru.trinitydigital.radio.util.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModel(MainViewModel::class)

    private lateinit var serviceConnection: ServiceConnection
    private val intentService by lazy { Intent(this, RadioService::class.java) }
    private var radioService: RadioService? = null
    private var radio: RadioStations? = null

    private val adapter by lazy {
        RadioAdapter {
            radio = it
            bindService(intentService, serviceConnection, BIND_AUTO_CREATE)
            radioService?.chooseRadio(it)
            viewModel.isBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupServiceConnection()
        setupListeners()
        setupRecyclerView()
    }

    private fun setupRadio() {
        radioService?.getActiveStation()?.observe(this, {
            binding.viewPlayer.tvTitle.text = it.name
        })
    }

    private fun setupRecyclerView() {
        binding.rvRadio.adapter = adapter
        adapter.update(RadioStationsRepository.radioList)
    }

    private fun setupServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Timber.d("onServiceConnected")
                viewModel.isBound = true
                radioService = (service as RadioService.RadioBinder).getService()
                setupRadio()
                radioService?.chooseRadio(radio ?: RadioStationsRepository.radioList[0])
                binding.viewPlayer.imgPlayPause.isActivated = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                Timber.d("onServiceDisconnected")
                viewModel.isBound = false
                binding.viewPlayer.imgPlayPause.isActivated = false
            }
        }
    }

    private fun setupListeners() {
        binding.viewPlayer.imgPlayPause.setOnClickListener {
            if (it.isActivated) {
                unbindService(serviceConnection)
                binding.viewPlayer.imgPlayPause.isActivated = false
                viewModel.isBound = false
            } else {
                bindService(intentService, serviceConnection, BIND_AUTO_CREATE)
                binding.viewPlayer.imgPlayPause.isActivated = true
                viewModel.isBound = true
            }
        }

        binding.viewPlayer.btnNext.setOnClickListener {
            radioService?.nextRadio()
        }

        binding.viewPlayer.btnPrev.setOnClickListener {
            radioService?.prevRadio()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //For destroy service when activity is destroyed
        unbindService(serviceConnection)
    }
}