package com.nicodev.product_service.controller;

import com.nicodev.product_service.model.Product;
import com.nicodev.product_service.service.IProductService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    // POST
    @PostMapping("/save")
    public ResponseEntity<String> saveProduct(@RequestBody Product product){
        productService.saveProduct(product);
        return ResponseEntity.created(URI.create("/products/save")).body("Producto creado correctamente");
    }

    // FIND BY ID
    @GetMapping("/find/{product_id}")
    public ResponseEntity<Product> findProduct(@PathVariable Long product_id) {
        return ResponseEntity.ok(productService.findProduct(product_id));
    }

    // GET ALL
    @GetMapping("/find")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    // DELETE
    @DeleteMapping("/delete/{product_id}")
    public String deleteProduct(@PathVariable Long product_id){
        productService.deleteProduct(product_id);
        return "Product deleted correctly";
    }

    // EDIT
    @PutMapping("/edit/{product_id}")
    public Product editProduct(@PathVariable Long product_id,
                               @RequestBody Product newProduct){
        return productService.editProduct(product_id,newProduct);
    }

    //PATCH
    @PatchMapping("/patch/{product_id}")
    public Product patchProduct(@PathVariable Long product_id,
                                @RequestBody Product pro){
        return productService.patchProduct(product_id,pro);
    }

}
