package com.ppdai.monitor.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author haijiang
 * @Description http请求类
 * @Date 2018/11/19
 **/
public class HttpUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    /**
     * 发送短信的url
     */
    private static final String SMS_URL = "api/monitor/queryMetricData?id={s_id}";
    /**
     * 发送语音的url
     */
    private static final String VOICE_URL = "http://acmessage.ppdapi.com/api/v2/voice/send";

    /**
     * 验证码校验接口 V2（只支持系统自动生成的验证码校验）请求地址
     */
    private static final String NEW_SMS_CHECKVERIFYCODE_URL = "http://acmessage.ppdapi.com/api/v2/CheckVerifyCode?callerId={callerId}&recipient={recipient}&" +
            "inputCode={inputCode}&verifyCodeBusinessAlias={verifyCodeBusinessAlias}&verifyCodeTemplateAlias={verifyCodeTemplateAlias}&" +
            "onlyCheck={onlyCheck}&tokenId={tokenId}";

    private static final String PARAM_NAME_CALLERID = "callerId";
    private static final String PARAM_NAME_RECIPIENT = "recipient";
    private static final String PARAM_NAME_INPUTCODE = "inputCode";
    private static final String PARAM_NAME_BUSINESSALIAS = "verifyCodeBusinessAlias";
    private static final String PARAM_NAME_TEMPLATEALIAS = "verifyCodeTemplateAlias";
    private static final String PARAM_NAME_ONLYCHECK = "onlyCheck";
    private static final String PARAM_NAME_TOKENID = "tokenId";

    private static String doHttpRequest(String url, String parameters) {
        String result = null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            StringEntity entity = new StringEntity(parameters);
            entity.setContentType("text/json");
            entity.setContentEncoding("UTF-8");
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(entity);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
            httpPost.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity, "UTF-8");
            }
        } catch (Throwable ex) {
            return result;
        }
        return result;
    }

    /**
     * 发送短信验证码接口
     * @param requestDTO
     * @return
     */
//    public static SmsResponseDTO sendSms(SmsRequestDTO requestDTO)
//    {
//        SmsResponseDTO responseDTO=new SmsResponseDTO();
//        String s= JSONObject.toJSONString(requestDTO);
//        String result=doHttpRequest(SMS_URL, JSONObject.toJSONString(requestDTO));
//        if(result!=null)
//        {
//            responseDTO= JSONObject.parseObject(result,SmsResponseDTO.class);
//        }
//        return responseDTO;
//    }
//
//    /**
//     * 发送语音验证码接口
//     * @param requestDTO
//     * @return
//     */
//    public static VoiceResponseDTO sendVoice(VoiceRequestDTO requestDTO)
//    {
//        VoiceResponseDTO response=new VoiceResponseDTO();
//        String result=doHttpRequest(VOICE_URL, JSONObject.toJSONString(requestDTO));
//        if(result!=null)
//        {
//            response= JSONObject.parseObject(result,VoiceResponseDTO.class);
//        }
//        return response;
//    }
//
//    public static String getCheckVerifyCodeResponse(CodeValidateRequestDTO smsCheckVerifyCodeRequest) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
//            headers.setContentType(type);
//            org.springframework.http.HttpEntity<String> formEntity = new org.springframework.http.HttpEntity<>(headers);
//            //拼装query参数
//            Map<String, Object> params = new HashMap<>(7);
//            params.put(PARAM_NAME_CALLERID, smsCheckVerifyCodeRequest.getCallerId());
//            params.put(PARAM_NAME_RECIPIENT, smsCheckVerifyCodeRequest.getRecipient());
//            params.put(PARAM_NAME_INPUTCODE, smsCheckVerifyCodeRequest.getInputCode());
//            params.put(PARAM_NAME_BUSINESSALIAS, smsCheckVerifyCodeRequest.getVerifyCodeBusinessAlias() != null ? smsCheckVerifyCodeRequest.getVerifyCodeBusinessAlias() : "");
//            params.put(PARAM_NAME_TEMPLATEALIAS, smsCheckVerifyCodeRequest.getVerifyCodeTemplateAlias());
//            params.put(PARAM_NAME_ONLYCHECK, smsCheckVerifyCodeRequest.getOnlyCheck());
//            params.put(PARAM_NAME_TOKENID, smsCheckVerifyCodeRequest.getTokenId());
//            OkHttp3ClientHttpRequestFactory clientHttpRequestFactory = new OkHttp3ClientHttpRequestFactory();
//            clientHttpRequestFactory.setConnectTimeout(5000);
//            clientHttpRequestFactory.setReadTimeout(5000);
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.setRequestFactory(clientHttpRequestFactory);
//            String response = restTemplate.postForObject(NEW_SMS_CHECKVERIFYCODE_URL, formEntity, String.class, params);
//            return response;
//        } catch (Exception e) {
//            LOGGER.error("调用校验短信接口异常：", e);
//        }
//        return null;
//    }

}
