package com.example.assignmentbalusonawane.data.repository

import android.graphics.drawable.Icon
import android.util.Log
import com.example.assignmentbalusonawane.data.remote.Api
import com.example.assignmentbalusonawane.model.Categories
import com.example.assignmentbalusonawane.model.IconSets
import com.example.assignmentbalusonawane.model.Icons
import com.example.assignmentbalusonawane.utilities.Resource
import java.lang.Exception

class MainRepositoryImp(private val api: Api) : MainRepository {

    override suspend fun searchIcons(query: String, count: Int): Resource<List<Icons>> {
        return try{

            val response = api.searchIcons(query = query, count = count)
            val result = response.body()

            Log.e("SUCCESS", result.toString())

            if (result != null){
                if (result.icons.isNotEmpty()) Resource.Success(result.icons) else Resource.Error(result.message)
            }else{
                Resource.Error(response.message() ?: "Unknown error")
            }

        } catch (e: Exception){

            Resource.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getCategories(count: Int): Resource<List<Categories>> {

        return try{

            val response = api.getCategories(count = count)
            val result = response.body()

            Log.e("SUCCESS", result.toString())

            if (result != null){
                if (result.categories.isNotEmpty()) Resource.Success(result.categories) else Resource.Error(result.message)
            }else{
                Resource.Error(response.message() ?: "Unknown error")
            }

        } catch (e: Exception){

            Resource.Error(e.message ?: "Unknown error")
        }

    }

    override suspend fun getIconSets(identifier: String, count: Int): Resource<List<IconSets>> {

        return try{

            val response = api.getIconSets(identifier = identifier, count = count)
            val result = response.body()

            Log.e("SUCCESS", result.toString())

            if (result != null){
                if (result.iconsets.isNotEmpty()) Resource.Success(result.iconsets) else Resource.Error(result.message)
            }else{
                Resource.Error(response.message() ?: "Unknown error")
            }

        } catch (e: Exception){

            Resource.Error(e.message ?: "Unknown error")
        }

    }

    override suspend fun getIcons(iconset_id: Int, count: Int): Resource<List<Icons>> {


        return try{

            val response = api.getIcons(iconset_id = iconset_id, count = count)
            val result = response.body()

            Log.e("SUCCESS", result.toString())

            if (result != null){
                if (result.icons.isNotEmpty()) Resource.Success(result.icons) else Resource.Error(result.message)
            }else{
                Resource.Error(response.message() ?: "Unknown error")
            }

        } catch (e: Exception){

            Resource.Error(e.message ?: "Unknown error")
        }


    }

}