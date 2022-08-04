package com.example.assignmentbalusonawane.data.repository

import com.example.assignmentbalusonawane.model.Categories
import com.example.assignmentbalusonawane.model.IconSets
import com.example.assignmentbalusonawane.model.Icons
import com.example.assignmentbalusonawane.utilities.Resource

interface MainRepository {

    suspend fun getCategories(count: Int) : Resource<List<Categories>>

    suspend fun getIconSets(identifier: String, count: Int) : Resource<List<IconSets>>

    suspend fun getIcons(iconset_id: Int, count: Int) : Resource<List<Icons>>

    suspend fun searchIcons(query: String, count: Int) : Resource<List<Icons>>

}