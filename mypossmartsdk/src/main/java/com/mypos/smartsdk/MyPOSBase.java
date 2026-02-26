package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;

import java.io.Serializable;
import java.util.Locale;

public class MyPOSBase<D extends MyPOSBase> implements Serializable {

    private String      foreignTransactionId;
    private Locale      language;
    private int         printMerchantReceipt;
    private int         printCustomerReceipt;
    private int         baseColor;
    private String      applicationId;

    public String getForeignTransactionId() {
        return foreignTransactionId;
    }

    public D setForeignTransactionId(String foreignTransactionId) {
        this.foreignTransactionId = foreignTransactionId;
        return (D) this;
    }

    public Locale getLanguage() {
        return language;
    }

    public D seLanguage(Locale language) {
        this.language = language;
        return (D) this;
    }

    public int getPrintMerchantReceipt() {
        return printMerchantReceipt;
    }

    public D setPrintMerchantReceipt(int printMerchantReceipt) {
        this.printMerchantReceipt = printMerchantReceipt;
        return (D) this;
    }

    public int getPrintCustomerReceipt() {
        return printCustomerReceipt;
    }

    public D setPrintCustomerReceipt(int printCustomerReceipt) {
        this.printCustomerReceipt = printCustomerReceipt;
        return (D) this;
    }

    public int getBaseColor() {
        return baseColor;
    }

    public D setBaseColor(int baseColor) {
        this.baseColor = baseColor;
        return (D) this;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public D setApplicationId(String applicationId) {
        this.applicationId = applicationId;
        return (D) this;
    }

    protected MyPOSBase(BaseBuilder builder) {
        this.printMerchantReceipt = builder.printMerchantReceipt;
        this.printCustomerReceipt = builder.printCustomerReceipt;
        this.foreignTransactionId = builder.foreignTransactionId;
        this.language = builder.language;
        this.baseColor = builder.baseColor;
        this.applicationId = builder.applicationId;
    }

    public static BaseBuilder builder() {
        return new BaseBuilder();
    }

    public static class BaseBuilder<T extends BaseBuilder<T>> implements Serializable {

        protected String foreignTransactionId;
        protected Locale language;
        protected int    printMerchantReceipt;
        protected int    printCustomerReceipt;
        protected int    baseColor;
        protected String applicationId;

        public T printMerchantReceipt(int printMerchantReceipt) {
            this.printMerchantReceipt = printMerchantReceipt;
            return (T) this;
        }

        public T printCustomerReceipt(int printCustomerReceipt) {
            this.printCustomerReceipt = printCustomerReceipt;
            return (T) this;
        }

        public T baseColor(int baseColor) {
            this.baseColor = baseColor;
            return (T) this;
        }

        public T foreignTransactionId(String foreignTransactionId) {
            this.foreignTransactionId = foreignTransactionId;
            return (T) this;
        }

        public T language(Locale language) {
            this.language = language;
            return (T) this;
        }

        public T applicationId(String applicationId) {
            this.applicationId = applicationId;
            return (T) this;
        }

        public MyPOSBase build() {
            if (applicationId != null && !applicationId.matches("[a-zA-Z0-9!\"#$%&'()*+,\\-./:<=>?@\\[\\]^_`{|}~]{1,50}")) {
                throw new ApplicationIdException("Invalid application id");
            }
            return new MyPOSBase(this);
        }

    }
}