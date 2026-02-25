package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.InvalidEReceiptReceiverException;
import com.mypos.smartsdk.exceptions.InvalidReferenceNumberException;
import com.mypos.smartsdk.exceptions.InvalidReferenceTypeException;
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
 * Unit tests for {@link MyPOSPreauthorization} builder validation and getters.
 */
@RunWith(RobolectricTestRunner.class)
public class MyPOSPreauthorizationTest {

    // -------------------------------------------------------------------------
    // Happy-path builds
    // -------------------------------------------------------------------------

    @Test
    public void build_minimalValid_succeeds() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(30.00)
                .currency(Currency.EUR)
                .build();

        assertNotNull(preauth);
        assertEquals(30.00, preauth.getProductAmount(), 0.001);
        assertEquals(Currency.EUR, preauth.getCurrency());
    }

    @Test
    public void build_withMotoTransaction_succeeds() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(50.00)
                .currency(Currency.USD)
                .motoTransaction(true)
                .motoPassword("pass")
                .motoPAN("4111111111111111")
                .motoExpDate("0627")
                .build();

        assertTrue(preauth.isMotoTransaction());
        assertEquals("pass", preauth.getMotoPassword());
        assertEquals("4111111111111111", preauth.getMotoPAN());
        assertEquals("0627", preauth.getMotoExpDate());
    }

    @Test
    public void build_withOnlyAuthorization_succeeds() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .isOnlyAuthorization(true)
                .build();

        assertTrue(preauth.isOnlyAuthorization());
    }

    @Test
    public void build_withReference_succeeds() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("REF-PREAUTH", ReferenceType.REFERENCE_NUMBER)
                .build();

        assertEquals("REF-PREAUTH", preauth.getReferenceNumber());
        assertEquals(ReferenceType.REFERENCE_NUMBER, preauth.getReferenceType());
    }

    @Test
    public void build_withValidEmailEReceipt_succeeds() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver("user@domain.com")
                .build();

        assertEquals("user@domain.com", preauth.getEReceiptReceiver());
    }

    @Test
    public void build_nullEReceiptReceiver_succeeds() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver(null)
                .build();

        assertNull(preauth.getEReceiptReceiver());
    }

    @Test
    public void build_withApplicationId16Chars_succeeds() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("1234567890123456")
                .build();

        assertEquals("1234567890123456", preauth.getApplicationId());
    }

    @Test
    public void build_fixedPinpad_defaultTrue() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        assertTrue(preauth.getFixedPinpad());
    }

    // -------------------------------------------------------------------------
    // Amount validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidAmountException.class)
    public void build_nullAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPreauthorization.builder()
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_zeroAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPreauthorization.builder()
                .productAmount(0.0)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_negativeAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPreauthorization.builder()
                .productAmount(-10.00)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_nanAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPreauthorization.builder()
                .productAmount(Double.NaN)
                .currency(Currency.EUR)
                .build();
    }

    // -------------------------------------------------------------------------
    // Currency validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingCurrencyException.class)
    public void build_nullCurrency_throwsMissingCurrencyException() throws Exception {
        MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .build();
    }

    // -------------------------------------------------------------------------
    // Reference type / number validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidReferenceTypeException.class)
    public void build_referenceTypeOutOfBound_throwsInvalidReferenceTypeException() throws Exception {
        MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("REF", 99)
                .build();
    }

    @Test(expected = InvalidReferenceTypeException.class)
    public void build_referenceTypeNegative_throwsInvalidReferenceTypeException() throws Exception {
        MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference("REF", -1)
                .build();
    }

    @Test(expected = InvalidReferenceNumberException.class)
    public void build_referenceEnabled_tooLongNumber_throwsInvalidReferenceNumberException() throws Exception {
        String tooLong = new String(new char[51]).replace('\0', 'A');
        MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .reference(tooLong, ReferenceType.INVOICE_ID)
                .build();
    }

    // -------------------------------------------------------------------------
    // eReceipt validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidEReceiptReceiverException.class)
    public void build_invalidEReceiptReceiver_throwsInvalidEReceiptReceiverException() throws Exception {
        MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .eReceiptReceiver("not-an-email")
                .build();
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdWrongLength_throwsApplicationIdException() throws Exception {
        MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("tooshort")
                .build();
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    @Test
    public void setters_updateFields() throws Exception {
        MyPOSPreauthorization preauth = MyPOSPreauthorization.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        preauth.setProductAmount(75.00);
        preauth.setCurrency(Currency.SEK);
        preauth.setMotoTransaction(true);
        preauth.setFixedPinpad(false);
        preauth.setOnlyAuthorization(true);
        preauth.setEReceiptReceiver("new@domain.com");
        preauth.setReference("NEW-REF", ReferenceType.PRODUCT_ID);

        assertEquals(75.00, preauth.getProductAmount(), 0.001);
        assertEquals(Currency.SEK, preauth.getCurrency());
        assertTrue(preauth.isMotoTransaction());
        assertFalse(preauth.getFixedPinpad());
        assertTrue(preauth.isOnlyAuthorization());
        assertEquals("new@domain.com", preauth.getEReceiptReceiver());
        assertEquals("NEW-REF", preauth.getReferenceNumber());
        assertEquals(ReferenceType.PRODUCT_ID, preauth.getReferenceType());
    }
}

