package com.alfred_core.intent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/intent")
public class IntentController {

    private final IntentRouterService router;

    public IntentController(IntentRouterService router) {
        this.router = router;
    }

    @GetMapping
    public IntentResult test(@RequestParam String q) {
        return router.route(q);
    }
}