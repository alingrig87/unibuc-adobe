package ro.unibuc.hello.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.Product;
import ro.unibuc.hello.service.ProductsService;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerTest {

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ProductsController productsController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productsController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product("1", "Product 1", 10.0,
                "http://example.com/image.jpg",
                "Description for Product 1");

        when(productsService.addNewProduct(product)).thenReturn(product);

        MvcResult result = mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(product), result.getResponse().getContentAsString());
    }

    // @Test
    // void testGetProducts() throws Exception {
    // mockMvc.perform(MockMvcRequestBuilders.get("/products")
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(status().isOk());
    // }

    // @Test
    // void testDeleteProduct() throws Exception {
    // mockMvc.perform(MockMvcRequestBuilders.delete("/products/1")
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(status().isOk());
    // }

    // @Test
    // void testDeleteAllProducts() throws Exception {
    // mockMvc.perform(MockMvcRequestBuilders.delete("/products/deleteAll")
    // .contentType(MediaType.APPLICATION_JSON))
    // .andExpect(status().isNoContent());
    // }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
