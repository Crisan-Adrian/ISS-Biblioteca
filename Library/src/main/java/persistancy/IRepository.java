package persistancy;

public interface IRepository<T> {
    public T Save(T entity);
    public boolean Update(T entity);
    public boolean Delete(T entity);
    public T Find(T entity);
}
