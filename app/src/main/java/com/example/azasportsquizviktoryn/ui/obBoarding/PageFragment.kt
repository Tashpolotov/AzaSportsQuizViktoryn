package com.example.azasportsquizviktoryn.ui.obBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.azasportsquizviktoryn.R
import com.example.azasportsquizviktoryn.databinding.FragmentPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageFragment : Fragment() {

    private lateinit var binding : FragmentPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = OnBoardAdapter(requireActivity())
        adapter.also {
            it.also { binding.vp2.adapter = it }
        }
        binding.indicator.attachTo(binding.vp2)
    }
}