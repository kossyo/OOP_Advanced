package panzer.commands;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InspectCommand extends BaseCommand {


    @Override
    @SuppressWarnings("unchecked")
    public String execute(String[] args) {

        List<String> arguments = Arrays.stream(args).collect(Collectors.toList());
        return super.manager.inspect(arguments);
    }
}
