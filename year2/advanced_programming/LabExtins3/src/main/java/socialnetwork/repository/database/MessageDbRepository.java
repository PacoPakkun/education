package socialnetwork.repository.database;

import socialnetwork.domain.Message;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MessageDbRepository implements Repository<Long, Message> {
    private String url;
    private String username;
    private String password;
    private Validator<Message> validator;

    public MessageDbRepository(String url, String username, String password, Validator<Message> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Message> save(Message entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must not be null");
        if (findOne(entity.getId()).isPresent())
            throw new IllegalArgumentException("message already exists!");
        if (entity.getReply() != -1 && !findOne(entity.getReply()).isPresent())
            throw new IllegalArgumentException("reply message doesn't exist!");
        this.validator.validate(entity);

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO mesaje(id, froom, message, data, reply) VALUES (?,?,?,?,?);");
        ) {
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getFrom().getId());
            statement.setString(3, entity.getMessage());
            statement.setDate(4, entity.getData());
            if (entity.getReply() != null)
                statement.setLong(5, entity.getReply());
            else
                statement.setLong(5, -1);
            statement.executeUpdate();
            for (Utilizator u : entity.getTo()) {
                PreparedStatement newstatement = connection.prepareStatement("INSERT INTO usrmsg(id_mesaj, id_user) VALUES (?,?);");
                newstatement.setLong(1, entity.getId());
                newstatement.setLong(2, u.getId());
                newstatement.executeUpdate();
            }
            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(entity);
    }

    public Iterable<Message> findConversation(Long id1, Long id2) {
        Set<Message> msgs = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from mesaje m inner join usrmsg u on m.id=u.id_mesaj order by data");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long user1 = resultSet.getLong("froom");
                Long user2 = resultSet.getLong("id_user");
                if (id1 == user1 && id2 == user2) {
                    Long id = resultSet.getLong("id");
                    String message = resultSet.getString("message");
                    Date data = resultSet.getDate("data");
                    Long reply = resultSet.getLong("reply");
                    PreparedStatement newerstatement = connection.prepareStatement("SELECT * from utilizatori where id=?");
                    newerstatement.setLong(1, id1);
                    ResultSet res = newerstatement.executeQuery();
                    res.next();
                    Long id_user = res.getLong("id");
                    String nume = res.getString("nume");
                    String prenume = res.getString("prenume");
                    Utilizator user = new Utilizator(nume, prenume);
                    user.setId(id_user);
                    List<Utilizator> users = new ArrayList<Utilizator>();
                    PreparedStatement newstatement = connection.prepareStatement("SELECT * from mesaje m inner join usrmsg um on m.id=um.id_mesaj inner join utilizatori u on um.id_user=u.id where id_mesaj=?");
                    newstatement.setLong(1, id);
                    ResultSet res2 = newstatement.executeQuery();
                    while (res2.next()) {
                        id_user = res2.getLong("id_user");
                        nume = res2.getString("nume");
                        prenume = res2.getString("prenume");
                        Utilizator u = new Utilizator(nume, prenume);
                        u.setId(id_user);
                        users.add(u);
                    }
                    Message msg = new Message(id, user, users, message, reply);
                    msg.setData(data);
                    msgs.add(msg);
                } else if (id1 == user2 && id2 == user1) {
                    Long id = resultSet.getLong("id");
                    String message = resultSet.getString("message");
                    Date data = resultSet.getDate("data");
                    Long reply = resultSet.getLong("reply");
                    PreparedStatement newerstatement = connection.prepareStatement("SELECT * from utilizatori where id=?");
                    newerstatement.setLong(1, id2);
                    ResultSet res = newerstatement.executeQuery();
                    res.next();
                    Long id_user = res.getLong("id");
                    String nume = res.getString("nume");
                    String prenume = res.getString("prenume");
                    Utilizator user = new Utilizator(nume, prenume);
                    user.setId(id_user);
                    List<Utilizator> users = new ArrayList<Utilizator>();
                    PreparedStatement newstatement = connection.prepareStatement("SELECT * from mesaje m inner join usrmsg um on m.id=um.id_mesaj inner join utilizatori u on um.id_user=u.id where id_mesaj=?");
                    newstatement.setLong(1, id);
                    ResultSet res2 = newstatement.executeQuery();
                    while (res2.next()) {
                        id_user = res2.getLong("id_user");
                        nume = res2.getString("nume");
                        prenume = res2.getString("prenume");
                        Utilizator u = new Utilizator(nume, prenume);
                        u.setId(id_user);
                        users.add(u);
                    }
                    Message msg = new Message(id, user, users, message, reply);
                    msg.setData(data);
                    msgs.add(msg);
                }

            }
            return msgs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msgs;
    }

    @Override
    public Optional<Message> findOne(Long id) {
        Optional<Message> o = Optional.empty();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id from mesaje");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long idmsg = resultSet.getLong("id");
                if (id == idmsg) {
                    Message m = new Message();
                    o = Optional.of(m);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> msgs = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from mesaje order by id");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long user = resultSet.getLong("froom");
                String message = resultSet.getString("message");
                Date data = resultSet.getDate("data");
                Long reply = resultSet.getLong("reply");
                PreparedStatement newerstatement = connection.prepareStatement("SELECT * from utilizatori where id=?");
                newerstatement.setLong(1, user);
                ResultSet res = newerstatement.executeQuery();
                res.next();
                Long id_user = res.getLong("id");
                String nume = res.getString("nume");
                String prenume = res.getString("prenume");
                Utilizator u = new Utilizator(nume, prenume);
                u.setId(id_user);
                List<Utilizator> users = new ArrayList<Utilizator>();
                PreparedStatement newstatement = connection.prepareStatement("SELECT * from mesaje m inner join usrmsg um on m.id=um.id_mesaj inner join utilizatori u on um.id_user=u.id where id_mesaj=?");
                newstatement.setLong(1, id);
                ResultSet res2 = newstatement.executeQuery();
                while (res2.next()) {
                    id_user = res2.getLong("id_user");
                    nume = res2.getString("nume");
                    prenume = res2.getString("prenume");
                    Utilizator uu = new Utilizator(nume, prenume);
                    uu.setId(id_user);
                    users.add(uu);
                }
                Message msg = new Message(id, u, users, message, reply);
                msg.setData(data);
                msgs.add(msg);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return msgs;
    }

    @Override
    public Optional<Message> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Message> update(Message msg) {
        return Optional.empty();
    }

}
