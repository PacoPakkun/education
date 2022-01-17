package repo.database;

import domain.Inscriere;
import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repo.interfaces.InscriereRepository;
import utils.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InscriereRepositoryDB implements InscriereRepository<Pair<Integer, Integer>, Inscriere> {
    private final static Logger logger = LogManager.getLogger(InscriereRepositoryDB.class);
    private String url;

    public InscriereRepositoryDB(String url) {
        this.url = url;
    }

    @Override
    public Optional<Inscriere> findOne(Pair<Integer, Integer> id) throws SQLException {
        logger.traceEntry();
        Optional<Inscriere> i = Optional.empty();
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Inscrieri where id_participant=? and id_proba=?")) {
            preparedStatement.setInt(1, id.getE1());
            preparedStatement.setInt(2, id.getE2());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                i = Optional.of(new Inscriere(id));
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(i.get());
        return i;
    }

    @Override
    public Iterable<Inscriere> findAll() throws SQLException {
        logger.traceEntry();
        Connection connection = DriverManager.getConnection(url);
        List<Inscriere> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Inscrieri")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id_participant = resultSet.getInt("id_participant");
                    int id_proba = resultSet.getInt("id_proba");
                    Inscriere i = new Inscriere(new Pair<>(id_participant, id_proba));
                    list.add(i);
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
    public Optional<Inscriere> add(Inscriere entity) throws SQLException {
        logger.traceEntry("Adding task {} ", entity);
        Optional<Inscriere> i = Optional.of(entity);
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Inscrieri (id_participant, id_proba) values (?,?)")) {
            preStmt.setInt(1, entity.getId().getE1());
            preStmt.setInt(2, entity.getId().getE2());
            int result = preStmt.executeUpdate();
            logger.trace("Added {} instance", result);
            i = Optional.empty();
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return i;
    }

    @Override
    public Optional<Inscriere> delete(Pair<Integer, Integer> id) throws SQLException {
        logger.traceEntry();
        Optional<Inscriere> i = findOne(id);
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from Inscrieri where id_participant=? and id_proba=?")) {
            preparedStatement.setInt(1, id.getE1());
            preparedStatement.setInt(2, id.getE2());
            preparedStatement.executeUpdate();
            logger.trace("Removed {} instance", i);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit();
        return i;
    }

    @Override
    public Optional<Inscriere> update(Inscriere entity) throws SQLException {
        logger.traceEntry();
        Optional<Inscriere> i = findOne(entity.getId());
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Inscrieri set id_participant=?,id_proba=? where id_participant=? and id_proba=?")) {
            preparedStatement.setInt(1, entity.getId().getE1());
            preparedStatement.setInt(2, entity.getId().getE2());
            preparedStatement.setInt(3, entity.getId().getE1());
            preparedStatement.setInt(4, entity.getId().getE2());
            preparedStatement.executeUpdate();
            logger.trace("Updated {} instance", i);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit();
        return i;
    }

    @Override
    public Iterable<Participant> getInscrisi(Proba proba) throws SQLException {
        logger.traceEntry();
        Connection connection = DriverManager.getConnection(url);
        List<Participant> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select P.id, P.nume, P.varsta from Inscrieri I inner join Participanti P on I.id_participant=P.id where I.id_proba=?")) {
            preparedStatement.setInt(1, proba.getId());
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
    public Integer nrParticipanti(Proba proba) throws SQLException {
        logger.traceEntry();
        int nr = 0;
        Connection connection = DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(P.id) as nr from Inscrieri I inner join Participanti P on I.id_participant=P.id where I.id_proba=?")) {
            preparedStatement.setInt(1, proba.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    nr = resultSet.getInt("nr");
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(nr);
        return nr;
    }
}
