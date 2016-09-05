package com.tenx.ms.retail.order.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.order.exception.BackorderedItemsException;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "Order", description = "Order API")
@RestController("orderControllerV1")
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Create an order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful creation of an order"),
            @ApiResponse(code = 412, message = "Precondition Failure"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/{storeId:\\d+}", method = RequestMethod.POST)
    public ResourceCreated<Long> create(
            @PathVariable Long storeId,
            @Validated @RequestBody Order order) throws ResourceNotFoundException, BackorderedItemsException {
        order.setStoreId(storeId);
        Long orderId = orderService.create(order);
        return new ResourceCreated<>(orderId);
    }

}
