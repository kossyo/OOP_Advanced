package panzer.core;

import panzer.annotations.Inject;
import panzer.commands.BaseCommand;
import panzer.constants.CommandsConstants;
import panzer.constants.GlobalConstants;
import panzer.contracts.*;
import panzer.manager.ProgramManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    private static final String COMMANDS_PATH = CommandsConstants.COMMANDS_PATH;
    private static final String COMMAND_SUFFIX = CommandsConstants.COMMAND_SUFFIX;
    private static final String TERMINATE_OUTPUT_COMMAND = GlobalConstants.TERMINATE_OUTPUT_COMMAND;

    private String[] data;
    private InputReader inputReader;
    private OutputWriter outputWriter;
    private Manager manager;

    private Assembler assembler;
    private BattleOperator battleOperator;

    public Engine(InputReader inputReader, OutputWriter outputWriter, Assembler assembler, BattleOperator battleOperator) {
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
        this.manager = new ProgramManager();
        this.assembler = assembler;
        this.battleOperator = battleOperator;
    }

    @Override
    public void run() {

        while (true) {
            String userInput = this.inputReader.readLine();

            String[] args = userInput.split("\\s+");

            this.interpretCommand(args);
            if (TERMINATE_OUTPUT_COMMAND.equals(userInput.trim())) {
                break;
            }
        }
    }

    private void interpretCommand(String[] args) {
        this.data = Arrays.stream(args).toArray(String[]::new);
        String commandType = this.data[0];

        try {
            String commandName = COMMANDS_PATH + this.commandParser(commandType) + COMMAND_SUFFIX;
            Class<?> commandClass = Class.forName(commandName);
            Constructor<?> commandConstructor = commandClass.getDeclaredConstructor();
            BaseCommand command = (BaseCommand) commandConstructor.newInstance();

            this.injectDependencies(manager);
            this.injectDependenciesToSuperClass(command);
            this.injectDependencies(command);

            this.outputWriter.println(command.execute(args));

        } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
        }
    }

    private String commandParser(String commandType) {

        String parsed = "";

        if (commandType.contains("-")) {
            String[] commandTypeArray = commandType.split("-");
            parsed = Arrays.stream(commandTypeArray)
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(""));
        } else {
            parsed = commandType.substring(0, 1).toUpperCase() + commandType.substring(1);
        }
        return parsed;
    }

    private <T> void injectDependencies(T obj) throws IllegalAccessException {

        Field[] objFields = obj.getClass().getDeclaredFields();
        Field[] engineFields = this.getClass().getDeclaredFields();

        for (Field objField : objFields) {

            objField.setAccessible(true);

            if (objField.isAnnotationPresent(Inject.class)) {

                for (Field engineField : engineFields) {

                    engineField.setAccessible(true);

                    if (engineField.getType().equals(objField.getType())
                            && engineField.getName().equals(objField.getName())) {
                        objField.set(obj, engineField.get(this));
                    }
                }
            }
        }
    }

    private <T> void injectDependenciesToSuperClass(T obj) throws IllegalAccessException {

        Field[] objFields = obj.getClass().getSuperclass().getDeclaredFields();
        Field[] engineFields = this.getClass().getDeclaredFields();

        for (Field objField : objFields) {

            objField.setAccessible(true);

            if (objField.isAnnotationPresent(Inject.class)) {

                for (Field engineField : engineFields) {

                    engineField.setAccessible(true);

                    if (engineField.getType().equals(objField.getType())
                            && engineField.getName().equals(objField.getName())) {
                        objField.set(obj, engineField.get(this));
                    }
                }
            }
        }
    }
}
