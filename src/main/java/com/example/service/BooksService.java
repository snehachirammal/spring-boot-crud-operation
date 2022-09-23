package com.example.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Book;
import com.example.repository.BookDaoImpl;
import com.example.repository.BooksRepository;


@Service
public class BooksService {
	@Autowired
	BooksRepository booksRepository;
	@Autowired
	BookDaoImpl bookDaoImpl;

//getting all book records sorted by either bookname or authorname using the method findaAll() of JpaRepository
	public Set<Book> findBooksByAuthorNameOrTitle(String sortBy, String orderBy) {
		Set<Book> books = new LinkedHashSet<Book>();
		books = bookDaoImpl.findBooksByAuthorNameOrTitle(sortBy, orderBy);
		return books;
	}


//getting a specific book record by using the method findById() of JpaRepository
	public Optional<Book> getBooksById(int id) {
		Optional<Book> book = booksRepository.findById(id);
		return book;
	}

// saving a specific book record by using the method save() of JpaRepository
	public void saveOrUpdate(Book books) {
		booksRepository.save(books);
	}

//saving  multiple book records by using the method saveAll() of JpaRepository
	public List<Book> saveOrUpdate(List<Book> books) {
		return booksRepository.saveAll(books);
	}

//deleting a specific record by using the method deleteById() of JpaRepository
	public void delete(int id) {
		booksRepository.deleteById(id);
	}


}