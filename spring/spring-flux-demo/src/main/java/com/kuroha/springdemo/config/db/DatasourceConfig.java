package com.kuroha.springdemo.config.db;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import dev.miku.r2dbc.mysql.constant.SslMode;
import dev.miku.r2dbc.mysql.constant.TlsVersions;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kuroha
 */
@Configuration
public class DatasourceConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        MySqlConnectionConfiguration mySqlConnectionConfiguration = MySqlConnectionConfiguration.builder()
                .host("daodou-test.mysql.rds.aliyuncs.com")
                .user("horder")
                .port(3306)
                .password("DIOXRndKtIDK0rf8")
                .database("rights")
                .sslMode(SslMode.PREFERRED)
                .tlsVersion(TlsVersions.TLS1_3, TlsVersions.TLS1_2, TlsVersions.TLS1_1)
                .useServerPrepareStatement()
                .tcpKeepAlive(true)
                .tcpNoDelay(true)
                .autodetectExtensions(false)
                .build();
        return MySqlConnectionFactory.from(mySqlConnectionConfiguration);
    }

}
