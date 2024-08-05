package com.sky.controller.user;

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
@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺营业状态")
@Slf4j
public class ShopController {
//    注入Redis
    @Autowired
    RedisTemplate redisTemplate;

//    用户端获取营业状态
    @GetMapping("/status")
    @ApiOperation("用户获取营业状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取到营业状态为：{}",status==1?"营业中":"已打样");
        return Result.success(status);
    }
}
