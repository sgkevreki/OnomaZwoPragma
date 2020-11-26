package com.example.onomazwopragmaproject

// I made this a "data class" because I think it will only hold data, plus Firebase handles data class objects very elegantly.
// I sure hope I won't need to change this later :)
data class Member(
    val name: String = "",
    // memberId is a random string that gets created with the Member object instance
    val memberId: String =
        (1..GlobalsActivity.memberIdLength).map { GlobalsActivity.memberIdSource.random() }.joinToString("")
)