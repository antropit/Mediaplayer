package ru.geekbrains.mediaplayer.feature.list


import android.Manifest
import android.arch.lifecycle.Observer
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

import ru.geekbrains.mediaplayer.R

const val REQUEST_CODE = 1
class ListFragment : Fragment() {

    companion object {
        private const val TYPE_NAME = "type_name"

        fun newInstance(typeName: String): ListFragment {
            val bundle = Bundle().apply {
                putString(TYPE_NAME, typeName)
            }
            return ListFragment().apply { arguments = bundle }
        }
    }

    private val viewModel by viewModel<ListViewModel>{
        parametersOf(arguments!!.getString(TYPE_NAME))
    }

    private val adapter = MediaAdapter(listener = {viewModel.onClickItem(it)})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        subscribe()

        lifecycle.addObserver(viewModel)

        if (checkPermission() == PackageManager.PERMISSION_DENIED) requestPermission()
    }

    private fun initView() {
        rvMedia.layoutManager = LinearLayoutManager(context)
        rvMedia.adapter = adapter
        btnStartStop.setOnClickListener { viewModel.onStartStopPlayer() }
    }

    private fun subscribe() {
        viewModel.getMedia().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        viewModel.isPlayingState.observe(viewLifecycleOwner, Observer {
            it?.also {
                if (it) btnStartStop.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_stop))
                    else btnStartStop.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_play_arrow))
            }
        })
    }

    private fun checkPermission() = ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE)

    private fun requestPermission() {
        ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    viewModel.getFilesFromDisc()
                }
            }
        }
    }
}
