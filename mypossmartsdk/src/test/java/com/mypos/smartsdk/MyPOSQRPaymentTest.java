package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for {@link MyPOSQRPayment} builder validation and getters.
 * No Android dependencies — plain JVM test runner.
 */
public class MyPOSQRPaymentTest {

    // -------------------------------------------------------------------------
    // Happy-path builds
    // -------------------------------------------------------------------------

    @Test
    public void build_minimalValid_succeeds() {
        MyPOSQRPayment qr = MyPOSQRPayment.builder()
                .productAmount(12.50)
                .currency(Currency.EUR)
                .build();

        assertNotNull(qr);
        assertEquals(12.50, qr.getProductAmount(), 0.001);
        assertEquals(Currency.EUR, qr.getCurrency());
    }

    @Test
    public void build_withForeignTransactionId_succeeds() {
        MyPOSQRPayment qr = MyPOSQRPayment.builder()
                .productAmount(10.00)
                .currency(Currency.CHF)
                .foreignTransactionId("QR-TX-001")
                .build();

        assertEquals("QR-TX-001", qr.getForeignTransactionId());
    }

    @Test
    public void build_withLanguage_succeeds() {
        MyPOSQRPayment qr = MyPOSQRPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .language(Locale.ENGLISH)
                .build();

        assertEquals(Locale.ENGLISH, qr.getLanguage());
    }

    @Test
    public void build_withApplicationId16Chars_succeeds() {
        MyPOSQRPayment qr = MyPOSQRPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("1234567890123456")
                .build();

        assertEquals("1234567890123456", qr.getApplicationId());
    }

    @Test
    public void build_nullApplicationId_succeeds() {
        MyPOSQRPayment qr = MyPOSQRPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId(null)
                .build();

        assertNull(qr.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Amount validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidAmountException.class)
    public void build_nullAmount_throwsInvalidAmountException() {
        MyPOSQRPayment.builder()
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_zeroAmount_throwsInvalidAmountException() {
        MyPOSQRPayment.builder()
                .productAmount(0.0)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_negativeAmount_throwsInvalidAmountException() {
        MyPOSQRPayment.builder()
                .productAmount(-5.00)
                .currency(Currency.EUR)
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_nanAmount_throwsInvalidAmountException() {
        MyPOSQRPayment.builder()
                .productAmount(Double.NaN)
                .currency(Currency.EUR)
                .build();
    }

    // -------------------------------------------------------------------------
    // Currency validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingCurrencyException.class)
    public void build_nullCurrency_throwsMissingCurrencyException() {
        MyPOSQRPayment.builder()
                .productAmount(10.00)
                .build();
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooShort_throwsApplicationIdException() {
        MyPOSQRPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("short")
                .build();
    }

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooLong_throwsApplicationIdException() {
        MyPOSQRPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .applicationId("12345678901234567") // 17 chars
                .build();
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    @Test
    public void setters_updateFields() {
        MyPOSQRPayment qr = MyPOSQRPayment.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .build();

        qr.setProductAmount(99.99);
        qr.setCurrency(Currency.USD);
        qr.setForeignTransactionId("QR-TX-999");
        qr.seLanguage(Locale.GERMAN);
        qr.setApplicationId("ABCDEFGHIJKLMNOP");

        assertEquals(99.99, qr.getProductAmount(), 0.001);
        assertEquals(Currency.USD, qr.getCurrency());
        assertEquals("QR-TX-999", qr.getForeignTransactionId());
        assertEquals(Locale.GERMAN, qr.getLanguage());
        assertEquals("ABCDEFGHIJKLMNOP", qr.getApplicationId());
    }
}

