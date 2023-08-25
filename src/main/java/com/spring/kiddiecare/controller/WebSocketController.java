package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.webSocket.WebSocketRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class WebSocketController {
    @GetMapping("/websocket")
    public String websocket(Model model, HttpSession session) {
        String userId = "admin"; // 세션에서 가져온 사용자 ID, 실제 사용자 ID로 대체할 수 있습니다.
        session.setAttribute("log", userId); // 세션에 사용자 ID를 저장하고 WebSocket에 전달합니다.
        model.addAttribute("userId", userId); // HTML 페이지로 사용자 ID를 전달합니다.

        return "webSocket";
    }

    @GetMapping("/websocket2")
    public String websocket2(Model model, HttpSession session) {
        String userId = "admin2"; // 세션에서 가져온 사용자 ID, 실제 사용자 ID로 대체할 수 있습니다.
        session.setAttribute("log", userId); // 세션에 사용자 ID를 저장하고 WebSocket에 전달합니다.
        model.addAttribute("userId", userId); // HTML 페이지로 사용자 ID를 전달합니다.

        return "webSocket";
    }


    @GetMapping("/websocket1")
    public String websocket1(Model model, HttpSession session) {
        String userId = "admin1"; // 세션에서 가져온 사용자 ID, 실제 사용자 ID로 대체할 수 있습니다.
        session.setAttribute("log", userId); // 세션에 사용자 ID를 저장하고 WebSocket에 전달합니다.
        model.addAttribute("userId", userId); // HTML 페이지로 사용자 ID를 전달합니다.

        return "webSocket";
    }

    @PostMapping("/websocket/member")
    public Map webSocketTest(@ModelAttribute WebSocketRequest ws){
        JSONObject json = new JSONObject();

        return json.toMap();
    }

}
