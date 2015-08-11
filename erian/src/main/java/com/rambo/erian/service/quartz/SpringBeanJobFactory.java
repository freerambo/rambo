package com.rambo.erian.service.quartz;


import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;


/**
 * 
 * @description   自定义SpringBeanJobFactory，用于对Job注入ApplicationContext等。 
 * @version currentVersion(1.0)  
 * @author Rambo  
 * @createtime Aug 13, 2013 4:00:12 PM
 */
public class SpringBeanJobFactory extends org.springframework.scheduling.quartz.SpringBeanJobFactory {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    
    /**
     * 这里我们覆盖了super的createJobInstance方法，对其创建出来的类再进行autowire。
     */
    @Override
    
	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		Object jobInstance = super.createJobInstance(bundle);
		// applicationContext.getAutowireCapableBeanFactory().autowireBean(jobInstance);
		beanFactory.autowireBean(jobInstance);
		return jobInstance;
	}
}