package com.example.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.model.Book;
import com.example.model.BookTestData;
import com.example.service.BooksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(
		  locations = "classpath:application-test.properties")
public class BooksControllerTest {
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	
	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	BooksService booksService;
	@Autowired
	private MockMvc mockMvc;

	
	  @Before public void setup() { mockMvc =
	  MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); }
	 
	@Test
	public void getBooksByAuthorSuccess() throws Exception {
		when(booksService.findBooksByAuthorNameOrTitle(any(),any())).thenReturn(BookTestData.getBookList());
		this.mockMvc.perform(
                get("/books?orderBy=asc&sortBy=authorname")
        ).andExpect(
               status().isOk());
	}
	
	@Test
	public void getBooksByAuthorBadRequest() throws Exception {
		when(booksService.findBooksByAuthorNameOrTitle(any(),any())).thenReturn(BookTestData.getBookList());
		this.mockMvc.perform(
                get("/books?orderBy=abcd&sortBy=authorname")
        ).andExpect(
               status().isBadRequest());
	}
	
	@Test
	public void getBooksByAuthorNotFound() throws Exception {
		when(booksService.findBooksByAuthorNameOrTitle(any(),any())).thenReturn(new LinkedHashSet<Book>());
		this.mockMvc.perform(
                get("/books?orderBy=asc&sortBy=authorname")
        ).andExpect(
               status().isNotFound());
	}
	
	@Test
	public void getBookSuccess() throws Exception {
		when(booksService.getBooksById(anyInt())).thenReturn(BookTestData.getOptionalBook());
		this.mockMvc.perform(
                get("/book/1")
        ).andExpect(
               status().isOk());
	}
	
	@Test
	public void getBookNotFound() throws Exception {
		when(booksService.getBooksById(anyInt())).thenReturn(Optional.empty());
		this.mockMvc.perform(
                get("/book/1")
        ).andExpect(
               status().isNotFound());
	}
	
	@Test
	public void saveBooksSuccess() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(BookTestData.getBookList());

	    
		when(booksService.saveOrUpdate(anyList())).thenReturn(new ArrayList<Book>(BookTestData.getBookList()));
		this.mockMvc.perform(
                post("/books").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson)
        ).andExpect(
               status().isCreated());
	}
	
	@Test
	public void updateBookDetailsSuccess() throws Exception {
		String bookName = "{\"bookName\":\"science\"}";
		when(booksService.getBooksById(anyInt())).thenReturn(BookTestData.getOptionalBook());
		when(booksService.saveOrUpdate(anyList())).thenReturn(new ArrayList<Book>(BookTestData.getBookList()));
		this.mockMvc.perform(
                patch("/book/1").contentType(APPLICATION_JSON_UTF8)
                .content(bookName)
        ).andExpect(
               status().isOk());
	}
	
	@Test
	public void deleteBook() throws Exception {
		doNothing().when(booksService).delete(anyInt());
		this.mockMvc.perform(
                delete("/book/1")
        ).andExpect(
               status().isOk());
	}
	
	
	
	
}
