package com.account.modules.config;

import org.mengyun.tcctransaction.TransactionRepository;
import org.mengyun.tcctransaction.recover.RecoverConfig;
import org.mengyun.tcctransaction.serializer.KryoPoolSerializer;
import org.mengyun.tcctransaction.serializer.ObjectSerializer;
import org.mengyun.tcctransaction.spring.recover.DefaultRecoverConfig;
import org.mengyun.tcctransaction.spring.repository.SpringJdbcTransactionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * 分布式事务配置
 */
//@Configuration
public class TccConfig {
    @Bean
    public TransactionRepository transactionRepository(@Qualifier("transactionDataSource") DataSource dataSource, ObjectSerializer objectSerializer) {
        SpringJdbcTransactionRepository springJdbcTransactionRepository = new SpringJdbcTransactionRepository();
        springJdbcTransactionRepository.setDataSource(dataSource);
        springJdbcTransactionRepository.setSerializer(objectSerializer);

        //TbSuffix就是事务存储时用于拼凑表名得，比如这里就是表：TCC_TRANSACTION_ORDER
        springJdbcTransactionRepository.setTbSuffix("_ORDER");
        //domain就是TCC_TRANSACTION_ORDER表里的domain字段，如果只有一张事务表TCC_TRANSACTION，用于业务区分得吧
        springJdbcTransactionRepository.setDomain("ORDER");
        return springJdbcTransactionRepository;
    }
    @Bean
    public ObjectSerializer objectSerializer() {
        return new KryoPoolSerializer();
    }
    //重试机制配置，比如：confirm或者cancel阶段异常，需要重试
    @Bean
    public RecoverConfig recoverConfig() {
        DefaultRecoverConfig recoverConfig = new DefaultRecoverConfig();
        recoverConfig.setMaxRetryCount(30);
        recoverConfig.setCronExpression("0/5 * * * * ?");
        recoverConfig.setRecoverDuration(5);
        return recoverConfig;
    }
}
