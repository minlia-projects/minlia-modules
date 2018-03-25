package com.minlia.modules.tcp.server.starter;

import com.minlia.modules.tcp.server.tcp.Server;
import com.minlia.modules.tcp.server.tcp.TcpControllerBeanPostProcessor;
import com.minlia.modules.tcp.server.tcp.TcpServer;
import com.minlia.modules.tcp.server.tcp.TcpServerAutoStarterApplicationListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TcpServerProperties.class)
@ConditionalOnProperty(prefix = "minlia.tcp-server", name = {"port", "autoStart"})
public class TcpServerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    TcpServerAutoStarterApplicationListener tcpServerAutoStarterApplicationListener() {
        return new TcpServerAutoStarterApplicationListener();
    }

    @Bean
    @ConditionalOnMissingBean
    TcpControllerBeanPostProcessor tcpControllerBeanPostProcessor() {
        return new TcpControllerBeanPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    Server server(){
        return new TcpServer();
    }
}
