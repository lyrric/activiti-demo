package com.github.lyrric.plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lyrric.model.HttpResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created on 2019/3/12.
 * 封装返回数据
 * @author wangxiaodong
 */
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class ResponseWrapper implements ResponseBodyAdvice<Object> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter,
                                  MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        //swagger接口不封装返回数据
        String path = serverHttpRequest.getURI().getPath();
        if("/swagger-resources".equals(path) || "/v2/api-docs".equals(path)){
            return o;
        }
        //避免二次封装
        if(o instanceof HttpResult){
            return o;
        }
        if(o instanceof String){
            try {
                return objectMapper.writeValueAsString(HttpResult.success(o));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
        return HttpResult.success(o);
    }
}
