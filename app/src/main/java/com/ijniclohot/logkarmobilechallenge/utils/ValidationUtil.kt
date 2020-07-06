package com.ijniclohot.logkarmobilechallenge.utils

import java.util.regex.Pattern

fun validateSenderName(name: String): String {
    return when {
        name.isEmpty() -> {
            "Nama wajib diisi"
        }
        name.length < 3 -> {
            "Nama minimum 3 karakter"
        }
        name.length > 50 -> {
            "Nama maksimum 50 karakter"
        }
        else -> ""
    }
}

fun validatePhoneNumber(phoneNumber: String): String {
    val pattern = Pattern.compile("^[8][0-9]{9,12}")
    if (phoneNumber.isNotEmpty()) {
        if (phoneNumber.first().toString() != "8") {
            return "No HP wajib diawali dengan 8"
        }
        if (pattern.matcher(phoneNumber).matches()) {
            return ""
        }
        return "Nomor Handphone tidak sesuai"
    }
    return "Nomor Hanphone tidak boleh kosong"

}

fun validateText(text: String): String {
    if (text.isNotEmpty()) {
        return when {
            text.length < 3 -> {
                "Field minimal 3 karakter"
            }
            text.length > 50 -> {
                "Field maksimal 50 karakter"
            }
            else -> ""
        }
    }
    return "Field tidak boleh kosong"
}

fun validateJlhPesanan(text: String): String {
    if (text.isNotEmpty()) {
        return when {
            text.toInt() == 0 -> {
                "Jumlah pesanan harus lebih dari 0"
            }
            else -> ""
        }
    }
    return "Field tidak boleh kosong"
}
