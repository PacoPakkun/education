package repo.db;

import domain.Inscriere;
import domain.Participant;
import domain.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repo.intf.InscriereRepository;
import utils.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InscriereRepositoryDB implements InscriereRepository {
    private final static Logger logger = LogManager.getLogger(InscriereRepositoryDB.class);
    private String url;

    public InscriereRepositoryDB(Properties props) {
        this.url = props.getProperty("db.url");
    }

    @Override
    public Inscriere findOne(Pair<Integer, Integer> id) {
        logger.traceEntry();
        Inscriere i = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Participanti P inner join Inscrieri I on P.id=I.id_participant inner join Probe PR on PR.id=I.id_proba where I.id_participant=? and I.id_proba=?")) {
            preparedStatement.setInt(1, id.getE1());
            preparedStatement.setInt(2, id.getE2());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id_participant = resultSet.getInt("id_participant");
                String nume = resultSet.getString("nume");
                int varsta = resultSet.getInt("varsta");
                Participant participant = new Participant(id_participant, nume, varsta);
                int id_proba = resultSet.getInt("id_proba");
                String stil = resultSet.getString("stil");
                int distanta = resultSet.getInt("distanta");
                Proba proba = new Proba(id_proba, distanta, stil);
                i = new Inscriere(id_participant, id_proba, participant, proba);
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(i);
        return i;
    }

    @Override
    public List<Inscriere> findAll() {
        logger.traceEntry();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Inscriere> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Participanti P inner join Inscrieri I on P.id=I.id_participant inner join Probe PR on PR.id=I.id_proba")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id_participant = resultSet.getInt("id_participant");
                    String nume = resultSet.getString("nume");
                    int varsta = resultSet.getInt("varsta");
                    Participant participant = new Participant(id_participant, nume, varsta);
                    int id_proba = resultSet.getInt("id_proba");
                    String stil = resultSet.getString("stil");
                    int distanta = resultSet.getInt("distanta");
                    Proba proba = new Proba(id_proba, distanta, stil);
                    Inscriere i = new Inscriere(id_participant, id_proba, participant, proba);
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
    public Inscriere add(Inscriere entity) {
        logger.traceEntry("Adding task {} ", entity);
        Inscriere i = entity;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Inscrieri (id_participant, id_proba) values (?,?)")) {
            preStmt.setInt(1, entity.getId_participant());
            preStmt.setInt(2, entity.getId_proba());
            int result = preStmt.executeUpdate();
            logger.trace("Added {} instance", result);
            i = null;
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return i;
    }

    @Override
    public Inscriere delete(Pair<Integer, Integer> id) {
        logger.traceEntry();
        Inscriere i = findOne(id);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public Inscriere update(Inscriere entity) {
        logger.traceEntry();
        Inscriere i = findOne(new Pair<>(entity.getId_participant(),entity.getId_proba()));
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement("update Inscrieri set id_participant=?,id_proba=? where id_participant=? and id_proba=?")) {
            preparedStatement.setInt(1, entity.getId_participant());
            preparedStatement.setInt(2, entity.getId_proba());
            preparedStatement.setInt(3, entity.getId_participant());
            preparedStatement.setInt(4, entity.getId_proba());
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
    public List<Participant> getInscrisi(Proba proba) {
        logger.traceEntry();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public Integer nrParticipanti(Proba proba) {
        logger.traceEntry();
        int nr = 0;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    @Override
    public List<Proba> getInscrieri(Participant participant) {
        logger.traceEntry();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Proba> list = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select P.id, P.distanta, P.stil from Inscrieri I inner join Probe P on I.id_proba=P.id where I.id_participant=?")) {
            preparedStatement.setInt(1, participant.getId());
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
}
