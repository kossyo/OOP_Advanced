package panzer.models.vehicles;

import java.math.BigDecimal;

public class Revenger extends BaseVehicle {


    private static final BigDecimal PRICE_INCREASE_PERCENTAGE = new BigDecimal("50");
    private static final int ATTACK_INCREASE_PERCENTAGE = 150;
    private static final int DEFENSE_DECREASE_PERCENTAGE = 50;
    private static final int HITPOINTS_DECREASE_PERCENTAGE = 50;

    public Revenger(String model, double weight, BigDecimal price, int attack, int defense, int hitPoints) {
        super(model, weight, price, attack, defense, hitPoints);
    }

    @Override
    public void setPrice(BigDecimal price) {

        BigDecimal increasedPrice = price.add(PRICE_INCREASE_PERCENTAGE.multiply(price).divide(new BigDecimal(100)));
        super.setPrice(increasedPrice);
    }

    @Override
    public void setAttack(int attack) {

        int increasedAttack = attack + ((ATTACK_INCREASE_PERCENTAGE * attack) / 100);
        super.setAttack(increasedAttack);
    }

    @Override
    public void setDefense(int defense) {

        int decreasedDefence = defense - ((DEFENSE_DECREASE_PERCENTAGE * defense) / 100);
        super.setDefense(decreasedDefence);
    }

    @Override
    public void setHitPoints(int hitPoints) {

        int decreasedHitPoints = hitPoints - ((HITPOINTS_DECREASE_PERCENTAGE * hitPoints) / 100);
        super.setHitPoints(decreasedHitPoints);
    }


}
