package panzer.models.parts;

import panzer.contracts.DefenseModifyingPart;

import java.math.BigDecimal;

public class ShellPart extends BasePart implements DefenseModifyingPart {

    private int defenceModifier;

    public ShellPart(String model, double weight, BigDecimal price, int additionalParameter) {
        super(model, weight, price);
        this.defenceModifier = additionalParameter;
    }

    @Override
    public int getDefenseModifier() {
        return this.defenceModifier;
    }


}
