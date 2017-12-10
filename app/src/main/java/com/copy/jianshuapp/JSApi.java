package com.copy.jianshuapp;

import java.util.Collections;
import java.util.List;

/**
 * 简书API接口清单
 * @version imkarl 2017-03
 */
public interface JSApi {

    String HTTP = "http://www.jianshu.com/";
    String HTTPS = "https://www.jianshu.com/";

    String API_HOST = HTTPS;
    String WEBPAGE_REGISTER = HTTPS;
    List<String> IMAGE_HOSTS = Collections.emptyList();

    String URL_LOGIN = HTTP;
    String URL_REGISTER = HTTPS + "sign_up";

}
