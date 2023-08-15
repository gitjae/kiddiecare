package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import com.spring.kiddiecare.domain.children.ChildrenRequestDto;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.service.ChildrenService;
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
    private final ChildrenService childrenService;
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

    @DeleteMapping("child/{no}")
    public Map deleteChild(@PathVariable(name = "no") int id){
        JSONObject jsonObject = new JSONObject();
        boolean delete = childrenService.deleteChildById(id);

        if(delete){
            jsonObject.put("delete","success");
            return jsonObject.toMap();
        }
        jsonObject.put("delete","fail");
        return jsonObject.toMap();
    }

    @PutMapping("child/{no}")
    public Map updateChild(@PathVariable(name = "no") int id, @RequestBody ChildrenRequestDto childDto){
        JSONObject jsonObject = new JSONObject();
        System.out.println("childDto:" + childDto);
        boolean update = childrenService.updateChild(childDto, id);
        if(update){
            jsonObject.put("update", "success");
            return jsonObject.toMap();
        }
        jsonObject.put("update", "fail");
        return jsonObject.toMap();
    }

    @GetMapping("/getChildrenByParentId")
    public List<Children> getChildrenByParentId(@RequestParam("parentId") int parentId) {
        return childrenRepository.findByParentNo(parentId);
    }

    @GetMapping("child/{no}")
    public Map getChildById(@PathVariable(name = "no") int id){
        JSONObject jsonObject = new JSONObject();
        Optional<Children> foundChild = childrenRepository.findById(id);
        if(foundChild.isPresent()){
            Children child = foundChild.get();
            jsonObject.put("child", child);
        }
        return jsonObject.toMap();
    }
}
