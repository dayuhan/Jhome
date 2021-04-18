package com.account.modules.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
/*    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1),5);
    }*/
/*
        @Bean
        public Decoder feignDecoder() {
                HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper());
                ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
                return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
        }
        @Bean
        public Encoder feignEncoder(){
                HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper());
                ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
                return new SpringEncoder(objectFactory);
        }
        public ObjectMapper objectMapper(){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
                return objectMapper;
        }*/
}
