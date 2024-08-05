package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * 店铺营业状态
 */
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺营业状态")
@Slf4j
public class ShopController {
//    注入Redis
    @Autowired
    RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置营业状态为{}",status==1?"营业中":"已打样");
        redisTemplate.opsForValue().set("SHOP_STATUS",status);
        return Result.success();
    }
//    管理员端获取营业状态
    @GetMapping("/status")
    @ApiOperation("管理员获取营业状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取到营业状态为：{}",status==1?"营业中":"已打样");
        return Result.success(status);
    }
}
