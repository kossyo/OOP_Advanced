package panzer.commands;

import panzer.annotations.Inject;
import panzer.contracts.Manager;

public abstract class BaseCommand {

    @Inject
    protected Manager manager;

    public abstract String execute(String[] args);
}
