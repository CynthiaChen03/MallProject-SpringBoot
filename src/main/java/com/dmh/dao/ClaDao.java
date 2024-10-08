package com.dmh.dao;

import java.util.Date;
import java.util.List;

import com.dmh.entity.Cla;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dmh.entity.Product;

public interface ClaDao extends JpaRepository<Cla, Integer> {

    List<Cla> findAll();
}
