package models.interfaces;

import java.util.List;

public interface Repository<T, ID> {
    void inserir(T entidade);
    T read(ID id);
    void update(T entidade);
    void delete(T entidade);
    List<T> readAll();
}
