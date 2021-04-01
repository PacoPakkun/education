package socialnetwork.service;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;
import socialnetwork.repository.file.UtilizatorFile;


import java.util.*;

public class UtilizatorService {
    private UtilizatorFile repo;
    private Repository<Tuple<Long, Long>, Prietenie> repo2;

    public UtilizatorService(UtilizatorFile repo, Repository<Tuple<Long, Long>, Prietenie> repo2) {
        this.repo = repo;
        this.repo2 = repo2;
        for (Prietenie p : repo2.findAll()) {
            repo.addPrietenie(p);
        }
    }

    public Iterable<Utilizator> getAllUsers() {
        return repo.findAll();
    }

    public Iterable<Prietenie> getAllFriendships() {
        return repo2.findAll();
    }

//    public List<Utilizator> filterUsersName(String s) {
//        return null;
//    }

    public Optional<Utilizator> findUtilizator(Long id) {
        return repo.findOne(id);
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

    public Optional<Prietenie> addPrietenie(Prietenie p) {
        Optional<Utilizator> o1 = repo.findOne(p.getId().getLeft());
        Optional<Utilizator> o2 = repo.findOne(p.getId().getRight());
        if (o1.isPresent() && o2.isPresent()) {
            repo.addPrietenie(p);
            return repo2.save(p);
        } else
            throw new ValidationException("user invalid");
    }

    public Optional<Prietenie> deletePrietenie(Tuple<Long, Long> id) {
        repo.deletePrietenie(new Prietenie(id));
        return repo2.delete(id);
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
}
