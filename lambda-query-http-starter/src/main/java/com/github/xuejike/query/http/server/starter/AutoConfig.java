package com.github.xuejike.query.http.server.starter;

import com.github.xuejike.query.http.LambdaQueryHttpConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author xuejike
 * @date 2021/1/4
 */
@Configuration
@Import({HttpServiceConfig.class})
public class AutoConfig {
    @Bean
    @ConditionalOnBean(ServiceInstanceChooser.class)
    public ServiceSelect serviceSelect(ServiceInstanceChooser loadBalancer){
        return new ServiceSelect(loadBalancer);
    }
    @Bean
    @ConditionalOnBean(ServiceSelect.class)
    public LbHttpClient lbHttpClient(ServiceSelect serviceSelect){
        LbHttpClient lbHttpClient = new LbHttpClient(serviceSelect);
        LambdaQueryHttpConfig.getInstance().setHttpClient(lbHttpClient);
        return lbHttpClient;
    }

}
