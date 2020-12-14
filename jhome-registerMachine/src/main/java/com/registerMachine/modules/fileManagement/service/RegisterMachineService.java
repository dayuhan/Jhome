package com.registerMachine.modules.fileManagement.service;


import com.registerMachine.modules.fileManagement.model.query.RegisterQuery;

public interface RegisterMachineService {
    RegisterQuery getGenerateCode(String declareCode);
}
