package com.kushalsharma.e_com.models

data class cartProducts(
    val itemId: String = "",
    val itemName: String = "",
    val createdBy: user = user(),
    val itemPrice: String = "",
    val itemCount: String = "",
    val userId: String = user().uid,
    val itemImage : String = ""

        )