package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.GiftCardUnsupportedParamsException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidEReceiptReceiverException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link MyPOSRefund} builder validation and getters.
 */
@RunWith(RobolectricTestRunner.class)
public class MyPOSRefundTest {

    // -------------------------------------------------------------------------
    // Happy-path builds
    // -------------------------------------------------------------------------

    @Test
    public void build_minimalValidRefund_succeeds() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(15.00)
                .currency(Currency.EUR)
                .build();

        assertNotNull(refund);
        assertEquals(15.00, refund.getRefundAmount(), 0.001);
        assertEquals(Currency.EUR, refund.getCurrency());
    }

    @Test
    public void build_withMotoTransaction_succeeds() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(20.00)
                .currency(Currency.GBP)
                .motoTransaction(true)
                .motoPassword("secret")
                .motoPAN("4111111111111111")
                .motoExpDate("1225")
                .build();

        assertTrue(refund.isMotoTransaction());
        assertEquals("secret", refund.getMotoPassword());
        assertEquals("4111111111111111", refund.getMotoPAN());
        assertEquals("1225", refund.getMotoExpDate());
    }

    @Test
    public void build_withGiftCardTransaction_succeeds() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .giftCardTransaction(true)
                .build();

        assertTrue(refund.isGiftCardTransaction());
    }

    @Test
    public void build_withOnlyAuthorization_succeeds() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .isOnlyAuthorization(true)
                .build();

        assertTrue(refund.isOnlyAuthorization());
    }

    @Test
    public void build_withValidEmailEReceipt_succeeds() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver("client@test.com")
                .build();

        assertEquals("client@test.com", refund.getEReceiptReceiver());
    }

    @Test
    public void build_nullEReceiptReceiver_succeeds() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver(null)
                .build();

        assertNull(refund.getEReceiptReceiver());
    }

    @Test
    public void build_fixedPinpad_defaultTrue() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .build();

        assertTrue(refund.getFixedPinpad());
    }

    @Test
    public void build_withApplicationId16Chars_succeeds() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("1234567890123456")
                .build();

        assertEquals("1234567890123456", refund.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Amount validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidAmountException.class)
    public void build_nullAmount_throwsInvalidAmountException() throws Exception {
        MyPOSRefund.builder()
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_zeroAmount_throwsInvalidAmountException() throws Exception {
        MyPOSRefund.builder()
                .refundAmount(0.0)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_negativeAmount_throwsInvalidAmountException() throws Exception {
        MyPOSRefund.builder()
                .refundAmount(-5.00)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_nanAmount_throwsInvalidAmountException() throws Exception {
        MyPOSRefund.builder()
                .refundAmount(Double.NaN)
                .currency(Currency.EUR)
                .build();
    }

    // -------------------------------------------------------------------------
    // Currency validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingCurrencyException.class)
    public void build_nullCurrency_throwsMissingCurrencyException() throws Exception {
        MyPOSRefund.builder()
                .refundAmount(10.00)
                .build();
    }

    // -------------------------------------------------------------------------
    // Gift card + MOTO combination
    // -------------------------------------------------------------------------

    @Test(expected = GiftCardUnsupportedParamsException.class)
    public void build_motoAndGiftCard_throwsGiftCardUnsupportedParamsException() throws Exception {
        MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .motoTransaction(true)
                .giftCardTransaction(true)
                .build();
    }

    // -------------------------------------------------------------------------
    // eReceipt validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidEReceiptReceiverException.class)
    public void build_invalidEReceiptReceiver_throwsInvalidEReceiptReceiverException() throws Exception {
        MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver("not-valid")
                .build();
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdWrongLength_throwsApplicationIdException() throws Exception {
        MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("short")
                .build();
    }

    @Test
    public void build_nullApplicationId_succeeds() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .applicationId(null)
                .build();

        assertNull(refund.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    @Test
    public void setters_updateFields() throws Exception {
        MyPOSRefund refund = MyPOSRefund.builder()
                .refundAmount(10.00)
                .currency(Currency.EUR)
                .build();

        refund.setRefundAmount(99.99);
        refund.setCurrency(Currency.USD);
        refund.setMotoTransaction(true);
        refund.setGiftCardTransaction(false);
        refund.setFixedPinpad(false);
        refund.setOnlyAuthorization(true);
        refund.setEReceiptReceiver("new@test.com");
        refund.setMotoPassword("pw2");
        refund.setMotoPAN("5500005555555559");

        assertEquals(99.99, refund.getRefundAmount(), 0.001);
        assertEquals(Currency.USD, refund.getCurrency());
        assertTrue(refund.isMotoTransaction());
        assertFalse(refund.isGiftCardTransaction());
        assertFalse(refund.getFixedPinpad());
        assertTrue(refund.isOnlyAuthorization());
        assertEquals("new@test.com", refund.getEReceiptReceiver());
        assertEquals("pw2", refund.getMotoPassword());
        assertEquals("5500005555555559", refund.getMotoPAN());
    }
}

