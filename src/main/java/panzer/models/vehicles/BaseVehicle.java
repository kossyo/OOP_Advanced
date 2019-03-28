package panzer.models.vehicles;

import panzer.contracts.Assembler;
import panzer.contracts.Modelable;
import panzer.contracts.Part;
import panzer.contracts.Vehicle;
import panzer.models.miscellaneous.VehicleAssembler;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseVehicle implements Vehicle {

    private Assembler assembler;

    protected String model;
    private double weight;
    private BigDecimal price;
    private int attack;
    private int defense;
    private int hitPoints;

    protected BaseVehicle(String model, double weight, BigDecimal price, int attack, int defense, int hitPoints) {
        this.model = model;
        this.setWeight(weight);
        this.setPrice(price);
        this.setAttack(attack);
        this.setDefense(defense);
        this.setHitPoints(hitPoints);
        this.assembler = new VehicleAssembler();
    }

    @Override
    public double getTotalWeight() {
        return this.weight + this.assembler.getTotalWeight();
    }

    @Override
    public BigDecimal getTotalPrice() {

        return this.price.add(this.assembler.getTotalPrice());
    }

    @Override
    public long getTotalAttack() {
        return this.attack + this.assembler.getTotalAttackModification();
    }

    @Override
    public long getTotalDefense() {
        return this.defense + this.assembler.getTotalDefenseModification();
    }

    @Override
    public long getTotalHitPoints() {
        return this.hitPoints + this.assembler.getTotalHitPointModification();
    }

    @Override
    public void addArsenalPart(Part arsenalPart) {
        this.assembler.addArsenalPart(arsenalPart);
    }

    @Override
    public void addShellPart(Part shellPart) {
        this.assembler.addShellPart(shellPart);
    }

    @Override
    public void addEndurancePart(Part endurancePart) {
        this.assembler.addEndurancePart(endurancePart);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterable<Part> getParts() {

        List<Part> parts = new LinkedList<>();
        try {
            Field[] fields = this.assembler.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                parts.addAll((List<Part>) field.get(this.assembler));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return parts;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    protected void setWeight(double weight) {
        this.weight = weight;
    }

    protected void setPrice(BigDecimal price) {
        this.price = price;
    }

    protected void setAttack(int attack) {
        this.attack = attack;
    }

    protected void setDefense(int defense) {
        this.defense = defense;
    }

    protected void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    @Override

    public String toString() {

        StringBuilder sb = new StringBuilder();
        String parts = ((Collection<Part>) (this.getParts())).stream().map(Modelable::getModel).collect(Collectors.joining(", "));


        sb
                .append(String.format("%s - %s\r\n", this.getClass().getSimpleName(), this.getModel()))
                .append(String.format("Total Weight: %.3f\n", this.getTotalWeight()))
                .append(String.format("Total Price: %.3f\r\n", this.getTotalPrice()))
                .append(String.format("Attack: %d\r\n", this.getTotalAttack()))
                .append(String.format("Defense: %d\r\n", this.getTotalDefense()))
                .append(String.format("HitPoints: %d\r\n", this.getTotalHitPoints()))
                .append("Parts: ")
                .append(parts.isEmpty() ? "None" : parts)
        ;

        return sb.toString();
    }
}
