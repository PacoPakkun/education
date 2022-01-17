package repo.db;

import domain.Participant;
import domain.validator.ParticipantValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repo.intf.ParticipantRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantRepositoryDB implements ParticipantRepository {

    private final static Logger logger = LogManager.getLogger(ParticipantRepositoryDB.class);
    private String url;
    private ParticipantValidator validator;

    public ParticipantRepositoryDB(Properties props, ParticipantValidator validator) {
        this.url = props.getProperty("db.url");
        this.validator = validator;
    }

    @Override
    public Participant findOne(Integer id) {
        logger.traceEntry();
        Participant p = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Participanti where id=?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                int varsta = resultSet.getInt("varsta");
                p = new Participant(id, nume, varsta);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(p);
        return p;
    }

    @Override
    public List<Participant> findAll() {
        logger.traceEntry();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public Participant add(Participant entity) throws Exception {
        validator.validate(entity);
        logger.traceEntry("Adding task {} ", entity);
        Participant p = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Participanti (nume, varsta) values (?,?)")) {
            preStmt.setString(1, entity.getNume());
            preStmt.setInt(2, entity.getVarsta());
            int result = preStmt.executeUpdate();
            logger.trace("Added {} instance", result);
            p = entity;
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("select max(P.id) as id from Participanti P")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    p.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit();
        return p;
    }

    @Override
    public Participant delete(Integer id) {
        logger.traceEntry();
        Participant p = findOne(id);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public Participant update(Participant entity) throws Exception {
        validator.validate(entity);
        logger.traceEntry();
        Participant p = findOne(entity.getId());
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
