package com.tenx.ms.retail.stock.service;

import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class StockService {

    @Autowired
    private StockRepository repo;

    private final ModelMapper mapper = new ModelMapper();

    @Transactional
    public void upsert(Stock stock) {
        StockEntity se = mapper.map(stock, StockEntity.class);
        repo.saveAndFlush(se);
    }
}
