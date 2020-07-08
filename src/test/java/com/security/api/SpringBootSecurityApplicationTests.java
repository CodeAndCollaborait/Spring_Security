package com.security.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.api.model.Book;
import com.security.api.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;



import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SpringBootSecurityApplicationTests {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private BookRepository bookRepository;
  
  @BeforeEach
  public void init() {
	Book book = new Book(101L, "Guide to Java", "Josh Denial", 13.99);
	when(bookRepository.findById(101L)).thenReturn(Optional.of(book));
  }
  
  //Auth with user..
  @WithMockUser("USER")
  @Test
  public void findBook() throws Exception {
	
	mockMvc.perform(get("/books/101"))
			.andDo(print())
			.andExpect(status().isOk());
  }
  
  @WithMockUser("USER")
  @Test
  public void verifyBookByID() throws Exception {
	mockMvc.perform(get("/books/101"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(101)))
			.andExpect(jsonPath("$.name", is("Guide to Java")))
			.andExpect(jsonPath("$.author", is("Josh Denial")))
			.andExpect(jsonPath("$.price", is(13.99)));
	
  }
  
  //WithOut auth...
  @Test
  public void withOutAuth() throws Exception {
	mockMvc.perform(get("/book/101"))
			.andDo(print())
			.andExpect(status().isNotFound());
  }
  
  //Get All with Auth
  
  @WithMockUser("USER")
  @Test
  public void findBookList() throws Exception {
	mockMvc.perform(get("/books"))
			.andDo(print())
			.andExpect(status().isOk());
  }
  
  
  @Test
  public void findBookListWithOutAuth() throws Exception {
	mockMvc.perform(get("/books"))
			.andDo(print())
			.andExpect(status().isUnauthorized());
  }
  
  
//  public static String asJsonString(final Object object) {
//	try {
//	  return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
//
//	} catch (Exception e) {
//	  throw new RuntimeException(e);
//	}
//
//  }
//
//  @WithMockUser("ADMIN")
//  @Test
//  public void addBook() throws Exception {
//    mockMvc.perform(post("/books")
//			.content(asJsonString(new Book(108L,"Some book", "somename", 14.99)))
//			.contentType(MediaType.APPLICATION_JSON)
//			.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isCreated());
//  }
  
  
//  @Autowired
//  private TestRestTemplate restTemplate;
//  @Test
//  public void addBook() throws Exception{
//
//    String newBook = "{id:108,name:\" Guide to SpringBoot\",author:\"Mark Miller\",price:15.99}";
//
//	ResponseEntity<String> response = restTemplate.withBasicAuth("admin","password")
//			.postForEntity("/books",asJsonString(newBook),String.class );
//
//
//
//
//  }
  
  
}
