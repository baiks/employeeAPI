package com.employee.management.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class apiController {
    @GetMapping(value = "ping")
    public String ping() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", "00");
        jsonObject.addProperty("message", "Ping is successful");
        return new Gson().toJson(jsonObject);
    }
}
