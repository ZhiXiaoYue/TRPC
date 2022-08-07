package com.jill.rpc.config.spring;

import com.jill.rpc.common.serialize.Serialization;
import com.jill.rpc.common.tools.SpiUtils;
import com.jill.rpc.config.ProtocolConfig;
import com.jill.rpc.config.RegistryConfig;
import com.jill.rpc.config.ServiceConfig;
import com.jill.rpc.config.annotation.TRpcService;
import com.jill.rpc.config.util.TrpcBootstrap;
import com.jill.rpc.remoting.Transporter;
import com.jill.rpc.rpc.protocol.Protocol;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 在spring初始化对象之后，找到使用了TRPC注解的
 */
public class TRPCPostProcessor implements ApplicationContextAware, InstantiationAwareBeanPostProcessor {
    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(TRpcService.class)){
            System.out.println(" 提供网络服务接受请求。");
            ServiceConfig serviceConfig = new ServiceConfig();
            serviceConfig.addProtocolConfig(applicationContext.getBean(ProtocolConfig.class));
            serviceConfig.addRegistryConfig(applicationContext.getBean(RegistryConfig.class));
            serviceConfig.setReference(bean); // 需要代理的bean

            TRpcService tRpcService= bean.getClass().getAnnotation(TRpcService.class);
            if(void.class== tRpcService.interfaceClass()){

                serviceConfig.setService(bean.getClass().getInterfaces()[0]);
            }else{
                serviceConfig.setService(tRpcService.interfaceClass());
            }

            // boorstrap
            TrpcBootstrap.export(serviceConfig);
//            try {
//                transporter.start(new URI("http://127.0.0.1:8080/"));
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
        }

        if(bean.getClass().equals(RegistryConfig.class)){
            RegistryConfig config = (RegistryConfig) bean;
            System.out.println("证明成功的加载了配置文件并创建bean"+ config.getAddress());
        }
        return bean;
    }
}
