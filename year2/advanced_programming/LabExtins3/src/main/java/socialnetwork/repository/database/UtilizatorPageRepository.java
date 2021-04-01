package socialnetwork.repository.database;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.paging.Page;
import socialnetwork.repository.paging.Pageable;
import socialnetwork.repository.paging.Paginator;
import socialnetwork.repository.paging.PagingRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorPageRepository extends UtilizatorDbRepository implements PagingRepository<Long, Utilizator> {

    public UtilizatorPageRepository(String url, String username, String password, Validator<Utilizator> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Page<Utilizator> findAll(Pageable pageable) {
        Paginator<Utilizator> paginator = new Paginator<>(pageable, findAll());
        return paginator.paginate();
    }

    private Optional<Prietenie> findOneFriendship(Tuple<Long, Long> aLong) {
        Optional<Prietenie> o = Optional.empty();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id1, id2, prietenii.date from prietenii");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                Date date = resultSet.getDate("date");
                Tuple<Long, Long> id = new Tuple(id1, id2);
                if (id.equals(aLong)) {
                    Prietenie p = new Prietenie(id);
                    p.setDate(date);
                    o = Optional.of(p);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    public Page<Utilizator> findAllFiltered(Pageable pageable, Utilizator user) {
        List<Utilizator> list = StreamSupport.stream(findAll().spliterator(), false)
                .filter(u ->
                        u.getId() != user.getId() && !findOneFriendship(new Tuple<Long, Long>(user.getId(), u.getId())).isPresent())
                .collect(Collectors.toList());
        Paginator<Utilizator> paginator = new Paginator<>(pageable, list);
        return paginator.paginate();
    }
}
