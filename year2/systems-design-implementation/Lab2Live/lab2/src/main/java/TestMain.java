import model.ComputerRepairRequest;
import repository.RequestRepository;

public class TestMain {
    public static void main(String[] args){
        RequestRepository test = new RequestRepository();

        test.add(new ComputerRepairRequest(1,"A A","Address A","072222","Asus","13/10/2020","Broken display"));
        test.add(new ComputerRepairRequest(2,"B B","Address B","072222","Acer","10/10/2020","Faulty keyboard"));
        for(ComputerRepairRequest first: test.findAll())
            System.out.println(first);
    }

}
