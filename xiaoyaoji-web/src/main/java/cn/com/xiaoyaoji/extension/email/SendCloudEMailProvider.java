package cn.com.xiaoyaoji.extension.email;

import cn.com.xiaoyaoji.core.util.ConfigUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujingjie
 *         created on 2017/5/18
 */
public class SendCloudEMailProvider implements EmailProvider {
    private static Logger logger = Logger.getLogger(SendCloudEMailProvider.class);
    //private static String TEMPLATE_URL = "http://sendcloud.sohu.com/webapi/mail.send_template.json";
    private static String TEMPLATE_URL = "http://api.sendcloud.net/apiv2/mail/sendtemplate";

    @Override
    public void sendCaptcha(String code, String to) {
         String xsmtpapi = convert(to,"captcha",code);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(TEMPLATE_URL);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("apiUser", ConfigUtils.getProperty("sendcloud.system.apiuser")));
        params.add(new BasicNameValuePair("apiKey", ConfigUtils.getProperty("sendcloud.apikey")));
        params.add(new BasicNameValuePair("xsmtpapi", xsmtpapi));
        params.add(new BasicNameValuePair("templateInvokeName", "captcha"));
        params.add(new BasicNameValuePair("from", ConfigUtils.getProperty("sendcloud.system.from")));
        params.add(new BasicNameValuePair("fromName", "虎牙系统通知"));
        params.add(new BasicNameValuePair("subject", "虎牙系统通知-验证码"));

        httpRequest(httpClient, httpPost, params);

    }
    @Override
    public void findPassword(String findPageURL, String to) {
        String xsmtpapi = convert(to,"url",findPageURL);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(TEMPLATE_URL);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("apiUser", ConfigUtils.getProperty("sendcloud.system.apiuser")));
        params.add(new BasicNameValuePair("apiKey", ConfigUtils.getProperty("sendcloud.apikey")));
        params.add(new BasicNameValuePair("xsmtpapi", xsmtpapi));
        params.add(new BasicNameValuePair("templateInvokeName", "find_password"));
        params.add(new BasicNameValuePair("from", ConfigUtils.getProperty("sendcloud.system.from")));
        params.add(new BasicNameValuePair("fromName", "虎牙系统通知"));
        params.add(new BasicNameValuePair("subject", "虎牙系统通知-找回密码"));

        httpRequest(httpClient, httpPost, params);
    }

    private void httpRequest(DefaultHttpClient httpClient, HttpPost httpPost, List<NameValuePair> params) {
        HttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            response = httpClient.execute(httpPost);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
            httpPost.releaseConnection();
            logger.info(response.toString());
        } else {
            httpPost.releaseConnection();
            throw new RuntimeException();
        }
    }

    public static String convert(String to,String key,String value) {
        JSONObject ret = new JSONObject();

        JSONArray toArr = new JSONArray();

        JSONArray valueArr = new JSONArray();

        toArr.add(to);
        valueArr.add(value);

        JSONObject sub = new JSONObject();
        sub.put("%" + key + "%", valueArr);

        ret.put("to", toArr);
        ret.put("sub", sub);

        return ret.toString();
    }
}
