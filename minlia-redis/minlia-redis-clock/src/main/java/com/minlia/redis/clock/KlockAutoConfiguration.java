package com.minlia.redis.clock;

import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import com.minlia.redis.clock.config.KlockConfig;
import com.minlia.redis.clock.core.BusinessKeyProvider;
import com.minlia.redis.clock.core.KlockAspectHandler;
import com.minlia.redis.clock.core.LockInfoProvider;
import com.minlia.redis.clock.lock.LockFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ClassUtils;

/**
 *
 * @author kl
 * @date 2017/12/29
 * Content :klock自动装配
 */
@Configuration
@ConditionalOnProperty(prefix = KlockConfig.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(KlockConfig.class)
@Import({KlockAspectHandler.class})
public class KlockAutoConfiguration {

    @Autowired
    private KlockConfig klockConfig;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    RedissonClient redisson() throws Exception {
        Config config = new Config();
        if(klockConfig.getClusterServer()!=null){
            config.useClusterServers().setPassword(klockConfig.getPassword())
                    .addNodeAddress(klockConfig.getClusterServer().getNodeAddresses());
        }else {
            config.useSingleServer().setAddress(klockConfig.getAddress())
                    .setDatabase(klockConfig.getDatabase())
                    .setPassword(klockConfig.getPassword());
        }
        Codec codec=(Codec) ClassUtils.forName(klockConfig.getCodec(),ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec);
        config.setEventLoopGroup(new NioEventLoopGroup());
        return Redisson.create(config);
    }

    @Bean
    public LockInfoProvider lockInfoProvider(){
        return new LockInfoProvider();
    }

    @Bean
    public BusinessKeyProvider businessKeyProvider(){
        return new BusinessKeyProvider();
    }

    @Bean
    public LockFactory lockFactory(){
        return new LockFactory();
    }
}
