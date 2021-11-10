package com.articles.popular.ui

import android.os.Bundle
import android.os.RecoverySystem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.articles.popular.databinding.FragmentMostViewedBinding
import com.articles.popular.model.Article
import com.articles.popular.model.DataState
import com.articles.popular.vm.ArticleAdapter
import com.articles.popular.vm.MostViewedViewModel

class MostViewedFragment : Fragment() {

    private  val mostViewedViewModel by activityViewModels<MostViewedViewModel>()
    private var _binding: FragmentMostViewedBinding? = null
    private var allArticles = ArrayList<Article>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState!=null){
            allArticles = savedInstanceState.getParcelableArrayList<Article>(ALL_ARTICLES)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMostViewedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (allArticles.isEmpty())
            mostViewedViewModel.getMostViewed("all-sections",7)
        else{
            binding.articlesRecyclerView.adapter = ArticleAdapter(allArticles)
        }
        mostViewedViewModel.viewState.observe(viewLifecycleOwner,  {
            when(it){
                is DataState.Success->{
                    allArticles = it.data.results
                    binding.articlesRecyclerView.adapter = ArticleAdapter(allArticles)
                    binding.loadingProgressBar.visibility = View.GONE
                }
                is DataState.Error ->{
                    binding.loadingProgressBar.visibility = View.GONE
                }
                else ->{
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }
            }
        })
        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(ALL_ARTICLES, allArticles)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val ALL_ARTICLES = "all_articles"
    }
}