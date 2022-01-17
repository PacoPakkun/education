package repo.database;

import domain.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repo.interfaces.ParticipantRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticipantRepositoryDB implements ParticipantRepository {

    private final static Logger logger = LogManager.getLogger(ParticipantRepositoryDB.class);
    private String url;

    public ParticipantRepositoryDB(String url) {
        this.url = url;
    }

    @Override
    public Optional<Participant> findOne(Integer id) throws SQLException {
        logger.traceEntry();
        Optional<Participant> p = Optional.empty();
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Participanti where id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                int varsta = resultSet.getInt("varsta");
                p = Optional.of(new Participant(id, nume, varsta));
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(p.get());
        return p;
    }

    @Override
    public Iterable<Participant> findAll() throws SQLException {
        logger.traceEntry();
        Connection connection = DriverManager.getConnection(url);
        List<Participant> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Participanti")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nume = resultSet.getString("nume");
                    int varsta = resultSet.getInt("varsta");
                    Participant p = new Participant(id, nume, varsta);
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
    public Optional<Participant> add(Participant entity) throws SQLException {
        logger.traceEntry("Adding task {} ", entity);
        Optional<Participant> p = Optional.of(entity);
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Participanti (nume, varsta) values (?,?)")) {
            preStmt.setString(1, entity.getNume());
            preStmt.setInt(2, entity.getVarsta());
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
    public Optional<Participant> delete(Integer id) throws SQLException {
        logger.traceEntry();
        Optional<Participant> p = findOne(id);
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Participanti where id=?")) {
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
    public Optional<Participant> update(Participant entity) throws SQLException {
        logger.traceEntry();
        Optional<Participant> p = findOne(entity.getId());
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Participanti set nume=?,varsta=? where id=?")) {
            preparedStatement.setString(1, entity.getNume());
            preparedStatement.setInt(2, entity.getVarsta());
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
