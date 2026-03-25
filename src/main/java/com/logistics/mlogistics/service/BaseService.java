package com.logistics.mlogistics.service;

import com.logistics.mlogistics.domain.Base;
import com.logistics.mlogistics.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseService {

    private BaseRepository baseRepository;

    @Autowired
    public BaseService(BaseRepository baseRepository){
        this.baseRepository = baseRepository;
    }

    public List<Base> getAllBases(){
        return baseRepository.findAll();
    }
}
