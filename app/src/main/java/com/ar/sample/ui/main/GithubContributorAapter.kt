package com.ar.sample.ui.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ar.sample.R
import com.ar.sample.data.models.GithubContributors
import com.ar.sample.databinding.ContributerListItemBinding
import com.ar.sample.interfaces.ItemClickListener
import com.bumptech.glide.Glide


/**
 * @Author: Abdul Rehman
 */

internal class GithubContributorAapter(var context: Context) :
    ListAdapter<GithubContributors, GithubContributorAapter.ViewHolder>(UserDiffCallBack()) {

    private val TAG = "GithubContributorAapter"

    //    private var items: List<GithubContributors> = emptyList()
    private var itemClickLister: ItemClickListener<GithubContributors>? = null

    fun setClickListener(itemClickLister: ItemClickListener<GithubContributors>?) {
        this.itemClickLister = itemClickLister
    }

    internal class ViewHolder(binding: ContributerListItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ContributerListItemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ContributerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.name.text =
            (context.getString(R.string.contributor_adapter_developer_name) + " " + item.name)
        holder.binding.contributions.setText(context.getString(R.string.contributor_adapter_total_commits) + " " + item.contributions)
        Glide.with(context).load(item.imageURL).into(holder.binding.avatar)
        holder.itemView.setOnClickListener {
            itemClickLister?.onItemClick(item, "ItemClick")
        }
    }

    //This check runs on background thread
    class UserDiffCallBack : DiffUtil.ItemCallback<GithubContributors>() {
        private val TAG = "TaskDiffCallBack"
        override fun areItemsTheSame(
            oldItem: GithubContributors,
            newItem: GithubContributors
        ): Boolean {
            Log.d(TAG, Thread.currentThread().name)
            return oldItem.id == newItem.id;
        }

        override fun areContentsTheSame(
            oldItem: GithubContributors,
            newItem: GithubContributors
        ): Boolean {
            Log.d(TAG, Thread.currentThread().name)
            return oldItem == newItem
        }
    }
}