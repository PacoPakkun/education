package socialnetwork.service;

import socialnetwork.domain.*;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.MessageDbRepository;
import socialnetwork.repository.database.PrietenieDbRepository;
import socialnetwork.repository.database.RequestDbRepository;
import socialnetwork.repository.database.UtilizatorDbRepository;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.utils.ChangeEvent;
import socialnetwork.utils.ChangeEventType;
import socialnetwork.utils.Observer;
import socialnetwork.utils.Observable;


import javax.swing.text.DateFormatter;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class UtilizatorService implements Observable<ChangeEvent> {
    private UtilizatorDbRepository repo;
    private PrietenieDbRepository repo2;
    private MessageDbRepository repo3;
    private RequestDbRepository repo4;

    public UtilizatorService(UtilizatorDbRepository repo, PrietenieDbRepository repo2, MessageDbRepository repo3, RequestDbRepository repo4) {
        this.repo = repo;
        this.repo2 = repo2;
        this.repo3 = repo3;
        this.repo4 = repo4;
    }

    public Iterable<Utilizator> getAllUsers() {
        return repo.findAll();
    }

    public Iterable<Prietenie> getAllFriendships() {
        return repo2.findAll();
    }

    public Iterable<Request> getAllRequests() {
        return repo4.findAll();
    }

//    public List<Utilizator> filterUsersName(String s) {
//        return null;
//    }

    public Optional<Utilizator> findUtilizator(Long id) {
        return repo.findOne(id);
    }

    public Optional<Utilizator> findByName(String name) {
        for (Utilizator u : repo.findAll()) {
            if (u.getLastName().equals(name))
                return Optional.of(u);
        }
        return Optional.empty();
    }

    public Optional<Utilizator> addUtilizator(Utilizator utilizator) {
        return repo.save(utilizator);
    }

    public Optional<Utilizator> deleteUtilizator(Long id) {
        for (Prietenie p : repo2.findAll()) {
            if (p.getId().getLeft() == id)
                deletePrietenie(p.getId());
            if (p.getId().getRight() == id)
                deletePrietenie(p.getId());
        }
        return repo.delete(id);
    }

    public Optional<Utilizator> updateUtilizator(Utilizator user) {
        return repo.update(user);
    }

    public Optional<Prietenie> findPrietenie(Tuple<Long, Long> id) {
        return repo2.findOne(id);
    }

    public Iterable<Utilizator> findPrieteni(Long id) {
//        return repo.findFriends(id).stream()
//                .map(user -> {
//                    String s = user.toString();
//                    Prietenie p = repo2.findOne(new Tuple<Long, Long>(id, user.getId())).get();
//                    s += ", since " + p.getDate();
//                    return s;
//                })
//                .collect(Collectors.toList());
        return repo.findFriends(id);
    }

    public Iterable<String> findPrieteniByDate(Long id, int month, int year) {
        List<Utilizator> u = repo.findFriends(id).stream()
                .filter(user ->
                        repo2.findOne(new Tuple<Long, Long>(id, user.getId())).get().getDate().toLocalDate().getMonthValue() == month && repo2.findOne(new Tuple<Long, Long>(id, user.getId())).get().getDate().toLocalDate().getYear() == year)
                .collect(Collectors.toList());

        return u.stream()
                .map(user -> {
                    String s = user.toString();
                    Prietenie p = repo2.findOne(new Tuple<Long, Long>(id, user.getId())).get();
                    s += ", since " + p.getDate();
                    return s;
                })
                .collect(Collectors.toList());
    }

    public Optional<Prietenie> addPrietenie(Prietenie p) {
        Optional<Utilizator> o1 = repo.findOne(p.getId().getLeft());
        Optional<Utilizator> o2 = repo.findOne(p.getId().getRight());
        if (o1.isPresent() && o2.isPresent()) {
            return repo2.save(p);
        } else
            throw new ValidationException("user invalid");
    }

    public Optional<Prietenie> deletePrietenie(Tuple<Long, Long> id) {
        Optional<Prietenie> p = repo2.delete(id);
        if (p.isPresent())
            notifyObservers(new ChangeEvent());
        return p;
    }

    public Optional<Prietenie> updatePrietenie(Prietenie p) {
        return repo2.update(p);
    }

    public int nrComunitati() {
        int count = 0;
        Map<Long, HashSet<Long>> adjMap = new HashMap<Long, HashSet<Long>>();
        Map<Long, Boolean> visited = new HashMap<Long, Boolean>();
        for (Prietenie p : repo2.findAll()) {
            adjMap.putIfAbsent(p.getId().getLeft(), new HashSet<Long>());
            adjMap.putIfAbsent(p.getId().getRight(), new HashSet<Long>());
            adjMap.get(p.getId().getLeft()).add(p.getId().getRight());
            adjMap.get(p.getId().getLeft()).add(p.getId().getLeft());
            adjMap.get(p.getId().getRight()).add(p.getId().getLeft());
            adjMap.get(p.getId().getRight()).add(p.getId().getRight());
            visited.put(p.getId().getLeft(), false);
            visited.put(p.getId().getRight(), false);
        }
        for (Long v : visited.keySet()) {
            if (visited.get(v) == false) {
                Stack<Long> stack = new Stack<>();
                stack.push(v);
                while (stack.empty() == false) {
                    Long s = stack.peek();
                    stack.pop();
                    if (visited.get(s) == false) {
                        visited.put(s, true);
                    }
                    Iterator<Long> itr = adjMap.get(s).iterator();

                    while (itr.hasNext()) {
                        Long n = itr.next();
                        if (!visited.get(n))
                            stack.push(n);
                    }
                }
                count++;
            }
        }
        return count;
    }

    public List<Utilizator> comunitateMax() {
        List<Utilizator> max = new ArrayList<Utilizator>();
        Map<Long, HashSet<Long>> adjMap = new HashMap<Long, HashSet<Long>>();
        Map<Long, Boolean> visited = new HashMap<Long, Boolean>();
        for (Prietenie p : repo2.findAll()) {
            adjMap.putIfAbsent(p.getId().getLeft(), new HashSet<Long>());
            adjMap.putIfAbsent(p.getId().getRight(), new HashSet<Long>());
            adjMap.get(p.getId().getLeft()).add(p.getId().getRight());
            adjMap.get(p.getId().getLeft()).add(p.getId().getLeft());
            adjMap.get(p.getId().getRight()).add(p.getId().getLeft());
            adjMap.get(p.getId().getRight()).add(p.getId().getRight());
            visited.put(p.getId().getLeft(), false);
            visited.put(p.getId().getRight(), false);
        }
        for (Long v : visited.keySet()) {
            if (visited.get(v) == false) {
                List<Utilizator> aux = new ArrayList<Utilizator>();
                Stack<Long> stack = new Stack<>();
                stack.push(v);
                while (stack.empty() == false) {
                    Long s = stack.peek();
                    stack.pop();
                    if (visited.get(s) == false) {
                        aux.add(repo.findOne(s).get());
                        visited.put(s, true);
                    }
                    Iterator<Long> itr = adjMap.get(s).iterator();

                    while (itr.hasNext()) {
                        Long n = itr.next();
                        if (!visited.get(n))
                            stack.push(n);
                    }
                }
                if (aux.size() > max.size())
                    max = aux;
            }
        }
        return max;
    }

    public Iterable<Message> getConversation(Long id1, Long id2) {
        return repo3.findConversation(id1, id2);
    }

    public void sendMessage(Message msg) {
        repo3.save(msg);
    }

    public String addRequest(Request req) {
        if (repo4.findOne(req.getId()).isPresent()) {
            req.setStatus("accepted");
            repo4.update(req);
            repo2.save(new Prietenie(req.getId()));
            notifyObservers(new ChangeEvent());
            return "accepted";
        } else {
            req.setStatus("pending");
            repo4.save(req);
            notifyObservers(new ChangeEvent());
            return "pending";
        }
    }

    public String declineRequest(Request req) {
        req.setStatus("declined");
        repo4.update(req);
        notifyObservers(new ChangeEvent());
        return "declined";
    }

    public Optional<Request> findRequest(Tuple<Long, Long> id) {
        return repo4.findOne(id);
    }

    private List<Observer<ChangeEvent>> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x -> x.update(t));
    }
}
