package br.com.akgs.doevida.ui.util

import java.util.Date


fun String.formatDate(): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("ddMMyyyy", java.util.Locale.getDefault())
        val outputFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        val parsedDate = inputFormat.parse(this)
        outputFormat.format(parsedDate ?: this)
    } catch (e: Exception) {
        "-" // Retorna a data original em caso de erro
    }
}
