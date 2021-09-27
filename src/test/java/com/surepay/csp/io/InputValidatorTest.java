package com.surepay.csp.io;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InputValidatorTest {

    private InputValidator inputValidator;

    @Before
    public void init() {
        inputValidator = new InputValidator();
    }

    @Test
    public void testValidInput() {
        Assert.assertTrue(inputValidator.validateInput("194261", "NL91RABO0315273637", "21.6", "-41.83", "-20.23"));
    }

    @Test
    public void testInvalidReference() {
        Assert.assertFalse(inputValidator.validateInput("1a42b1", "NL91RABO0315273637", "21.6", "-41.83", "-20.23"));
    }

    @Test
    public void testInvalidStartBalance() {
        Assert.assertFalse(inputValidator.validateInput("194261", "NL91RABO0315273637", "+21.6", "-41.83", "-20.23"));
    }

    @Test
    public void testInvalidMutation() {
        Assert.assertFalse(inputValidator.validateInput("194261", "NL91RABO0315273637", "21.6", "++41.83", "-20.23"));
    }

    @Test
    public void testInvalidEndBalance() {
        Assert.assertFalse(inputValidator.validateInput("194261", "NL91RABO0315273637", "21.6", "-41.83", "+20.23"));
    }
}
