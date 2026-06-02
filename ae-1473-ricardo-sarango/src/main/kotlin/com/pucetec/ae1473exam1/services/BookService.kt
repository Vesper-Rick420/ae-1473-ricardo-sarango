package com.pucetec.ae1473exam1.services

import com.pucetec.ae1473exam1.dto.BookRequest
import com.pucetec.ae1473exam1.dto.BookResponse
import com.pucetec.ae1473exam1.entities.Book
import com.pucetec.ae1473exam1.repositories.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    fun createBook(request: BookRequest): BookResponse {
        val normalizedTitle = capitalize(request.title.trim())
        val normalizedAuthor = capitalize(request.author.trim())

        val book = Book(
            title = normalizedTitle,
            author = normalizedAuthor,
            priceUsd = request.priceUsd
        )

        val savedBook = bookRepository.save(book)
        return toResponse(savedBook)
    }

    fun getAllBooks(): List<BookResponse> {
        return bookRepository.findAll()
            .map { toResponse(it) }
            .sortedBy { it.title }
    }

    // --- Helpers privados ---

    private fun capitalize(text: String): String {
        return text.split(" ")
            .filter { it.isNotEmpty() }
            .joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
    }

    private fun toResponse(book: Book): BookResponse {
        val slug = book.title.lowercase().replace(" ", "-")
        val finalPrice = book.priceUsd * 1.12

        return BookResponse(
            id = book.id,
            title = book.title,
            author = book.author,
            slug = slug,
            priceUsd = book.priceUsd,
            finalPrice = finalPrice
        )
    }
}
