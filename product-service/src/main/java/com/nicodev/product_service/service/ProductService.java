package com.nicodev.product_service.service;

import com.nicodev.product_service.exception.NotFoundException;
import com.nicodev.product_service.exception.BadRequestException;
import com.nicodev.product_service.model.Product;
import com.nicodev.product_service.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    //POST
    @Override
    public void saveProduct(Product product) {

        // Validation of required files.
        if(product.getName() == null || product.getName().isEmpty() ){
            throw new BadRequestException("Product name required");
        }
        if(product.getBrand() == null || product.getBrand().isEmpty() ){
            throw new BadRequestException("Product brand required");
        }
        if(product.getUnit_price() == null || product.getUnit_price() <= 0.0 ){
            throw new BadRequestException("Product name required");
        }

        // If everything is ok, save.
        productRepository.save(product);
    }

    // FIND BY ID
    @Override
    public Product findProduct(Long product_id) {
        return productRepository.findById(product_id).
                orElseThrow(() -> new NotFoundException("Product with ID: " + product_id + " not found"));
    }

    // GET ALL
    @Override
    public List<Product> getProducts() {
        // Validation of list empty.
        if (productRepository.findAll().isEmpty()) {
            throw new NotFoundException("The list of products is Empty");
        }

        return productRepository.findAll();
    }

    // DELETE
    @Override
    public void deleteProduct(Long product_id) {
        if (!productRepository.existsById(product_id)){
            throw new NotFoundException("Product not found");
        }

        productRepository.deleteById(product_id);
    }

    // EDIT
    @Override
    public Product editProduct(Long product_id, Product newProduct) {
        // Find the Product to edit
        Product productToEdit = this.findProduct(product_id);

        // set the new values
        productToEdit.setName(newProduct.getName());
        productToEdit.setBrand(newProduct.getBrand());
        productToEdit.setUnit_price(newProduct.getUnit_price());

        // save the produc with the new values.
        this.saveProduct(productToEdit);

        // return the Product with the new values.
        return productToEdit;
    }

    // PATCH
    @Override
    public Product patchProduct(Long product_id, Product pro) {
        // Find the product to Edit/Ptach in my DB
        Product product = this.findProduct(product_id);

        // Set the request values.
        if (pro.getName() != null){
            product.setName(pro.getName());
        };

        if (pro.getBrand() != null){
             product.setBrand(pro.getBrand());
        };

        if (pro.getUnit_price() != null){
             product.setUnit_price(pro.getUnit_price());
        };

        // save product with the new values
        this.saveProduct(product);

        // Return the Product with the new values.
        return product;
    }
}
