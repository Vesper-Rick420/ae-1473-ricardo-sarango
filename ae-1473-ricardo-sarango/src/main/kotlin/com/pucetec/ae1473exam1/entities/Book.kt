package com.pucetec.ae1473exam1.entities

import jakarta.persistence.*

@Entity
@Table(name = "books")
class Book(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,

    val author: String,

    val priceUsd: Double
)
