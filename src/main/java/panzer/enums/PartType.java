package panzer.enums;

public enum PartType {
    ARSENAL,
    ENDURANCE,
    SHELL;

    @Override
    public String toString() {
        return this.name().substring(0,1) + this.name().substring(1).toLowerCase();
    }
}


