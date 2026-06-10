package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.GiftCardUnsupportedParamsException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidOperatorCodeException;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link MyPOSVendingPayment} builder validation and getters.
 */
@RunWith(RobolectricTestRunner.class)
public class MyPOSVendingPaymentTest {

    // -------------------------------------------------------------------------
    // Happy-path builds
    // -------------------------------------------------------------------------

    @Test
    public void build_minimalValidVendingPayment_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        assertNotNull(payment);
        assertEquals(10.00, payment.getProductAmount(), 0.001);
        assertEquals(Currency.EUR, payment.getCurrency());
    }

    @Test
    public void build_withTippingEnabled_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(20.00)
                .currency(Currency.USD)
                .tippingModeEnabled(true)
                .tipAmount(3.00)
                .build();

        assertTrue(payment.isTippingModeEnabled());
        assertEquals(3.00, payment.getTipAmount(), 0.001);
    }

    @Test
    public void build_withMotoTransaction_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(50.00)
                .currency(Currency.GBP)
                .motoTransaction(true)
                .motoPassword("pw")
                .motoPAN("4111111111111111")
                .motoExpDate("1225")
                .build();

        assertTrue(payment.isMotoTransaction());
        assertEquals("pw", payment.getMotoPassword());
        assertEquals("4111111111111111", payment.getMotoPAN());
        assertEquals("1225", payment.getMotoExpDate());
    }

    @Test
    public void build_withGiftCardTransaction_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(15.00)
                .currency(Currency.EUR)
                .giftCardTransaction(true)
                .build();

        assertTrue(payment.isGiftCardTransaction());
    }

    @Test
    public void build_brandingFlags_defaultToTrue() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        assertTrue(payment.mastercardSonicBranding());
        assertTrue(payment.visaSensoryBranding());
    }

    @Test
    public void build_dccAndShowFlags_defaultToTrue() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        assertTrue(payment.isDccEnabled());
        assertTrue(payment.isShowAmount());
        assertTrue(payment.isShowCancel());
    }

    @Test
    public void build_withValidOperatorCode_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("99")
                .build();

        assertEquals("99", payment.getOperatorCode());
    }

    @Test
    public void build_withReference_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("REF-001", ReferenceType.INVOICE_ID)
                .build();

        assertEquals("REF-001", payment.getReferenceNumber());
        assertEquals(ReferenceType.INVOICE_ID, payment.getReferenceType());
    }

    @Test
    public void build_withCardDetectionTimeout_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .cardDetectionTimeout(30)
                .build();

        assertEquals(30, payment.getCardDetectionTimeout());
    }

    @Test
    public void build_withApplicationId_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("1234567890123456")
                .build();

        assertEquals("1234567890123456", payment.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Amount validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidAmountException.class)
    public void build_nullAmount_throwsInvalidAmountException() throws Exception {
        MyPOSVendingPayment.builder()
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_zeroAmount_throwsInvalidAmountException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(0.0)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_negativeAmount_throwsInvalidAmountException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(-1.00)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_nanAmount_throwsInvalidAmountException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(Double.NaN)
                .currency(Currency.EUR)
                .build();
    }

    // -------------------------------------------------------------------------
    // Currency validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingCurrencyException.class)
    public void build_nullCurrency_throwsMissingCurrencyException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .build();
    }

    // -------------------------------------------------------------------------
    // Tip amount validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidTipAmountException.class)
    public void build_tippingEnabled_zeroTip_throwsInvalidTipAmountException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .tippingModeEnabled(true)
                .tipAmount(0.0)
                .build();
    }

    @Test(expected = InvalidTipAmountException.class)
    public void build_tippingEnabled_negativeTip_throwsInvalidTipAmountException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .tippingModeEnabled(true)
                .tipAmount(-2.0)
                .build();
    }

    @Test
    public void build_tippingDisabled_zeroTip_doesNotThrow() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .tippingModeEnabled(false)
                .tipAmount(0.0)
                .build();
        assertNotNull(payment);
    }

    // -------------------------------------------------------------------------
    // Gift card + MOTO combination
    // -------------------------------------------------------------------------

    @Test(expected = GiftCardUnsupportedParamsException.class)
    public void build_motoAndGiftCard_throwsGiftCardUnsupportedParamsException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .motoTransaction(true)
                .giftCardTransaction(true)
                .build();
    }

    // -------------------------------------------------------------------------
    // Operator code validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidOperatorCodeException.class)
    public void build_operatorCodeTooLong_throwsInvalidOperatorCodeException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("12345")
                .build();
    }

    @Test(expected = InvalidOperatorCodeException.class)
    public void build_operatorCodeEmpty_throwsInvalidOperatorCodeException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("")
                .build();
    }

    @Test(expected = InvalidOperatorCodeException.class)
    public void build_operatorCodeNonNumeric_throwsInvalidOperatorCodeException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("AB")
                .build();
    }

    @Test
    public void build_operatorCodeNull_succeeds() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode(null)
                .build();
        assertNull(payment.getOperatorCode());
    }

    // -------------------------------------------------------------------------
    // Reference type / number validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidReferenceTypeException.class)
    public void build_referenceTypeOutOfBound_throwsInvalidReferenceTypeException() throws Exception {
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("REF", 99)
                .build();
    }

    @Test(expected = InvalidReferenceNumberException.class)
    public void build_referenceEnabled_invalidRefNumber_throwsInvalidReferenceNumberException() throws Exception {
        String tooLong = new String(new char[51]).replace('\0', 'X');
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference(tooLong, ReferenceType.PRODUCT_ID)
                .build();
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdInvalidChars_throwsApplicationIdException() throws Exception {
        // Space is not allowed
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("invalid id")
                .build();
    }

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooLong_throwsApplicationIdException() throws Exception {
        // 51 chars — exceeds the 50-char limit
        MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("123456789012345678901234567890123456789012345678901")
                .build();
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    @Test
    public void setters_updateFields() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setProductAmount(25.00);
        payment.setCurrency(Currency.CHF);
        payment.setDccEnabled(false);
        payment.setShowAmount(false);
        payment.setShowCancel(false);
        payment.setCardDetectionTimeout(60);
        payment.setFixedPinpad(false);
        payment.setMastercardSonicBranding(false);
        payment.setVisaSensoryBranding(false);

        assertEquals(25.00, payment.getProductAmount(), 0.001);
        assertEquals(Currency.CHF, payment.getCurrency());
        assertFalse(payment.isDccEnabled());
        assertFalse(payment.isShowAmount());
        assertFalse(payment.isShowCancel());
        assertEquals(60, payment.getCardDetectionTimeout());
        assertFalse(payment.getFixedPinpad());
        assertFalse(payment.mastercardSonicBranding());
        assertFalse(payment.visaSensoryBranding());
    }

    @Test
    public void setReference_updatesValues() throws Exception {
        MyPOSVendingPayment payment = MyPOSVendingPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setReference("ORD-002", ReferenceType.RESERVATION_NUMBER);
        assertEquals("ORD-002", payment.getReferenceNumber());
        assertEquals(ReferenceType.RESERVATION_NUMBER, payment.getReferenceType());
    }
}

