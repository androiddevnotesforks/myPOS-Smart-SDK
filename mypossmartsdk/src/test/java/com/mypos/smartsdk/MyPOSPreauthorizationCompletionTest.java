package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;
import com.mypos.smartsdk.exceptions.MissingPreauthCodeException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for {@link MyPOSPreauthorizationCompletion} builder validation and getters.
 * No Android dependencies — plain JVM test runner.
 */
public class MyPOSPreauthorizationCompletionTest {

    // -------------------------------------------------------------------------
    // Happy-path builds
    // -------------------------------------------------------------------------

    @Test
    public void build_minimalValid_succeeds() throws Exception {
        MyPOSPreauthorizationCompletion completion = MyPOSPreauthorizationCompletion.builder()
                .productAmount(25.00)
                .currency(Currency.EUR)
                .preauthorizationCode("PREAUTH-ABC")
                .build();

        assertNotNull(completion);
        assertEquals(25.00, completion.getProductAmount(), 0.001);
        assertEquals(Currency.EUR, completion.getCurrency());
        assertEquals("PREAUTH-ABC", completion.getPreauthorizationCode());
    }

    @Test
    public void build_withApplicationId16Chars_succeeds() throws Exception {
        MyPOSPreauthorizationCompletion completion = MyPOSPreauthorizationCompletion.builder()
                .productAmount(10.00)
                .currency(Currency.USD)
                .preauthorizationCode("CODE123")
                .applicationId("1234567890123456")
                .build();

        assertEquals("1234567890123456", completion.getApplicationId());
    }

    @Test
    public void build_nullApplicationId_succeeds() throws Exception {
        MyPOSPreauthorizationCompletion completion = MyPOSPreauthorizationCompletion.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .preauthorizationCode("CODE")
                .applicationId(null)
                .build();

        assertNull(completion.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Amount validation
    // -------------------------------------------------------------------------

    @Test(expected = InvalidAmountException.class)
    public void build_nullAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPreauthorizationCompletion.builder()
                .currency(Currency.EUR)
                .preauthorizationCode("CODE")
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_zeroAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPreauthorizationCompletion.builder()
                .productAmount(0.0)
                .currency(Currency.EUR)
                .preauthorizationCode("CODE")
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_negativeAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPreauthorizationCompletion.builder()
                .productAmount(-1.00)
                .currency(Currency.EUR)
                .preauthorizationCode("CODE")
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_nanAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPreauthorizationCompletion.builder()
                .productAmount(Double.NaN)
                .currency(Currency.EUR)
                .preauthorizationCode("CODE")
                .build();
    }

    // -------------------------------------------------------------------------
    // Currency validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingCurrencyException.class)
    public void build_nullCurrency_throwsMissingCurrencyException() throws Exception {
        MyPOSPreauthorizationCompletion.builder()
                .productAmount(10.00)
                .preauthorizationCode("CODE")
                .build();
    }

    // -------------------------------------------------------------------------
    // Preauth code validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingPreauthCodeException.class)
    public void build_nullPreauthCode_throwsMissingPreauthCodeException() throws Exception {
        MyPOSPreauthorizationCompletion.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .preauthorizationCode(null)
                .build();
    }

    @Test(expected = MissingPreauthCodeException.class)
    public void build_emptyPreauthCode_throwsMissingPreauthCodeException() throws Exception {
        MyPOSPreauthorizationCompletion.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .preauthorizationCode("")
                .build();
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdInvalidChars_throwsApplicationIdException() throws Exception {
        // Space is not allowed
        MyPOSPreauthorizationCompletion.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .preauthorizationCode("CODE")
                .applicationId("invalid id")
                .build();
    }

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooLong_throwsApplicationIdException() throws Exception {
        // 51 chars — exceeds the 50-char limit
        MyPOSPreauthorizationCompletion.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .preauthorizationCode("CODE")
                .applicationId("123456789012345678901234567890123456789012345678901")
                .build();
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    @Test
    public void setters_updateFields() throws Exception {
        MyPOSPreauthorizationCompletion completion = MyPOSPreauthorizationCompletion.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .preauthorizationCode("OLD-CODE")
                .build();

        completion.setProductAmount(55.00);
        completion.setCurrency(Currency.CHF);
        completion.setPreauthorizationCode("NEW-CODE");

        assertEquals(55.00, completion.getProductAmount(), 0.001);
        assertEquals(Currency.CHF, completion.getCurrency());
        assertEquals("NEW-CODE", completion.getPreauthorizationCode());
    }
}

