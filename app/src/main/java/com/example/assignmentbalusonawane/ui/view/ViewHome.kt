package com.example.assignmentbalusonawane.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentbalusonawane.R
import com.example.assignmentbalusonawane.databinding.ViewHomeBinding
import com.example.assignmentbalusonawane.model.Categories
import com.example.assignmentbalusonawane.model.Icons
import com.example.assignmentbalusonawane.ui.adapter.AdapterCategories
import com.example.assignmentbalusonawane.ui.adapter.AdapterIcons
import com.example.assignmentbalusonawane.ui.viewmodel.ViewModelMain
import com.example.assignmentbalusonawane.utilities.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class ViewHome : Fragment() {

    private var _binding: ViewHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: ViewModelMain by viewModels()

    private lateinit var categoriesAdapter: AdapterCategories
    private var categories = listOf<Categories>()

    private lateinit var adapterIcons: AdapterIcons
    private val list = mutableListOf<Icons>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()

        binding.editTextSearch.doAfterTextChanged {
            it?.let { its ->
                mainViewModel.searchIcons(its.toString())
                binding.recyclerViewCategories.visibility = View.GONE
                binding.recyclerViewSearch.visibility = View.VISIBLE

            }

            if (it.isNullOrEmpty()) {

                binding.recyclerViewCategories.visibility = View.VISIBLE
                binding.recyclerViewSearch.visibility = View.GONE

            }
        }

        observeData()

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun observeData(){

        mainViewModel.getCategories()
        mainViewModel.categories.observe(viewLifecycleOwner){

            when(it){

                is Resource.Success -> {
                    progress("hide")
                        it.data?.let { data ->
                            categoriesAdapter.addCategories(data)
                            mainViewModel.setCatConsumed(true)

                    }
                    Log.e("SUCCESS", it.data.toString())
                }

                is Resource.Error -> {
                    progress("hide")
                    Toast.makeText(context, "Failed ${it.message}", Toast.LENGTH_SHORT).show()
                    mainViewModel.setCatConsumed(false)
                    Log.e("ERROR", it.message.toString())
                }

                is Resource.Loading -> {

                    if (!mainViewModel.categoriesConsumed) progress("show")
                    Log.e("LOADING","")
                    mainViewModel.setCatConsumed(false)

                }

            }


        }


        mainViewModel.search.observe(viewLifecycleOwner){

            when(it){

                is Resource.Success -> {
                    it.data?.let {icons ->
                        adapterIcons.addIcons(icons)
                    }
                    adapterIcons.addIcons(it.data!!)
                    Log.e("S ", "${it.data}")
                }

                is Resource.Error -> {

                    Log.e("S ", "${it.message}")
                }

                is Resource.Loading -> {

                    Log.e("S ", "Loading")

                }

            }

        }

    }



    private fun setRecyclerView(){
        val horizontal = DividerItemDecoration(activity, GridLayout.HORIZONTAL)


        categoriesAdapter = AdapterCategories(categories.toMutableList()){

            val bundle = bundleOf("identifier" to it)
            findNavController().navigate(R.id.action_viewHome_to_viewIconSets, bundle)
        }

        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriesAdapter
            addItemDecoration(horizontal)
            setHasFixedSize(true)
        }


        adapterIcons = AdapterIcons(list){

        }

        val horizontal1 = DividerItemDecoration(activity, GridLayout.HORIZONTAL)

        val vertical = DividerItemDecoration(activity, GridLayout.VERTICAL)

        binding.recyclerViewSearch.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = adapterIcons
            addItemDecoration(horizontal1)
            addItemDecoration(vertical)
            setHasFixedSize(true)
        }
    }

    private fun progress(state: String = "show"){
        binding.progressBar.visibility = if (state == "show") View.VISIBLE else View.INVISIBLE
    }
}