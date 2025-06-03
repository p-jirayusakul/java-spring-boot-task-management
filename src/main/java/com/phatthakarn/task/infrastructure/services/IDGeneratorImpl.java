package com.phatthakarn.task.infrastructure.services;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import com.phatthakarn.task.domain.services.IDGenerator;
import org.springframework.stereotype.Component;

@Component
public class IDGeneratorImpl implements IDGenerator {

    public IDGeneratorImpl() {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        YitIdHelper.setIdGenerator(options);
    }

    @Override
    public Long generateId() {
        return YitIdHelper.nextId();
    }
}
