package com.articles.popular.ui.most_viewed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.articles.popular.databinding.FragmentMostViewedBinding

class MostViewedFragment : Fragment() {

    private lateinit var mostViewedViewModel: MostViewedViewModel
    private var _binding: FragmentMostViewedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mostViewedViewModel =
            ViewModelProvider(this).get(MostViewedViewModel::class.java)

        _binding = FragmentMostViewedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMostViewed
        mostViewedViewModel.text.observe(viewLifecycleOwner,  {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}