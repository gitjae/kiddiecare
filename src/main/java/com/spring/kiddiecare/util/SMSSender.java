package com.spring.kiddiecare.util;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class SMSSender {
    @Value("${naver.sms.accessKey}")
    private String myAccessKey;

    @Value("${naver.sms.secretKey}")
    private String mySecretKey;

    @Value("${naver.sms.serviceId}")
    private String serviceId;

    @Value("${naver.sms.senderNumber}")
    private String senderNumber;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * https://api.ncloud-docs.com/docs/ai-application-service-sens-smsv2#APIHeader
     * @param recipientNumber 수신자 번호
     * @param message 문자 내용
     * @return 응답내용 : 요청시간, 요청 상태 등
     * @throws Exception
     */

    public Map sendSms(String recipientNumber, String message) throws Exception {
        Long time = System.currentTimeMillis();
        // Naver Sens API 엔드포인트 구성
        String url = "https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages";

        // 인증 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", myAccessKey);

        // Body를 Access Key Id와 맵핑되는 SecretKey로 암호화한 서명
        // HMAC 암호화 알고리즘은 HmacSHA256 사용
        String sig = makeSignature(time);
        headers.set("x-ncp-apigw-signature-v2", sig);

        // 메시지 본문 구성
        JSONObject requestJson = new JSONObject();
        requestJson.put("type", "SMS");
        requestJson.put("from", senderNumber);
        requestJson.put("content", message);
        ArrayList<JSONObject> messages = new ArrayList<>();
        JSONObject msg = new JSONObject();
        msg.put("to", recipientNumber);
        messages.add(msg);
        requestJson.put("messages", messages);
        System.out.println(requestJson.toString());
        HttpEntity<String> request = new HttpEntity<>(requestJson.toString(), headers);

        // API 요청
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        Map<String, Object> responseBody = response.getBody();

        return responseBody;
    }

    public Map sendLms(String recipientNumber, String message) throws Exception {
        Long time = System.currentTimeMillis();
        // Naver Sens API 엔드포인트 구성
        String url = "https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages";

        // 인증 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", myAccessKey);

        String sig = makeSignature(time);
        headers.set("x-ncp-apigw-signature-v2", sig);

        // 메시지 본문 구성
        JSONObject requestJson = new JSONObject();
        requestJson.put("type", "LMS");
        requestJson.put("from", senderNumber);
        requestJson.put("content", message);
        ArrayList<JSONObject> messages = new ArrayList<>();
        JSONObject msg = new JSONObject();
        msg.put("to", recipientNumber);
        messages.add(msg);
        requestJson.put("messages", messages);
        System.out.println(requestJson.toString());
        HttpEntity<String> request = new HttpEntity<>(requestJson.toString(), headers);

        // API 요청
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        Map<String, Object> responseBody = response.getBody();

        return responseBody;
    }

    /**
     * 헤더에 포함될 시그니처 생성
     * https://api.ncloud-docs.com/docs/common-ncpapi
     * @param time API Gateway 서버와 시간 차가 5분 이상 나는 경우 유효하지 않은 요청으로 간주
     * @return 헤더에 포함될 시그니처
     * @throws Exception
     */
    public String makeSignature(Long time) throws Exception{
        String space = " ";					// one space
        String newLine = "\n";					// new line
        String method = "POST";					// method
        String url = "/sms/v2/services/"+serviceId+"/messages";	// url (include query string)
        String timestamp = time.toString();			// current timestamp (epoch)
        String accessKey = myAccessKey;			// access key id (from portal or Sub Account)
        String secretKey = mySecretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        // SecretKeySpec : 대칭키 암호화에 사용되는 비밀키를 표현하는 객체, sens API에서 요구하는 HmacSHA256 알고리즘 사용
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

}
