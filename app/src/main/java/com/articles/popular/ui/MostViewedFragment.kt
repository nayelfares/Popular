package com.articles.popular.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.articles.popular.databinding.FragmentMostViewedBinding
import com.articles.popular.model.Article
import com.articles.popular.model.DataState
import com.articles.popular.vm.ArticleAdapter
import com.articles.popular.vm.MostViewedViewModel
import kotlin.collections.ArrayList

class MostViewedFragment : Fragment() {

    private  val mostViewedViewModel by activityViewModels<MostViewedViewModel>()
    private var allArticles = ArrayList<Article>()

    // data binding
    lateinit var binding :FragmentMostViewedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get saved state in case configuration change
        if (savedInstanceState!=null){
            allArticles = savedInstanceState.getParcelableArrayList(ALL_ARTICLES)?:ArrayList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMostViewedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (allArticles.isEmpty())
            mostViewedViewModel.getMostViewed(All_SECTIONS, PERIOD)
        else{
            binding.articlesRecyclerView.adapter = ArticleAdapter(allArticles)
        }
        subscribeObservers()
        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //  save state in case configuration change
        outState.putParcelableArrayList(ALL_ARTICLES, allArticles)
        super.onSaveInstanceState(outState)
    }

    private fun  subscribeObservers(){
        // observing articlesState when fetch Api
        mostViewedViewModel.articlesState.observe(viewLifecycleOwner,  {
            when(it){
                is DataState.Success->{
                    allArticles = it.data.results
                    binding.articlesRecyclerView.adapter = ArticleAdapter(allArticles)
                    binding.loadingProgressBar.visibility = View.GONE
                    if (allArticles.isEmpty()){
                        binding.noArticlesToShow.visibility = View.VISIBLE
                    }else{
                        binding.noArticlesToShow.visibility = View.GONE
                    }
                }
                is DataState.Error ->{
                    binding.loadingProgressBar.visibility = View.GONE
                    if (allArticles.isEmpty()){
                        binding.noArticlesToShow.visibility = View.VISIBLE
                    }else{
                        binding.noArticlesToShow.visibility = View.GONE
                    }
                }
                else ->{
                    binding.loadingProgressBar.visibility = View.VISIBLE
                }
            }
        })
        // observing search Text when user type in search view
        mostViewedViewModel.searchText.observe(viewLifecycleOwner, {
            val filteredList = allArticles.filter { article ->
                article.title.lowercase().contains(it.lowercase())
            }
            binding.articlesRecyclerView.adapter = ArticleAdapter(ArrayList(filteredList))
        })
    }

    companion object{
        const val ALL_ARTICLES = "all_articles"
        const val All_SECTIONS = "all-sections"
        const val PERIOD = 7
    }
}