package com.example.bookstoreserver.service.serviceImpl;

import com.example.bookstoreserver.dto.ProductRequest;
import com.example.bookstoreserver.exception.NotFoundException;
import com.example.bookstoreserver.exception.ProductException;
import com.example.bookstoreserver.exception.ResourceNotFoundException;
import com.example.bookstoreserver.model.*;
import com.example.bookstoreserver.repositories.*;
import com.example.bookstoreserver.service.ProductService;
import com.sun.source.tree.TryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public CategoryRepository categoryRepository;
    @Autowired
    public PublisherRepository publisherRepository;
    @Autowired
    public AuthorRepository authorRepository;



    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found with id "+productId));
    }

    @Override
    public Product saveProduct(ProductRequest productRequest) {
    try {
        Product product = new Product();
        product.setSales(0);
        Date currentDate = new Date();
        product.setCreateAt(currentDate);
        return getProduct(productRequest, product);
    }catch (ProductException ex){
        throw new ProductException("Save product failed");
    }

    }

    private Product getProduct(ProductRequest productRequest, Product product) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImage(productRequest.getImage());
        product.setQuantity(productRequest.getQuantity());

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found with ID: " + productRequest.getCategoryId()));
        Publisher publisher = publisherRepository.findByName(productRequest.getPublisher());
        Author author = authorRepository.findByName(productRequest.getAuthor());
        if(author == null ){
            Author author1 = new Author();
            author1.setName(productRequest.getAuthor());
            authorRepository.save(author1);
            product.setAuthor(author1);

        }else{
            product.setAuthor(author);
        }
        if(publisher == null){
            Publisher newPublisher = new Publisher();
            newPublisher.setName(productRequest.getPublisher());
            publisherRepository.save(newPublisher);
            product.setPublisher(newPublisher);
        }else{
            product.setPublisher(publisher);
        }
        product.setCategory(category);


        return productRepository.save(product);
    }
//lay ra tat ca san pham
    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        try {
            return productRepository.findAll(pageable);
        }catch (ProductException ex){
            throw new ProductException("Get all product failed");
        }

    }
//xoa sp
//    @Override
//    public List<Product> getAll(){
//        return productRepository.findAll();
//    }
    @Override
    public void deleteProductById(Long productId) {
        try{
            Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not exists in database"));
            productRepository.deleteById(product.getId());
        }catch (ProductException ex){
            throw new ProductException("Delete product failed");
        }


    }
//cap nhat san pham
    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not exists in database"));
            return getProduct(productRequest, product);
        }catch (ProductException ex){
            throw new ProductException("Update product failed");
        }

    }

    @Override
    public List<Product> getBestSeller() {
        try{
            return productRepository.findTop10ByOrderBySalesDesc();
        }catch (ProductException ex){
            throw new ProductException("Get best seller failed");
        }

    }

    @Override
    public List<Product> getNewArrivals() {
        try {
            return productRepository.findTop10ByOrderByCreatedAtDesc();
        }catch (ProductException ex){
            throw new ProductException("Get new arrivals failed");
        }

    }
//tim kiem
    @Override
    public List<Product> search(String name) {
        try {
            return productRepository.findByNameContaining(name);
        }catch (ProductException ex){
            throw new NotFoundException("not found products with key: " + name);
        }
    }
//loc
    @Override
    public List<Product> filterProducts(Publisher publisher, Author author, Double minPrice, Double maxPrice) {

            Specification<Product> spec = Specification.where(null);

            if (publisher != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("publisher"), publisher));
            }

            if (author != null) {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("author"), author));
            }

            if (minPrice != null) {
                spec = spec.and((root, query, cb) -> cb.ge(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                spec = spec.and((root, query, cb) -> cb.le(root.get("price"), maxPrice));
            }

            return productRepository.findAll(spec);


    }

}
