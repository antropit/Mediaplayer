package ru.geekbrains.mediaplayer.feature.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.mediaplayer.R
import ru.geekbrains.mediaplayer.feature.list.ListFragment
import ru.geekbrains.mediaplayer.model.MediaType
import ru.geekbrains.mediaplayer.service.PlayerService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        startService(Intent(this, PlayerService::class.java))
    }

    private fun initView() {
        val adapter = MainAdapter(supportFragmentManager)
        adapter.addPage(Pair("ONLINE", ListFragment.newInstance(MediaType.ONLINE.name)))
        adapter.addPage(Pair("FILE", ListFragment.newInstance(MediaType.FILE.name)))
        pager.adapter = adapter
        tabs.setupWithViewPager(pager)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        supportFragmentManager.fragments.forEach { it.onRequestPermissionsResult(requestCode, permissions, grantResults) }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, PlayerService::class.java))
    }
}
