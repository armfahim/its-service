package com.its.service.resource.auth;

import com.its.service.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class TestAuthResource {

    @GetMapping("/test")
    public ResponseEntity<Object> register() {
        return ResponseEntity.ok("test admin api");
//        return ResponseEntity.ok(service.register(request));
    }
}
