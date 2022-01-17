import domain.Proba;
import org.springframework.web.client.RestTemplate;

public class RestClient {
    public static void main(String[] args) {
        String url = "http://localhost:8080/swim/probe";
        RestTemplate restTemplate = new RestTemplate();
        final Integer[] id = {null};

        // find all
        System.out.println("\n\t--- Find All Probe ---");
        show(() -> {
            Proba[] probe = restTemplate.getForObject(url, Proba[].class);
            System.out.println();
            for (Proba p : probe) {
                System.out.println(p);
            }
        });

        // find one
        System.out.println("\n\t--- Find One Proba ---");
        show(() -> {
            Proba proba = restTemplate.getForObject(String.format("%s/%s", url, "1"), Proba.class);
            System.out.println("\n" + proba);
        });

        // create
        System.out.println("\n\t--- Create Proba ---");
        show(() -> {
            Proba p = new Proba(500, "spate");
            Proba proba = restTemplate.postForObject(url, p, Proba.class);
            id[0] = proba.getId();

            Proba[] probe = restTemplate.getForObject(url, Proba[].class);
            System.out.println();
            for (Proba pr : probe) {
                System.out.println(pr);
            }
        });

        // update
        System.out.println("\n\t--- Update Proba ---");
        show(() -> {
            Proba p = new Proba(id[0], 350, "lateral");
            restTemplate.put(String.format("%s/%s", url, id[0]), p);

            Proba[] probe = restTemplate.getForObject(url, Proba[].class);
            System.out.println();
            for (Proba pr : probe) {
                System.out.println(pr);
            }
        });

        // delete
        System.out.println("\n\t--- Delete Proba ---");
        show(() -> {
            restTemplate.delete(String.format("%s/%s", url, id[0]));

            Proba[] probe = restTemplate.getForObject(url, Proba[].class);
            System.out.println();
            for (Proba p : probe) {
                System.out.println(p);
            }
        });
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception" + e);
        }
    }
}
