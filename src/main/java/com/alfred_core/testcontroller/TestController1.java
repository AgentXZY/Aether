//package com.alfred_core.testcontroller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alfred_core.service.OllamaService;
//
//@RestController
//@RequestMapping("/test")
//public class TestController1 {
//
//    private final OllamaService ollamaService;
//
//    public TestController1(OllamaService ollamaService) {
//        this.ollamaService = ollamaService;
//    }
//
//    @GetMapping
//    public String test() {
//
//        return ollamaService.generate(
//                "just tell me whats 2+2"
//        );
//    }
//}