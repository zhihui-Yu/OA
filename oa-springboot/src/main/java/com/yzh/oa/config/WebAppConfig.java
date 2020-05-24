package com.yzh.oa.config;

import com.yzh.oa.web.global.LoginInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *   配置类
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    // 多个拦截器组成一个拦截器链
    // addPathPatterns 用于添加拦截规则
    // excludePathPatterns 用户排除拦截


    @Bean("txManager")
    public DataSourceTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /*事务拦截器*/
    @Bean("txAdvice")
    public 	TransactionInterceptor txAdvice(DataSourceTransactionManager txManager){

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        //当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务
        //RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        //requiredTx.setRollbackRules(
        //    Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        //requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setTimeout(5);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("search*", readOnlyTx);
        source.setNameMap( txMap );
        return new TransactionInterceptor(txManager ,source) ;
    }

    /**切面拦截规则 参数会自动从容器中注入*/
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(TransactionInterceptor txAdvice){
        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
        pointcutAdvisor.setAdvice(txAdvice);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* com.yzh.oa.service.*.*(..))");
        pointcutAdvisor.setPointcut(pointcut);
        return pointcutAdvisor;
    }

    /**
     * 高版本的配置文件中视图解析器会失效  建议用这种方法
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/pages/",".jsp");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**");
    }
}