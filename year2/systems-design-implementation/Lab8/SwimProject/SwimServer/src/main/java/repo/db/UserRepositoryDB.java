package repo.db;

import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repo.intf.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepositoryDB implements UserRepository {
    private final static Logger logger = LogManager.getLogger(UserRepositoryDB.class);
    private String url;

    public UserRepositoryDB(Properties props) {
        this.url = props.getProperty("db.url");
    }

    @Override
    public User findOne(Integer id){
        logger.traceEntry();
        User u = null;
        Connection connection = null;try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Useri where id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                u = new User(id, username, password);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(u);
        return u;
    }

    @Override
    public List<User> findAll(){
        logger.traceEntry();
        Connection connection = null;try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public User add(User entity){
        logger.traceEntry("Adding task {} ", entity);
        User u = entity;
        Connection connection = null;try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Useri (username, password) values (?,?)")) {
            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPassword());
            int result = preStmt.executeUpdate();
            logger.trace("Added {} instance", result);
            u = null;
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return u;
    }

    @Override
    public User delete(Integer id){
        logger.traceEntry();
        User u = findOne(id);
        Connection connection = null;try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public User update(User entity){
        logger.traceEntry();
        User u = findOne(entity.getId());
        Connection connection = null;try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
