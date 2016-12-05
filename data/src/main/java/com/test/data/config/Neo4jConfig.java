package com.test.data.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = { "com.test.data.repositories" })
public class Neo4jConfig extends Neo4jConfiguration {
//SDN 升级到4.1.5，连接服务器的配置改在ogm.properties中设定，这样可以访问Neo4j 2.x 到 3.x 版本
//    @Override
//    public Neo4jServer neo4jServer() {
//        return new RemoteServer("http://192.168.1.211:7474","neo4j","12345678");
//    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("com.test.data.domain");
    }
}
