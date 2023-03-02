package ru.lopa10ko.banks.console;

import lombok.Getter;

import java.util.List;

public class Options {
    @Getter
    private static final String Confirm = "\nPress ENTER to confirm...";
    @Getter
    private static final String Title = "What's your god damn choice?";
    @Getter
    private static final String CreateCentralBank = "Create Central Bank";
    @Getter
    private static final String CreateBank = "Create bank (string bankName, BankInfo bankInfo)";
    @Getter
    private static final String CreateAccount = "Create account (Debit, Deposit, Credit)";
    @Getter
    private static final String CreateClient = "Create client";
    @Getter
    private static final String CreateDebit = "Debit";
    @Getter
    private static final String CreateDeposit = "Deposit";
    @Getter
    private static final String CreateCredit = "Credit";
    @Getter
    private static final String SkipDays = "Skip n days";
    @Getter
    private static final String SetBankInfo = "Set BankInfo for bank (Bank bank, BankInfo)";
    @Getter
    private static final String CreateTransaction = "Create transaction";
    @Getter
    private static final String OneSideWithdraw = "One-sided withdraw transaction (-)";
    @Getter
    private static final String OneSideReplenish = "One-sided replenishment transaction (+)";
    @Getter
    private static final String TwoSide = "Two-sided transaction (*)->(.)";
    @Getter
    private static final String Exit = "Exit CLI";
    @Getter
    private static final String GoBack = "Go back";
    @Getter
    private static final List<String> CentralBankLogicOptions = List.of(CreateBank, CreateAccount, CreateClient, SetBankInfo, CreateTransaction, SkipDays, Exit);
    @Getter
    private static final List<String> MainHandlerOptions = List.of(CreateCentralBank, Exit);
    @Getter
    private static final List<String> AccountTypes = List.of(CreateDebit, CreateDeposit, CreateCredit);
    @Getter
    private static final List<String> TransactionTypes = List.of(OneSideReplenish, OneSideWithdraw, TwoSide);

}
