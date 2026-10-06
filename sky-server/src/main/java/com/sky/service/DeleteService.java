package com.sky.service;

import java.util.List;

public interface DeleteService {
    /**
     * 按照id依次删除菜品
     * @param ids
     */
    public void deleteBatch(List<Long> ids);

}
