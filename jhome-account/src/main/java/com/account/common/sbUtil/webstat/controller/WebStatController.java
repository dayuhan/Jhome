package com.account.common.sbUtil.webstat.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "WebStat", tags = "SYS、ACC内部WebStat服务")
@RestController
@RequestMapping(value = "/jkqframe/webstat", produces = {MediaType.APPLICATION_JSON_VALUE})
@Slf4j
public class WebStatController {
/*    @Autowired
    private WebCtrFilter webCtrFilter;

    @RequestMapping(value = "/gettime", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "获取服务器时间", notes = "Requires AccessToken")
    public CommonRlt gettime() throws IllegalValidateException {
        long time = System.currentTimeMillis();
        return CommonRlt.success(time);
    }

    @RequestMapping(value = "/getlist", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation(value = "获取统一返回码情况", notes = "Requires AccessToken")
    public CommonRlt getlist() throws IllegalValidateException {
        Map<String, List<StatCtrRetCodeEntity>> mapResult = new HashMap<String, List<StatCtrRetCodeEntity>>();
        mapResult.put("minute", webCtrFilter.getTempResult());
        mapResult.put("total", webCtrFilter.totalTempResult());
        return CommonRlt.success(mapResult);
    }*/
}
