package validator;

public interface Validator<E> {
    void validate(E entity) throws Exception;
}
