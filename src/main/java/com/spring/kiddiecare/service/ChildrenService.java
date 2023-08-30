package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import com.spring.kiddiecare.domain.children.ChildrenRequestDto;
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

    /**
     * # 로그인 유저와 보호자 일치 확인 필요
     * 자녀 정보 업데이트 서비스
     * @param childDto 업데이트 할 자녀 정보가 담긴 Dto
     * @param id 자녀 PK값
     * @return 업데이트 성공 여부
     */
    @Transactional
    public boolean updateChild(ChildrenRequestDto childDto, int id){
        Optional<Children> foundChild = childrenRepository.findById(id);
        if(foundChild.isPresent()){
            Children child = foundChild.get();
            child.update(childDto);
            return true;
        }
        return false;
    }

    /**
     * # 로그인 유저와 보호자 일치 확인 필요
     * 자녀 정보 삭제 서비스
     * @param id 삭제 할 자녀 PK값
     * @return 삭제 성공 여부
     */
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
