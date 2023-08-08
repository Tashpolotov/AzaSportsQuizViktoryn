package com.example.azasportsquizviktoryn.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.azasportsquizviktoryn.R
import com.example.azasportsquizviktoryn.databinding.FragmentHomeBinding
import com.example.azasportsquizviktoryn.ui.adapter.SportsHomeAdapter
import com.example.azasportsquizviktoryn.viewmodel.SportsQuizViewModel
import com.example.domain.model.SportsModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = SportsHomeAdapter(this::onItemClick)
    private val viewModel by viewModels<SportsQuizViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(name = String())
    }

    private fun initView(name: String? = null) {
        binding.recyclerViewCategories.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewModel.quizSport.collect {
                adapter.submitList(it.sports)
            }
        }
        if (name.isNullOrEmpty()) {
            viewModel.loadSports("Football") // Загрузить все спорты по умолчанию, например, футбол
        } else {
            viewModel.loadSports(name)
        }
    }

    private fun onItemClick(selectedSport: SportsModel) {
        val bundle = Bundle().apply {
            putString("sportName", selectedSport.name)
            putInt("sportImage", selectedSport.image)
        }
        val sportLevelFragment = SportLevelFragment()
        sportLevelFragment.arguments = bundle

        fragmentManager?.beginTransaction()
            ?.replace(R.id.fr_container, sportLevelFragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}