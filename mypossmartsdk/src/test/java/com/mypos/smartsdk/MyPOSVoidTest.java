package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.MissingAuthCodeException;
import com.mypos.smartsdk.exceptions.MissingDateTimeException;
import com.mypos.smartsdk.exceptions.MissingSTANException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link MyPOSVoid} builder validation and getters.
 * No Android dependencies — plain JVM test runner.
 */
public class MyPOSVoidTest {

    // -------------------------------------------------------------------------
    // Happy-path: void last transaction (no STAN/authCode/dateTime required)
    // -------------------------------------------------------------------------

    @Test
    public void build_voidLastTransaction_succeeds() throws Exception {
        MyPOSVoid voidTx = MyPOSVoid.builder()
                .voidLastTransactionFlag(true)
                .build();

        assertNotNull(voidTx);
        assertTrue(voidTx.getVoidLastTransactionFlag());
    }

    @Test
    public void build_voidLastTransaction_stanAuthDateTimeNotRequired() throws Exception {
        // When voidLastTransactionFlag=true, STAN/authCode/dateTime are ignored
        MyPOSVoid voidTx = MyPOSVoid.builder()
                .voidLastTransactionFlag(true)
                .build();

        assertEquals(0, voidTx.getSTAN());
        assertNull(voidTx.getAuthCode());
        assertNull(voidTx.getDateTime());
    }

    // -------------------------------------------------------------------------
    // Happy-path: specific transaction void (STAN + authCode + dateTime required)
    // -------------------------------------------------------------------------

    @Test
    public void build_specificVoid_withAllFields_succeeds() throws Exception {
        MyPOSVoid voidTx = MyPOSVoid.builder()
                .voidLastTransactionFlag(false)
                .STAN(123456)
                .authCode("AUTH99")
                .dateTime("20260101120000")
                .build();

        assertNotNull(voidTx);
        assertFalse(voidTx.getVoidLastTransactionFlag());
        assertEquals(123456, voidTx.getSTAN());
        assertEquals("AUTH99", voidTx.getAuthCode());
        assertEquals("20260101120000", voidTx.getDateTime());
    }

    @Test
    public void build_applicationIdAlphanumeric_succeeds() throws Exception {
        MyPOSVoid voidTx = MyPOSVoid.builder()
                .voidLastTransactionFlag(true)
                .applicationId("1234567890123456")
                .build();

        assertEquals("1234567890123456", voidTx.getApplicationId());
    }

    @Test
    public void build_nullApplicationId_succeeds() throws Exception {
        MyPOSVoid voidTx = MyPOSVoid.builder()
                .voidLastTransactionFlag(true)
                .applicationId(null)
                .build();

        assertNull(voidTx.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // STAN validation (only when voidLastTransactionFlag = false)
    // -------------------------------------------------------------------------

    @Test(expected = MissingSTANException.class)
    public void build_specificVoid_zeroStan_throwsMissingSTANException() throws Exception {
        MyPOSVoid.builder()
                .voidLastTransactionFlag(false)
                .STAN(0)
                .authCode("AUTH01")
                .dateTime("20260101120000")
                .build();
    }

    // -------------------------------------------------------------------------
    // AuthCode validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingAuthCodeException.class)
    public void build_specificVoid_nullAuthCode_throwsMissingAuthCodeException() throws Exception {
        MyPOSVoid.builder()
                .voidLastTransactionFlag(false)
                .STAN(100)
                .authCode(null)
                .dateTime("20260101120000")
                .build();
    }

    // -------------------------------------------------------------------------
    // DateTime validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingDateTimeException.class)
    public void build_specificVoid_nullDateTime_throwsMissingDateTimeException() throws Exception {
        MyPOSVoid.builder()
                .voidLastTransactionFlag(false)
                .STAN(100)
                .authCode("AUTH01")
                .dateTime(null)
                .build();
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdInvalidChars_throwsApplicationIdException() throws Exception {
        // Space is not allowed (not alphanumeric/punctuation)
        MyPOSVoid.builder()
                .voidLastTransactionFlag(true)
                .applicationId("invalid id")
                .build();
    }

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooLong_throwsApplicationIdException() throws Exception {
        // 51 chars — exceeds the 50-char limit
        MyPOSVoid.builder()
                .voidLastTransactionFlag(true)
                .applicationId("123456789012345678901234567890123456789012345678901")
                .build();
    }

    @Test
    public void build_applicationIdShortValid_succeeds() throws Exception {
        MyPOSVoid voidTx = MyPOSVoid.builder()
                .voidLastTransactionFlag(true)
                .applicationId("AB-1")
                .build();

        assertEquals("AB-1", voidTx.getApplicationId());
    }

    @Test
    public void build_applicationIdWithPunctuation_succeeds() throws Exception {
        MyPOSVoid voidTx = MyPOSVoid.builder()
                .voidLastTransactionFlag(true)
                .applicationId("my.app_id-01,v2")
                .build();

        assertEquals("my.app_id-01,v2", voidTx.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Setters
    // -------------------------------------------------------------------------

    @Test
    public void setters_updateFields() throws Exception {
        MyPOSVoid voidTx = MyPOSVoid.builder()
                .voidLastTransactionFlag(false)
                .STAN(999)
                .authCode("AUTH01")
                .dateTime("20260101000000")
                .build();

        voidTx.setSTAN(111);
        voidTx.setAuthCode("AUTH02");
        voidTx.setDateTime("20261231235959");
        voidTx.setVoidLastTransactionFlag(true);

        assertEquals(111, voidTx.getSTAN());
        assertEquals("AUTH02", voidTx.getAuthCode());
        assertEquals("20261231235959", voidTx.getDateTime());
        assertTrue(voidTx.getVoidLastTransactionFlag());
    }
}

