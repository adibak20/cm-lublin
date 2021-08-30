package pl.cm.core.common;

import java.util.Set;


public abstract class AbstractMapper<T,U> {

    public abstract T entityToDto(U u);
    public abstract U dtoToEntity(T t);
    public abstract Set<T> entitySetToDtoSet(Set<U> u);
    public abstract Set<U> dtoSetToEntitySet(Set<T> tSet);

}
