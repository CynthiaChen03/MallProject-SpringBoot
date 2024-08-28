package com.dmh.dao;

import java.util.Date;
import java.util.List;

import com.dmh.entity.Score;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ScoreDao extends JpaRepository<Score, Integer> {
List<Score> findAllByProductId(Integer id);
}
