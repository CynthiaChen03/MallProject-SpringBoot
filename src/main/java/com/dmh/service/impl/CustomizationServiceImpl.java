package com.dmh.service.impl;

import com.dmh.dao.CustomizationDao;
import com.dmh.entity.Customization;
import com.dmh.service.CustomizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomizationServiceImpl implements CustomizationService {

    @Autowired
    CustomizationDao customizationDao;
    @Override
    public Customization findByid(int id) {
        return customizationDao.findById(id);
    }

}
