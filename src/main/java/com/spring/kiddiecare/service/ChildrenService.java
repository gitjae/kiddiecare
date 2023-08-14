package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildrenService {
    @Autowired
    private ChildrenRepository childrenRepository;

    public List<Children> getChildrenByParentNo(int parentNo) {
        return childrenRepository.findByParentNo(parentNo);
    }
}
