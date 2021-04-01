package socialnetwork.repository.file;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.memory.InMemoryRepository;
import socialnetwork.repository.memory.InMemoryRepository0;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    String fileName;

    public AbstractFileRepository(String fileName, Validator<E> validator) throws Exception {
        super(validator);
        this.fileName = fileName;
        loadData();

    }

    private void loadData() throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                List<String> attr = Arrays.asList(linie.split(";"));
                E e = extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * extract entity  - template method design pattern
     * creates an entity of type E having a specified list of @code attributes
     *
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes) throws Exception;
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    protected void writeToFile(E entity) {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName, true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String createEntityAsString(E entity);

    protected void clearFile() {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName, false))) {
            bW.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<E> save(E entity) {
        Optional<E> e = super.save(entity);
        if (!e.isPresent()) {
            writeToFile(entity);
        }
        return e;
    }

    @Override
    public Optional<E> delete(ID id) {
        Optional<E> e = super.delete(id);
        if (e.isPresent()) {
            clearFile();
            for (E entity : super.findAll()) {
                writeToFile(entity);
            }
        }
        return e;
    }

    @Override
    public Optional<E> update(E entity) {
        Optional<E> e = super.update(entity);
        if (e == null) {
            clearFile();
            for (E ent : super.findAll()) {
                writeToFile(ent);
            }
        }
        return e;
    }
}

