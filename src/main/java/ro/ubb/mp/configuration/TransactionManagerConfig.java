package ro.ubb.mp.configuration;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;

@Configuration
public class TransactionManagerConfig {
    @Bean(name = "transactionManager")
    public JtaTransactionManager transactionManager() throws SystemException {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransactionImp userTransaction = new UserTransactionImp();
        userTransaction.setTransactionTimeout(10000);

        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}