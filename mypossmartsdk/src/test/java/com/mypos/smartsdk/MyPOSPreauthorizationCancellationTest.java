package com.mypos.smartsdk;

import com.mypos.smartsdk.exceptions.ApplicationIdException;
import com.mypos.smartsdk.exceptions.MissingPreauthCodeException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for {@link MyPOSPreauthorizationCancellation} builder validation and getters.
 * No Android dependencies — plain JVM test runner.
 */
public class MyPOSPreauthorizationCancellationTest {

    // -------------------------------------------------------------------------
    // Happy-path builds
    // -------------------------------------------------------------------------

    @Test
    public void build_validPreauthCode_succeeds() throws Exception {
        MyPOSPreauthorizationCancellation cancellation = MyPOSPreauthorizationCancellation.builder()
                .preauthorizationCode("CANCEL-CODE-01")
                .build();

        assertNotNull(cancellation);
        assertEquals("CANCEL-CODE-01", cancellation.getPreauthorizationCode());
    }

    @Test
    public void build_withApplicationId16Chars_succeeds() throws Exception {
        MyPOSPreauthorizationCancellation cancellation = MyPOSPreauthorizationCancellation.builder()
                .preauthorizationCode("CODE")
                .applicationId("1234567890123456")
                .build();

        assertEquals("1234567890123456", cancellation.getApplicationId());
    }

    @Test
    public void build_nullApplicationId_succeeds() throws Exception {
        MyPOSPreauthorizationCancellation cancellation = MyPOSPreauthorizationCancellation.builder()
                .preauthorizationCode("CODE")
                .applicationId(null)
                .build();

        assertNull(cancellation.getApplicationId());
    }

    // -------------------------------------------------------------------------
    // Preauth code validation
    // -------------------------------------------------------------------------

    @Test(expected = MissingPreauthCodeException.class)
    public void build_nullPreauthCode_throwsMissingPreauthCodeException() throws Exception {
        MyPOSPreauthorizationCancellation.builder()
                .preauthorizationCode(null)
                .build();
    }

    @Test(expected = MissingPreauthCodeException.class)
    public void build_emptyPreauthCode_throwsMissingPreauthCodeException() throws Exception {
        MyPOSPreauthorizationCancellation.builder()
                .preauthorizationCode("")
                .build();
    }

    // -------------------------------------------------------------------------
    // Application ID validation
    // -------------------------------------------------------------------------

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooShort_throwsApplicationIdException() throws Exception {
        MyPOSPreauthorizationCancellation.builder()
                .preauthorizationCode("CODE")
                .applicationId("short")
                .build();
    }

    @Test(expected = ApplicationIdException.class)
    public void build_applicationIdTooLong_throwsApplicationIdException() throws Exception {
        MyPOSPreauthorizationCancellation.builder()
                .preauthorizationCode("CODE")
                .applicationId("12345678901234567") // 17 chars
                .build();
    }

    // -------------------------------------------------------------------------
    // Setter
    // -------------------------------------------------------------------------

    @Test
    public void setPreauthCode_updatesValue() throws Exception {
        MyPOSPreauthorizationCancellation cancellation = MyPOSPreauthorizationCancellation.builder()
                .preauthorizationCode("OLD")
                .build();

        cancellation.setPreauthorizationCode("NEW-CODE");
        assertEquals("NEW-CODE", cancellation.getPreauthorizationCode());
    }
}

