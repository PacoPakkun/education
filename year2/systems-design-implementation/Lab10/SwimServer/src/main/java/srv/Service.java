package srv;

import domain.Proba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import repo.intf.ProbaRepository;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("swim/probe")
public class Service {

    @Autowired
    private ProbaRepository probaRepository;

    @GetMapping("/test")
    public String test(@RequestParam(value = "name", defaultValue = "Hello") String name) {
        return name.toUpperCase();
    }

    @GetMapping
    public List<Proba> findAllProbe() {
        System.out.println("Find All Probe requested..");
        return probaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Proba findProba(@PathVariable Integer id) throws Exception {
        System.out.println("Find Proba requested..");
        return probaRepository.findOne(id);
    }

    @PostMapping
    public Proba addProba(@RequestBody Proba proba) {
        System.out.println("Add Proba requested..");
        return probaRepository.add(proba);
    }

    @PutMapping("/{id}")
    public Proba updateProba(@PathVariable Integer id, @RequestBody Proba proba) throws Exception {
        if (proba.getId() == id) {
            System.out.println("Update Proba requested..");
            return probaRepository.update(proba);
        } else
            throw new Exception("bad request");
    }

    @DeleteMapping("/{id}")
    public Proba deleteProba(@PathVariable Integer id) throws Exception {
        System.out.println("Delete Proba requested..");
        return probaRepository.delete(id);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String exceptionHandler(Exception e) {
        return e.getMessage();
    }
}
