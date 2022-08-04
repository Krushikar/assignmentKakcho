package com.example.assignmentbalusonawane.model

data class NetworkResponse(
    val categories: List<Categories>,
    val iconsets: List<IconSets>,
    val icons: List<Icons>,
    val message: String,
    val code: String
)
