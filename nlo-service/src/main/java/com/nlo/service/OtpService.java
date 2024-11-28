package com.nlo.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nlo.entity.User;
import com.nlo.model.MemberShipDTO;
import com.nlo.model.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestTemplate restTemplate;

    @Lazy
    @Autowired
    private UserService userService;

    private static final Integer EXPIRE_MIN = 5;
    private LoadingCache<String, String> otpCache;

    public OtpService() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String s) {
                        return "";
                    }
                });
    }

    public String generateOtp(String phoneNo) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNo);
        if (userDetails == null) {
            throw new UsernameNotFoundException(phoneNo + " Not found!!");
        }
        return getRandomOTP(phoneNo);
    }

    private String getRandomOTP(String phoneNo) {
        String otp = new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
        otpCache.put(phoneNo, otp);
        return otp;
    }

    //get saved otp
    public String getCacheOtp(String key) {
        try {
            return otpCache.get(key);
        } catch (Exception e) {
            return "";
        }
    }

    //clear stored otp
    public void clearOtp(String key) {
        otpCache.invalidate(key);
    }

    public Map<String, Object> getOtpByMemberShipId(String memberShipId) {
        String mobileNumber = userService.loadByMembershipId(memberShipId);

        if(Objects.isNull(mobileNumber)) {
            MemberShipDTO memberShipDTO = new MemberShipDTO();
            memberShipDTO.setMembershipId(memberShipId);
            MemberShipDTO body = restTemplate.postForEntity("https://www.mypartydashboard.com/BSA2/WebService/Cadre/getCadreMobileNoByMembershipId",
                    memberShipDTO,
                    MemberShipDTO.class
            ).getBody();
            UserDto userDto = new UserDto();
            userDto.setMobile(body.getMobileNo());
            mobileNumber = userService.saveUser(userDto).getMobile();
        }
        String otp = generateOtp(mobileNumber);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("otp", otp);
        returnMap.put("mobile", mobileNumber);
        returnMap.put("status", "success");
        returnMap.put("message", "Otp sent successfully");
        return returnMap;
    }


}
