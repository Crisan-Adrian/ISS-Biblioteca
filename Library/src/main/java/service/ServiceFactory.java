package service;

import persistancy.account.ISubscriberRepo;
import persistancy.book.IBookRepo;
import persistancy.lend.ILendRepo;

public class ServiceFactory {
    private final ILendRepo lendRepo;
    private final IBookRepo bookRepo;
    private final ISubscriberRepo subscriberRepo;

    public ServiceFactory(ILendRepo lendRepo, IBookRepo bookRepo, ISubscriberRepo subscriberRepo) {
        this.lendRepo = lendRepo;
        this.bookRepo = bookRepo;
        this.subscriberRepo = subscriberRepo;
    }

    public Object GetService(UserType type){
        return switch (type) {
            case SUBSCRIBER -> new SubscriberService(lendRepo, bookRepo);
            case EMPLOYEE -> new EmployeeService(lendRepo, subscriberRepo);
        };
    }
}
