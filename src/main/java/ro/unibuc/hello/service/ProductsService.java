package ro.unibuc.hello.service;

import ro.unibuc.hello.data.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.unibuc.hello.data.Product;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;

// Service class responsible for encapsulating business logic related to 'Product' entities.
//The @Service annotation marks the class as
// a Spring service, indicating that it contains business logic.
@Service
public class ProductsService {

    // Injects the 'ProductRepository' for database interactions.
    @Autowired
    private ProductsRepository productsRepository;

    // CRUD operation: Adds a new product to the database.
    public Product addNewProduct(Product product) {
        product.setId(UUID.randomUUID().toString().split("-")[0]); // Generates a random UUID as the product ID.
        return productsRepository.save(product); // Saves the product to the database.
    }

    // CRUD operation: Adds an array of new products to the database.
    public List<Product> addNewProducts(List<Product> productList) {
        List<Product> savedProducts = new ArrayList<>();
        for (Product product : productList) {
            product.setId(UUID.randomUUID().toString().split("-")[0]); // Generates a random UUID as the product ID.
            savedProducts.add(productsRepository.save(product)); // Saves the product to the database and adds it to the list of saved products.
        }
        return savedProducts; // Returns the list of saved products.
    }


    // Retrieves all products from the database.
    public List<Product> findAllProducts() {
        return productsRepository.findAll();
    }

    // Retrieves a product by its ID from the database.
    public Product getProductById(String id) {
        return productsRepository.findById(id).get();
    }

    // Retrieves all products with a specific price from the database.
    public List<Product> findAllProductsByPrice(double price) {
        return productsRepository.findByPrice(price);
    }

    // CRUD operation: Updates an existing product in the database.
    public Product updateProduct(Product product) {
        // Retrieves the existing product from the database.
        Product existingProduct = productsRepository.findById(product.getId()).get();

        // Updates the existing product with values from the request.
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setName(product.getName());
        existingProduct.setImageURL(product.getImageURL());

        return productsRepository.save(existingProduct); // Saves the updated product to the database.
    }

    // CRUD operation: Deletes a product by its ID from the database.
    public void deleteProduct(String id) {
        productsRepository.deleteById(id);
    }

    // Method to delete all products
    public void deleteAllProducts() {
        productsRepository.deleteAll();
    }
}