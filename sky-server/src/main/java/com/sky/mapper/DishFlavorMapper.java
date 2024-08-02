package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品表删除口味数据
     * @param dishId
     */
    void deleteByDishId(Long dishId);

    /**
     * 根据id获取菜品口味
     * @param id
     * @return
     */
    List<DishFlavor> getDishFlavorById(Long id);



}
