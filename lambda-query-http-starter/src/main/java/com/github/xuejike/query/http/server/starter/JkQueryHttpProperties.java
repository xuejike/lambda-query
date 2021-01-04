package com.github.xuejike.query.http.server.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xuejike
 * @date 2021/1/4
 */
@ConfigurationProperties("jk-query.http")
@Data
public class JkQueryHttpProperties {
    private String httpServicePath = "/lambda/";

}
