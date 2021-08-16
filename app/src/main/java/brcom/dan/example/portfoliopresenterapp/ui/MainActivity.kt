package brcom.dan.example.portfoliopresenterapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import brcom.dan.example.portfoliopresenterapp.R
import brcom.dan.example.portfoliopresenterapp.core.createDialog
import brcom.dan.example.portfoliopresenterapp.core.createProgressDialog
import brcom.dan.example.portfoliopresenterapp.core.hideSoftKeyboard
import brcom.dan.example.portfoliopresenterapp.databinding.ActivityMainBinding
import brcom.dan.example.portfoliopresenterapp.domain.Repository
import brcom.dan.example.portfoliopresenterapp.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel.repos.observe(this){
            when(it){
                MainViewModel.State.Loadind -> dialog.show()
                is MainViewModel.State.Error -> {
                    val list = ArrayList<Repository>()
                    binding.rvRepos.adapter = RepoListAdapter(list)
                    updateVisibility(viewModel.checkReposVisibility(list))
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    updateVisibility(viewModel.checkReposVisibility(it.list))
                    binding.rvRepos.adapter = RepoListAdapter(it.list)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                showTextViewEmptyRepos(false)
            }
            else {
                showTextViewEmptyRepos(true)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewModel.getRepoList(query)
        }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun updateVisibility(param: Boolean){
        showRecyclerView(!param)
        showTextViewEmptyRepos(param)
    }

    private fun showRecyclerView(shouldShow: Boolean){
        if (shouldShow)
            binding.rvRepos.visibility = View.VISIBLE
        else
            binding.rvRepos.visibility = View.GONE
    }

    private fun showTextViewEmptyRepos(shouldShow: Boolean){
        if (shouldShow)
            binding.tvEmptyRepos.visibility = View.VISIBLE
        else
            binding.tvEmptyRepos.visibility = View.GONE
    }

    companion object{
        private const val TAG = "TAG"
    }

}