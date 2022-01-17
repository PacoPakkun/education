package repo.database;

import domain.Participant;
import domain.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repo.interfaces.ProbaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProbaRepositoryDB implements ProbaRepository<Integer, Proba> {
    private final static Logger logger = LogManager.getLogger(ParticipantRepositoryDB.class);
    private String url;

    public ProbaRepositoryDB(String url) {
        this.url = url;
    }

    @Override
    public Optional<Proba> findOne(Integer id) throws SQLException {
        logger.traceEntry();
        Optional<Proba> p = Optional.empty();
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Probe where id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String stil = resultSet.getString("stil");
                int distanta = resultSet.getInt("distanta");
                p = Optional.of(new Proba(id, distanta, stil));
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(p.get());
        return p;
    }

    @Override
    public Iterable<Proba> findAll() throws SQLException {
        logger.traceEntry();
        Connection connection = DriverManager.getConnection(url);
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
    public Optional<Proba> add(Proba entity) throws SQLException {
        logger.traceEntry("Adding task {} ", entity);
        Optional<Proba> p = Optional.of(entity);
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Probe (distanta, stil) values (?,?)")) {
            preStmt.setInt(1, entity.getDistanta());
            preStmt.setString(2, entity.getStil());
            int result = preStmt.executeUpdate();
            logger.trace("Added {} instance", result);
            p = Optional.empty();
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return p;
    }

    @Override
    public Optional<Proba> delete(Integer id) throws SQLException {
        logger.traceEntry();
        Optional<Proba> p = findOne(id);
        Connection connection = DriverManager.getConnection(url);
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
    public Optional<Proba> update(Proba entity) throws SQLException {
        logger.traceEntry();
        Optional<Proba> p = findOne(entity.getId());
        Connection connection = DriverManager.getConnection(url);
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
