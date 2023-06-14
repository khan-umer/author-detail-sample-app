package com.example.ezobook.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ezobook.R
import com.example.ezobook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUI()
    }

    private fun initUI() {
        binding.btnShowAuthorDetail.setOnClickListener {
            viewModel.getAuthorDetails()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.author.collect {
                    if (it.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.txtLabelAuthorName.visibility = View.GONE
                        binding.txtAuthorName.visibility = View.GONE
                        binding.ivAuthProfile.visibility = View.GONE
                    }
                    if (!it.error.isNullOrEmpty()) {
                        binding.progressBar.visibility = View.GONE
                        binding.txtLabelAuthorName.visibility = View.GONE
                        binding.txtAuthorName.visibility = View.GONE
                        binding.ivAuthProfile.visibility = View.GONE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }

                    it.data?.let { author ->
                        binding.progressBar.visibility = View.GONE
                        binding.txtLabelAuthorName.visibility = View.VISIBLE
                        binding.txtAuthorName.visibility = View.VISIBLE
                        binding.txtAuthorName.text = author.author
                        binding.ivAuthProfile.visibility = View.VISIBLE

                        Glide.with(this@MainActivity)
                            .load(author.image)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .centerCrop()
                            .placeholder(R.drawable.loading_image_placeholder)
                            .error(R.drawable.image_error_placeholder)
                            .into(binding.ivAuthProfile)
                    }

                }
            }
        }
    }


}