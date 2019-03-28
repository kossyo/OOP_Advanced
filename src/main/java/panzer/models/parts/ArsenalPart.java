package panzer.models.parts;

import panzer.contracts.AttackModifyingPart;

import java.math.BigDecimal;

public class ArsenalPart extends BasePart implements AttackModifyingPart {

    private int attackModifier;

    public ArsenalPart(String model, double weight, BigDecimal price, int additionalParameter) {
        super(model, weight, price);
        this.attackModifier = additionalParameter;
    }

    public int getAttackModifier() {
        return this.attackModifier;
    }


}
