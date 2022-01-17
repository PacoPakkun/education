import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
 	//to do 
        return null;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
	//to do
        return null;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving task {} ",elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Masini (manufacturer, model, year) values (?,?,?)")) {

            preStmt.setString(1,elem.getManufacturer( ) ) ;
            preStmt.setString(2,elem.getModel( ) ) ;
            preStmt.setInt(3,elem.getYear( ) ) ;
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances",result);
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {

    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from Masini")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");
                    int id = resultSet.getInt("id");
                    Car car = new Car(manufacturer,model,year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        }
        catch(SQLException ex){
            logger.error(ex);
            System.err.println(ex);
        }
        logger.traceExit(cars);
        return cars;
    }
}
