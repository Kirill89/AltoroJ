/**
 * This application is for demonstration use only. It contains known application security
 * vulnerabilities that were created expressly for demonstrating the functionality of
 * application security testing tools. These vulnerabilities may present risks to the
 * technical environment in which the application is installed. You must delete and
 * uninstall this demonstration application upon completion of the demonstration for
 * which it is intended.
 * <p>
 * IBM DISCLAIMS ALL LIABILITY OF ANY KIND RESULTING FROM YOUR USE OF THE APPLICATION
 * OR YOUR FAILURE TO DELETE THE APPLICATION FROM YOUR ENVIRONMENT UPON COMPLETION OF
 * A DEMONSTRATION. IT IS YOUR RESPONSIBILITY TO DETERMINE IF THE PROGRAM IS APPROPRIATE
 * OR SAFE FOR YOUR TECHNICAL ENVIRONMENT. NEVER INSTALL THE APPLICATION IN A PRODUCTION
 * ENVIRONMENT. YOU ACKNOWLEDGE AND ACCEPT ALL RISKS ASSOCIATED WITH THE USE OF THE APPLICATION.
 * <p>
 * IBM AltoroJ
 * (c) Copyright IBM Corp. 2008, 2013 All Rights Reserved.
 */
package com.ibm.security.appscan.altoromutual.model;

import java.sql.SQLException;
import java.util.Date;

import com.ibm.security.appscan.altoromutual.util.DBUtils;

/**
 * This class models a user
 * @author Alexei
 *
 */
public class User implements java.io.Serializable {

    private static final long serialVersionUID = -4566649173574593144L;
    private String username, firstName, lastName;

    ;
    private Role role = Role.User;
    private Date lastAccessDate = null;

    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        lastAccessDate = new Date();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Account[] getAccounts() {
        try {
            return DBUtils.getAccounts(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account lookupAccount(Long accountNumber) {
        for (Account account : getAccounts()) {
            if (account.getAccountId() == accountNumber)
                return account;
        }
        return null;
    }

    public long getCreditCardNumber() {
        for (Account account : getAccounts()) {
            if (DBUtils.CREDIT_CARD_ACCOUNT_NAME.equals(account.getAccountName()))
                return account.getAccountId();
        }
        return -1L;
    }

    public Transaction[] getUserTransactions(String startDate, String endDate, Account[] accounts) throws SQLException {

        Transaction[] transactions = null;
        transactions = DBUtils.getTransactions(startDate, endDate, accounts, -1);
        return transactions;
    }

    public static enum Role {User, Admin}
}
