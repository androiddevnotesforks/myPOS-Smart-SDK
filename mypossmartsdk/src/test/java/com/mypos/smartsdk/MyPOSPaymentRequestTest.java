package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.InvalidAmountException;
import com.mypos.smartsdk.exceptions.MissingCurrencyException;
import com.mypos.smartsdk.exceptions.MissingRecipientException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for {@link MyPOSPaymentRequest} builder validation and getters.
 * No Android dependencies — plain JVM test runner.
 */
public class MyPOSPaymentRequestTest {

    // -------------------------------------------------------------------------
    // Happy-path builds
    // -------------------------------------------------------------------------

    @Test
    public void build_withGSMRecipient_succeeds() throws Exception {
        MyPOSPaymentRequest request = MyPOSPaymentRequest.builder()
                .productAmount(20.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988123456")
                .build();

        assertNotNull(request);
        assertEquals(20.00, request.getProductAmount(), 0.001);
        assertEquals(Currency.EUR, request.getCurrency());
        assertEquals("+35988123456", request.getGSM());
    }

    @Test
    public void build_withEmailRecipient_succeeds() throws Exception {
        MyPOSPaymentRequest request = MyPOSPaymentRequest.builder()
                .productAmount(15.00)
                .currency(Currency.USD)
                .language(Language.ENGLISH)
                .eMail("payer@example.com")
                .build();

        assertNotNull(request);
        assertEquals("payer@example.com", request.getEMail());
    }

    @Test
    public void build_withAllOptionalFields_succeeds() throws Exception {
        MyPOSPaymentRequest request = MyPOSPaymentRequest.builder()
                .productAmount(100.00)
                .currency(Currency.BGN)
                .language(Language.BULGARIAN)
                .GSM("+35988000000")
                .reason("Invoice #001")
                .recipientName("John Doe")
                .requestCode("REQ-001")
                .expiryDays(7)
                .build();

        assertEquals("Invoice #001", request.getReason());
        assertEquals("John Doe", request.getRecipientName());
        assertEquals("REQ-001", request.getRequestCode());
        assertEquals(Integer.valueOf(7), request.getExpiryDays());
    }

    @Test
    public void build_withApplicationId16Chars_succeeds() throws Exception {
        MyPOSPaymentRequest request = MyPOSPaymentRequest.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .applicationId("1234567890123456")
                .build();

        assertEquals("1234567890123456", request.getApplicationId());
    }

    @Test
    public void build_nullApplicationId_succeeds() throws Exception {
        MyPOSPaymentRequest request = MyPOSPaymentRequest.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .applicationId(null)
                .build();

        assertNull(request.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Amount validation
    // -------------------------------------------------------------------------

    /**
     * When productAmount is null the builder's condition evaluates Double.isNaN(null),
     * which auto-unboxes and throws NullPointerException (a known quirk of the builder).
     * Zero and negative amounts correctly throw InvalidAmountException.
     */
    @Test(expected = NullPointerException.class)
    public void build_nullAmount_throwsNullPointerException() throws Exception {
        MyPOSPaymentRequest.builder()
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_zeroAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPaymentRequest.builder()
                .productAmount(0.0)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .build();
    }

    @Test(expected = InvalidAmountException.class)
    public void build_negativeAmount_throwsInvalidAmountException() throws Exception {
        MyPOSPaymentRequest.builder()
                .productAmount(-10.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .build();
    }

    // -------------------------------------------------------------------------
    // Currency validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingCurrencyException.class)
    public void build_nullCurrency_throwsMissingCurrencyException() throws Exception {
        MyPOSPaymentRequest.builder()
                .productAmount(10.00)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .build();
    }

    // -------------------------------------------------------------------------
    // Recipient validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingRecipientException.class)
    public void build_noRecipient_throwsMissingRecipientException() throws Exception {
        MyPOSPaymentRequest.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .build();
    }

    @Test(expected = MissingRecipientException.class)
    public void build_emptyGSMAndNullEmail_throwsMissingRecipientException() throws Exception {
        MyPOSPaymentRequest.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("")
                .eMail(null)
                .build();
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdInvalidChars_throwsApplicationIdException() throws Exception {
        // Space is not allowed
        MyPOSPaymentRequest.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .applicationId("invalid id")
                .build();
    }

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooLong_throwsApplicationIdException() throws Exception {
        // 51 chars — exceeds the 50-char limit
        MyPOSPaymentRequest.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .applicationId("123456789012345678901234567890123456789012345678901")
                .build();
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    @Test
    public void setters_updateFields() throws Exception {
        MyPOSPaymentRequest request = MyPOSPaymentRequest.builder()
                .productAmount(10.00)
                .currency(Currency.EUR)
                .language(Language.ENGLISH)
                .GSM("+35988000000")
                .build();

        request.setProductAmount(50.00);
        request.setCurrency(Currency.RON);
        request.setLanguage(Language.ROMANIAN);
        request.setGSM("+40721000000");
        request.setEMail("new@payer.com");
        request.setReason("Updated reason");
        request.setRecipientName("Jane Doe");
        request.setRequestCode("REQ-999");
        request.setExpiryDays(14);

        assertEquals(50.00, request.getProductAmount(), 0.001);
        assertEquals(Currency.RON, request.getCurrency());
        assertEquals(Language.ROMANIAN, request.getLanguage());
        assertEquals("+40721000000", request.getGSM());
        assertEquals("new@payer.com", request.getEMail());
        assertEquals("Updated reason", request.getReason());
        assertEquals("Jane Doe", request.getRecipientName());
        assertEquals("REQ-999", request.getRequestCode());
        assertEquals(Integer.valueOf(14), request.getExpiryDays());
    }
}

