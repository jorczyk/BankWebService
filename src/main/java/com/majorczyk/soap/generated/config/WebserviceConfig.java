package com.majorczyk.soap.generated.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Configuration for web service
 */
@EnableWs
@Configuration
public class WebserviceConfig extends WsConfigurerAdapter {

    @Value("${soap.schema}")
    private String schema;
    @Value("${soap.namespace}")
    private String namespace;

    /**
     * Sets endpoints address
     * @param context
     * @return
     */
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context){
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(messageDispatcherServlet,"/ws/*");
    }

    /**
     * Configures port name and namespace for web services
     * @param schema
     * @return
     */
    @Bean(name = "account")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("AccountPort");
        definition.setTargetNamespace("com/majorczyk/soap/account");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace(namespace);
        definition.setSchema(schema);
        return definition;
    }

    /**
     * Configures xsd schema
     * @return
     */
    @Bean
    public XsdSchema schema(){
        return new SimpleXsdSchema(new ClassPathResource(schema));
    }
}
