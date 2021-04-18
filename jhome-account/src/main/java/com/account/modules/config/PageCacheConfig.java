package com.account.modules.config;
/**
 * 1、pom.xml:
 * <dependency>
 * 	<groupId>net.sf.ehcache</groupId>
 * 	<artifactId>ehcache-controller</artifactId>
 * 	<version>2.0.4</version>
 * </dependency>
 *
 * 2、application.yml:
 * # 页面缓存配置
 * ehcache:
 *   pageCaching:
 *     enabled: false
 *     urlPatterns: "*.html"
 *
 * */
//@Configuration
public class PageCacheConfig {

//	/**
//	 * PageCache Filter, cache .html suffix.
//	 */
//	@Bean
//	@Order(2000)
//	@ConditionalOnProperty(name = "ehcache.pageCaching.enabled", havingValue = "true")
//	@ConditionalOnMissingBean(name="pageCachingFilter")
//	public FilterRegistrationBean<PageCachingFilter> pageCachingFilter(EhCacheManagerFactoryBean ehCacheManager) {
//		FilterRegistrationBean<PageCachingFilter> bean = new FilterRegistrationBean<>();
//		SimplePageCachingFilter pageCachingFilter = new SimplePageCachingFilter();
//		pageCachingFilter.setCacheManager(ehCacheManager.getObject());
//		bean.setFilter(pageCachingFilter);
//		bean.addInitParameter("cacheName", "pageCachingFilter");
//		bean.addUrlPatterns(StringUtils.split(Global.getProperty(
//				"ehcache.pageCaching.urlPatterns"), ","));
//		return bean;
//	}

}