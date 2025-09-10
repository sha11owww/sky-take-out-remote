package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.MyCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class MyCategoryController {

    @Autowired
    private MyCategoryService myCategoryService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = myCategoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增 分类
     */

    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        myCategoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 根据菜品种类查询
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        log.info("根据分类查询：{}", type);
        List< Category> list = myCategoryService.list(type);
        return Result.success(list);
    }
    /**
     * 修改 分类
     */
    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类：{}", categoryDTO);
        myCategoryService.update(categoryDTO);
        return Result.success();
    }
    /**
     * 启用、禁用 分类
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用分类：{},{}",status,id);
        myCategoryService.startOrStop(status,id);
        return Result.success();
    }
    /**
     * 删除 分类
     */
    @DeleteMapping
    public Result deleteById(Long id){
        log.info("删除分类：{}", id);
        myCategoryService.deleteById(id);
        return Result.success();
    }
}

