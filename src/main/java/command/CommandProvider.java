package command;

import command.impl.*;
import command.impl.go.*;

import java.util.HashMap;

public class CommandProvider {

    private static CommandProvider instance;
    private final HashMap<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.GO_TO_SIGN_IN_COMMAND, new GoToSignInCommand());
        commands.put(CommandName.GO_TO_SIGN_UP_COMMAND, new GoToSignUpCommand());
        commands.put(CommandName.GO_TO_ERROR_PAGE_COMMAND, new GoToErrorPageCommand());
        commands.put(CommandName.GO_TO_PERSONAL_PAGE_COMMAND, new GoToPersonalPageCommand());
        commands.put(CommandName.GO_TO_PERSONAL_EDIT_COMMAND, new GoToPersonalEditCommand());
        commands.put(CommandName.GO_TO_PAYMENTS_COMMAND, new GoToPaymentsCommand());
        commands.put(CommandName.PERSONAL_EDIT_COMMAND, new PersonalEditCommand());
        commands.put(CommandName.SIGN_IN_COMMAND, new SignInCommand());
        commands.put(CommandName.LOG_OUT_COMMAND, new LogOutCommand());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        commandName = commandName.toUpperCase();
        CommandName enumName = CommandName.valueOf(commandName);

        return commands.get(enumName);
    }
}
