package repo.database;

import domain.User;
import domain.Proba;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repo.interfaces.ProbaRepository;
import repo.interfaces.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryDB implements UserRepository<Integer, User> {
    private final static Logger logger = LogManager.getLogger(UserRepositoryDB.class);
    private String url;

    public UserRepositoryDB(String url) {
        this.url = url;
    }

    @Override
    public Optional<User> findOne(Integer id) throws SQLException {
        logger.traceEntry();
        Optional<User> u = Optional.empty();
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Useri where id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                u = Optional.of(new User(id, username, password));
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(u.get());
        return u;
    }

    @Override
    public Iterable<User> findAll() throws SQLException {
        logger.traceEntry();
        Connection connection = DriverManager.getConnection(url);
        List<User> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Useri")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User u = new User(id, username, password);
                    list.add(u);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(list);
        return list;
    }

    @Override
    public Optional<User> add(User entity) throws SQLException {
        logger.traceEntry("Adding task {} ", entity);
        Optional<User> u = Optional.of(entity);
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Useri (username, password) values (?,?)")) {
            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPassword());
            int result = preStmt.executeUpdate();
            logger.trace("Added {} instance", result);
            u = Optional.empty();
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return u;
    }

    @Override
    public Optional<User> delete(Integer id) throws SQLException {
        logger.traceEntry();
        Optional<User> u = findOne(id);
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Useri where id=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.trace("Removed {} instance", u);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit();
        return u;
    }

    @Override
    public Optional<User> update(User entity) throws SQLException {
        logger.traceEntry();
        Optional<User> u = findOne(entity.getId());
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Useri set username=?,password=? where id=?")) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
            logger.trace("Updated {} instance", u);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit();
        return u;
    }
}
