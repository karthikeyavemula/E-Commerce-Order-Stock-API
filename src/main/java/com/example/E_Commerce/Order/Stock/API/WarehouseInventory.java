package com.example.E_Commerce.Order.Stock.API;

import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("Prototype")
@Entity
@Table(name="warehouse_inventory")
public class WarehouseInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

   public String skucode;
   public int stockquantity;
   public String warehousezone;
   public double unit_price;

}
