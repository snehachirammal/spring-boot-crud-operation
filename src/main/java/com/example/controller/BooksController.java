package com.example.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Author;
import com.example.model.Book;
import com.example.service.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class BooksController {

	@Autowired
	BooksService booksService;

//creating a get mapping that retrieves all the books detail from the database sorted by author or name
	@GetMapping("/books")
	private ResponseEntity<Set<Book>> getBooksByAuthorOrName(
			@RequestParam(value = "sortBy", required = true) String sortBy,
			@RequestParam(value = "orderBy", required = true) String orderBy) {
		List<String> permittedSortBy = Arrays.asList("bookname", "authorname");
		List<String> permittedOrderBy = Arrays.asList("asc", "desc");
		if ((permittedOrderBy).contains(orderBy.toLowerCase()) && permittedSortBy.contains(sortBy.toLowerCase())) {
			Set<Book> bookListSortedAndOrder = new LinkedHashSet<Book>();
			bookListSortedAndOrder = booksService.findBooksByAuthorNameOrTitle(sortBy, orderBy);
			if (bookListSortedAndOrder.isEmpty()) {
				return new ResponseEntity<Set<Book>>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Set<Book>>(bookListSortedAndOrder, HttpStatus.OK);
		} else {
			return new ResponseEntity<Set<Book>>(HttpStatus.BAD_REQUEST);
		}
	}

//creating a get mapping that retrieves the detail of a specific book
	@GetMapping("/book/{bookid}")
	private ResponseEntity getBook(@PathVariable("bookid") int bookid) {
		Optional<Book> bookById = booksService.getBooksById(bookid);
		System.out.println("bookById" + bookById);
		if (bookById.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Book Found");
		}
		return new ResponseEntity<Book>(bookById.get(), HttpStatus.OK);
	}

// creating post mapping that post the  books detail in the database
	@PostMapping("/books")
	private ResponseEntity<List<Book>> saveBooks(@RequestBody List<Book> books) {
		List<Book> savedBook =booksService.saveOrUpdate(books);
		return new ResponseEntity<List<Book>>(savedBook, HttpStatus.CREATED);
	}

//create patch mapping that update the book and author details
	@PatchMapping("/book/{bookid}")
	private ResponseEntity<String> updateBookDetails(@PathVariable("bookid") int id,
			@RequestBody Map<String, Object> fields) {
		Optional<Book> book = booksService.getBooksById(id);
		if (book.isPresent()) {
			fields.forEach((k, v) -> {
				Field field = ReflectionUtils.findField(Book.class, k);
				field.setAccessible(true);
				ReflectionUtils.setField(field, book.get(), v);

				if (k.equals("bookName")) {
					String bookName = (String) v;
					book.get().setBookName(bookName);
				} else if (k.equals("authorList")) {
					List<Author> authorList = partialBookUpdate((List) v, fields);
					book.get().setAuthorList(authorList);
				}
			});
		} else {
			new ResponseEntity<String>("Unable to find book details", HttpStatus.NOT_FOUND);
		}
		booksService.saveOrUpdate(book.get());
		return new ResponseEntity<String>("Books details Successfully Updated", HttpStatus.OK);
	}

	private List<Author> partialBookUpdate(List authorDetails, Map<String, Object> detailUpdates) {
		List<Author> authorLst = new ArrayList<Author>();
		final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
		for (int i = 0; i < authorDetails.size(); i++) {
			final Author pojo = mapper.convertValue(authorDetails.get(i), Author.class);
			authorLst.add(pojo);
		}

		return authorLst;
	}

//creating a delete mapping that deletes a specified book
	@DeleteMapping("/book/{bookid}")
	private ResponseEntity<String> deleteBook(@PathVariable("bookid") int bookid) {
		booksService.delete(bookid);
		return new ResponseEntity<String>("Book deleted succesfully", HttpStatus.OK);
	}

}
