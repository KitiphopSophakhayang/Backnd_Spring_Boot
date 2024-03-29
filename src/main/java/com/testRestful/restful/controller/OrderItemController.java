// package com.testRestful.restful.controller;

// import com.testRestful.restful.entity.OrderItem;
// import com.testRestful.restful.service.OrderItemService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// import java.util.Date;
// import java.util.List;
// import java.util.UUID;

// @RestController
// public class OrderItemController {

//     private final OrderItemService orderItemService;

//     @Autowired
//     public OrderItemController(OrderItemService orderItemService) {
//         this.orderItemService = orderItemService;
//     }

//     @PostMapping("/orderItems")
//     public ResponseEntity<?> addOrderItems(@RequestBody OrderItem[] orderItems) {
//         String transactionId = UUID.randomUUID().toString(); // สร้าง transactionId
//         for (OrderItem orderItem : orderItems) {
//             orderItem.setOrderDate(new Date());
//         }
//         orderItemService.saveOrderItems(orderItems, transactionId); // เรียกใช้เมธอด saveOrderItems พร้อมส่ง transactionId มาด้วย
//         return new ResponseEntity<>("Order items added successfully", HttpStatus.CREATED);
//     }
    
//     @GetMapping("/orderItems")
//     public ResponseEntity<List<OrderItem>> getOrderItems() {
//         List<OrderItem> orderItems = orderItemService.getAllOrderItems();
//         return new ResponseEntity<>(orderItems, HttpStatus.OK);
//     }

//     @GetMapping("/orderItems/{transactionId}")
//     public ResponseEntity<List<OrderItem>> getOrderItemsByTransactionId(@PathVariable String transactionId) {
//         List<OrderItem> orderItems = orderItemService.getOrderItemsByTransactionId(transactionId);
//         return new ResponseEntity<>(orderItems, HttpStatus.OK);
//     }

// }


package com.testRestful.restful.controller;

import com.testRestful.restful.entity.OrderItem;
import com.testRestful.restful.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/orderItems")
    public ResponseEntity<?> addOrderItems(@RequestBody OrderItem[] orderItems) {
        String transactionId = UUID.randomUUID().toString(); // สร้าง transactionId
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderDate(new Date());
        }
        orderItemService.saveOrderItems(orderItems, transactionId); // เรียกใช้เมธอด saveOrderItems พร้อมส่ง transactionId มาด้วย
        return new ResponseEntity<>("Order items added successfully", HttpStatus.CREATED);
    }
    
    @GetMapping("/orderItems")
    public ResponseEntity<List<OrderItem>> getOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/orderItems/{transactionId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByTransactionId(@PathVariable String transactionId) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByTransactionId(transactionId);
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    
    @GetMapping("/orderItems/getTableData/{id}")
    public List<OrderItem> getTableIdAndStatus(@PathVariable("id") Long tableId) {
        return orderItemService.getTableIdAndStatus(tableId);
    }

    
}
