package com.ford.springbootstart.Controller;

import com.ford.springbootstart.Entity.Coin;
import com.ford.springbootstart.Entity.Product;
import com.ford.springbootstart.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    @Test
    public void shouldReturnSomething() throws Exception {
        when(productService.greet()).thenReturn("Hello");
        this.mockMvc
                .perform(get("/machine/test"))
                .andDo(print())
                .andExpect(content().string(containsString("Hello")));
    }


    @Test
    public void callDispatchForBuy() throws Exception{
        Product testProductToDispatch = new Product(1, 10.0, 5, "Test Product");
        when(productService.dispatchProduct(1)).thenReturn(testProductToDispatch);
        this.mockMvc
                .perform(patch("/machine/buy/1"))
                .andDo(print())
                .andExpect(content().string(containsString("Enjoy")));
    }

    @Test
    public void callProcessInsertCoin() throws Exception{
        Product testProductToDispatch = new Product(1, 10.0, 5, "Test Product");

        String JsonTestBodyString = "{ " +
                "\"thicknessInMM\":1.75, " +
                "\"weightInGrams\":5.67" +

                "}";
        this.mockMvc
                .perform(post("/machine/insertcoin")
                .content(JsonTestBodyString)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("")));
    }
}