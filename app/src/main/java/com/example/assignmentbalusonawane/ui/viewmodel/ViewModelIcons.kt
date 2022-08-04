package com.example.assignmentbalusonawane.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentbalusonawane.data.repository.MainRepository
import com.example.assignmentbalusonawane.model.IconSets
import com.example.assignmentbalusonawane.model.IconSize
import com.example.assignmentbalusonawane.model.Icons
import com.example.assignmentbalusonawane.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelIcons
@Inject constructor(
    private val repository: MainRepository
) : ViewModel()
{

    private val _iconSets = MutableLiveData<Resource<List<IconSets>>>(Resource.Loading())
    val iconSets = _iconSets
    private val count : Int =  10

    private val _icons = MutableLiveData<Resource<List<Icons>>>(Resource.Loading())
    val icons = _icons

    private var _iconSetsConsumed: Boolean = false
    val iconSetsConsumed get() =  _iconSetsConsumed

    fun setIconSetsConsumed(consumed: Boolean){
        _iconSetsConsumed = consumed
    }

    private var _iconsConsumed: Boolean = false
    val iconsConsumed get() =  _iconsConsumed

    fun setIconsConsumed(consumed: Boolean){
        _iconsConsumed = consumed
    }

    private var _iconSizes = mutableListOf<IconSize>()
    val iconSizes get() = _iconSizes

    fun setIconSizes(list: List<IconSize>){
        _iconSizes.clear()
        _iconSizes.addAll(list)
    }

    fun getIconSets(identifier: String){

        _iconSets.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {

            when (val result = repository.getIconSets(identifier,count)) {

                is Resource.Success -> _iconSets.postValue(Resource.Success(result.data))

                is Resource.Error -> _iconSets.postValue(Resource.Error(result.message))

                is Resource.Loading -> _iconSets.postValue(Resource.Loading())
            }

        }


    }

    fun getIcons(id: Int){

        _icons.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {

            when (val result = repository.getIcons(id,count)) {

                is Resource.Success -> _icons.postValue(Resource.Success(result.data))

                is Resource.Error -> _icons.postValue(Resource.Error(result.message))

                is Resource.Loading -> _icons.postValue(Resource.Loading())
            }

        }


    }

}