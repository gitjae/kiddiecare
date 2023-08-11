package com.spring.kiddiecare.domain.children;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Integer> {
//    @Query(value = "SELECT * FROM children WHERE parent_no = ?1", nativeQuery = true)
    List<Children> findByParentNo(int parentNo);
}