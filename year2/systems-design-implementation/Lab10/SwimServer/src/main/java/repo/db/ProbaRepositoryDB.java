package repo.db;

import domain.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repo.intf.ProbaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class ProbaRepositoryDB implements ProbaRepository {
    private final static Logger logger = LogManager.getLogger(ProbaRepositoryDB.class);
    private String url;

    @Autowired
    public ProbaRepositoryDB(Properties props) {
        this.url = props.getProperty("db.url");
    }

    @Override
    public Proba findOne(Integer id) throws Exception {
        logger.traceEntry();
        Proba p = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Probe where id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String stil = resultSet.getString("stil");
                int distanta = resultSet.getInt("distanta");
                p = new Proba(id, distanta, stil);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(p);
        if (p == null) {
            throw new Exception("not found");
        }
        return p;
    }

    @Override
    public List<Proba> findAll() {
        logger.traceEntry();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Proba> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Probe")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String stil = resultSet.getString("stil");
                    int distanta = resultSet.getInt("distanta");
                    Proba p = new Proba(id, distanta, stil);
                    list.add(p);
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
    public Proba add(Proba entity) {
        logger.traceEntry("Adding task {} ", entity);
        Proba p = entity;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Probe (distanta, stil) values (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preStmt.setInt(1, entity.getDistanta());
            preStmt.setString(2, entity.getStil());
            int result = preStmt.executeUpdate();
            logger.trace("Added {} instance", result);

            ResultSet rs = preStmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                p.setId(id);
                logger.trace("Generated id {} ", id);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return p;
    }

    @Override
    public Proba delete(Integer id) throws Exception {
        logger.traceEntry();
        Proba p = findOne(id);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Probe where id=?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            logger.trace("Removed {} instance", p);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit();
        return p;
    }

    @Override
    public Proba update(Proba entity) throws Exception {
        logger.traceEntry();
        Proba p = findOne(entity.getId());
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Probe set distanta=?,stil=? where id=?")) {
            preparedStatement.setInt(1, entity.getDistanta());
            preparedStatement.setString(2, entity.getStil());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
            logger.trace("Updated {} instance", p);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit();
        return p;
    }
}
