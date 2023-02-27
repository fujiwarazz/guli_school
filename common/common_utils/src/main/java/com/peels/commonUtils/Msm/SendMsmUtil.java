package com.peels.commonUtils.Msm;


import com.peels.commonUtils.Exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class SendMsmUtil {
    private static final String APP_CODE = "0180af33266443a7bdc6b62731d74098";
    private static final String HOST = "https://gyytz.market.alicloudapi.com";
    private static final String PATH = "sms/smsSend";
    private static final String METHOD = "POST";

    public static void Send(String phone,String code){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + APP_CODE);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code);
        bodys.put("phone_number", phone);
        bodys.put("template_id", "TPL_0000");
        try {
            HttpResponse response = HttpUtils.doPost(HOST, PATH, METHOD, headers, querys, bodys);
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("短信发送失败:phone:{}",phone);
            throw new SystemException(20001,"短信发送失败!");
        }
    }
}
