package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.MyCategoryMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.MyCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MyCategoryServiceImpl implements MyCategoryService {

  @Autowired
  private MyCategoryMapper myCategoryMapper;
  @Autowired
  private DishMapper dishMapper;
  @Autowired
  private SetmealMapper setmealMapper;
  /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());


        Page<Category> page = myCategoryMapper.queryPage(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }
/**
     * 新增分类
     * @param categoryDTO
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setStatus(StatusConstant.DISABLE);

        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        BaseContext.removeCurrentId();
        myCategoryMapper.insert(category);

    }

    @Override
    public List<Category> list(Integer type) {
       return myCategoryMapper.list(type);


    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        myCategoryMapper.update(category);
        BaseContext.removeCurrentId();

    }

    @Override
    public void startOrStop(Integer status, Long id) {
       Category category = Category.builder().
                status(status).
                id(id).
                updateTime(LocalDateTime.now()).
                updateUser(BaseContext.getCurrentId())
        .build();
        myCategoryMapper.update(category);
    }

    @Override
    public void deleteById(Long id) {
        Integer i = dishMapper.countByCategoryId(id);
        if (i > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        Integer j = setmealMapper.countByCategoryId(id);
        if (j > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        myCategoryMapper.deleteById(id);
    }
}
