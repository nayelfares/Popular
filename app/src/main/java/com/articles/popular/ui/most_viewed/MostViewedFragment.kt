package com.articles.popular.ui.most_viewed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.articles.popular.databinding.FragmentMostViewedBinding
import com.articles.popular.model.DataState

class MostViewedFragment : Fragment() {

    private  val mostViewedViewModel by activityViewModels<MostViewedViewModel>()
    private var _binding: FragmentMostViewedBinding? = null

    lateinit var loadingProgressBar: RelativeLayout
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMostViewedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMostViewed
        loadingProgressBar = binding.loadingProgressBar
        mostViewedViewModel.getMostViewed("all-sections",7)
        mostViewedViewModel.viewState.observe(viewLifecycleOwner,  {
            when(it){
                is DataState.Success->{
                    loadingProgressBar.visibility = View.GONE
                    textView.text = it.data.status
                }
                is DataState.Error ->{
                    loadingProgressBar.visibility = View.GONE
                }
                else ->{
                    loadingProgressBar.visibility = View.VISIBLE
                }
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}