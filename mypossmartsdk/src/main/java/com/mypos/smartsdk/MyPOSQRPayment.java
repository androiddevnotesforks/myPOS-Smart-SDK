package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

import java.io.Serializable;

public class MyPOSQRPayment extends MyPOSBase<MyPOSQRPayment> implements Serializable {

    private double      productAmount;
    private Currency    currency;

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
        super(builder);
        this.productAmount = builder.productAmount;
        this.currency = builder.currency;
    }

    public static QRBuilder builder() {
        return new QRBuilder();
    }

    public static class QRBuilder extends MyPOSBase.BaseBuilder<QRBuilder>{

        private Double   productAmount;
        private Currency currency;

        public QRBuilder productAmount(Double productAmount) {
            this.productAmount = productAmount;
            return this;
        }
        public QRBuilder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public MyPOSQRPayment build() {
            if (this.productAmount == null || this.productAmount <= 0.0D || Double.isNaN(this.productAmount)) {
                throw new InvalidAmountException("Invalid or missing amount");
            }
            if (this.currency == null) {
                throw new MissingCurrencyException("Missing currency");
            }
            if (applicationId != null && !applicationId.matches("[a-zA-Z0-9!\"#$%&'()*+,\\-./:<=>?@\\[\\]^_`{|}~]{1,50}")) {
                throw new ApplicationIdException("Invalid application id");
            }
            return new MyPOSQRPayment(this);
        }

    }
}