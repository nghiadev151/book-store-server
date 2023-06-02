package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.dto.ProductRequest;
import com.example.bookstoreserver.model.Author;
import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.model.Publisher;
import com.example.bookstoreserver.repositories.AuthorRepository;
import com.example.bookstoreserver.repositories.PublisherRepository;
import com.example.bookstoreserver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
@Autowired
public PublisherRepository publisherRepository;
@Autowired
public AuthorRepository authorRepository;
    private final ProductService productService;
@Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct (@RequestBody ProductRequest productRequest){
        productService.saveProduct(productRequest);
        return ResponseEntity.ok("Successfully");
    }
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProduct(@RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(productService.getAllProduct(pageable));
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
//        Product result = productService.getProductById(id);
        return this.productService.getProductById(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok("Delete product successfully");
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateProductById(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        productService.updateProduct(id, productRequest);
        return ResponseEntity.ok("Update product successfully");
    }
    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam("name") String name) {
        return productService.search(name);
    }
    @GetMapping("/filter")
    public List<Product> filterProducts(@RequestParam(value = "publisher", required = false) String publisherName,
                                        @RequestParam(value = "author", required = false) String authorName,
                                        @RequestParam(value = "minPrice", required = false) Double minPrice,
                                        @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        Publisher publisher = null;
        Author author = null;

        if (publisherName != null) {
            publisher = publisherRepository.findByName(publisherName);
        }

        if (authorName != null) {
            author = authorRepository.findByName(authorName);
        }

        return productService.filterProducts(publisher, author, minPrice, maxPrice);
    }
    @GetMapping("/new-arrivals")
    public ResponseEntity<List<Product>> getNewArrivals() {
        return ResponseEntity.ok(productService.getNewArrivals());
    }
    @GetMapping("/bestsellers")
    public ResponseEntity<List<Product>> getBestsellers() {
        return ResponseEntity.ok(productService.getBestSeller());
    }
}
