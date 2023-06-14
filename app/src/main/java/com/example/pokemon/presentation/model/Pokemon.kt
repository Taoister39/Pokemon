package com.example.pokemon.presentation.model

import androidx.compose.runtime.mutableStateListOf

/**
 * data 关键字等同于lombok的@Data
 *
 * 后面的参数对应着类的构造函数
 */
data class Pokemon (
    val id:String,
    val title:String,
    val coverUrl:String
)

var myLoveList =mutableStateListOf<Pokemon>();