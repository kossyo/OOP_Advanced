package panzer.models.vehicles;

import java.math.BigDecimal;

public class Vanguard extends BaseVehicle {

    private static final double WEIGHT_INCREASE_PERCENTAGE = 100;
    private static final int ATTACK_DECREASE_PERCENTAGE = 25;
    private static final int DEFENSE_INCREASE_PERCENTAGE = 50;
    private static final int HITPOINTS_INCREASE_PERCENTAGE = 75;

    public Vanguard(String model, double weight, BigDecimal price, int attack, int defense, int hitPoints) {
        super(model, weight, price, attack, defense, hitPoints);
    }

    @Override
    protected void setWeight(double weight){

        double increasedWeight = weight + ((WEIGHT_INCREASE_PERCENTAGE * weight) / 100);

        super.setWeight(increasedWeight);
    }

    @Override
    protected void setAttack(int attack) {

        int decreasedAttack = attack - ((ATTACK_DECREASE_PERCENTAGE * attack) / 100);

        super.setAttack(decreasedAttack);
    }

    @Override
    protected void setDefense(int defense) {

        int increasedDefense = defense + ((DEFENSE_INCREASE_PERCENTAGE * defense) / 100);

        super.setDefense(increasedDefense);
    }

    @Override
    protected void setHitPoints(int hitPoints) {

        int increasedHitPoints = hitPoints + ((HITPOINTS_INCREASE_PERCENTAGE * hitPoints) / 100);

        super.setHitPoints(increasedHitPoints);
    }

}
