package brcom.dan.example.portfoliopresenterapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import brcom.dan.example.portfoliopresenterapp.R
import brcom.dan.example.portfoliopresenterapp.core.utils.ResourceFinder
import brcom.dan.example.portfoliopresenterapp.databinding.ItemRepoBinding
import brcom.dan.example.portfoliopresenterapp.domain.Repository
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RepoListAdapter(private var list: List<Repository>): RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(
        private val binding: ItemRepoBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repository){
            binding.tvRepoName.text = item.name
            binding.tvRepoDescription.text = item.description
            binding.tvRepoLanguage.text = item.language
            binding.chipStars.text = item.stargazersCount.toString()

            val image = if(item.language.isNullOrBlank()){
                item.owner.avatarUrl
            }else{
                ResourceFinder.formatProgrammingLanguageUri(item.language)
            }

            Glide.with(binding.root.context)
                .applyDefaultRequestOptions(RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_placeholder_24)
                    .centerCrop())
                .load(image)
                .into(binding.ivOwner)
        }

    }
}