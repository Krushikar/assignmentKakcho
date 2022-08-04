package com.example.assignmentbalusonawane.ui.view

import android.os.Bundle
import android.service.carrier.CarrierIdentifier
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentbalusonawane.R
import com.example.assignmentbalusonawane.databinding.ViewIconSetsBinding
import com.example.assignmentbalusonawane.model.IconSets
import com.example.assignmentbalusonawane.ui.adapter.AdapterCategories
import com.example.assignmentbalusonawane.ui.adapter.AdapterIconSets
import com.example.assignmentbalusonawane.ui.viewmodel.ViewModelIcons
import com.example.assignmentbalusonawane.utilities.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewIconSets : Fragment() {

    private var _binding: ViewIconSetsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterIconSets: AdapterIconSets
    private  val  iconSets = mutableListOf<IconSets>()

    private val viewmodelIconSets: ViewModelIcons by hiltNavGraphViewModels(R.id.nav_icons_sets)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewIconSetsBinding.inflate(layoutInflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val identifier = arguments?.getString("identifier")

        setRecyclerView()
        identifier?.let {
            observeData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setRecyclerView(){

        adapterIconSets = AdapterIconSets(iconSets){

            val bundle = bundleOf("iconset_id" to it)
            findNavController().navigate(R.id.action_viewIconSets_to_viewIcons, bundle)
        }
        val horizontal = DividerItemDecoration(activity, GridLayout.HORIZONTAL)

        binding.recyclerViewIconSets.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterIconSets
            addItemDecoration(horizontal)
            setHasFixedSize(true)
        }

    }

    private fun observeData(id: String){

        viewmodelIconSets.getIconSets(id)
        viewmodelIconSets.iconSets.observe(viewLifecycleOwner){

            when(it){

                is Resource.Success -> {
                    progress("hide")
                        it.data?.let { data ->
                            adapterIconSets.addIconSets(data)
                            viewmodelIconSets.setIconSetsConsumed(true)
                        }

                    Log.e("SUCCESS", it.data.toString())
                }

                is Resource.Error -> {
                    progress("hide")
                    Toast.makeText(context, "Failed ${it.message}", Toast.LENGTH_SHORT).show()
                    viewmodelIconSets.setIconSetsConsumed(false)
                    Log.e("ERROR", it.message.toString())
                }

                is Resource.Loading -> {

                    if (!viewmodelIconSets.iconSetsConsumed) progress("show")
                    Log.e("LOADING","")
                    viewmodelIconSets.setIconSetsConsumed(false)

                }

            }


        }

    }

    private fun progress(state: String = "show"){
        binding.progressBar2.visibility = if (state == "show") View.VISIBLE else View.INVISIBLE
    }

}