package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("api/v1/children")
@RestController
public class ChildrenController {
    private final UserRepository userRepository;
    private final ChildrenRepository childrenRepository;
    @GetMapping("list/{page}")
    public Map childrenPage(WebRequest request, @PageableDefault(size = 2) Pageable pageable, @PathVariable int page){
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        JSONObject jsonObject = new JSONObject();

        Optional<User> foundUser = userRepository.findUserById(log);
        if (foundUser.isPresent()){
            User user = foundUser.get();
            List<Children> children = childrenRepository.findByParentNo(pageable.withPage(page-1), user.getNo());
            jsonObject.put("children", children);
        }

        return jsonObject.toMap();
    }
}
