package socialnetwork.repository.database;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PrietenieDbRepository implements Repository<Tuple<Long, Long>, Prietenie> {
    private String url;
    private String username;
    private String password;
    private Validator<Prietenie> validator;

    public PrietenieDbRepository(String url, String username, String password, Validator<Prietenie> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Prietenie> findOne(Tuple<Long, Long> aLong) {
        Optional<Prietenie> o = Optional.empty();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id1, id2, prietenii.date from prietenii");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                Date date = resultSet.getDate("date");
                Tuple<Long, Long> id = new Tuple(id1, id2);
                if (id.equals(aLong)) {
                    Prietenie p = new Prietenie(id);
                    p.setDate(date);
                    o = Optional.of(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> prietenii = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from prietenii");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                Date date = resultSet.getDate("date");

                Prietenie p = new Prietenie(new Tuple(id1, id2));
                p.setDate(date);
                prietenii.add(p);
            }
            return prietenii;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prietenii;
    }

    @Override
    public Optional<Prietenie> save(Prietenie entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null");
        this.validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO prietenii(id1, id2, date) VALUES (?,?,?)");
        ) {
            statement.setLong(1, entity.getId().getLeft());
            statement.setLong(2, entity.getId().getRight());
            statement.setDate(3, entity.getDate());
            statement.executeUpdate();
            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<Prietenie> delete(Tuple<Long, Long> aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must not be null");
        Optional<Prietenie> o = findOne(aLong);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM prietenii WHERE (id1=? AND id2=?) OR (id2=? AND id1=?) ");
        ) {

            statement.setLong(1, aLong.getLeft());
            statement.setLong(2, aLong.getRight());
            statement.setLong(3, aLong.getLeft());
            statement.setLong(4, aLong.getRight());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public Optional<Prietenie> update(Prietenie entity) {
        return Optional.empty();
    }

}
