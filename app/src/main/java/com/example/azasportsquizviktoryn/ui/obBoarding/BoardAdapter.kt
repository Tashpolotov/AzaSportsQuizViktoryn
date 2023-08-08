package com.example.azasportsquizviktoryn.ui.obBoarding

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.azasportsquizviktoryn.R
import com.example.azasportsquizviktoryn.ui.obBoarding.model.OnBoardModel
import dagger.hilt.android.qualifiers.ApplicationContext

class OnBoardAdapter(@ApplicationContext fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    private val listBoarding = arrayOf(
        OnBoardModel(
            "Добро пожаловать в нашу викторину",
            R.drawable.quizz,
            "Выберите категорию и пройдите викторину"
        ),
        OnBoardModel(
            "Пробуйте разные спортивные категории ",
            R.drawable.sport,
            "и узнавайте что то новое"

        ),
        OnBoardModel(
            "Вы получите награду за ваши знания",
            R.drawable.cup,
            "Выигрывай в викторине и окажись в таблице лидеров",
        )
    )
    override fun getItemCount(): Int = listBoarding.size

    override fun createFragment(position: Int): Fragment {
        val data = bundleOf("board" to listBoarding[position])
        val fragment = BoardFragment()
        fragment.arguments = data
        return fragment
    }
}