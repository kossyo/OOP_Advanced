package panzer.factories;

import panzer.contracts.Vehicle;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

public class VehicleFactory {

    private static final String VEHICLE_PATH = "panzer.models.vehicles.";

    private VehicleFactory(){}

    public static Vehicle createVehicle(List<String> arguments) {


        String vehType = arguments.get(1);
        String model = arguments.get(2);
        double weight = Double.parseDouble(arguments.get(3));
        BigDecimal price = new BigDecimal(arguments.get(4));
        int attack = Integer.parseInt(arguments.get(5));
        int defence = Integer.parseInt(arguments.get(6));
        int hitPoints = Integer.parseInt(arguments.get(7));

        Vehicle vehicle = null;

        try {
            vehicle = (Vehicle) Class.forName(VEHICLE_PATH + vehType.substring(0, 1).toUpperCase() + vehType.substring(1))
                    .getDeclaredConstructor(
                            String.class,
                            double.class,
                            BigDecimal.class,
                            int.class,
                            int.class,
                            int.class)
                    .newInstance(model, weight, price, attack, defence, hitPoints);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return vehicle;
    }
}
