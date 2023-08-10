package com.spring.kiddiecare.domain.children;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Integer> {
    List<Children> findByParentNo(int parentNo);
}