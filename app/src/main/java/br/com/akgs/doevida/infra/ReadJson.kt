package br.com.akgs.doevida.infra

import android.content.Context
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.InputStreamReader
import com.google.gson.Gson

@Serializable
data class Estado(
    val id: String,
    val sigla: String,
    val nome: String
)

@Serializable
data class Cidade(
    val id: String,
    val nome: String,
    val estado: String
)

class ReadJson(private val context: Context) {
    fun readCidadesFromJson(fileName: String): List<Cidade> {
        val inputStream = context.assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        return Gson().fromJson(reader, Array<Cidade>::class.java).toList()
    }

    fun readEstadosFromJson(fileName: String): List<Estado> {
        val inputStream = context.assets.open(fileName)
        val reader = InputStreamReader(inputStream)
        return Gson().fromJson(reader, Array<Estado>::class.java).toList()
    }
}

