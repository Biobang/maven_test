package com.atguigu.imperial.court.dao.api;

import com.atguigu.imperial.court.entity.Memorials;

import java.util.List;

/**
 * @description:
 * @author: 1223
 **/
public interface MemorialsDao {
    List<Memorials> selectAllMemorialsDigest();

    Memorials selectMemorialsById(String memorialsId);

    void updateMemorialsStatusToRead(String memorialsId);

    void updateMemorialsFeedBack(String memorialsId, String feedbackContent);
}
