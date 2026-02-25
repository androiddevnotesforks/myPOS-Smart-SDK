package com.mypos.smartsdk;

import android.content.Intent;

import com.mypos.smartsdk.exceptions.GiftCardUnsupportedParamsException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
import com.mypos.smartsdk.exceptions.InvalidTipAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link MyPOSIntents} payment intent construction.
 * Uses Robolectric to allow creation of Android {@link Intent} objects on the JVM.
 */
@RunWith(RobolectricTestRunner.class)
public class MyPOSIntentsTest {

    // -------------------------------------------------------------------------
    // getPaymentIntent – standard (card) payment
    // -------------------------------------------------------------------------

    @Test
    public void getPaymentIntent_standardPayment_usesPaymentEntryPointAction() throws Exception {
        MyPOSPayment payment = buildBasicPayment(Currency.EUR);
        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertNotNull(intent);
        assertEquals(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_INTENT, intent.getAction());
    }

    @Test
    public void getPaymentIntent_standardPayment_containsCorrectAmount() throws Exception {
        MyPOSPayment payment = buildBasicPayment(Currency.EUR, 19.99);
        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals(19.99, intent.getDoubleExtra(MyPOSUtil.INTENT_TRANSACTION_AMOUNT, 0), 0.001);
    }

    @Test
    public void getPaymentIntent_standardPayment_containsCorrectCurrency() throws Exception {
        MyPOSPayment payment = buildBasicPayment(Currency.BGN);
        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals("BGN", intent.getStringExtra(MyPOSUtil.INTENT_TRANSACTION_CURRENCY));
    }

    @Test
    public void getPaymentIntent_standardPayment_containsPaymentRequestCode() throws Exception {
        MyPOSPayment payment = buildBasicPayment(Currency.EUR);
        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals(MyPOSUtil.TRANSACTION_TYPE_PAYMENT,
                intent.getIntExtra(MyPOSUtil.INTENT_TRANSACTION_REQUEST_CODE, -1));
    }

    @Test
    public void getPaymentIntent_skipConfirmationScreen_flagPropagated() throws Exception {
        MyPOSPayment payment = buildBasicPayment(Currency.EUR);

        Intent intentWithSkip = MyPOSIntents.getPaymentIntent(payment, true);
        Intent intentWithoutSkip = MyPOSIntents.getPaymentIntent(payment, false);

        assertTrue(intentWithSkip.getBooleanExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, false));
        assertFalse(intentWithoutSkip.getBooleanExtra(MyPOSUtil.INTENT_SKIP_CONFIRMATION_SCREEN, true));
    }

    @Test
    public void getPaymentIntent_withTipping_tipAmountAndFlagPropagated() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(50.00)
                .currency(Currency.EUR)
                .tippingModeEnabled(true)
                .tipAmount(5.00)
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertTrue(intent.getBooleanExtra(MyPOSUtil.INTENT_TRANSFER_TIPS_ENABLED, false));
        assertEquals(5.00, intent.getDoubleExtra(MyPOSUtil.INTENT_TRANSACTION_TIP_AMOUNT, 0), 0.001);
    }

    @Test
    public void getPaymentIntent_withOperatorCode_operatorCodePropagated() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("42")
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals("42", intent.getStringExtra(MyPOSUtil.INTENT_OPERATOR_CODE));
    }

    @Test
    public void getPaymentIntent_withReference_referencePropagated() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("ORD-001", ReferenceType.INVOICE_ID)
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals("ORD-001", intent.getStringExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER));
        assertEquals(ReferenceType.INVOICE_ID, intent.getIntExtra(MyPOSUtil.INTENT_REFERENCE_NUMBER_TYPE, -1));
    }

    @Test
    public void getPaymentIntent_withEReceiptReceiver_receiverPropagated() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver("customer@test.com")
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals("customer@test.com", intent.getStringExtra(MyPOSUtil.INTENT_E_RECEIPT_RECEIVER));
    }

    @Test
    public void getPaymentIntent_withFixedPinpadFalse_flagPropagated() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .fixedPinpad(false)
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertFalse(intent.getBooleanExtra(MyPOSUtil.INTENT_FIXED_PINPAD, true));
    }

    @Test
    public void getPaymentIntent_brandingFlags_propagated() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .mastercardSonicBranding(false)
                .visaSensoryBranding(false)
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertFalse(intent.getBooleanExtra(MyPOSUtil.INTENT_ENABLE_MASTERCARD_SONIC, true));
        assertFalse(intent.getBooleanExtra(MyPOSUtil.INTENT_ENABLE_VISA_SENSORY, true));
    }

    // -------------------------------------------------------------------------
    // getPaymentIntent – MOTO transaction
    // -------------------------------------------------------------------------

    @Test
    public void getPaymentIntent_motoTransaction_usesMotoEntryPointAction() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(30.00)
                .currency(Currency.USD)
                .motoTransaction(true)
                .motoPassword("pw")
                .motoPAN("4111111111111111")
                .motoExpDate("1226")
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_MOTO_INTENT, intent.getAction());
    }

    @Test
    public void getPaymentIntent_motoTransaction_motoFieldsPropagated() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(30.00)
                .currency(Currency.USD)
                .motoTransaction(true)
                .motoPassword("secret")
                .motoPAN("4111111111111111")
                .motoExpDate("1226")
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals("secret", intent.getStringExtra(MyPOSUtil.INTENT_MOTO_PASSWORD));
        assertEquals("4111111111111111", intent.getStringExtra(MyPOSUtil.INTENT_MOTO_PAN));
        assertEquals("1226", intent.getStringExtra(MyPOSUtil.INTENT_MOTO_EXP_DATE));
    }

    // -------------------------------------------------------------------------
    // getPaymentIntent – Gift card transaction
    // -------------------------------------------------------------------------

    @Test
    public void getPaymentIntent_giftCardTransaction_usesGiftCardEntryPointAction() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(20.00)
                .currency(Currency.EUR)
                .giftCardTransaction(true)
                .build();

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals(MyPOSUtil.PAYMENT_CORE_ENTRY_POINT_GIFTCARD_INTENT, intent.getAction());
    }

    // -------------------------------------------------------------------------
    // getPaymentIntent – foreignTransactionId
    // -------------------------------------------------------------------------

    @Test
    public void getPaymentIntent_withForeignTransactionId_propagated() throws Exception {
        MyPOSPayment payment = buildBasicPayment(Currency.EUR);
        payment.setForeignTransactionId("FTX-99999");

        Intent intent = MyPOSIntents.getPaymentIntent(payment, false);

        assertEquals("FTX-99999", intent.getStringExtra(MyPOSUtil.INTENT_TRANSACTION_FOREIGN_TRANSACTION_ID));
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private static MyPOSPayment buildBasicPayment(Currency currency) throws Exception {
        return buildBasicPayment(currency, 10.00);
    }

    private static MyPOSPayment buildBasicPayment(Currency currency, double amount) throws Exception {
        return MyPOSPayment.builder()
                .productAmount(amount)
                .currency(currency)
                .build();
    }
}

