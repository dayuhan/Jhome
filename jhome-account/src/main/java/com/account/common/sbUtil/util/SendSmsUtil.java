package com.account.common.sbUtil.util;


import com.alibaba.fastjson.JSONObject;
/*import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;*/

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 * aliyun 短信服务
 */
public class SendSmsUtil {

    /**
     * 短信API产品名称
     */
    private static final String product = "Dysmsapi";
    /**
     * 短信API产品域名
     */
    private static final String domain = "dysmsapi.aliyuncs.com";
    private static final String accessKeyId = "LTAIWkcgVP0QGfna";
    private static final String accessKeySecret = "KHxSg5LRi1LvKzv8nSuHMMzIiJUMk4";
    private static final String sginName = "匠在线";
    public final String templateCode = "SMS_152857345";

    /**
     * 构造请求Body体
     * @param mobile 手机号码
     * @return
     */
    public Boolean sendAliMessage(String mobile,String code) {
        //设置超时时间-10秒
      /*  System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        SendSmsResponse sendSmsResponse = null;
        JSONObject codeObj = new JSONObject();
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,
                    domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(mobile);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(sginName);
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            codeObj.put("code", code);
            request.setTemplateParam(codeObj.toString());
            request.setOutId("yourOutId");
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            return true;
        }*/
        return false;
    }
}
