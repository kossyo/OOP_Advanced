package panzer.commands;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BattleCommand extends BaseCommand {

    @Override
    public String execute(String[] args) {

        List<String> arguments = Arrays.stream(args).collect(Collectors.toList());
        return super.manager.battle(arguments);
    }
}
