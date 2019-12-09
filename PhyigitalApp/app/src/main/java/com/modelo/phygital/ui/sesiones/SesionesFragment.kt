package com.modelo.phygital.ui.sesiones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.modelo.phygital.R

class SesionesFragment : Fragment() {

    private lateinit var galleryViewModel: SesionesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(SesionesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
    //    val textView: TextView = root.findViewById(R.id.text_gallery)
    //    galleryViewModel.text.observe(this, Observer {
    //        textView.text = it
    //    })
        return root
    }
}