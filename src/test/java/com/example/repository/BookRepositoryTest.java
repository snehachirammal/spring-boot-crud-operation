package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.model.Book;
import com.example.model.BookTestData;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
@TestPropertySource(
		  locations = "classpath:application-test.properties")
public class BookRepositoryTest {
	
	@Autowired
	BooksRepository booksRepository;
	
	@Test
	public void saveBookSuccessTest() {
		Book book = BookTestData.getBookList().iterator().next();
		Book savedBook = booksRepository.save(book);
		assertEquals(savedBook, book);
	}
	
	@Test
	public void saveBookFailureTest() {
		Book book = BookTestData.getBookList().iterator().next();
		Book savedBook = booksRepository.save(book);
		book = new Book("textbook", null);
		assertNotEquals(savedBook, book);
	}
	
	@Test
	public void saveBooksSuccessTest() {
		List<Book> books = BookTestData.getBookList().stream().collect(Collectors.toList());
		List<Book> savedBooks = booksRepository.saveAll(books);
		assertEquals(savedBooks, books);
	}
	
	@Test
	public void saveBooksFailureTest() {
		List<Book> books = BookTestData.getBookList().stream().collect(Collectors.toList());
		List<Book> savedBooks = booksRepository.saveAll(books);
		savedBooks = null;
		assertNotEquals(savedBooks, books);
	}
	
	@Test
	public void deleteBookSuccessTest() {
		Book book = BookTestData.getBookList().iterator().next();
		Book savedBook = booksRepository.save(book);
		booksRepository.deleteById(savedBook.getBookId());
		assertEquals(Optional.empty(), booksRepository.findById(savedBook.getBookId()));
	}
	
	@Test
	public void deleteBookFailureTest() {
		List<Book> books = BookTestData.getBookList().stream().collect(Collectors.toList());
		List<Book> savedBooks = booksRepository.saveAll(books);
		savedBooks = null;
		assertNotEquals(savedBooks, books);
	}

}
