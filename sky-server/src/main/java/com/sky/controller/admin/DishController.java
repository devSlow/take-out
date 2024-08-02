package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    DishService dishService;

    /**
     * 新增菜品接口
     *
     * @param dishDTO
     * @return
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result insertDishWithFlavor(@RequestBody DishDTO dishDTO) {
        dishService.insertDishWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页")
//    分页统一返回：PageResult
    public Result<PageResult> DishPageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult = dishService.DishPageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     */
    @DeleteMapping()
    @ApiOperation("批量删除菜品")
    public Result deleteDish(@RequestParam List<Long> ids) {
        log.info("批量删除菜品：{}", ids);
        dishService.deleteBatchDish(ids);
        return Result.success();
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getDishById(@PathVariable Long id){
        DishVO dishVO = dishService.getDishByIdWithFlavor(id);
        return Result.success(dishVO);
    }
    /**
     * 修改菜品
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDishWithFlavor(@RequestBody DishDTO dishDTO){
        dishService.updateDishWithFlavor(dishDTO);
        return Result.success();
    }
}
