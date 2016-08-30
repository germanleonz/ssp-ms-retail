package com.tenx.ms.retail.rest;

import com.google.common.collect.Lists;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.domain.StoreEntity;
import com.tenx.ms.retail.repository.StoreRepository;
import com.tenx.ms.retail.rest.dto.Store;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Store", description = "Store API")
@RestController("storeControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @ApiOperation(value = "Create a store")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful creation of "),
        @ApiResponse(code = 412, message = "Precondition Failure"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResourceCreated<Long> create(@RequestBody Store store) {
        StoreEntity s = storeRepository.saveAndFlush(new StoreEntity(store.getName()));
        return new ResourceCreated<Long>(s.getStoreId());
    }

    @ApiOperation(value = "List of Stores")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of a list of stores"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.GET)
    public List<StoreEntity> findAll() {
        return Lists.newArrayList(storeRepository.findAll());
    }

//    @ApiOperation(value = "Get a store")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful retrieval of a list of stores"),
//            @ApiResponse(code = 404, message = "Store not found"),
//            @ApiResponse(code = 500, message = "Internal Server Error")
//    })
//    @RequestMapping(method = RequestMethod.GET)
//    public List<> getStore() {
//        Store store =
//    }
}
