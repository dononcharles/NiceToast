package com.dononcharles.nicetoast

enum class NiceToastType {
    INFO,
    SUCCESS,
    WARNING,
    ERROR,
    DELETE,
    NO_INTERNET;

    fun getName(): String = this.name.replaceFirst("_", " ")
}