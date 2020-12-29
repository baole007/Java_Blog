package com.jdxl.seedcourse.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class RestTemplateUtil {

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(@Qualifier("restTemplate") RestTemplate restTemplate) {
        RestTemplateUtil.restTemplate = restTemplate;
    }

    public static String postHttpJson(String url, HashMap<String, Object> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject json = (JSONObject) JSONObject.toJSON(request);
        String returnValue = null;
        try {
            HttpEntity<String> formEntity = new HttpEntity<String>(json.toJSONString(), headers);
            returnValue = restTemplate.postForObject(url, formEntity, String.class);
        } catch (HttpServerErrorException e) {
            log.warn("postHttpJson HttpClientErrorException: ", e);
        } catch (HttpClientErrorException e) {
            log.warn("postHttpJson HttpServerErrorException: ", e);
        } catch (RestClientException e) {
            log.warn("postHttpJson RestClientException: ", e);
        }
        return returnValue;
    }

    public static String getHttpJson(String url, HashMap<String, Object> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String returnValue = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (!request.isEmpty()) {
                int i = 0;
                for (String key : request.keySet()) {
                    sb.append(key).append("=").append(request.get(key).toString());
                    if (i < request.size() - 1) {
                        sb.append("&");
                    }
                    i++;
                }
            }

            url = url + "?" + sb.toString();
            returnValue = restTemplate.getForObject(url, String.class);
        } catch (HttpServerErrorException e) {
            log.warn("getHttpJson HttpClientErrorException: ", e);
        } catch (HttpClientErrorException e) {
            log.warn("getHttpJson HttpServerErrorException: ", e);
        } catch (RestClientException e) {
            log.warn("getHttpJson RestClientException: ", e);
        }
        return returnValue;
    }


    public static HttpEntity<MultiValueMap<String, String>> buildUrlEncodeReqData(Map<String, String> requestParams) {
        return buildReqData(MediaType.APPLICATION_FORM_URLENCODED, requestParams);
    }


    public static HttpEntity<MultiValueMap<String, String>> buildReqData(MediaType reqEncode, Map<String, String> requestParams) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(reqEncode);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.setAll(requestParams);

        return new HttpEntity<MultiValueMap<String, String>>(map, headers);
    }


    /**
     * getForEntity
     */
    public static <T> T getForEntity(String url, Class<T> responseType, Map<String, String> params) {

        ResponseEntity<T> forEntity = restTemplate.getForEntity(url, responseType, params);

        return forEntity.getBody();

    }


    /**
     * postForEntity
     */
    public static <T> T postForEntity(String url,  Class<T> responseType, MultiValueMap<String, String> request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity  = new HttpEntity<MultiValueMap<String, String>>(request, headers);

        T t = restTemplate.postForObject(url, requestEntity, responseType);

        return t;

    }
}
