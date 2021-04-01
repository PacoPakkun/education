package socialnetwork.repository.database;

import socialnetwork.domain.Eveniment;
import socialnetwork.domain.Message;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class EventDbRepository implements Repository<Long, Eveniment> {
    private String url;
    private String username;
    private String password;
    private Validator<Eveniment> validator;

    public EventDbRepository(String url, String username, String password, Validator<Eveniment> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    public long nextId() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) as max from events");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                return resultSet.getLong("max") + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public Optional<Eveniment> findOne(Long id) {
        Optional<Eveniment> o = Optional.empty();
//        try (Connection connection = DriverManager.getConnection(url, username, password);
//             PreparedStatement statement = connection.prepareStatement("SELECT id from mesaje");
//             ResultSet resultSet = statement.executeQuery()) {
//            while (resultSet.next()) {
//                Long idmsg = resultSet.getLong("id");
//                if (id == idmsg) {
//                    Message m = new Message();
//                    o = Optional.of(m);
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return o;
    }

    public Iterable<Eveniment> findUserEvents(Utilizator u) {
        Set<Eveniment> events = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from events e inner join usrevt ue on e.id=ue.id_event inner join utilizatori u on ue.id_user=u.id");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long organizator = resultSet.getLong("organizator");
                Long abonat = resultSet.getLong("id_user");
                if (u.getId() == organizator) {
                    Long id = resultSet.getLong("id");
                    String titlu = resultSet.getString("titlu");
                    String descriere = resultSet.getString("descriere");
                    Date data = resultSet.getDate("data");
                    Eveniment e = new Eveniment(u, titlu, descriere, data);
                    e.setId(id);
                    events.add(e);
                } else if (u.getId() == abonat) {
                    Long id = resultSet.getLong("id");
                    String titlu = resultSet.getString("titlu");
                    String descriere = resultSet.getString("descriere");
                    Date data = resultSet.getDate("data");
                    String nume = resultSet.getString("nume");
                    String prenume = resultSet.getString("prenume");
                    Utilizator user = new Utilizator(abonat, nume, prenume);
                    Eveniment e = new Eveniment(user, titlu, descriere, data);
                    e.setId(id);
                    events.add(e);
                }
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public Iterable<Eveniment> findAll() {
        Set<Eveniment> events = new HashSet<>();
//
        return events;
    }

    @Override
    public Optional<Eveniment> save(Eveniment entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null");
        if (findOne(entity.getId()).isPresent())
            throw new IllegalArgumentException("event already exists!");
        this.validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO events(id, organizator, titlu, descriere, data) VALUES (?,?,?,?,?);");
        ) {
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getOrganizator().getId());
            statement.setString(3, entity.getTitlu());
            statement.setString(4, entity.getDescriere());
            statement.setDate(5, entity.getData());
            statement.executeUpdate();
            PreparedStatement newstatement = connection.prepareStatement("INSERT INTO usrevt(id_event, id_user, notificari) VALUES (?,?,?);");
            newstatement.setLong(1, entity.getId());
            newstatement.setLong(2, entity.getOrganizator().getId());
            newstatement.setBoolean(3, false);
            newstatement.executeUpdate();
            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Eveniment> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Eveniment> update(Eveniment ev) {
        return Optional.empty();
    }

    public boolean notifications(Utilizator u, Eveniment e) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from events e inner join usrevt ue on e.id=ue.id_event inner join utilizatori u on ue.id_user=u.id");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long organizator = resultSet.getLong("organizator");
                Long abonat = resultSet.getLong("id_user");
                if (e.getId() == id && (u.getId() == organizator || u.getId() == abonat)) {
                    return resultSet.getBoolean("notificari");
                }
            }
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void setNotifications(Utilizator u, Eveniment e, boolean on) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("UPDATE usrevt SET notificari = ? WHERE id_user=? AND id_event=?");
        ) {
            statement.setBoolean(1, on);
            statement.setLong(2, u.getId());
            statement.setLong(3, e.getId());
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
