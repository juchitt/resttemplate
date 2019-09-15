package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class TestController {

    @PostMapping(value = "/test1")
    public String restTemplateTest(@ModelAttribute Object1 object1) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
        Object2 object2 = new Object2();
        if (object1.getPhoneNumber().isEmpty()) {
            return "가입실패";
        } else {
            object2.setCheck(true);
        }
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(object2), headers);
        String response = new RestTemplate().postForEntity("http://localhost:8080/test2", request, String.class).getBody();
        return response;
    }

    @PostMapping(value = "/test2")
    public String restTemplateTest2(@RequestBody String object2) throws IOException {
        if (new ObjectMapper().readValue(object2, Object2.class).check) {
            return "가입성공";
        } else {
            return "가입실패";
        }
    }


    @Data
    public static class Object1 {
        private String name;
        private String phoneNumber;
    }


    @Data
    public static class Object2 {
        private boolean check;
    }


}
