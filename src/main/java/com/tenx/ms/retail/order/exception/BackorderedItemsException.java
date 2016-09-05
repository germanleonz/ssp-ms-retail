package com.tenx.ms.retail.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Backordered Items")
public final class BackorderedItemsException extends RuntimeException {
    private static final String message = "Backordered Items";

    public BackorderedItemsException() {
        super(message);
    }
}
