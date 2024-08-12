package com.msp.spring.service;

import com.msp.spring.database.entity.Company;
import com.msp.spring.database.pool.ConnectionPool;
import com.msp.spring.database.repository.CrudRepository;
import com.msp.spring.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE/*, proxyMode = ScopedProxyMode.INTERFACES*/) //"prototype"
//  proxyMode вернет прокси объект и возникнет ошибка создания CompanyService
// Unsatisfied dependency expressed through method 'setUserService' parameter 0; nested exception is org.springframework.beans.factory.BeanNotOfRequiredTypeException:
// Bean named 'userService' is expected to be of type 'com.msp.spring.service.UserService' but was actually of type 'com.sun.proxy.$Proxy16'
public class UserService {

    private final UserRepository userRepository;
    /*
     Ошибка
        Bean named 'companyRepository' is expected to be of type 'com.msp.spring.database.repository.CompanyRepository' but was actually of type 'com.sun.proxy.$Proxy15'
     Поэтому используем интерфейс
     */
    private final /*CompanyRepository*/ CrudRepository<Integer, Company> companyRepository;

    private final CompanyService companyService;

    public UserService() {
        // UserService управляет созданием XXXRepository и ConnectionPool, что плохо
        // Также могут быть другие классы, требующие эти объекты
        userRepository = new UserRepository(new ConnectionPool());
        companyRepository = null; //new CompanyRepository(new ConnectionPool());
        companyService = null;
    }

    @Autowired
    public UserService(UserRepository userRepository,
                       CrudRepository<Integer, Company> companyRepository,
                       CompanyService companyService) {
        // зависимоти должны передаваться в конструктор
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    @Override
    public String toString() {
        return "UserService class initialized.\n" +
                userRepository.toString() + "\n" +
                (companyRepository != null ? companyRepository.toString() : null);
    }
}
