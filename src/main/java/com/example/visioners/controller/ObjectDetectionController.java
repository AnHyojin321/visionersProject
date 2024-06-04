package com.example.visioners.controller;

import com.example.visioners.dto.ProhibitedItem;
import com.example.visioners.repository.ProhibitedItemRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ObjectDetectionController {
    private static final Logger logger = LoggerFactory.getLogger(ObjectDetectionController.class);

    @Autowired
    private ProhibitedItemRepository prohibitedItemRepository;

    private final String FLASK_SERVER_URL = "http://127.0.0.1:5000/detect_objects";

    @PostMapping("/detect_and_compare")
    public ResponseEntity<String> detectAndCompare() {
        RestTemplate restTemplate = new RestTemplate();
        // Flask 서버로부터 객체 인식 결과를 JSON 문자열로 받아옴
        String response = restTemplate.postForObject(FLASK_SERVER_URL, "", String.class);

        // JSON 파싱
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray items = jsonResponse.getJSONArray("names");
        String itemName = items.getString(0); // 첫 번째 요소를 문자열로 직접 추출

        // 데이터베이스에서 객체 이름으로 검색
        ProhibitedItem prohibitedItem = prohibitedItemRepository.findByName(itemName.trim().toLowerCase());
        if (prohibitedItem == null) {
            logger.info("ProhibitedItem found: {}", prohibitedItem);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("message", "반입 가능합니다");
            return ResponseEntity.ok(jsonResult.toString());
        } else {
            logger.info("ProhibitedItem not found for name: {}", itemName.trim().toLowerCase());
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("message", "반입 불가능합니다");
            return ResponseEntity.ok(jsonResult.toString());
        }
    }
}
