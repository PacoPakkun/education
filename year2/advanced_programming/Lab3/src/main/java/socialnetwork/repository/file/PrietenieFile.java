package socialnetwork.repository.file;

import socialnetwork.domain.Entity;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.validators.Validator;

import java.util.List;
import java.util.Optional;

public class PrietenieFile extends AbstractFileRepository<Tuple<Long, Long>, Prietenie> {

    public PrietenieFile(String fileName, Validator<Prietenie> validator) throws Exception {
        super(fileName, validator);
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) throws Exception {
        if (attributes.size() != 2)
            throw new Exception("fisier corupt");
        Prietenie p = new Prietenie(new Tuple(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1))));
        return p;
    }

    @Override
    protected String createEntityAsString(Prietenie p) {
        return "" + p.getId();
    }
}
