package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.service.PaymentService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public String createOrder(@RequestParam int amount){
        try {
            return paymentService.createOrder(amount);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return "Error creating order";
        }
    }

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("X-Razorpay-Signature") String signature){
        
        System.out.println("Webhook payload: " + payload);
        System.out.println("Webhook signature: " + signature);

        try {
            
            boolean isValid = true;
            
            if(isValid) {
                JSONObject json =  new JSONObject(payload);
                String eventType = json.getString("event");

                if("payment.captured".equals(eventType)){
                    JSONObject paymentEntity = json.getJSONObject("payload")
                                                    .getJSONObject("payment")
                                                    .getJSONObject("entity");

                    String paymentId = paymentEntity.getString("id");
                    String orderId = paymentEntity.getString("order_id");
                    String status =  paymentEntity.getString("status");

                    System.out.println(">>>> payment successfull payment id : " + paymentId + "| status: " + status);                    
                }
                return ResponseEntity.ok("Webhook processed successfully");                
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid webhook signature");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook");
        }

    }

}
