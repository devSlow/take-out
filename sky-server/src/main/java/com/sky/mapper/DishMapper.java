package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    /**
     * 根据分类id查询菜品数量
     */
    @Select("select count(id) from dish WHERE category_id=#{category_id}")
    Integer countByCategoryId(Long categoryId);
    /**
     * 新增菜品相关Mapper
     */
     void insertDishWithFlavors(Dish dish);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> DishPageQuery(DishPageQueryDTO dishPageQueryDTO);


    /**
     * 通过id查询菜品售卖状态
     * @param id
     */
    @Select("select  * from dish where id=#{id}")
    Dish queryStatusById(Long id);

    /**
     * 根据id删除菜品数据
     * @param id
     */
    void deleteById(Long id);
    /**
     * 根据id查询菜品数据
     *
     * @param id
     * @return
     */
    Dish getDishById(Long id);

    /**
     * 更新菜品数据
     * @param dish
     */
    void update(Dish dish);
}
