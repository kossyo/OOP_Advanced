package panzer.manager;

import panzer.annotations.Inject;
import panzer.contracts.*;
import panzer.enums.PartType;
import panzer.factories.PartFactory;
import panzer.factories.VehicleFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramManager implements Manager {

    private static final String CREATED_VEHICLE_MESSAGE = "Created %s Vehicle - %s";
    private static final String CREATED_PART_MESSAGE = "Added %s - %s to Vehicle - %s";
    private static final String BATTLE_MESSAGE = "%s versus %s -> %s Wins! Flawless Victory!";

    private Map<String, Vehicle> remainingVehicles;
    private Map<String, Vehicle> defeatedVehicles;
    private Map<String, Part> parts;

    @Inject
    private BattleOperator battleOperator;

    public ProgramManager() {
        this.remainingVehicles = new LinkedHashMap<>();
        this.defeatedVehicles = new LinkedHashMap<>();
        this.parts = new LinkedHashMap<>();
    }

    @Override
    public String addVehicle(List<String> arguments) {

        Vehicle vehicle = VehicleFactory.createVehicle(arguments);
        this.remainingVehicles.put(vehicle.getModel(), vehicle);

        return String.format(CREATED_VEHICLE_MESSAGE, arguments.get(1), arguments.get(2));
    }

    @Override
    public String addPart(List<String> arguments) {

        String vehicleToAddPartToStr = arguments.get(1);
        PartType partType = Enum.valueOf(PartType.class, arguments.get(2).toUpperCase());
        switch (partType) {
            case ARSENAL:

                AttackModifyingPart attackModifyingPart = (AttackModifyingPart) PartFactory.createPart(arguments);


                this.parts.put(attackModifyingPart.getModel(), attackModifyingPart);
                this.remainingVehicles.get(vehicleToAddPartToStr).addArsenalPart(attackModifyingPart);
                break;

            case ENDURANCE:

                HitPointsModifyingPart hitPointsModifyingPart = (HitPointsModifyingPart) PartFactory.createPart(arguments);


                this.parts.put(hitPointsModifyingPart.getModel(), hitPointsModifyingPart);
                this.remainingVehicles.get(vehicleToAddPartToStr).addEndurancePart(hitPointsModifyingPart);
                break;

            case SHELL:

                DefenseModifyingPart defenseModifyingPart = (DefenseModifyingPart) PartFactory.createPart(arguments);


                this.parts.put(defenseModifyingPart.getModel(), defenseModifyingPart);
                this.remainingVehicles.get(vehicleToAddPartToStr).addShellPart(defenseModifyingPart);
                break;
        }
        return String.format(CREATED_PART_MESSAGE,
                arguments.get(2),
                arguments.get(3),
                arguments.get(1));
    }

    @Override
    @SuppressWarnings("unchecked")
    public String inspect(List<String> arguments) {
        String result;

        String model = arguments.get(1);

        if (this.remainingVehicles.containsKey(model)) {
            result = this.remainingVehicles.get(model).toString();
        } else {
            result = this.parts.get(model).toString();
        }
        return result;
    }

    @Override
    public String battle(List<String> arguments) {

        Vehicle attacker = this.remainingVehicles.get(arguments.get(1));
        Vehicle target = this.remainingVehicles.get(arguments.get(2));

        String winner = this.battleOperator.battle(attacker, target);
        String loser = (winner.equals(attacker.getModel())) ? target.getModel() : attacker.getModel();

        this.defeatedVehicles.put(loser, this.remainingVehicles.get(loser));
        this.remainingVehicles.remove(loser);

        return String.format(BATTLE_MESSAGE, attacker.getModel(), target.getModel(), winner);
    }

    @Override
    public String terminate(List<String> arguments) {

        StringBuilder sb = new StringBuilder();
        sb.append("Remaining Vehicles: ");


        String remainingVehiclesStr = (!this.remainingVehicles.isEmpty())
                ? this.remainingVehicles.values().stream().map(Vehicle::getModel).collect(Collectors.joining(", "))
                : "None";

        sb
                .append(remainingVehiclesStr).append(System.lineSeparator())
                .append("Defeated Vehicles: ");


        String defeatedVehiclesStr = (!this.defeatedVehicles.isEmpty())
                ? this.defeatedVehicles.values().stream().map(Vehicle::getModel).collect(Collectors.joining(", "))
                : "None";

        sb
                .append(defeatedVehiclesStr).append(System.lineSeparator())
                .append("Currently Used Parts: ");

        long usedParts = this.remainingVehicles.values()

                .stream()
                .mapToLong(vehicle -> ((List<Part>) vehicle.getParts()).size()).sum();

        sb.append(usedParts);

        return sb.toString();
    }
}

