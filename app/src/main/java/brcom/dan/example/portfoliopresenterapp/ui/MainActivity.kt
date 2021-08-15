package brcom.dan.example.portfoliopresenterapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import brcom.dan.example.portfoliopresenterapp.R
import brcom.dan.example.portfoliopresenterapp.core.createDialog
import brcom.dan.example.portfoliopresenterapp.core.createProgressDialog
import brcom.dan.example.portfoliopresenterapp.core.hideSoftKeyboard
import brcom.dan.example.portfoliopresenterapp.databinding.ActivityMainBinding
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

        viewModel.getRepoList("")
        viewModel.repos.observe(this){
            when(it){
                MainViewModel.State.Loadind -> dialog.show()
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    binding.rvRepos.adapter = RepoListAdapter(it.list)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
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

    companion object{
        private const val TAG = "TAG"
    }

}