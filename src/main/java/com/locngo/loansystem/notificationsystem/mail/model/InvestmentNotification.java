package com.locngo.loansystem.notificationsystem.mail.model;

import com.locngo.loansystem.model.Investment;

public class InvestmentNotification {

    private Mail mail;

    private Investment investment;

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Investment getInvestment() {
        return investment;
    }

    public void setInvestment(Investment investment) {
        this.investment = investment;
    }
}
