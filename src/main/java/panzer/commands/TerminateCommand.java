package panzer.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TerminateCommand extends BaseCommand {

    @Override
    public String execute(String[] args) {

        List<String> arguments = Arrays.stream(args).collect(Collectors.toList());
        return super.manager.terminate(arguments);
    }
}
