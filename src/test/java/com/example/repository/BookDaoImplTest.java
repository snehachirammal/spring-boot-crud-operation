package com.example.repository;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;

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
public class BookDaoImplTest {

	@Autowired
	BookDaoImpl bookDaoImpl;
	
	@Autowired
	BooksRepository booksRepository;

	@Test
	public void findBooksByTitleAscSuccessTest() {
		Set<Book> bookLst = BookTestData.getBookList();
		booksRepository.saveAll(bookLst);
		Set<Book> sortedBookLst = bookDaoImpl.findBooksByAuthorNameOrTitle("bookName", "asc");
		assertEquals(sortedBookLst.iterator().next().getBookName(), BookTestData.getBookList().iterator().next().getBookName());
		
	}
	@Test
	public void findBooksByTitleDescSuccessTest() {
		Set<Book> bookLst = BookTestData.getBookList();
		booksRepository.saveAll(bookLst);
		Set<Book> sortedBookLst = bookDaoImpl.findBooksByAuthorNameOrTitle("bookName", "desc");
		assertEquals(sortedBookLst.iterator().next().getBookName(), "science");
		
	}
	
	@Test
	public void findBooksByAuthorNameAscSuccessTest() {
		Set<Book> bookLst = BookTestData.getBookList();
		booksRepository.saveAll(bookLst);
		LinkedHashSet<Book> sortedBookLst = (LinkedHashSet<Book>) bookDaoImpl.findBooksByAuthorNameOrTitle("authorName", "asc");
		assertEquals(sortedBookLst.iterator().next().getBookName(), "maths");
		
	}
	@Test
	public void findBooksByAuthorNameDescSuccessTest() {
		Set<Book> bookLst = BookTestData.getBookList();
		booksRepository.saveAll(bookLst);
		Set<Book> sortedBookLst = bookDaoImpl.findBooksByAuthorNameOrTitle("authorName", "desc");
		assertEquals(sortedBookLst.iterator().next().getBookName(), "science");
		
	}

	
}
