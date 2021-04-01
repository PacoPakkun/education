package socialnetwork;

import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.file.PrietenieFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.repository.file.UtilizatorFile0;
import socialnetwork.service.UtilizatorService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String fileName2 = "data/friendships.csv";
        UtilizatorFile userFileRepository;
        Repository<Tuple<Long, Long>, Prietenie> friendshipFileRepository;
        UtilizatorService srv;

        try {
            userFileRepository = new UtilizatorFile(fileName, new UtilizatorValidator());
            friendshipFileRepository = new PrietenieFile(fileName2, new PrietenieValidator());
            srv = new UtilizatorService(userFileRepository, friendshipFileRepository);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }

        System.out.println("insert command add/del/show/find/nrcom/bestcom/exit");
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                switch (input) {
                    case "add":
                        System.out.println("select user/friend");
                        input = scanner.nextLine();
                        switch (input) {
                            case "user":
                                System.out.println("give id/name/name");
                                input = scanner.nextLine();
                                List<String> attr = Arrays.asList(input.split(" "));
                                Utilizator user = new Utilizator(attr.get(1), attr.get(2));
                                user.setId(Long.parseLong(attr.get(0)));
                                Optional<Utilizator> o = srv.addUtilizator(user);
                                if (!o.isPresent())
                                    System.out.println("added");
                                else
                                    System.out.println("user exists!");
                                break;
                            case "friend":
                                System.out.println("give id/id");
                                input = scanner.nextLine();
                                attr = Arrays.asList(input.split(" "));
                                Optional<Prietenie> op = srv.addPrietenie(new Prietenie(new Tuple(Long.parseLong(attr.get(0)), Long.parseLong(attr.get(1)))));
                                if (!op.isPresent())
                                    System.out.println("added");
                                else
                                    System.out.println("friendship exists!");
                                break;
                            default:
                                System.out.println("unknown command");
                        }
                        break;
                    case "del":
                        System.out.println("select user/friend");
                        input = scanner.nextLine();
                        switch (input) {
                            case "user":
                                System.out.println("give id");
                                input = scanner.nextLine();
                                Optional<Utilizator> o = srv.deleteUtilizator(Long.parseLong(input));
                                if (o.isPresent())
                                    System.out.println("removed");
                                else
                                    System.out.println("user doesn't exist!");
                                break;
                            case "friend":
                                System.out.println("give id/id");
                                input = scanner.nextLine();
                                List<String> attr = Arrays.asList(input.split(" "));
                                Optional<Prietenie> op = srv.deletePrietenie(new Tuple(Long.parseLong(attr.get(0)), Long.parseLong(attr.get(1))));
                                if (op.isPresent())
                                    System.out.println("removed");
                                else
                                    System.out.println("friendship doesn't exist!");
                                break;
                            default:
                                System.out.println("unknown command");
                        }
                        break;
                    case "find":
                        System.out.println("select user/friends");
                        input = scanner.nextLine();
                        switch (input) {
                            case "user":
                                System.out.println("give id");
                                input = scanner.nextLine();
                                Optional<Utilizator> o = srv.findUtilizator(Long.parseLong(input));
                                if (o.isPresent()) {
                                    System.out.println(o.get().toString());
                                } else
                                    System.out.println("user doesn't exist!");
                                break;
                            case "friends":
                                System.out.println("give id");
                                input = scanner.nextLine();
                                Optional<Utilizator> op = srv.findUtilizator(Long.parseLong(input));
                                if (op.isPresent())
                                    System.out.println(op.get().getFriends());
                                else
                                    System.out.println("user doesn't exist!");
                                break;
                            default:
                                System.out.println("unknown command");
                        }
                        break;
                    case "show":
                        System.out.println("select users/friends");
                        input = scanner.nextLine();
                        switch (input) {
                            case "users":
                                srv.getAllUsers().forEach(System.out::println);
                                break;
                            case "friends":
                                srv.getAllFriendships().forEach(System.out::println);
                                break;
                        }
                        break;
                    case "nrcom":
                        System.out.println(srv.nrComunitati());
                        break;
                    case "bestcom":
                        List<Utilizator> l = srv.comunitateMax();
                        for (Utilizator user : l)
                            System.out.println(user);
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("unknown command");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
}


