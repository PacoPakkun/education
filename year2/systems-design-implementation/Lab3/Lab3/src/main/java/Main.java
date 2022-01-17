import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import domain.User;
import repo.database.InscriereRepositoryDB;
import repo.database.ParticipantRepositoryDB;
import repo.database.ProbaRepositoryDB;
import repo.database.UserRepositoryDB;
import utils.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/config.properties"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        final String url = props.getProperty("database.url");
        if (url == null)
            System.out.println("error");

        ParticipantRepositoryDB repo = new ParticipantRepositoryDB(url);
        ProbaRepositoryDB repo2 = new ProbaRepositoryDB(url);
        InscriereRepositoryDB repo3 = new InscriereRepositoryDB(url);
        UserRepositoryDB repo4 = new UserRepositoryDB(url);
        repo.findAll().forEach(System.out::println);
        repo2.findAll().forEach(System.out::println);
        repo3.findAll().forEach(System.out::println);
        repo4.findAll().forEach(System.out::println);

//        repo.add(new Participant(null, "test",10));
//        repo2.add(new Proba(null, 10,"test"));
//        repo3.add(new Inscriere(new Pair<>(1,1)));
//        repo4.add(new User(null, "test","test"));
//        repo.update(new Participant(9, "tezt",10));
//        repo2.update(new Proba(19, 10,"tezt"));
//        repo3.update(new Inscriere(new Pair<>(1,1)));
//        repo4.update(new User(4, "tezt","tezt"));
//        System.out.println(repo.findOne(9).get());
//        System.out.println(repo2.findOne(19).get());
//        System.out.println(repo3.findOne(new Pair<>(1,1)).get());
//        System.out.println(repo4.findOne(4).get());
//        repo.delete(9);
//        repo2.delete(19);
//        repo3.delete(new Pair<>(1,1));
//        repo4.delete(4);
//        repo.findAll().forEach(System.out::println);
//        repo2.findAll().forEach(System.out::println);
//        repo3.findAll().forEach(System.out::println);
//        repo4.findAll().forEach(System.out::println);
//        repo3.getInscrisi(new Proba(1,null,null)).forEach(System.out::println);
//        System.out.println(repo3.nrParticipanti(new Proba(1,null,null)));
    }
}