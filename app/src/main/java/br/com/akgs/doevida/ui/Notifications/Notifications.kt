package br.com.akgs.doevida.ui.Notifications

import java.util.Date

data class Notifications(
    val id: String,
    val title: String,
    val body: String,
    val timestamp: Date,
    val isRead: Boolean = false,
)
