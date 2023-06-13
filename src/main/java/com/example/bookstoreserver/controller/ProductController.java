package com.example.bookstoreserver.controller;

import com.example.bookstoreserver.Validator;
import com.example.bookstoreserver.dto.ProductRequest;
import com.example.bookstoreserver.exception.CustomExceptionHandler;
import com.example.bookstoreserver.exception.NotFoundException;
import com.example.bookstoreserver.exception.ProductException;
import com.example.bookstoreserver.model.Author;
import com.example.bookstoreserver.model.Product;
import com.example.bookstoreserver.model.Publisher;
import com.example.bookstoreserver.repositories.AuthorRepository;
import com.example.bookstoreserver.repositories.ProductRepository;
import com.example.bookstoreserver.repositories.PublisherRepository;
import com.example.bookstoreserver.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//xác định controller là mộp restfulcontroller
@RequestMapping("/api/products") //tất cả các yêu cầu đến url nà đều được xl bằng controller này.
public class ProductController {

    @Autowired
    private Validator validator;
    @Autowired
    private CustomExceptionHandler customExceptionHandler;
@Autowired
public PublisherRepository publisherRepository;
@Autowired//TỰ ĐỘNG ẾT NỐI CÁC DEPENDENCY VÀO CÁC THÀ VIÊ CLASS.
public AuthorRepository authorRepository;

private final ProductService productService;
@Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct (@RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<String> errorMessage = validator.getErrorMessage(bindingResult);
            return ResponseEntity.badRequest().body(errorMessage);
        }
        try {
            productService.saveProduct(productRequest);
            return ResponseEntity.ok("Successfully");
        }catch (ProductException ex){
            return customExceptionHandler.handleProductException(ex);
        }catch (NotFoundException ex){
            return customExceptionHandler.handleNotFoundException(ex);
        }

    }
    //phan trang
    @GetMapping
    public ResponseEntity<?> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size){
    try{
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> product = productService.getAllProduct(pageable);
        return ResponseEntity.ok(product);
    }catch (ProductException ex){
        return customExceptionHandler.handleProductException(ex);
    }


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){
    try {
        return ResponseEntity.ok(this.productService.getProductById(id));
    }catch (ProductException ex){
        return customExceptionHandler.handleProductException(ex);
    }catch (NotFoundException ex){
        return customExceptionHandler.handleNotFoundException(ex);
    }

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
    try {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Delete product successfully");
    }catch (ProductException ex){
        return customExceptionHandler.handleProductException(ex);
    }catch (NotFoundException ex){
        return customExceptionHandler.handleNotFoundException(ex);
    }

    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateProductById(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<String> errorMessage = validator.getErrorMessage(bindingResult);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        try {
            productService.updateProduct(id, productRequest);
            return ResponseEntity.ok("Update product successfully");
        } catch (NotFoundException e) {
            return customExceptionHandler.handleNotFoundException(e);
        }catch (ProductException ex){
            return customExceptionHandler.handleProductException(ex);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchProductsByName(@RequestParam("name") String name) {
        try{
            return ResponseEntity.ok(productService.search(name));
        }catch (NotFoundException ex){
            return customExceptionHandler.handleNotFoundException(ex);
        }
    }
    @GetMapping("/filter")
    public ResponseEntity<?> filterProducts(@RequestParam(value = "publisher", required = false) String publisherName,
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

        return ResponseEntity.ok(productService.filterProducts(publisher, author, minPrice, maxPrice));


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
