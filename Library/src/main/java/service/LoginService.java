package service;

import model.Employee;
import model.Subscriber;
import persistancy.account.IEmployeeRepo;
import persistancy.account.ISubscriberRepo;

public class LoginService {
    private final IEmployeeRepo employeeRepo;
    private final ISubscriberRepo subscriberRepo;

    public LoginService(IEmployeeRepo employeeRepo, ISubscriberRepo subscriberRepo) {
        this.employeeRepo = employeeRepo;
        this.subscriberRepo = subscriberRepo;
    }

    public Object Login(String username, String password, UserType userType) {
        switch (userType) {
            case EMPLOYEE -> {
                Employee e = employeeRepo.Find(new Employee(username));
                if (e == null) {
                    return null;
                }
                return e.getPassword().equals(password) ? e : null;
            }
            case SUBSCRIBER -> {
                Subscriber s = subscriberRepo.Find(new Subscriber(username));
                if (s == null) {
                    return false;
                }
                return s.getPassword().equals(password) ? s : null;
            }
            default -> {
                return null;
            }
        }
    }
}
