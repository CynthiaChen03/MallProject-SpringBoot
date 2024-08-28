package com.dmh.dao;

import com.dmh.entity.Customization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizationDao extends JpaRepository<Customization, Integer> {
    List<Customization> findAllByProductId(Integer pid);
   Customization findById(int id);

}
