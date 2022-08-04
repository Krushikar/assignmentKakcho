package com.example.assignmentbalusonawane.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentbalusonawane.data.repository.MainRepository
import com.example.assignmentbalusonawane.model.Categories
import com.example.assignmentbalusonawane.model.Icons
import com.example.assignmentbalusonawane.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {


    private val _categories = MutableLiveData<Resource<List<Categories>>>(Resource.Loading())
    val categories = _categories
    val count : Int =  10

    private val _search = MutableLiveData<Resource<List<Icons>>>(Resource.Loading())
    val search = _search

    private var _categoriesConsumed: Boolean = false
    val categoriesConsumed get() =  _categoriesConsumed

    fun setCatConsumed(consumed: Boolean){
        _categoriesConsumed = consumed
    }

    fun getCategories(){

        _categories.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {

            when (val result = repository.getCategories(count)) {

                is Resource.Success -> _categories.postValue(Resource.Success(result.data))

                is Resource.Error -> _categories.postValue(Resource.Error(result.message))

                is Resource.Loading -> _categories.postValue(Resource.Loading())
            }

        }

    }

    fun searchIcons(query: String){

        _search.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {

            when (val result = repository.searchIcons(query,count)) {

                is Resource.Success -> _search.postValue(Resource.Success(result.data))

                is Resource.Error -> _search.postValue(Resource.Error(result.message))

                is Resource.Loading -> _search.postValue(Resource.Loading())
            }

        }


    }

}