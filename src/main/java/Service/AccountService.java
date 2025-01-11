package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accDAO;

    public AccountService() {
        accDAO = new AccountDAO();
    }

    public AccountService(AccountDAO dao) {
        this.accDAO = dao;
    }

    public Account addAccount(Account acc) {
        if (accDAO.getAccountByUsername(acc.getUsername()) != null) return null;
        if (acc.getUsername().length() == 0) return null;
        if (acc.getPassword().length() < 4) return null;
        return accDAO.insertAccount(acc);
    }

    public Account logIntoAcc(Account acc) {
        Account userAcc = accDAO.getAccountByUsername(acc.getUsername());
        if (userAcc != null) {
            if (userAcc.getPassword().equals(acc.getPassword())) {
                return accDAO.login(acc);
            }
        }
        return null;
    }
}
