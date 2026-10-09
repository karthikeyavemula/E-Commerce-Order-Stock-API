package com.example.E_Commerce.Order.Stock.API;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<ProductOrder,Long> {

    @Query(value = "select SUM(stockquantity) from warehouse_inventory where skucode= :sku",nativeQuery = true)
    Integer gettotalstock(@Param("sku") String sku);

    @Query(value="select MAX(unit_price) from warehouse_inventory where skucode= :sku",nativeQuery = true)
    Double getprice(@Param("sku") String sku);
}
