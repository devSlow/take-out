package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;


    /**
     * 新增菜品和口味
     *
     * @param dishDTO
     */
//    需要向菜品表和口味表都插入数据，所以使用@Transactional注解
@Transactional
    public void insertDishWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setCreateUser(dish.getCreateUser());
        dish.setUpdateUser(dish.getUpdateUser());
        dish.setCreateTime(dish.getCreateTime());
        dish.setUpdateTime(dish.getUpdateTime());
//        向菜品表插入一条数据
        dishMapper.insertDishWithFlavors(dish);
//        向口味表插入多条数据
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
//        判断口味是否是空
        if (flavors != null && flavors.size() > 0) {
//            遍历出dishId主键
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult DishPageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.DishPageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void deleteBatchDish(List<Long> ids) {
//        起售中的菜品不能删除
        for (Long id : ids) {
            Dish dish = dishMapper.queryStatusById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
//        被套餐关联的菜品不能删除(根据菜品id查询套餐id，有就不能删除)
            List<Long> dishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
//            查出来了
            if (dishIds!=null && dishIds.size()>0){
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
//            删除菜品表中的菜品数据
//        todo 可以优化为批量删除
        for (Long id : ids) {
            //删除菜品表中的菜品数据
            dishMapper.deleteById(id);
//            删除口味表中的口味数据
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    /**
     * 根据id查询接口
     * @param id
     * @return
     */
    @Override
    public DishVO getDishByIdWithFlavor(Long id) {
//        先查询菜品表
      Dish dish=  dishMapper.getDishById(id);
//        再查询口味表
       List<DishFlavor> dishFlavors=dishFlavorMapper.getDishFlavorById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 修改菜品和口味数据
     * @param dishDTO
     */
    @Override
    public void updateDishWithFlavor(DishDTO dishDTO) {
//        更新菜品数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dish.setUpdateUser(dish.getUpdateUser());
        dish.setCreateUser(dish.getCreateUser());
        dish.setCreateTime(dish.getCreateTime());
        dish.setUpdateTime(dish.getUpdateTime());
        dishMapper.update(dish);
//        更新口味数据
//            1.删除口味
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
//            2.新增口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
//            遍历出dishId主键
            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishDTO.getId());
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }
}