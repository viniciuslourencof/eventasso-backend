package br.com.spring.eventasso.controller;

import br.com.spring.eventasso.repository.ProductRepository;
import br.com.spring.eventasso.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    public void saveProduct(@RequestBody Product product){
        this.productRepository.save(product);
    }

    @GetMapping("/product")
    public List<Product> getProducts(){
       return (List<Product>) productRepository.findAll();
    }

    @DeleteMapping("/product/{id}")
    @RolesAllowed("ADMIN")
    public void delete(@PathVariable Long id){
      this.productRepository.deleteById(id);
    }

}
