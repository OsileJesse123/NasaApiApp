package com.jesse.nasaapi

import kotlinx.coroutines.withContext


fun formatText(text: String): String =
        if (text.length > 32) {
            text.dropLast(text.length - 28) + "..."
        } else {
            text
        }