package com.ford.springbootstart.Controller;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ford.springbootstart.Entity.Product;
import com.ford.springbootstart.Service.ProductService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(OwnerController.class)
class ownerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void shouldReturnSomething() throws Exception {
        when(productService.greet()).thenReturn("Hello");
        this.mockMvc
                .perform(get("/product/test"))
                .andDo(print())
                .andExpect(content().string(containsString("Hello")));
    }

    @Test
    public void shouldReturnAllProducts() throws Exception {
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(new Product(1, 10.0, 5, "Test Product"));
        when(productService.getProducts()).thenReturn(testProducts);
        mockMvc.perform(get("/product/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Product")));
    }
    @Test
    public void shouldReturnSuccessWhenNewProductAdded() throws Exception {
        List<Product> testProducts = new ArrayList<>();
        testProducts.add(new Product(1, 10.0, 5, "Test Product"));
        when(productService.getProducts()).thenReturn(testProducts);
        String JsonTestBodyString = "{ " +
                        "\"pId\":1, " +
                        "\"pCost\":10.0," +
                        " \"quantity\":5, " +
                        "\"name\":\"Test Product\"" +
                        "}";
        mockMvc.perform(post("/product/new").contentType(MediaType.APPLICATION_JSON)
                .content(JsonTestBodyString)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("success")));
    }

    @Test
    public void shouldReturnCorrectProductWhenIdGiven() throws Exception {
        when(productService.getProductById(1)).thenReturn(new Product(1, 10.0, 5, "Test Product"));

        mockMvc.perform(get("/product/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("pId\":1"))));
    }

    @Test
    public void shouldReturnDeletedSuccessfully() throws Exception{
        when(productService.deleteProduct(1)).thenReturn("Successfully Deleted");

        mockMvc.perform(delete("/product/delete/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("success")));
    }
}