package com.tenx.ms.retail.product.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.product.rest.dto.Product;
import com.tenx.ms.retail.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;


@Api(value = "Product", description = "Product API")
@RestController("productControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Create a product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful creation of a product"),
            @ApiResponse(code = 412, message = "Precondition Failure"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.POST)
    public ResourceCreated<Long> create(
            @PathVariable("storeId") Long storeId,
            @Validated @RequestBody Product product) {
        product.setStoreId(storeId);
        Long productId = productService.create(product);
        return new ResourceCreated<>(productId);
    }

    @ApiOperation(value = "List of Products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of a list of products"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value="/{storeId:\\d+}", method = RequestMethod.GET)
    public List<Product> findAll(
            @PathVariable("storeId") Long storeId,
            @RequestParam(value = "name", required = false) Optional<String> name) {
        List<Product> products;
        if (name.isPresent()) {
            products = productService.getAllByName(storeId, name.get());
        } else {
            products = productService.getAll(storeId);
        }
        return products;
    }

    @ApiOperation(value = "Get a product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of a product"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{storeId:\\d+}/{productId:\\d+}", method = RequestMethod.GET)
    public Product getById(
            @PathVariable("storeId") Long storeId,
            @PathVariable("productId") Long productId) {
        return productService.getById(storeId, productId);
    }

    @ApiOperation(value = "Delete a product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful deletion of a product"),
            @ApiResponse(code = 404, message = "Product not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{storeId:\\d+}/{productId:\\d+}", method = RequestMethod.DELETE)
    public void delete(
            @PathVariable("storeId") Long storeId,
            @PathVariable("productId") Long productId) {
        productService.delete(storeId, productId);
    }
}
