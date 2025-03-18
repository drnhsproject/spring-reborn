package com.sagara.spring.module.example.application;

import com.sagara.spring.services.SingleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/examples")
public class ExampleResource {

    @GetMapping("")
    public ResponseEntity<SingleResponse<String>> getExample() {
        String result = "This data example";
        SingleResponse<String> response = new SingleResponse<>( "Example detail retrieved",result);
        return ResponseEntity.ok().body(response);
    }
}
