package socialnetwork.repository.file;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;

import java.util.List;
import java.util.Optional;

public class UtilizatorFile extends AbstractFileRepository<Long, Utilizator> {

    public UtilizatorFile(String fileName, Validator<Utilizator> validator) throws Exception {
        super(fileName, validator);
    }

    @Override
    public Utilizator extractEntity(List<String> attributes) throws Exception {
        if (attributes.size() != 3)
            throw new Exception("fisier corupt");
        Utilizator user = new Utilizator(attributes.get(1), attributes.get(2));
        user.setId(Long.parseLong(attributes.get(0)));

        return user;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getId() + ";" + entity.getFirstName() + ";" + entity.getLastName();
    }

    public void addPrietenie(Prietenie p) {
        for (Utilizator user : super.findAll()) {
            if(user.getId()==p.getId().getLeft()){
                Optional<Utilizator> o = findOne(p.getId().getRight());
                if(o.isPresent())
                    user.addFriend(o.get());
            }
            if(user.getId()==p.getId().getRight()){
                Optional<Utilizator> o = findOne(p.getId().getLeft());
                if(o.isPresent())
                    user.addFriend(o.get());
            }
        }
    }

    public void deletePrietenie(Prietenie p) {
        for (Utilizator user : super.findAll()) {
            if(user.getId()==p.getId().getLeft()){
                user.deleteFriend(p.getId().getRight());
            }
            if(user.getId()==p.getId().getRight()){
                user.deleteFriend(p.getId().getLeft());
            }
        }
    }
}
