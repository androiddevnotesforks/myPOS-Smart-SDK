package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.GiftCardUnsupportedParamsException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidEReceiptReceiverException;
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
 * Unit tests for {@link MyPOSPayment} builder validation logic and getters.
 */
@RunWith(RobolectricTestRunner.class)
public class MyPOSPaymentTest {

    // -------------------------------------------------------------------------
    // Happy-path / successful builds
    // -------------------------------------------------------------------------

    @Test
    public void build_minimalValidPayment_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        assertNotNull(payment);
        assertEquals(10.00, payment.getProductAmount(), 0.001);
        assertEquals(Currency.EUR, payment.getCurrency());
    }

    @Test
    public void build_withTippingEnabled_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(20.00)
                .currency(Currency.USD)
                .tippingModeEnabled(true)
                .tipAmount(2.00)
                .build();

        assertNotNull(payment);
        assertTrue(payment.isTippingModeEnabled());
        assertEquals(2.00, payment.getTipAmount(), 0.001);
    }

    @Test
    public void build_withMotoTransaction_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(50.00)
                .currency(Currency.GBP)
                .motoTransaction(true)
                .motoPassword("secret")
                .motoPAN("4111111111111111")
                .motoExpDate("1225")
                .build();

        assertNotNull(payment);
        assertTrue(payment.isMotoTransaction());
        assertEquals("secret", payment.getMotoPassword());
        assertEquals("4111111111111111", payment.getMotoPAN());
        assertEquals("1225", payment.getMotoExpDate());
    }

    @Test
    public void build_withGiftCardTransaction_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(15.00)
                .currency(Currency.EUR)
                .giftCardTransaction(true)
                .build();

        assertNotNull(payment);
        assertTrue(payment.isGiftCardTransaction());
    }

    @Test
    public void build_withValidOperatorCode_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("12")
                .build();

        assertNotNull(payment);
        assertEquals("12", payment.getOperatorCode());
    }

    @Test
    public void build_withValidReferenceNumber_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("REF123", ReferenceType.REFERENCE_NUMBER)
                .build();

        assertNotNull(payment);
        assertEquals("REF123", payment.getReferenceNumber());
        assertEquals(ReferenceType.REFERENCE_NUMBER, payment.getReferenceType());
    }

    @Test
    public void build_referenceTypeOff_withNoReferenceNumber_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference(null, ReferenceType.OFF)
                .build();

        assertNotNull(payment);
        assertNull(payment.getReferenceNumber());
        assertEquals(ReferenceType.OFF, payment.getReferenceType());
    }

    @Test
    public void build_withValidEmailEReceipt_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver("user@example.com")
                .build();

        assertNotNull(payment);
        assertEquals("user@example.com", payment.getEReceiptReceiver());
    }

    @Test
    public void build_withValidApplicationId_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();
        // Set via setter after build
        payment.setApplicationId("1234567890123456");
        assertEquals("1234567890123456", payment.getApplicationId());
    }

    @Test
    public void build_withFixedPinpadFalse_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .fixedPinpad(false)
                .build();

        assertNotNull(payment);
        assertFalse(payment.getFixedPinpad());
    }

    @Test
    public void build_brandingFlags_defaultToTrue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        assertTrue(payment.mastercardSonicBranding());
        assertTrue(payment.visaSensoryBranding());
    }

    @Test
    public void build_withBrandingFlagsDisabled_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .mastercardSonicBranding(false)
                .visaSensoryBranding(false)
                .build();

        assertFalse(payment.mastercardSonicBranding());
        assertFalse(payment.visaSensoryBranding());
    }

    // -------------------------------------------------------------------------
    // Amount validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidAmountException.class)
    public void build_nullAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPayment.builder()
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_zeroAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(0.0)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_negativeAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(-5.00)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_nanAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(Double.NaN)
                .currency(Currency.EUR)
                .build();
    }

    // -------------------------------------------------------------------------
    // Currency validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingCurrencyException.class)
    public void build_nullCurrency_throwsMissingCurrencyException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .build();
    }

    // -------------------------------------------------------------------------
    // Tip amount validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidTipAmountException.class)
    public void build_tippingEnabled_zeroTipAmount_throwsInvalidTipAmountException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(20.00)
                .currency(Currency.EUR)
                .tippingModeEnabled(true)
                .tipAmount(0.0)
                .build();
    }

    @Test(expected = InvalidTipAmountException.class)
    public void build_tippingEnabled_negativeTipAmount_throwsInvalidTipAmountException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(20.00)
                .currency(Currency.EUR)
                .tippingModeEnabled(true)
                .tipAmount(-1.0)
                .build();
    }

    @Test(expected = InvalidTipAmountException.class)
    public void build_tippingEnabled_nanTipAmount_throwsInvalidTipAmountException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(20.00)
                .currency(Currency.EUR)
                .tippingModeEnabled(true)
                .tipAmount(Double.NaN)
                .build();
    }

    @Test
    public void build_tippingDisabled_tipAmountNotValidated() throws Exception {
        // When tipping is disabled, tip amount should not be validated
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(20.00)
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
    public void build_giftCardAndMoto_throwsGiftCardUnsupportedParamsException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .giftCardTransaction(true)
                .motoTransaction(true)
                .build();
    }

    // -------------------------------------------------------------------------
    // Operator code validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidOperatorCodeException.class)
    public void build_operatorCodeTooLong_throwsInvalidOperatorCodeException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("12345") // 5 chars, max is 4
                .build();
    }

    @Test(expected = InvalidOperatorCodeException.class)
    public void build_operatorCodeEmpty_throwsInvalidOperatorCodeException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("")
                .build();
    }

    @Test(expected = InvalidOperatorCodeException.class)
    public void build_operatorCodeNonNumeric_throwsInvalidOperatorCodeException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("AB")
                .build();
    }

    @Test(expected = InvalidOperatorCodeException.class)
    public void build_operatorCodeNegativeNumber_throwsInvalidOperatorCodeException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("-1")
                .build();
    }

    @Test
    public void build_operatorCodeNull_noValidation_succeeds() throws Exception {
        // null operator code should be skipped entirely
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode(null)
                .build();
        assertNotNull(payment);
        assertNull(payment.getOperatorCode());
    }

    @Test
    public void build_operatorCodeMaxLength_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .operatorCode("9999") // exactly 4 digits
                .build();
        assertNotNull(payment);
        assertEquals("9999", payment.getOperatorCode());
    }

    // -------------------------------------------------------------------------
    // Reference type validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidReferenceTypeException.class)
    public void build_referenceTypeOutOfBound_throwsInvalidReferenceTypeException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("REF", 999)
                .build();
    }

    @Test(expected = InvalidReferenceTypeException.class)
    public void build_referenceTypeNegative_throwsInvalidReferenceTypeException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("REF", -1)
                .build();
    }

    // -------------------------------------------------------------------------
    // Reference number validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidReferenceNumberException.class)
    public void build_referenceEnabled_invalidReferenceNumber_tooLong_throwsInvalidReferenceNumberException() throws Exception {
        String longRef = new String(new char[51]).replace('\0', 'A'); // 51 chars
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference(longRef, ReferenceType.INVOICE_ID)
                .build();
    }

    // -------------------------------------------------------------------------
    // eReceipt receiver validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidEReceiptReceiverException.class)
    public void build_invalidEmailEReceiptReceiver_throwsInvalidEReceiptReceiverException() throws Exception {
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver("not-a-valid-email")
                .build();
    }

    @Test
    public void build_nullEReceiptReceiver_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver(null)
                .build();
        assertNotNull(payment);
        assertNull(payment.getEReceiptReceiver());
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdInvalidChars_throwsApplicationIdException() throws Exception {
        // Space is not allowed
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("invalid id")
                .build();
    }

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooLong_throwsApplicationIdException() throws Exception {
        // 51 chars — exceeds the 50-char limit
        MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("123456789012345678901234567890123456789012345678901")
                .build();
    }

    @Test
    public void build_applicationIdNullSkipsValidation_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId(null)
                .build();
        assertNotNull(payment);
        assertNull(payment.getApplicationId());
    }

    @Test
    public void build_applicationIdAlphanumeric_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("1234567890123456")
                .build();
        assertNotNull(payment);
        assertEquals("1234567890123456", payment.getApplicationId());
    }

    @Test
    public void build_applicationIdShortValid_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("AB-1")
                .build();
        assertNotNull(payment);
        assertEquals("AB-1", payment.getApplicationId());
    }

    @Test
    public void build_applicationIdWithPunctuation_succeeds() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("my.app_id-01,v2")
                .build();
        assertNotNull(payment);
        assertEquals("my.app_id-01,v2", payment.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Setters (fluent API)
    // -------------------------------------------------------------------------

    @Test
    public void setters_returnSameInstance() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        MyPOSPayment returned = payment.setProductAmount(25.00);
        assertEquals(payment, returned);
        assertEquals(25.00, payment.getProductAmount(), 0.001);
    }

    @Test
    public void setCurrency_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setCurrency(Currency.BGN);
        assertEquals(Currency.BGN, payment.getCurrency());
    }

    @Test
    public void setTippingModeEnabled_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setTippingModeEnabled(true);
        assertTrue(payment.isTippingModeEnabled());
    }

    @Test
    public void setMotoTransaction_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setMotoTransaction(true);
        assertTrue(payment.isMotoTransaction());
    }

    @Test
    public void setGiftCardTransaction_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setGiftCardTransaction(true);
        assertTrue(payment.isGiftCardTransaction());
    }

    @Test
    public void setReference_updatesValues() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setReference("ORDER-001", ReferenceType.INVOICE_ID);
        assertEquals("ORDER-001", payment.getReferenceNumber());
        assertEquals(ReferenceType.INVOICE_ID, payment.getReferenceType());
    }

    @Test
    public void setEReceiptReceiver_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setEReceiptReceiver("merchant@store.com");
        assertEquals("merchant@store.com", payment.getEReceiptReceiver());
    }

    @Test
    public void setOperatorCode_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setOperatorCode("42");
        assertEquals("42", payment.getOperatorCode());
    }

    @Test
    public void setFixedPinpad_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .fixedPinpad(true)
                .build();

        payment.setFixedPinpad(false);
        assertFalse(payment.getFixedPinpad());
    }

    @Test
    public void setMastercardSonicBranding_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setMastercardSonicBranding(false);
        assertFalse(payment.mastercardSonicBranding());
    }

    @Test
    public void setVisaSensoryBranding_updatesValue() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setVisaSensoryBranding(false);
        assertFalse(payment.visaSensoryBranding());
    }

    @Test
    public void setMotoFields_updateValues() throws Exception {
        MyPOSPayment payment = MyPOSPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        payment.setMotoPassword("pass123");
        payment.setMotoPAN("5500005555555559");
        payment.setMotoExpDate("0627");

        assertEquals("pass123", payment.getMotoPassword());
        assertEquals("5500005555555559", payment.getMotoPAN());
        assertEquals("0627", payment.getMotoExpDate());
    }
}

