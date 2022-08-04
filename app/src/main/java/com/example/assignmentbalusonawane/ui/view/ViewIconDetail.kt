package com.example.assignmentbalusonawane.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assignmentbalusonawane.R
import com.example.assignmentbalusonawane.databinding.ViewIconDetailBinding
import com.example.assignmentbalusonawane.databinding.ViewIconsBinding
import com.example.assignmentbalusonawane.model.IconSize
import com.example.assignmentbalusonawane.ui.adapter.AdapterIcons
import com.example.assignmentbalusonawane.ui.adapter.AdapterIconsSizes
import com.example.assignmentbalusonawane.ui.viewmodel.ViewModelIcons

class ViewIconDetail : Fragment() {

    private var _binding: ViewIconsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterIconsSizes: AdapterIconsSizes
    private val iconsSizes = mutableListOf<IconSize>()

    private val viewModelIcons: ViewModelIcons by hiltNavGraphViewModels(R.id.nav_icons_sets)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewIconsBinding.inflate(layoutInflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iconsSizes.addAll(viewModelIcons.iconSizes)
        Log.e("icons","${viewModelIcons.iconSizes}")
        setRecyclerView()
        binding.progressBar3.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setRecyclerView(){

        adapterIconsSizes = AdapterIconsSizes(iconsSizes){

          //  val bundle = bundleOf("identifier" to it)
            findNavController().navigate(R.id.action_viewIconSets_to_viewIcons)
        }

        val horizontal = DividerItemDecoration(activity, GridLayout.HORIZONTAL)
        val vertical = DividerItemDecoration(activity, GridLayout.VERTICAL)

        binding.recyclerViewIcons.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext,2)
            adapter = adapterIconsSizes
            addItemDecoration(horizontal)
            addItemDecoration(vertical)
            setHasFixedSize(true)
        }

    }

}