package service;

import model.Lend;
import model.Subscriber;
import persistancy.account.ISubscriberRepo;
import persistancy.lend.ILendRepo;

import java.time.LocalDate;

public class EmployeeService {
    private final ILendRepo lendRepo;
    private final ISubscriberRepo subscriberRepo;

    public EmployeeService(ILendRepo lendRepo, ISubscriberRepo subscriberRepo) {
        this.lendRepo = lendRepo;
        this.subscriberRepo = subscriberRepo;
    }

    public void ApplyPenalty(long id, String penalty) throws LibraryException {
        Lend lend = lendRepo.FindBookLends(id);
        if(lend == null)
        {
            throw new LibraryException("Book is not lent");
        }
        Subscriber subscriber = new Subscriber(lend.getSubscriber());

        subscriber = subscriberRepo.Find(subscriber);
        subscriber.setPenalties(subscriber.getPenalties()+"\n"+penalty);
        subscriberRepo.Update(subscriber);

    }

    public LocalDate GetLend(long id) throws LibraryException {
        Lend lend = lendRepo.FindBookLends(id);
        if(lend == null)
        {
            throw new LibraryException("Book is not lent");
        }
        return lend.getEnd();
    }

    public void ReturnLend(long id) throws LibraryException {
        Lend lend = lendRepo.FindBookLends(id);
        if(lend == null)
        {
            throw new LibraryException("Book is not lent");
        }
        lend.setReturned(true);
        lendRepo.Update(lend);
    }

    public void ExtendLend(long id, LocalDate newReturnDate) throws LibraryException {
        Lend lend = lendRepo.FindBookLends(id);
        if(lend == null)
        {
            throw new LibraryException("Book is not lent");
        }
        lend.setEnd(newReturnDate);
        lendRepo.Update(lend);
    }
}
