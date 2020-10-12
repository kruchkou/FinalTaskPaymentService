package command;

import command.impl.GoToErrorPageCommand;
import command.impl.GoToSignInCommand;
import command.impl.SignInCommand;

import java.util.HashMap;

public class CommandProvider {

    private static CommandProvider instance;
    private final HashMap<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.SIGN_IN_COMMAND, new SignInCommand());
        commands.put(CommandName.GO_TO_ERROR_PAGE_COMMAND, new GoToErrorPageCommand());
        commands.put(CommandName.GO_TO_SIGN_IN_COMMAND, new GoToSignInCommand());
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
