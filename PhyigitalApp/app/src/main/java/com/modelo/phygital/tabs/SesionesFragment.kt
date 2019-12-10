package com.modelo.phygital.tabs


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.modelo.phygital.R
import kotlinx.android.synthetic.main.fragment_sesiones_contenido.*


/**
 * A simple [Fragment] subclass.
 */
class SesionesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sesiones_contenido, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        frag_sesiones_text.text= "sesiones fragment baby"
    }

}
