package com.example.repository;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.model.Book;

@Repository
public class BookDaoImpl {
	@Autowired
	EntityManager em;

	public Set<Book> findBooksByAuthorNameOrTitle(String sortBy, String orderBy) {
		Set<Book> bookList = new LinkedHashSet<Book>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);

		Root<Book> book = cq.from(Book.class);
		// List<Predicate> predicates = new ArrayList<>();
		
		if (sortBy.equalsIgnoreCase("authorName")) {
			if (orderBy.equalsIgnoreCase("ASC")) 
				cq.orderBy(cb.asc(book.join("authorList").get("authorName")));
		
			else
				cq.orderBy(cb.desc(book.join("authorList").get("authorName")));
		}
		if (sortBy.equalsIgnoreCase("bookName")) {
			if (orderBy.equalsIgnoreCase("ASC"))
				cq.orderBy(cb.asc(book.get("bookName")));
			else
				cq.orderBy(cb.desc(book.get("bookName")));
		}
		
		bookList = em.createQuery(cq).getResultList().stream().collect(Collectors.toCollection( LinkedHashSet::new ));
		//System.out.println("test-------------------------------------"+bookList.iterator().next().getAuthorList().toString());
		return bookList;
	}
}
