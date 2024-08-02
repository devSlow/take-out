package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 新增菜品和对应的口味数据
     */
    public  void insertDishWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult DishPageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id批量删除菜品
     * @param ids
     * @return
     */
    void deleteBatchDish(List<Long> ids);

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    DishVO getDishByIdWithFlavor(Long id);

    /**
     * 修改菜品和口味
     * @param dishDTO
     */
    void updateDishWithFlavor(DishDTO dishDTO);
}
