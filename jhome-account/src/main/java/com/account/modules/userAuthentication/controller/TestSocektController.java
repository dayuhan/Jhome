package com.account.modules.userAuthentication.controller;

 import com.bracket.common.Bus.AbstractController.BaseController;
 import com.bracket.common.Bus.ResponseJson;
 import com.bracket.common.ToolKit.StringUtil;
 import com.rpc.common.thrift.socketService;
 import io.swagger.annotations.Api;
 import org.apache.thrift.transport.TTransport;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.*;

 /**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: account-root
 * @description: 测试socket服务
 * @author: Daxv
 * @create: 2020-07-29 13:51
 **/
@Api(tags = "C、测试Socket服务")
@RestController
@RequestMapping(name = "/${adminPath}")
public class TestSocektController extends BaseController {

    @Autowired
    private TTransport tTransport;
    @Autowired
    private socketService.Client client;

    @ResponseBody
    @RequestMapping(value = "/sendMagess", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String sendMagess(@RequestBody String messAge) {
        try {
            tTransport.open();
            String str = messAge;
            //默认发送格式
            if (StringUtil.isBlank(messAge))
                messAge="{\"fromUserId\":666,\"toUserId\":666,\"content\":\"霸气侧漏漏漏漏漏漏~~~~\",type:\"SINGLE_SENDING\"}";
            client.singleSend(messAge);
            String userListJson=client.getOnlineUserList();
            return new ResponseJson().success().setValue("data",userListJson).toString();
        } catch (Exception ex) {

        }
        finally {
            tTransport.close();
        }
        return "";
    }
}
