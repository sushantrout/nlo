package com.nlo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class SMSUtil {
    @Autowired
    private RestTemplate restTemplate;

    // Build the complete URL with query parameters
    private String API_KEY = "199bae84b03f4bf2cb88";
    private String TEMPLATE_ID = "1707172225489896206";
    private String SENDER_ID = "TDPOTP";
    private String USERNAME = "tdpotpsms";
    private String BASE_URL = "https://smslogin.co/v3/api.php";

    public void sendMessage(String otp, String mobileNumber) {


        String completeUrl = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("message", "సభ్యత్వ నమోదు OTP " + otp + " TDP")
                .queryParam("mobile", mobileNumber)
                .queryParam("apikey", API_KEY)
                .queryParam("templateid", TEMPLATE_ID)
                .queryParam("senderid", SENDER_ID)
                .queryParam("username", USERNAME)
                .toUriString();

        String forObject = restTemplate.getForObject(completeUrl, String.class);
        log.info(forObject);
    }



}
