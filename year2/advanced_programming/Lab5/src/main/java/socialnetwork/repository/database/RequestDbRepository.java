package socialnetwork.repository.database;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Request;
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

public class RequestDbRepository implements Repository<Tuple<Long, Long>, Request> {
    private String url;
    private String username;
    private String password;
    private Validator<Prietenie> validator;

    public RequestDbRepository(String url, String username, String password, Validator<Prietenie> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Request> findOne(Tuple<Long, Long> aLong) {
        Optional<Request> o = Optional.empty();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id1, id2, status from cereri");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String status = resultSet.getString("status");
                Tuple<Long, Long> id = new Tuple(id1, id2);
                if (id.equals(aLong)) {
                    Request p = new Request(id, status);
                    o = Optional.of(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public Iterable<Request> findAll() {
        Set<Request> cereri = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from cereri");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String status = resultSet.getString("status");

                Request p = new Request(new Tuple(id1, id2), status);
                cereri.add(p);
            }
            return cereri;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cereri;
    }

    @Override
    public Optional<Request> save(Request entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null");
        //this.validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO cereri(id1, id2, status) VALUES (?,?,?)");
        ) {
            statement.setLong(1, entity.getId().getLeft());
            statement.setLong(2, entity.getId().getRight());
            statement.setString(3, entity.getStatus());
            statement.executeUpdate();
            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(entity);
    }

    @Override
    public Optional<Request> delete(Tuple<Long, Long> aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must not be null");
        Optional<Request> o = findOne(aLong);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM cereri WHERE (id1=? AND id2=?) OR (id2=? AND id1=?) ");
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
    public Optional<Request> update(Request entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null");
        Optional<Request> o = findOne(entity.getId());
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE cereri SET status=? WHERE (id1=? AND id2=?) OR (id2=? AND id1=?)");
        ) {
            statement.setString(1,entity.getStatus());
            statement.setLong(2, entity.getId().getLeft());
            statement.setLong(3, entity.getId().getRight());
            statement.setLong(4, entity.getId().getLeft());
            statement.setLong(5, entity.getId().getRight());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

}
