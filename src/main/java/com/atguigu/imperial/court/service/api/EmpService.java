package com.atguigu.imperial.court.service.api;

import com.atguigu.imperial.court.entity.Emp;

/**
 * @description:
 * @author: 1223
 **/
public interface EmpService {

    Emp getEmpByLoginAccount(String loginAccount, String loginPassword);
}
