package com.mypos.smartsdk;

import android.content.Intent;
import android.content.pm.PackageInfo;

import java.util.Locale;

public class MyPOSIntents {

    public static Intent getPaymentIntent(MyPOSPayment payment, boolean skipConfirmationScreen) {
        return getPaymentIntent(payment, skipConfirmationScreen, null);
    }

    public static Intent getPaymentIntent(MyPOSPayment payment, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent;
        if (payment.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else if (payment.isGiftCardTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);
        }else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PAYMENT);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, payment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_TIP_AMOUNT, payment.getTipAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_TIPS_ENABLED, payment.isTippingModeEnabled());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, payment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, payment.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, payment.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, payment.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_OPERATOR_CODE, payment.getOperatorCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER, payment.getReferenceNumber());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER_TYPE, payment.getReferenceType());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, payment.getMotoPassword());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PAN, payment.getMotoPAN());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_EXP_DATE, payment.getMotoExpDate());
        myposIntent.putExtra(MyPOSUtil.INTENT_FIXED_PINPAD, payment.getFixedPinpad());
        myposIntent.putExtra(MyPOSUtil.INTENT_ENABLE_MASTERCARD_SONIC, payment.mastercardSonicBranding());
        myposIntent.putExtra(MyPOSUtil.INTENT_ENABLE_VISA_SENSORY, payment.visaSensoryBranding());
        myposIntent.putExtra(MyPOSUtil.INTENT_E_RECEIPT_RECEIVER, payment.getEReceiptReceiver());

        if (payment.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, payment.getLanguage().getLanguage());


        if (payment.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, payment.getBaseColor());

        if (payment.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = payment.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }


    public static Intent getVendingPaymentIntent(MyPOSVendingPayment payment, boolean skipConfirmationScreen) {
        return getVendingPaymentIntent(payment, skipConfirmationScreen, null);
    }

    public static Intent getVendingPaymentIntent(MyPOSVendingPayment payment, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent;
        if (payment.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else if (payment.isGiftCardTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);
        }else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PAYMENT);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, payment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_TIP_AMOUNT, payment.getTipAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_TIPS_ENABLED, payment.isTippingModeEnabled());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, payment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, payment.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, payment.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, payment.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_OPERATOR_CODE, payment.getOperatorCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER, payment.getReferenceNumber());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER_TYPE, payment.getReferenceType());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, payment.getMotoPassword());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PAN, payment.getMotoPAN());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_EXP_DATE, payment.getMotoExpDate());
        myposIntent.putExtra(MyPOSUtil.INTENT_FIXED_PINPAD, payment.getFixedPinpad());
        myposIntent.putExtra(MyPOSUtil.INTENT_ENABLE_MASTERCARD_SONIC, payment.mastercardSonicBranding());
        myposIntent.putExtra(MyPOSUtil.INTENT_ENABLE_VISA_SENSORY, payment.visaSensoryBranding());
        myposIntent.putExtra(MyPOSUtil.INTENT_AUTHORIZATION_ONLY, true);
        myposIntent.putExtra(MyPOSUtil.INTENT_DCC_ENABLED, payment.isDccEnabled());
        myposIntent.putExtra(MyPOSUtil.INTENT_HIDE_AMOUNT, !payment.isShowAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_HIDE_CANCEL, !payment.isShowCancel());

        if (payment.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, payment.getLanguage().getLanguage());

        if (payment.getCardDetectionTimeout() > 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_CARD_TIMEOUT, payment.getCardDetectionTimeout());

        if (payment.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, payment.getBaseColor());


        if (payment.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = payment.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getRefundIntent(MyPOSRefund refund, boolean skipConfirmationScreen) {
        return getRefundIntent(refund, skipConfirmationScreen, null);
    }

    public static Intent getRefundIntent(MyPOSRefund refund, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent;
        if (refund.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else if (refund.isGiftCardTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_REFUND);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, refund.getRefundAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, refund.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, refund.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, refund.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, refund.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, refund.getMotoPassword());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PAN, refund.getMotoPAN());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_EXP_DATE, refund.getMotoExpDate());
        myposIntent.putExtra(MyPOSUtil.INTENT_FIXED_PINPAD, refund.getFixedPinpad());
        myposIntent.putExtra(MyPOSUtil.INTENT_AUTHORIZATION_ONLY, refund.isOnlyAuthorization());
        myposIntent.putExtra(MyPOSUtil.INTENT_E_RECEIPT_RECEIVER, refund.getEReceiptReceiver());

        if (refund.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, refund.getLanguage().getLanguage());

        if (refund.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, refund.getBaseColor());

        if (refund.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = refund.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getVoidIntent(MyPOSVoid voidTr, boolean skipConfirmationScreen) {
        return getVoidIntent(voidTr, skipConfirmationScreen, null);
    }

    public static Intent getVoidIntent(MyPOSVoid voidTr, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent;
        if(voidTr.getVoidLastTransactionFlag()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_VOID_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_VOID_INTENT_EX);

            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_STAN, voidTr.getSTAN());
            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_AUTH_CODE, voidTr.getAuthCode());
            myposIntent.putExtra(MyPOSUtil.INTENT_VOID_DATE_TIME, voidTr.getDateTime());
        }
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_VOID);
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, voidTr.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, voidTr.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, voidTr.getPrintCustomerReceipt());

        if (voidTr.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, voidTr.getLanguage().getLanguage());

        if (voidTr.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, voidTr.getBaseColor());


        if (voidTr.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = voidTr.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getPreauthorizationIntent(MyPOSPreauthorization preauth, boolean skipConfirmationScreen) {
        return getPreauthorizationIntent(preauth, skipConfirmationScreen, null);
    }

    public static Intent getPreauthorizationIntent(MyPOSPreauthorization preauth, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent;
        if (preauth.isMotoTransaction()) {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT);
        } else {
            myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);
        }

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, preauth.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, preauth.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER, preauth.getReferenceNumber());
        myposIntent.putExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER_TYPE, preauth.getReferenceType());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PASSWORD, preauth.getMotoPassword());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_PAN, preauth.getMotoPAN());
        myposIntent.putExtra(MyPOSUtil.INTENT_MOTO_EXP_DATE, preauth.getMotoExpDate());
        myposIntent.putExtra(MyPOSUtil.INTENT_FIXED_PINPAD, preauth.getFixedPinpad());
        myposIntent.putExtra(MyPOSUtil.INTENT_AUTHORIZATION_ONLY, preauth.isOnlyAuthorization());
        myposIntent.putExtra(MyPOSUtil.INTENT_E_RECEIPT_RECEIVER, preauth.getEReceiptReceiver());

        if (preauth.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, preauth.getLanguage().getLanguage());

        if (preauth.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, preauth.getBaseColor());

        if (preauth.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = preauth.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getPreauthorizationCompletionIntent(MyPOSPreauthorizationCompletion preauth, boolean skipConfirmationScreen) {
        return getPreauthorizationCompletionIntent(preauth, skipConfirmationScreen, null);
    }

    public static Intent getPreauthorizationCompletionIntent(MyPOSPreauthorizationCompletion preauth, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH_COMPLETION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_PREAUTH_CODE, preauth.getPreauthorizationCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, preauth.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, preauth.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());

        if (preauth.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, preauth.getLanguage().getLanguage());

        if (preauth.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, preauth.getBaseColor());


        if (preauth.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = preauth.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getPreauthorizationCancellationIntent(MyPOSPreauthorizationCancellation preauth, boolean skipConfirmationScreen) {
        return getPreauthorizationCancellationIntent(preauth, skipConfirmationScreen, null);
    }

    public static Intent getPreauthorizationCancellationIntent(MyPOSPreauthorizationCancellation preauth, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PREAUTH_CANCELLATION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSFER_PREAUTH_CODE, preauth.getPreauthorizationCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, preauth.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, preauth.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, preauth.getPrintCustomerReceipt());

        if (preauth.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, preauth.getLanguage().getLanguage());


        if (preauth.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = preauth.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getGiftCardActivationIntent(MyPOSGiftCardActivation activation, boolean skipConfirmationScreen) {
        return getGiftCardActivationIntent(activation, skipConfirmationScreen, null);
    }

    public static Intent getGiftCardActivationIntent(MyPOSGiftCardActivation activation, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_GIFTCARD_ACTIVATION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, activation.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, activation.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, activation.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, activation.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, activation.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_FIXED_PINPAD, activation.getFixedPinpad());
        myposIntent.putExtra(MyPOSUtil.INTENT_AUTHORIZATION_ONLY, activation.isOnlyAuthorization());

        if (activation.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, activation.getLanguage().getLanguage());

        if (activation.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, activation.getBaseColor());


        if (activation.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = activation.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getGiftCardDeactivationIntent(MyPOSBase<?> base, boolean skipConfirmationScreen) {
        return getGiftCardDeactivationIntent(base, skipConfirmationScreen, null);
    }

    public static Intent getGiftCardDeactivationIntent(MyPOSBase<?> base, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_GIFTCARD_DEACTIVATION);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, base.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, base.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, base.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (base.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, base.getLanguage().getLanguage());

        if (base.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, base.getBaseColor());


        if (base.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = base.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getGiftCardBalanceCheckIntent(MyPOSBase<?> base, boolean skipConfirmationScreen) {
        return getGiftCardBalanceCheckIntent(base, skipConfirmationScreen, null);
    }

    public static Intent getGiftCardBalanceCheckIntent(MyPOSBase<?> base, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_GIFTCARD_BALANCE_CHECK);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, base.getForeignTransactionId());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_MERCHANT_RECEIPT, base.getPrintMerchantReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_PRINT_CUSTOMER_RECEIPT, base.getPrintCustomerReceipt());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (base.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, base.getLanguage().getLanguage());

        if (base.getBaseColor() != 0)
            myposIntent.putExtra(MyPOSUtil.INTENT_APP_MAIN_COLOR, base.getBaseColor());


        if (base.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = base.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getPaymentRequestIntent(MyPOSPaymentRequest paymentRequest) {
        return getPaymentRequestIntent(paymentRequest, null);
    }

    public static Intent getPaymentRequestIntent(MyPOSPaymentRequest paymentRequest, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_PAYMENT_REQUEST);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, paymentRequest.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, paymentRequest.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_GSM, paymentRequest.getGSM());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_EMAIL, paymentRequest.getEMail());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_REASON, paymentRequest.getReason());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_RECIPIENT_NAME, paymentRequest.getRecipientName());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_CODE, paymentRequest.getRequestCode());
        myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_EXPIRY_DAYS, paymentRequest.getExpiryDays());

        if (paymentRequest.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, paymentRequest.getLanguage().getLang());
        
        if (paymentRequest.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = paymentRequest.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getTwintPaymentIntent(MyPOSQRPayment myPOSQRPayment, boolean skipConfirmationScreen) {
        return getTwintPaymentIntent(myPOSQRPayment, skipConfirmationScreen, null);
    }

    public static Intent getTwintPaymentIntent(MyPOSQRPayment myPOSQRPayment, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_TWINT_PAYMENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PAYMENT);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, myPOSQRPayment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, myPOSQRPayment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (myPOSQRPayment.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, myPOSQRPayment.getLanguage().getLanguage());

        if (myPOSQRPayment.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = myPOSQRPayment.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getTwintRefundIntent(MyPOSQRPayment myPOSQRPayment, boolean skipConfirmationScreen) {
        return getTwintRefundIntent(myPOSQRPayment, skipConfirmationScreen, null);
    }

    public static Intent getTwintRefundIntent(MyPOSQRPayment myPOSQRPayment, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_TWINT_PAYMENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_REFUND);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, myPOSQRPayment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, myPOSQRPayment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (myPOSQRPayment.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, myPOSQRPayment.getLanguage().getLanguage());

        if (myPOSQRPayment.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = myPOSQRPayment.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getTwintVoidIntent(MyPOSQRPayment myPOSQRPayment, String originalTwintReference, boolean skipConfirmationScreen) {
        return getTwintVoidIntent(myPOSQRPayment, originalTwintReference, skipConfirmationScreen, null);
    }

    public static Intent getTwintVoidIntent(MyPOSQRPayment myPOSQRPayment, String originalTwintReference, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_TWINT_VOID);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_VOID);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, myPOSQRPayment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, myPOSQRPayment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_TWINT_ORIGINAL_REFERENCE, originalTwintReference);
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (myPOSQRPayment.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, myPOSQRPayment.getLanguage().getLanguage());

        if (myPOSQRPayment.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = myPOSQRPayment.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }


    public static Intent getSatispayPaymentIntent(MyPOSQRPayment myPOSQRPayment, boolean skipConfirmationScreen) {
        return getSatispayPaymentIntent(myPOSQRPayment, skipConfirmationScreen, null);
    }

    public static Intent getSatispayPaymentIntent(MyPOSQRPayment myPOSQRPayment, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_SATISPAY_PAYMENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PAYMENT);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, myPOSQRPayment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, myPOSQRPayment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (myPOSQRPayment.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, myPOSQRPayment.getLanguage().getLanguage());

        if (myPOSQRPayment.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = myPOSQRPayment.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getSatispayRefundIntent(double amount, Currency currency, String originalReference, boolean skipConfirmationScreen) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_SATISPAY_PAYMENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_REFUND);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, amount);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, currency.toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_SATISPAY_ORIGINAL_REFERENCE, originalReference);
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        return myposIntent;
    }

    public static Intent getSatispayVoidIntent(double amount, Currency currency, String originalReference, boolean skipConfirmationScreen) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_SATISPAY_PAYMENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_VOID);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, amount);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, currency.toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_SATISPAY_ORIGINAL_REFERENCE, originalReference);
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        return myposIntent;
    }

    public static Intent getIrisPaymentIntent(MyPOSQRPayment myPOSQRPayment, boolean skipConfirmationScreen) {
        return getIrisPaymentIntent(myPOSQRPayment, skipConfirmationScreen, null);
    }

    public static Intent getIrisPaymentIntent(MyPOSQRPayment myPOSQRPayment, boolean skipConfirmationScreen, String versionName) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_IRIS_PAYMENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_PAYMENT);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, myPOSQRPayment.getProductAmount());
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, myPOSQRPayment.getCurrency().toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (myPOSQRPayment.getLanguage() != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, myPOSQRPayment.getLanguage().getLanguage());

        if (myPOSQRPayment.getApplicationId() != null && versionName != null) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.packageName = myPOSQRPayment.getApplicationId();
            packageInfo.versionName = versionName;
            myposIntent.putExtra(MyPOSUtil.INTENT_ORIGIN_PACKAGE_INFO, packageInfo);
        }

        return myposIntent;
    }

    public static Intent getIrisRefundIntent(double amount, Currency currency, boolean skipConfirmationScreen) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_IRIS_PAYMENT);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_REFUND);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, amount);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY, currency.toString());
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        return myposIntent;
    }



    public static Intent getCompleteTxIntent(Double partialAmount, String credential, String foreignTransactionId, Locale language, boolean skipConfirmationScreen) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_COMPLETE_TX);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_COMPLETE_TX);
        myposIntent.putExtra(MyPOSUtil.INTENT_E_RECEIPT_RECEIVER, credential);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, partialAmount);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, foreignTransactionId);
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (language != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, language.getLanguage());

        return myposIntent;
    }

    public static Intent getCancelTxIntent(String foreignTransactionId, Locale language, boolean skipConfirmationScreen) {
        Intent myposIntent = new Intent(MyPOSUtil.PAYMENT_CORE_ENTRY_COMPLETE_TX);

        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, MyPOSUtil.TRANSACTION_TYPE_CANCEL_TX);
        myposIntent.putExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID, foreignTransactionId);
        myposIntent.putExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, skipConfirmationScreen);

        if (language != null)
            myposIntent.putExtra(MyPOSUtil.INTENT_PAYMENT_REQUEST_LANGUAGE, language.getLanguage());

        return myposIntent;
    }
}
