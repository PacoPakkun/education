package socialnetwork.repository.database;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UtilizatorDbRepository implements Repository<Long, Utilizator> {
    public String url;
    public String username;
    public String password;
    private Validator<Utilizator> validator;

    public UtilizatorDbRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Utilizator> findOne(Long aLong) {
        Optional<Utilizator> o = Optional.empty();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id,nume,prenume,hash,pic from utilizatori");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String hash = resultSet.getString("hash");
                String pic = resultSet.getString("pic");
                if (id == aLong) {
                    Utilizator u = new Utilizator(nume, prenume);
                    u.setId(id);
                    u.setHash(hash);
                    u.setPic(pic);
                    o = Optional.of(u);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id, nume, prenume, hash, pic from utilizatori");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("nume");
                String lastName = resultSet.getString("prenume");
                String hash = resultSet.getString("hash");
                String pic = resultSet.getString("pic");
                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id);
                utilizator.setHash(hash);
                utilizator.setPic(pic);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Set<Utilizator> findFriends(Long id) {
        Set<Utilizator> friends = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT u1.id as id1,u2.id as id2,u1.nume as nume1,u1.prenume as prenume1,u2.nume as nume2,u2.prenume as prenume2,u1.pic as pic1,u2.pic as pic2 from utilizatori u1 inner join prietenii p on u1.id=p.id1 inner join utilizatori u2 on p.id2=u2.id");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long userid = resultSet.getLong("id1");
                if (id == userid) {
                    Long uid = resultSet.getLong("id2");
                    String firstName = resultSet.getString("nume2");
                    String lastName = resultSet.getString("prenume2");
                    String pic = resultSet.getString("pic2");
                    Utilizator utilizator = new Utilizator(firstName, lastName);
                    utilizator.setId(uid);
                    utilizator.setPic(pic);
                    friends.add(utilizator);
                }
                userid = resultSet.getLong("id2");
                if (id == userid) {
                    Long uid = resultSet.getLong("id1");
                    String firstName = resultSet.getString("nume1");
                    String lastName = resultSet.getString("prenume1");
                    String pic = resultSet.getString("pic1");
                    Utilizator utilizator = new Utilizator(firstName, lastName);
                    utilizator.setId(uid);
                    utilizator.setPic(pic);
                    friends.add(utilizator);
                }
            }
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    public Optional<Utilizator> save(Utilizator entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null");
        this.validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO utilizatori(id, nume, prenume, hash, pic) VALUES (?,?,?,?,?)");
        ) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getHash());
            statement.setString(5, entity.getPic());
            statement.executeUpdate();
            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<Utilizator> delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must not be null");
        Optional<Utilizator> o = findOne(aLong);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM utilizatori WHERE id=? ");
        ) {

            statement.setLong(1, aLong);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public Optional<Utilizator> update(Utilizator entity) {
        return Optional.empty();
    }

    public Optional<Utilizator> lastLogin() {
        Optional<Utilizator> o = Optional.empty();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id,nume,prenume from utilizatori u inner join login l on u.id=l.id_user");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                Utilizator u = new Utilizator(nume, prenume);
                u.setId(id);
                o = Optional.of(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    public void setLogin(Utilizator u) {
        if (u == null) {
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM login");
            ) {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {

            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO login(id_user) VALUES (?)");
            ) {
                statement.setLong(1, u.getId());
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public long nextId() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) as max from utilizatori");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                return resultSet.getLong("max") + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
