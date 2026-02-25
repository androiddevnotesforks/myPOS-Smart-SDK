package com.mypos.smartsdk;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link MyPOSUtil} utility methods that do not depend on the Android framework.
 */
public class MyPOSUtilTest {

    // -------------------------------------------------------------------------
    // isReferenceNumberValid
    // -------------------------------------------------------------------------

    @Test
    public void isReferenceNumberValid_null_returnsTrue() {
        assertTrue(MyPOSUtil.isReferenceNumberValid(null));
    }

    @Test
    public void isReferenceNumberValid_emptyString_returnsTrue() {
        // Empty string matches the regex ("[a-zA-Z0-9\\p{Punct}\\s]+") check
        // but the regex requires at least one char, so empty should fail
        assertFalse(MyPOSUtil.isReferenceNumberValid(""));
    }

    @Test
    public void isReferenceNumberValid_validAlphanumeric_returnsTrue() {
        assertTrue(MyPOSUtil.isReferenceNumberValid("ORDER123"));
    }

    @Test
    public void isReferenceNumberValid_withPunctuation_returnsTrue() {
        assertTrue(MyPOSUtil.isReferenceNumberValid("ORD-001/2024"));
    }

    @Test
    public void isReferenceNumberValid_maxLength50_returnsTrue() {
        String ref = new String(new char[50]).replace('\0', 'A');
        assertTrue(MyPOSUtil.isReferenceNumberValid(ref));
    }

    @Test
    public void isReferenceNumberValid_exceeds50Chars_returnsFalse() {
        String ref = new String(new char[51]).replace('\0', 'A');
        assertFalse(MyPOSUtil.isReferenceNumberValid(ref));
    }

    @Test
    public void isReferenceNumberValid_withSpaces_returnsTrue() {
        assertTrue(MyPOSUtil.isReferenceNumberValid("REF 001"));
    }

    // -------------------------------------------------------------------------
    // isEmailValid
    // -------------------------------------------------------------------------

    @Test
    public void isEmailValid_validEmail_returnsTrue() {
        assertTrue(MyPOSUtil.isEmailValid("user@example.com"));
    }

    @Test
    public void isEmailValid_validEmailWithSubdomain_returnsTrue() {
        assertTrue(MyPOSUtil.isEmailValid("user@mail.example.com"));
    }

    @Test
    public void isEmailValid_missingAtSign_returnsFalse() {
        assertFalse(MyPOSUtil.isEmailValid("userexample.com"));
    }

    @Test
    public void isEmailValid_missingDomain_returnsFalse() {
        assertFalse(MyPOSUtil.isEmailValid("user@"));
    }

    @Test
    public void isEmailValid_missingTld_returnsFalse() {
        assertFalse(MyPOSUtil.isEmailValid("user@example"));
    }

    @Test
    public void isEmailValid_exceeds50Chars_returnsFalse() {
        // local-part + @ + domain that pushes total > 50
        String longEmail = "averylonglocalpartaddress@averylongdomain.example.com";
        assertFalse(MyPOSUtil.isEmailValid(longEmail));
    }

    @Test
    public void isEmailValid_plainString_returnsFalse() {
        assertFalse(MyPOSUtil.isEmailValid("not-an-email"));
    }

    // -------------------------------------------------------------------------
    // isBasicLatin
    // -------------------------------------------------------------------------

    @Test
    public void isBasicLatin_normalAsciiText_returnsTrue() {
        assertTrue(MyPOSUtil.isBasicLatin("Hello World 123!"));
    }

    @Test
    public void isBasicLatin_controlCharacter_returnsFalse() {
        // ASCII 0x01 (SOH) is a control character below 0x20
        assertFalse(MyPOSUtil.isBasicLatin("Hello\u0001World"));
    }

    @Test
    public void isBasicLatin_emptyString_returnsTrue() {
        assertTrue(MyPOSUtil.isBasicLatin(""));
    }

    // -------------------------------------------------------------------------
    // ReferenceType boundary checks
    // -------------------------------------------------------------------------

    @Test
    public void referenceType_isInBound_returnsTrue_forValidValues() {
        assertTrue(ReferenceType.isInBound(ReferenceType.OFF));
        assertTrue(ReferenceType.isInBound(ReferenceType.REFERENCE_NUMBER));
        assertTrue(ReferenceType.isInBound(ReferenceType.INVOICE_ID));
        assertTrue(ReferenceType.isInBound(ReferenceType.PRODUCT_ID));
        assertTrue(ReferenceType.isInBound(ReferenceType.RESERVATION_NUMBER));
    }

    @Test
    public void referenceType_isInBound_returnsFalse_forNegativeValue() {
        assertFalse(ReferenceType.isInBound(-1));
    }

    @Test
    public void referenceType_isInBound_returnsFalse_forValueAboveMax() {
        assertFalse(ReferenceType.isInBound(5));
    }

    @Test
    public void referenceType_isEnabled_returnsFalse_forOff() {
        assertFalse(ReferenceType.isEnabled(ReferenceType.OFF));
    }

    @Test
    public void referenceType_isEnabled_returnsTrue_forReferenceNumber() {
        assertTrue(ReferenceType.isEnabled(ReferenceType.REFERENCE_NUMBER));
    }

    @Test
    public void referenceType_isEnabled_returnsTrue_forReservationNumber() {
        assertTrue(ReferenceType.isEnabled(ReferenceType.RESERVATION_NUMBER));
    }
}

