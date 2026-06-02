package com.pucetec.ae1473exam1.controllers

import com.pucetec.ae1473exam1.dto.BookRequest
import com.pucetec.ae1473exam1.dto.BookResponse
import com.pucetec.ae1473exam1.services.BookService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService
) {

    @PostMapping
    fun createBook(@RequestBody request: BookRequest): BookResponse {
        return bookService.createBook(request)
    }

    @GetMapping
    fun getAllBooks(): List<BookResponse> {
        return bookService.getAllBooks()
    }
}
