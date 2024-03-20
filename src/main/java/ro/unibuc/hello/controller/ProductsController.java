package ro.unibuc.hello.controller;

import ro.unibuc.hello.data.Product;
import ro.unibuc.hello.service.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller class responsible for handling HTTP requests related to 'Product' entities.
// The @RestController annotation marks the class as a Spring REST controller,
// indicating that it handles HTTP requests and returns the response directly.
@RestController
// @RequestMapping annotation defines a base URL path
// for all the endpoint mappings within the controller.
@RequestMapping("/products")
public class ProductsController {

    // Injects the 'ProductService' for handling business logic.
    @Autowired
    private ProductsService productsService;

    // Handles HTTP POST requests to create a new product.

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED) annotation sets the
    // HTTP status code to 201 (Created) for the createProduct method.
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productsService.addNewProduct(product); // Delegates the creation operation to the ProductService.
    }

    // Endpoint for adding an array of new products
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> addProducts(@RequestBody List<Product> productList) {
        return productsService.addNewProducts(productList);
    }

    // Handles HTTP GET requests to retrieve all products.
    @GetMapping
    public List<Product> getProducts() {
        return productsService.findAllProducts(); // Delegates the retrieval operation to the ProductService.
    }

    // Handles HTTP GET requests to retrieve a product by its ID.
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id){
        return productsService.getProductById(id); // Delegates the retrieval operation to the ProductService.
    }

    // Handles HTTP GET requests to retrieve all products with a specific price.
    @GetMapping("/{price}")
    public List<Product> getAllProductsByPrice(@PathVariable double price) {
        return productsService.findAllProductsByPrice(price); // Delegates the retrieval operation to the ProductService.
    }

    // Handles HTTP PUT requests to update an existing product.
    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productsService.updateProduct(product); // Delegates the update operation to the ProductService.
    }

    // Handles HTTP DELETE requests to delete a product by its ID.
    @DeleteMapping("/{id}")
    public void deleteProductBy(@PathVariable String id) {
        productsService.deleteProduct(id); // Delegates the deletion operation to the ProductService.
    }

    // Endpoint for deleting all products
    @DeleteMapping("/deleteAll")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllProducts() {
        productsService.deleteAllProducts();
    }
}