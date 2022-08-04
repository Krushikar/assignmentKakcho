package com.example.assignmentbalusonawane.ui.view

import androidx.fragment.app.Fragment
import com.example.assignmentbalusonawane.databinding.ViewIconsBinding
import com.example.assignmentbalusonawane.model.Icons
import com.example.assignmentbalusonawane.ui.adapter.AdapterIcons

class ViewSearch: Fragment() {

    private var _binding: ViewIconsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterIcons: AdapterIcons
    private val icons = mutableListOf<Icons>()



}