package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

import java.io.Serializable;
import java.util.Locale;

public class MyPOSQRPayment implements Serializable {

    private String      foreignTransactionId;
    private Locale      language;
    private double      productAmount;
    private Currency    currency;
    private String      applicationId;

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public MyPOSQRPayment setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return this;
    }

    public Locale getLanguage() {
        return language;
    }

    public MyPOSQRPayment seLanguage(Locale language) {
        this.language = language;
        return this;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public MyPOSQRPayment setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public double getProductAmount() {
        return productAmount;
    }

    public MyPOSQRPayment setProductAmount(double productAmount) {
        this.productAmount = productAmount;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public MyPOSQRPayment setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    protected MyPOSQRPayment(QRBuilder builder) {
        this.foreignTransactionId = builder.foreignTransactionId;
        this.language = builder.language;
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
        this.applicationId = builder.applicationId;
    }

    public static QRBuilder builder() {
        return new QRBuilder();
    }

    public static class QRBuilder implements Serializable {

        protected String foreignTransactionId;
        protected Locale language;
        private Double      productAmount;
        private Currency    currency;
        protected String applicationId;


        public QRBuilder foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return this;
        }

        public QRBuilder language(Locale language) {
            this.language = language;
            return this;
        }

        public QRBuilder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }
        public QRBuilder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public QRBuilder applicationId(String applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public MyPOSQRPayment build() {
            if (this.productAmount == null || this.productAmount <= 0.0D || Double.isNaN(this.productAmount)) {
                throw new InvalidAmountException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Missing currency");
            }
            if (applicationId != null && applicationId.length() != 16) {
                throw new ApplicationIdException("Invalid application id");
            }
            return new MyPOSQRPayment(this);
        }

    }
}