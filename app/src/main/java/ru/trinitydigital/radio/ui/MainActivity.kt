package ru.trinitydigital.radio.ui

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.Observer
import ru.trinitydigital.radio.data.RadioService
import ru.trinitydigital.radio.data.RadioStations
import ru.trinitydigital.radio.data.Resources
import ru.trinitydigital.radio.databinding.ActivityMainBinding
import ru.trinitydigital.radio.util.forceRefresh
import ru.trinitydigital.radio.util.viewBinding
import ru.trinitydigital.radio.util.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModel(MainViewModel::class)

    private lateinit var serviceConnection: ServiceConnection
    private val intentService by lazy { Intent(this, RadioService::class.java) }
    private var radioService: RadioService? = null

    private val adapter by lazy {
        RadioAdapter {
            bindService(intentService, serviceConnection, BIND_AUTO_CREATE)
            viewModel.radioLiveData.postValue(it)
            viewModel.isBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupServiceConnection()
        setupListeners()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.radioLiveData.observe(this, {
            if (viewModel.isBound) radioService?.play(it.station)
            binding.viewPlayer.tvTitle.text = it.name
        })
    }

    private fun setupRecyclerView() {
        binding.rvRadio.adapter = adapter
        adapter.update(viewModel.radioList)
    }

    private fun setupServiceConnection() {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Timber.d("onServiceConnected")
                viewModel.isBound = true
                radioService = (service as RadioService.RadioBinder).getService()
                viewModel.radioLiveData.forceRefresh()
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
            viewModel.nextStation()
        }

        binding.viewPlayer.btnPrev.setOnClickListener {
            viewModel.prevStation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //For destroy service when activity is destroyed
        unbindService(serviceConnection)
    }
}