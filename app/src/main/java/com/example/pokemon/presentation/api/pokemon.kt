package com.example.pokemon.presentation.api

import android.util.Log
import com.alibaba.fastjson2.JSON
import com.example.pokemon.presentation.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

val apiUrl = "https://pokeapi.co/api/v2/pokemon"
/**
 * suspend 意思是是 在协程中使用
 * witchContext是包裹在 协程中，避免在主线程中阻塞
 */
suspend fun getPokemonById(id: String): Pokemon = withContext(Dispatchers.IO) {

    val request = Request.Builder().url("$apiUrl/$id").build()

    val response = OkHttpClient().newCall(request).execute()

    val body = response.body?.string()

    val jsonObject = JSON.parseObject(body)
    val pokemon = Pokemon(
        id,
        jsonObject.getJSONObject("species").getString("name"),
        jsonObject.getJSONObject("sprites").getString("front_default")
            .replace("raw.githubusercontent.com", "raw.fastgit.org")
    )

    Log.d("body ", JSON.toJSONString(pokemon))
    return@withContext pokemon
}
