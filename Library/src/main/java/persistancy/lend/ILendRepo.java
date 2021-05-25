package persistancy.lend;

import model.Employee;
import model.Lend;
import persistancy.IRepository;

import java.util.List;

public interface ILendRepo extends IRepository<Lend> {
    public List<Long> FindActiveLends();
    public Lend FindBookLends(Long bookID);
}
