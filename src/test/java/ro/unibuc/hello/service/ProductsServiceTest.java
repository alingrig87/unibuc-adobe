package ro.unibuc.hello.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.Product;
import ro.unibuc.hello.data.ProductsRepository;

@ExtendWith(SpringExtension.class)
public class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService = new ProductsService();

    @Test
    void testAddNewProduct() {
        String productId = "1";
        String productName = "Product 1";
        double productPrice = 10.0;
        String productImageUrl = "http://example.com/image.jpg";
        String productDescription = "Description for Product 1";

        Product product = new Product(productId, productName, productPrice, productImageUrl, productDescription);

        when(productsRepository.save(product)).thenReturn(product);

        Product savedProduct = productsService.addNewProduct(product);

        assertNotNull(savedProduct);
        assertEquals(productName, savedProduct.getName());
        assertEquals(productPrice, savedProduct.getPrice());
        assertEquals(productImageUrl, savedProduct.getImageURL());
        assertEquals(productDescription, savedProduct.getDescription());
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList
                .add(new Product("1", "Product 1", 10.0, "http://example.com/image1.jpg", "Description for Product 1"));
        productList
                .add(new Product("2", "Product 2", 20.0, "http://example.com/image2.jpg", "Description for Product 2"));
        productList
                .add(new Product("3", "Product 3", 30.0, "http://example.com/image3.jpg", "Description for Product 3"));

        when(productsRepository.findAll()).thenReturn(productList);

        List<Product> foundProducts = productsService.findAllProducts();

        assertNotNull(foundProducts);
        assertEquals(3, foundProducts.size());
    }

    @Test
    void testGetProductById() {
        String productId = "1";
        String productName = "Product 1";
        double productPrice = 10.0;
        String productImageUrl = "http://example.com/image.jpg";
        String productDescription = "Description for Product 1";

        Product product = new Product(productId, productName, productPrice, productImageUrl, productDescription);

        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

        Product foundProduct = productsService.getProductById(productId);

        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getId());
    }

    @Test
    void testUpdateProduct() {
        String productId = "1";
        String productName = "Updated Product";
        double productPrice = 20.0;
        String productImageUrl = "http://example.com/updated_image.jpg";
        String productDescription = "Updated description for Product";

        Product existingProduct = new Product(productId, "Old Product", 10.0, "http://example.com/image.jpg",
                "Old Description");

        when(productsRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        Product updatedProduct = new Product(productId, productName, productPrice, productImageUrl, productDescription);
        when(productsRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product savedProduct = productsService.updateProduct(updatedProduct);

        assertNotNull(savedProduct);
        assertEquals(productId, savedProduct.getId());
        assertEquals(productName, savedProduct.getName());
        assertEquals(productPrice, savedProduct.getPrice());
        assertEquals(productImageUrl, savedProduct.getImageURL());
        assertEquals(productDescription, savedProduct.getDescription());
    }

    @Test
    void testFindAllProductsByPrice() {
        double price = 10.0;
        List<Product> productList = new ArrayList<>();
        productList
                .add(new Product("1", "Product 1", 10.0, "http://example.com/image1.jpg", "Description for Product 1"));
        productList
                .add(new Product("2", "Product 2", 10.0, "http://example.com/image2.jpg", "Description for Product 2"));

        when(productsRepository.findByPrice(price)).thenReturn(productList);

        List<Product> foundProducts = productsService.findAllProductsByPrice(price);

        assertNotNull(foundProducts);
        assertEquals(2, foundProducts.size());
    }

    @Test
    void testDeleteProduct() {

    }

    @Test
    void testDeleteAllProducts() {
        productsService.deleteAllProducts();

        verify(productsRepository, times(1)).deleteAll();
        assertEquals(0, productsRepository.count());
    }
}
