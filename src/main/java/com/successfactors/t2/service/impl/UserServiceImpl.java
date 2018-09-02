package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.UserDAO;
import com.successfactors.t2.domain.LoginBean;
import com.successfactors.t2.domain.User;
import com.successfactors.t2.service.CacheService;
import com.successfactors.t2.service.UserService;
import com.successfactors.t2.utils.WxMappingJackson2HttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CacheService cacheService;

    @Override
    public String getOpenId(String code) {
        String appId = "";
        String appSecrect = "";
        String apiPrefix = "https://api.weixin.qq.com/sns/jscode2session?";
        String url = apiPrefix + "appid=" + appId + "&secret=" + appSecrect + "&js_code=" + code + "&grant_type=authorization_code";
        logger.info("wechat api url: " + url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        LoginBean loginBean = restTemplate.getForObject(url, LoginBean.class);
        if(loginBean != null){
            return loginBean.getOpenid();
        }
        return null;
    }

    public static void main(String... agrs){
        UserServiceImpl impl = new UserServiceImpl();
        System.out.println(impl.getOpenId("0236lKXI1DDAw60hnjZI1d0wXI16lKXq"));
    }

    @Override
    public int addUser(User user){
        Set<String> userList = cacheService.getUserToSessionCache().keySet();
        if (userList != null && userList.contains(user.getId())) {
            user.setStatus(1);
        } else {
            user.setStatus(0);
        }
        userDAO.addUser(user);
        return user.getStatus();
    }

}
