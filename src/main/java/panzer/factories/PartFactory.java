package panzer.factories;

import panzer.contracts.Part;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

public class PartFactory {

    private static final String PART_PATH = "panzer.models.parts.";

    private PartFactory(){}

    public static Part createPart(List<String> arguments){

        String vehicleModel = arguments.get(1);
        String partType = arguments.get(2);
        String partModel = arguments.get(3);
        double weight = Double.parseDouble(arguments.get(4));
        BigDecimal price = new BigDecimal(arguments.get(5));
        int additionalParameter = Integer.parseInt(arguments.get(6));

        Part part = null;

        try {
            part = (Part) Class.forName(PART_PATH + partType.substring(0,1).toUpperCase() + partType.substring(1) + "Part")
                    .getDeclaredConstructor(
                            String.class,
                            double.class,
                            BigDecimal.class,
                            int.class)
                    .newInstance(partModel, weight, price, additionalParameter);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return part;
    }
}
