package by.epamtc.PaymentService.controller.command;

import by.epamtc.PaymentService.controller.command.impl.LogOutCommand;
import by.epamtc.PaymentService.controller.command.impl.SignInCommand;
import by.epamtc.PaymentService.controller.command.impl.admin.impl.*;
import by.epamtc.PaymentService.controller.command.impl.admin.impl.go.*;
import by.epamtc.PaymentService.controller.command.impl.auth.impl.PersonalEditCommand;
import by.epamtc.PaymentService.controller.command.impl.auth.impl.UploadUserImageCommand;
import by.epamtc.PaymentService.controller.command.impl.auth.impl.go.*;
import by.epamtc.PaymentService.controller.command.impl.auth.impl.payment.*;
import by.epamtc.PaymentService.controller.command.impl.go.GoToErrorPageCommand;
import by.epamtc.PaymentService.controller.command.impl.go.GoToSignInCommand;
import by.epamtc.PaymentService.controller.command.impl.go.GoToSignUpCommand;
import by.epamtc.PaymentService.controller.command.impl.go.GoToSuccessPageCommand;
import by.epamtc.PaymentService.controller.command.impl.*;
import by.epamtc.PaymentService.controller.command.impl.admin.impl.*;
import by.epamtc.PaymentService.controller.command.impl.admin.impl.go.*;
import by.epamtc.PaymentService.controller.command.impl.auth.impl.go.GoToPaySelectOrgCommand;
import by.epamtc.PaymentService.controller.command.impl.auth.impl.payment.*;
import by.epamtc.PaymentService.controller.command.impl.auth.impl.go.*;
import by.epamtc.PaymentService.controller.command.impl.go.*;

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
        commands.put(CommandName.GO_TO_ACCOUNTS_COMMAND, new GoToAccountsCommand());
        commands.put(CommandName.GO_TO_ACCOUNT_COMMAND, new GoToAccountCommand());
        commands.put(CommandName.GO_TO_ACCOUNT_HISTORY_COMMAND, new GoToAccountHistoryCommand());
        commands.put(CommandName.GO_TO_USER_HISTORY_COMMAND, new GoToUserHistoryCommand());
        commands.put(CommandName.GO_TO_ADD_CARD_COMMAND, new GoToAddCardCommand());
        commands.put(CommandName.GO_TO_ADD_ORG_COMMAND, new GoToAddOrgCommand());
        commands.put(CommandName.GO_TO_ADD_ORG_CONFIRM_COMMAND, new GoToAddOrgConfirmCommand());
        commands.put(CommandName.GO_TO_EDIT_ORG_COMMAND, new GoToEditOrgCommand());
        commands.put(CommandName.GO_TO_EDIT_ORG_CONFIRM_COMMAND, new GoToEditOrgConfirmCommand());
        commands.put(CommandName.GO_TO_CARDS_COMMAND, new GoToCardsCommand());
        commands.put(CommandName.GO_TO_CARD_COMMAND, new GoToCardCommand());
        commands.put(CommandName.GO_TO_TOP_UP_ACCOUNT_COMMAND, new GoToTopUpAccountCommand());
        commands.put(CommandName.GO_TO_PAY_SELECT_CARD_COMMAND, new GoToPaySelectCardCommand());
        commands.put(CommandName.GO_TO_PAY_SELECT_ACCOUNT_COMMAND, new GoToPaySelectAccountCommand());
        commands.put(CommandName.GO_TO_PAY_TRANSFER_TO_COMMAND, new GoToPayTransferToCommand());
        commands.put(CommandName.GO_TO_PAY_TRANSFER_CONFIRM_COMMAND, new GoToPayTransferConfirmCommand());
        commands.put(CommandName.GO_TO_PAYMENT_CONFIRM_COMMAND, new GoToPaymentConfirmCommand());
        commands.put(CommandName.GO_TO_ADMIN_ACCOUNTS_COMMAND, new GoToAdminAccountsCommand());
        commands.put(CommandName.GO_TO_ADMIN_USER_COMMAND, new GoToAdminUserCommand());
        commands.put(CommandName.GO_TO_ADMIN_USER_ACCOUNTS_COMMAND, new GoToAdminUserAccountsCommand());
        commands.put(CommandName.GO_TO_ADMIN_ACCOUNT_COMMAND, new GoToAdminAccountCommand());
        commands.put(CommandName.GO_TO_ADMIN_USERS_COMMAND, new GoToAdminUsersCommand());
        commands.put(CommandName.GO_TO_ADMIN_ORGS_COMMAND, new GoToAdminOrgsCommand());
        commands.put(CommandName.GO_TO_ADMIN_ORG_COMMAND, new GoToAdminOrgCommand());
        commands.put(CommandName.GO_TO_SUCCESS_PAGE_COMMAND, new GoToSuccessPageCommand());
        commands.put(CommandName.GO_TO_PAY_SELECT_ORG_COMMAND, new GoToPaySelectOrgCommand());
        commands.put(CommandName.GRAND_ADMIN_RIGHTS_COMMAND, new GrandAdminRightsCommand());
        commands.put(CommandName.REVOKE_ADMIN_RIGHTS_COMMAND, new RevokeAdminRightsCommand());
        commands.put(CommandName.UPLOAD_USER_IMAGE_COMMAND, new UploadUserImageCommand());
        commands.put(CommandName.PAY_COMMAND, new PayCommand());
        commands.put(CommandName.PERSONAL_EDIT_COMMAND, new PersonalEditCommand());
        commands.put(CommandName.SIGN_IN_COMMAND, new SignInCommand());
        commands.put(CommandName.LOG_OUT_COMMAND, new LogOutCommand());
        commands.put(CommandName.ADD_ACCOUNT_COMMAND, new AddAccountCommand());
        commands.put(CommandName.TOP_UP_ACCOUNT_COMMAND, new TopUpAccountCommand());
        commands.put(CommandName.BLOCK_ACCOUNT_COMMAND, new BlockAccountCommand());
        commands.put(CommandName.UNLOCK_ACCOUNT_COMMAND, new UnlockAccountCommand());
        commands.put(CommandName.DELETE_ACCOUNT_COMMAND, new DeleteAccountCommand());
        commands.put(CommandName.ADD_CARD_COMMAND, new AddCardCommand());
        commands.put(CommandName.DELETE_CARD_COMMAND, new DeleteCardCommand());
        commands.put(CommandName.BLOCK_ORG_COMMAND, new BlockOrgCommand());
        commands.put(CommandName.UNLOCK_ORG_COMMAND, new UnlockOrgCommand());
        commands.put(CommandName.DELETE_ORG_COMMAND, new DeleteOrgCommand());
        commands.put(CommandName.ADD_ORG_COMMAND, new AddOrgCommand());
        commands.put(CommandName.EDIT_ORG_COMMAND, new EditOrgCommand());
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
