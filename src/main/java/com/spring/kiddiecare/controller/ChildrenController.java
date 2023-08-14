package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import com.spring.kiddiecare.domain.children.ChildrenRequestDto;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
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

//    @GetMapping("/getChildrenByParentId")
//    @ResponseBody
//    public List<Children> getChildrenByParentId(@RequestParam("parentId") int parentId) {
//
//        List<Children> children = childrenRepository.findByParentNo(parentId);
//        System.out.println("자녀 정보 : " + children);
//        // Fetched children: [com.spring.kiddiecare.domain.children.Children@72694adc, com.spring.kiddiecare.domain.children.Children@72694adc]
//
//        return childrenRepository.findByParentNo(parentId);
//    }

    @PostMapping("child")
    public Map childRegister(@RequestBody ChildrenRequestDto childDto, WebRequest request){
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);
        JSONObject jsonObject = new JSONObject();
        Optional<User> foundUser = userRepository.findUserById(log);
        if(foundUser.isPresent()){
            User user = foundUser.get();
            Children child = new Children(childDto, user.getNo());
            childrenRepository.save(child);
            jsonObject.put("register","success");
            return jsonObject.toMap();
        }
        jsonObject.put("register","fail");
        return jsonObject.toMap();
    }

    @GetMapping("/getChildrenByParentId")
    public List<Children> getChildrenByParentId(@RequestParam("parentId") int parentId) {
        return childrenRepository.findByParentNo(parentId);
    }
}
