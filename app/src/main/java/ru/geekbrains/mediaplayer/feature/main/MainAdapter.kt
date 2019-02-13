package ru.geekbrains.mediaplayer.feature.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MainAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    private val items = mutableListOf<Pair<String, Fragment>>()

    fun addPage(item: Pair<String, Fragment>) {
        items.add(item)
    }

    override fun getItem(position: Int) = items[position].second
    override fun getCount() = items.size
    override fun getPageTitle(position: Int) = items[position].first
}