package com.example.E_Commerce.Order.Stock.API;

import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public final OrderRepository orderrepo;
    public final ApplicationContext context;

    public OrderService(OrderRepository orderrepo, ApplicationContext context) {
        this.orderrepo = orderrepo;
        this.context = context;
    }
  @Transactional
    public ProductOrder processcheck(String email, String sku, int quantity) {
        Integer available=orderrepo.gettotalstock(sku);
        int currentstock=available!=null?available:0;
        Double dbprice=orderrepo.getprice(sku);
        double price=dbprice!=null?dbprice:0.0;
        ProductOrder p=context.getBean(ProductOrder.class);
        p.customerEmail=email;
        p.skuCode=sku;
        p.orderQuantity=quantity;
        p.totalAmount=quantity*price;
        p.createdAt = java.time.LocalDateTime.now();
        if(currentstock>=quantity){
            p.orderStatus="FullFilled";
        }
        else{
            p.orderStatus="Failed";
        }
        return orderrepo.save(p);
    }
}

