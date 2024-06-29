package com.example.demo.entities.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Data
@CrossOrigin("http://localhost:4200")
public class PurchaseResponse {

    private final String orderTrackingNumber;

}
