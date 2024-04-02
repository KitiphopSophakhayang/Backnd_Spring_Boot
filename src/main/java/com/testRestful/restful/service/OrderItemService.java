// // OrderItemService.java
// package com.testRestful.restful.service;

// import com.testRestful.restful.entity.OrderItem;
// import com.testRestful.restful.repository.OrderItemRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class OrderItemService {

//     private final OrderItemRepository orderItemRepository;

//     @Autowired
//     public OrderItemService(OrderItemRepository orderItemRepository) {
//         this.orderItemRepository = orderItemRepository;
//     }

//     public void saveOrderItems(OrderItem[] orderItems, String transactionId) {
//         for (OrderItem orderItem : orderItems) {
//             orderItem.setTransactionId(transactionId); // กำหนด transactionId ให้กับทุกรายการอาหาร
//             orderItemRepository.save(orderItem);
//         }
//     }
// }

// package com.testRestful.restful.service;

// import com.testRestful.restful.entity.Order;
// import com.testRestful.restful.entity.OrderItem;
// import com.testRestful.restful.repository.OrderItemRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class OrderItemService {

//     private final OrderItemRepository orderItemRepository;

//     @Autowired
//     public OrderItemService(OrderItemRepository orderItemRepository) {
//         this.orderItemRepository = orderItemRepository;
//     }

//     public void saveOrderItems(OrderItem[] orderItems, String transactionId) {
//         for (OrderItem orderItem : orderItems) {
//             orderItem.setTransactionId(transactionId); // กำหนด transactionId ให้กับทุกรายการอาหาร
//             orderItemRepository.save(orderItem);
//         }
//     }

//     public List<OrderItem> getAllOrderItems() {
//         return orderItemRepository.findAll();
//     }

//     public List<OrderItem> getOrderItemsByTransactionId(String transactionId) {
//         return orderItemRepository.findByTransactionId(transactionId);
//     }

//     public List<OrderItem> getTableIdAndStatus(Long tableId) {
//         return orderItemRepository.getTableIdAndStatus(tableId);
//     }

// }


package com.testRestful.restful.service;

import com.testRestful.restful.entity.OrderItem;
import com.testRestful.restful.models.DailyTotalPrice;
import com.testRestful.restful.models.Top5MenuList;
import com.testRestful.restful.repository.OrderItemRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public void saveOrderItems(OrderItem[] orderItems, String transactionId) {
        for (OrderItem orderItem : orderItems) {
            orderItem.setTransactionId(transactionId); // กำหนด transactionId ให้กับทุกรายการอาหาร
            orderItemRepository.save(orderItem);
        }
    }
    
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> getOrderItemsByTransactionId(String transactionId) {
        return orderItemRepository.findByTransactionId(transactionId);
    }

    public List<OrderItem> getTableIdAndStatus(Long tableId) {
        return orderItemRepository.getTableIdAndStatus(tableId);
    }

    public List<DailyTotalPrice> getTotalPriceByWeekAndGetDayName() {
        List<Object[]> result = orderItemRepository.getTotalPriceByWeekAndGetDayName();

        List<DailyTotalPrice> dailyTotalPrices = new ArrayList<>();
        for (Object[] row : result) {
            String dayOfWeek = (String) row[0];
            double totalPrice = (double) row[1];
            dailyTotalPrices.add(new DailyTotalPrice(dayOfWeek, totalPrice));
        }

        return dailyTotalPrices;
    }

    public Integer getAllTotalPrice() {
        return orderItemRepository.getAllTotalPrice();
    }

    public Integer getAllOrder() {
        return orderItemRepository.getAllOrder();
    }

    public Map<String, String> getTotalPriceByDateInOneWeek() {
        List<Object[]> resultQuery = orderItemRepository.getTotalPriceByDateInOneWeek();
        Map<String, String> afterFormat = new HashMap<>();

        for (Object[] dateTime : resultQuery) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatDate = simpleDateFormat.format(dateTime[0]);
            BigDecimal price = (BigDecimal) dateTime[1];
            // Set scale to 2 for two decimal places
            BigDecimal scaledPrice = price.setScale(2, RoundingMode.HALF_UP);
            afterFormat.put(formatDate, scaledPrice.toPlainString());
        }

        return afterFormat;
    }

    public List<Top5MenuList> getTop5MenuList() {
        List<Object[]> result = orderItemRepository.getTop5MenuList();
        List<Top5MenuList> top5MenuList = new ArrayList<>();

        for (Object[] row : result) {
            String menuName = (String) row[0];
            BigDecimal totalOrderedQuantity = (BigDecimal) row[1];
            Integer newVal = totalOrderedQuantity.intValue();

            Top5MenuList menu = new Top5MenuList(menuName, newVal);
            top5MenuList.add(menu);
        }

        return top5MenuList;
    }

    public List<OrderItem> getOrderStatus(String status) {
        if (status.equals("pending")) {
            return orderItemRepository.getOrderPending();
        } else {
            return orderItemRepository.getOrderSuccess();
        }
    }

}

