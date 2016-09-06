package com.tenx.ms.retail.store.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.store.rest.dto.Store;
import com.tenx.ms.retail.store.service.StoreService;
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

@Api(value = "Store", description = "Store API")
@RestController("storeControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "Create a store")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successful creation of a store"),
        @ApiResponse(code = 412, message = "Precondition Failure"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> create(
            @Validated @RequestBody Store store) {
        Long storeId = storeService.create(store);
        return new ResourceCreated<>(storeId);
    }

    @ApiOperation(value = "List of Stores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of a list of stores"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.GET)
    public List<Store> findAll(
            @RequestParam(value = "name", required = false) Optional<String> name) {
        List<Store> stores;
        if (name.isPresent()) {
            stores = storeService.getAllByName(name.get());
        } else {
            stores = storeService.getAll();
        }
        return stores;
    }

    @ApiOperation(value = "Get a store by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of a store"),
            @ApiResponse(code = 404, message = "Store not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.GET)
    public Store getById(
            @PathVariable("storeId") Long storeId) {
        return storeService.getById(storeId);
    }

    @ApiOperation(value = "Delete a store by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful deletion of a store"),
            @ApiResponse(code = 404, message = "Store not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.DELETE)
    public void delete(
            @PathVariable("storeId") Long storeId) {
        storeService.delete(storeId);
    }
}
