package com.github.xuejike.query.http.server.starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xuejike
 * @date 2021/1/4
 */
@Configuration
@Import({HttpServiceConfig.class,HttpClientConfig.class})
public class AutoConfig {
}
