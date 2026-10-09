package com.example.E_Commerce.Order.Stock.API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    public final OrderService orderservice;

    public OrderController(OrderService orderservice){
        this.orderservice=orderservice;
    }

    @PostMapping()
    public ProductOrder createorder(@RequestBody OrderRequest request){
        return orderservice.processcheck(request.email(), request.sku(), request.quantity());
    }
}
