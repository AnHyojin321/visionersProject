package com.example.visioners.controller;

import com.example.visioners.dto.ProhibitedItem;
import com.example.visioners.repository.ProhibitedItemRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RestController
public class ObjectDetectionController {

    @Autowired
    private ProhibitedItemRepository prohibitedItemRepository;

    private final String FLASK_SERVER_URL = "http://127.0.0.1:5000/detect_objects";

    @PostMapping("/detect_and_compare")
    public ResponseEntity<String> detectAndCompare() {
        RestTemplate restTemplate = new RestTemplate();
        String jsonString = restTemplate.getForObject(FLASK_SERVER_URL, String.class);

        // JSON 파싱
        JSONArray items = new JSONArray(jsonString);
        String itemName = items.getJSONObject(0).getString("name");

        System.out.println("Received item name: " + itemName);

        // 문자열 정규화와 데이터베이스 검색
        ProhibitedItem item = prohibitedItemRepository.findByName(itemName.trim().toLowerCase());
        if (item != null) {
            return ResponseEntity.ok("{\"message\":\"반입 불가능합니다\"}");
        }
        return ResponseEntity.ok("{\"message\":\"반입 가능합니다\"}");
    }
}
