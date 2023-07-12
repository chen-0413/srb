package com.atguigu.srb.sms.controller.api;


import com.atguigu.common.exception.Assert;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.common.util.RandomUtils;
import com.atguigu.common.util.RegexValidateUtils;
import com.atguigu.srb.sms.service.SmsService;
import com.atguigu.srb.sms.util.SmsProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author :Chen Xu Jiang
 * @date :  2023/7/6~18:44~星期四
 * @description: 短信功能接口
 */
@Slf4j
@Api(tags = "短信服务管理")
@RestController
@CrossOrigin // 解决前后端跨域问题
@RequestMapping("/api/sms")
public class ApiSmsController {
    @Resource
    private SmsService smsService;
    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation("获取验证码")
    @GetMapping("/send/{mobile}")
    public R send(
            @ApiParam(value = "手机号", required = true)
            @PathVariable String mobile) {

        // MOBILE_NULL_ERROR(-202, "手机号不能为空")
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        //MOBILE_ERROR(-203, "手机号不正确"),
        // 具体的原因是RegexValidateUtils.checkCellphone()的方法里面验证手机号段 其中不包括181这个号码段
        Assert.isTrue(RegexValidateUtils.checkCellphone(mobile), ResponseEnum.MOBILE_ERROR);

        //生成 随机验证码
        String code = RandomUtils.getFourBitRandom();
        //组装短信 模板参数
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        // 发送短信
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE, param);

        // 将验证码存入  redis 并且设置过期时间 具体的过期时间 默认验证码有效期为 5分钟
        redisTemplate.opsForValue().set("srb:sms:code: "+ mobile, code, 5, TimeUnit.MINUTES);

        return R.ok().message("验证码发送成功，请注意查收");

    }
}
