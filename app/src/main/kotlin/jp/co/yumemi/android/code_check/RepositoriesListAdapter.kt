package jp.co.yumemi.android.code_check

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.model.RepositorySummary

class RepositoriesListAdapter(private val onItemClick: (RepositorySummary) -> Unit) :
    ListAdapter<RepositorySummary, RepositoriesListAdapter.ViewHolder>(ITEM_CALLBACK) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val txtRepoName = holder.itemView.findViewById<TextView>(R.id.repositoryNameView)
        txtRepoName.text = item.name

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}

private val ITEM_CALLBACK = object : DiffUtil.ItemCallback<RepositorySummary>() {
    override fun areItemsTheSame(oldItem: RepositorySummary, newItem: RepositorySummary): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RepositorySummary, newItem: RepositorySummary): Boolean {
        return oldItem == newItem
    }
}