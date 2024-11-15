package com.nlo.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    @Autowired
    private UserDetailsService userDetailsService;

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
        if(userDetails == null) {
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
}
