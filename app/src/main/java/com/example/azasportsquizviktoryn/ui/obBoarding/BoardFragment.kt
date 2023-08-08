package com.example.azasportsquizviktoryn.ui.obBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.azasportsquizviktoryn.R
import com.example.azasportsquizviktoryn.databinding.FragmentBoardBinding
import com.example.azasportsquizviktoryn.ui.activity.MainActivity
import com.example.azasportsquizviktoryn.ui.fragments.HomeFragment
import com.example.azasportsquizviktoryn.ui.obBoarding.model.OnBoardModel
import com.example.azasportsquizviktoryn.ui.obBoarding.utils.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BoardFragment : Fragment() {

    private lateinit var binding: FragmentBoardBinding
    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        arguments.let {
            val data = it?.getSerializable("board") as OnBoardModel
            binding.tvStazam.text = data.title
            binding.tvTitleBoard.text = data.titleText
            data.img?.let { it1 -> binding.imgBoard.setImageResource(it1) }

        }
        binding.btnGetStarted.setOnClickListener{
            val fragmentManager = requireFragmentManager()
            fragmentManager.beginTransaction().replace(R.id.fr_container, HomeFragment()).commit()
            preferences.setBoardingShowed(true)
            (activity as? MainActivity)?.binding?.bottomNavView?.visibility = View.VISIBLE

        }
    }

}