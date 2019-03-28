package panzer;

import panzer.contracts.Assembler;
import panzer.contracts.BattleOperator;
import panzer.contracts.InputReader;
import panzer.contracts.OutputWriter;
import panzer.core.Engine;
import panzer.core.PanzerBattleOperator;
import panzer.io.Reader;
import panzer.io.Writer;
import panzer.models.miscellaneous.VehicleAssembler;

public class Main {
    public static void main(String[] args) {

        // try {

        InputReader reader = new Reader();
        OutputWriter writer = new Writer();
        Assembler vehicleAssembler = new VehicleAssembler();
        BattleOperator battleOperator = new PanzerBattleOperator();

        Engine engine = new Engine(reader, writer, vehicleAssembler, battleOperator);

        engine.run();
//        } catch (Error | Exception e) {
//        }
    }
}
