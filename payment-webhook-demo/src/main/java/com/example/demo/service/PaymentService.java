package com.example.demo.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService {
    
    @Autowired
    private RazorpayClient razorpayClient;

    public String createOrder(int amount) throws RazorpayException {
     
        JSONObject orderRequest =  new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_"+ System.currentTimeMillis());

        Order order = razorpayClient.orders.create(orderRequest);
        return order.toString();
        
        
        
    }
}
