package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MyCategoryService {
    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增 分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 分类列表查询
     * @param type
     * @return
     */
    List<Category> list(Integer type);

    /**
     * 修改 分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启售停售 分类
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 删除 分类
     * @param id
     */
    void deleteById(Long id);
}
