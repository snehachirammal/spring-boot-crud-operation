package com.example.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookTestData {
	
	public static Set<Book> getBookList() {
		Set<Book> bookList = new LinkedHashSet<Book>();
		Book book1 = new Book("maths", setAuthorList1());
		bookList.add(book1);
		Book book2 = new Book("science", setAuthorList2());
		bookList.add(book1);
		bookList.add(book2);
		return bookList;
	}

	public static List<Author> setAuthorList1() {
		List<Author> authorLst = new ArrayList<Author>();
		Author author = new Author("sneha");
		authorLst.add(author);
		author = new Author("arun");
		authorLst.add(author);
		return authorLst;

	}

	public static List<Author> setAuthorList2() {
		List<Author> authorLst = new ArrayList<Author>();
		Author author = new Author("zain");
		authorLst.add(author);
		author = new Author("mithun");
		authorLst.add(author);
		return authorLst;

	}
	
	public static Optional<Book> getOptionalBook() {
		Optional<Book> book1 = Optional.of(new Book("maths", setAuthorList1()));
		return book1;
	}

}
