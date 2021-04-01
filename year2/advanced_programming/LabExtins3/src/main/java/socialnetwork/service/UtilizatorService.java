package socialnetwork.service;

import socialnetwork.domain.*;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.database.*;
import socialnetwork.repository.paging.Page;
import socialnetwork.repository.paging.Pageable;
import socialnetwork.repository.paging.PageableImplementation;
import socialnetwork.utils.ChangeEvent;
import socialnetwork.utils.Observer;
import socialnetwork.utils.Observable;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilizatorService implements Observable<ChangeEvent> {
    private UtilizatorPageRepository repo;
    private PrietenieDbRepository repo2;
    private MessageDbRepository repo3;
    private RequestDbRepository repo4;
    private EventDbRepository repo5;

    public UtilizatorService(UtilizatorPageRepository repo, PrietenieDbRepository repo2, MessageDbRepository repo3, RequestDbRepository repo4, EventDbRepository repo5) {
        this.repo = repo;
        this.repo2 = repo2;
        this.repo3 = repo3;
        this.repo4 = repo4;
        this.repo5 = repo5;
    }

    public Iterable<Utilizator> getAllUsers() {
        return repo.findAll();
    }

    public Page<Utilizator> getPagedUsers(int page, Utilizator user) {
        Pageable p = new PageableImplementation(page,14);
        return repo.findAllFiltered(p, user);
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
        if (p.isPresent()) {
            if (repo4.findOne(p.get().getId()).isPresent())
                repo4.delete(p.get().getId());
            notifyObservers(new ChangeEvent("friendship"));
        }
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

    public Iterable<Message> getAllMessages() {
        return repo3.findAll();
    }

    public void sendMessage(Message msg) {
        repo3.save(msg);
        notifyObservers(new ChangeEvent("message"));
    }

    public String addRequest(Request req) {
        if (repo4.findOne(req.getId()).isPresent() && repo4.findOne(req.getId()).get().getStatus().equals("pending")) {
            req.setStatus("accepted");
            repo4.update(req);
            repo2.save(new Prietenie(req.getId()));
            notifyObservers(new ChangeEvent("request"));
            return "accepted";
        } else if (repo4.findOne(req.getId()).isPresent() && !repo4.findOne(req.getId()).get().getStatus().equals("pending")) {
            repo4.delete(req.getId());
            req.setStatus("pending");
            repo4.save(req);
            notifyObservers(new ChangeEvent("request"));
            return "pending";
        } else {
            req.setStatus("pending");
            repo4.save(req);
            notifyObservers(new ChangeEvent("request"));
            return "pending";
        }
    }

    public String declineRequest(Request req) {
        req.setStatus("declined");
        repo4.update(req);
        notifyObservers(new ChangeEvent("request"));
        return "declined";
    }

    public void removeRequest(Tuple<Long, Long> id) {
        repo4.delete(id);
        notifyObservers(new ChangeEvent("request"));
    }

    public Optional<Request> findRequest(Tuple<Long, Long> id) {
        return repo4.findOne(id);
    }

    public void addEvent(Eveniment ev) {
        repo5.save(ev);
        notifyObservers(new ChangeEvent("event"));
    }

    public void deleteEvent(Long id) {
        repo5.delete(id);
    }

    public Iterable<Eveniment> getEvents(Utilizator u) {
        return repo5.findUserEvents(u);
    }

    public long getNextEventId() {
        return repo5.nextId();
    }

    public long getNextUserId() {
        return repo.nextId();
    }

    public boolean notifications(Utilizator u, Eveniment e) {
        return repo5.notifications(u, e);
    }

    public void setNotifications(Utilizator u, Eveniment e, boolean on) {
        repo5.setNotifications(u, e, on);
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

    public Optional<Utilizator> lastLogin() {
        return repo.lastLogin();
    }

    public void setLogin(Utilizator u) {
        repo.setLogin(u);
    }
}
