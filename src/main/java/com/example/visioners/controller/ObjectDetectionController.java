package com.example.visioners.controller;

import com.example.visioners.dto.ProhibitedItem;
import com.example.visioners.repository.ProhibitedItemRepository;
import org.json.JSONArray;
import org.json.JSONObject;
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
        // 요청 본문은 비어있는 문자열로 전달합니다.
        String jsonString = restTemplate.postForObject(FLASK_SERVER_URL, null, String.class);

        JSONObject jsonResponse = new JSONObject(jsonString);
        JSONArray detectedItems = jsonResponse.getJSONArray("detected");

        // 문자열 정규화와 데이터베이스 검색
        for (int i = 0; i < detectedItems.length(); i++) {
            String itemName = detectedItems.getString(i).trim().toLowerCase();
            ProhibitedItem prohibitedItem = prohibitedItemRepository.findByName(itemName);
            if (prohibitedItem != null) {
                JSONObject response = new JSONObject();
                response.put("message", "반입 불가능합니다");
                return ResponseEntity.ok(response.toString()); // JSON 형식의 응답 반환
            }
        }

        JSONObject response = new JSONObject();
        response.put("message", "반입 가능합니다");
        return ResponseEntity.ok(response.toString()); // JSON 형식의 응답 반환
    }
}
