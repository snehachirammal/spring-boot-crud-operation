package com.example.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer bookId;
	@Column(name = "name")
	private String bookName;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "book_authors", joinColumns = 
	@JoinColumn(name = "bookId"), 
	inverseJoinColumns = @JoinColumn(name = "authorId"))
	List<Author> authorList = new ArrayList<Author>();

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public List<Author> getAuthorList() {
		return authorList;
	}

	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
		
	}
	
	public void addAuthors(Author author) {
		authorList.add(author);
		author.getBookList().add(this);
	}
	public Book() {
		super();
	}

	public Book(String bookName, List<Author> authorList) {
		super();
		this.bookName = bookName;
		this.authorList = authorList;
	}

	public Book(Integer bookId, String bookName, List<Author> authorList) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.authorList = authorList;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", authorList=" + authorList + "]";
	}
}