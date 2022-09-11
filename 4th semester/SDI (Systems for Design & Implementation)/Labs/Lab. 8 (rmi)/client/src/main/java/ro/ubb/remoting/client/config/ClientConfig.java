package ro.ubb.remoting.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ro.ubb.remoting.client.service.ServiceClient;
import ro.ubb.remoting.client.view.Console;
import ro.ubb.remoting.common.BookService;
import ro.ubb.remoting.common.CustomerService;
import ro.ubb.remoting.common.Service;
import ro.ubb.remoting.common.TransactionService;

@Configuration
public class ClientConfig {
    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanCustomer() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(CustomerService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/customerService");
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanBook() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(BookService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/bookService");
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBeanTransaction() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(TransactionService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/transactionService");
        return rmiProxyFactoryBean;
    }

    @Bean
    Console console() {
        return new Console();
    }
}
