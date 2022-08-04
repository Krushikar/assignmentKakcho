package com.example.assignmentbalusonawane.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentbalusonawane.R
import com.example.assignmentbalusonawane.databinding.ViewIconsBinding
import com.example.assignmentbalusonawane.model.Icons
import com.example.assignmentbalusonawane.ui.adapter.AdapterIconSets
import com.example.assignmentbalusonawane.ui.adapter.AdapterIcons
import com.example.assignmentbalusonawane.ui.viewmodel.ViewModelIcons
import com.example.assignmentbalusonawane.utilities.Resource

class ViewIcons : Fragment() {

    private var _binding: ViewIconsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterIcons: AdapterIcons
    private val icons = mutableListOf<Icons>()

    private val viewmodelIcons: ViewModelIcons by hiltNavGraphViewModels(R.id.nav_icons_sets)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewIconsBinding.inflate(layoutInflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val iconSetsId: Int? = arguments?.getInt("iconset_id")

        setRecyclerView()
        iconSetsId?.let {
            observeData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


    private fun setRecyclerView(){

        adapterIcons = AdapterIcons(icons){

            viewmodelIcons.setIconSizes(it)
            Log.e("Icon", "${viewmodelIcons.iconSizes} $it")
            findNavController().navigate(R.id.action_viewIcons_to_viewIconDetail)
        }

        val horizontal = DividerItemDecoration(activity, GridLayout.HORIZONTAL)
        val vertical = DividerItemDecoration(activity, GridLayout.VERTICAL)

        binding.recyclerViewIcons.apply {
            layoutManager = GridLayoutManager(activity?.applicationContext,2)
            adapter = adapterIcons
            addItemDecoration(horizontal)
            addItemDecoration(vertical)
            setHasFixedSize(true)
        }

    }

    private fun observeData(id: Int){

        viewmodelIcons.getIcons(id)
        viewmodelIcons.icons.observe(viewLifecycleOwner){

            when(it){

                is Resource.Success -> {
                    progress("hide")
                        it.data?.let { data ->
                            adapterIcons.addIcons(data)
                            viewmodelIcons.setIconsConsumed(true)
                        }

                    Log.e("SUCCESS", it.data.toString())
                }

                is Resource.Error -> {
                    progress("hide")
                    Toast.makeText(context, "Failed ${it.message}", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR", it.message.toString())
                }

                is Resource.Loading -> {

                    if (!viewmodelIcons.iconsConsumed)progress("show")
                    Log.e("LOADING","")

                }

            }


        }

    }

    private fun progress(state: String = "show"){
        binding.progressBar3.visibility = if (state == "show") View.VISIBLE else View.INVISIBLE
    }
}