package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ChildrenService {
    @Autowired
    private ChildrenRepository childrenRepository;

    public List<Children> getChildrenByParentNo(int parentNo) {
        return childrenRepository.findByParentNo(parentNo);
    }

    @Transactional
    public boolean deleteChildById(int id){
        Optional<Children> foundChild = childrenRepository.findById(id);

        if(foundChild.isPresent()){
            Children child = foundChild.get();
            childrenRepository.delete(child);
            return true;
        }

        return false;
    }
}
