package com.shalmezad.swervedrive;

import org.junit.Before;

import static org.junit.Assert.*;

public class SwerveDriveMathTest {

    private static double DOUBLE_DELTA = 0.001;
    private SwerveDriveConfig testConfig;

    @Before
    public void initObjects() {
        testConfig = new SwerveDriveConfig();
        // To make testing easier, ticks will be 360
        // Meaning ticks = degrees
        testConfig.ticks_per_360 = 360;
        // We're also going to make it a square:
        testConfig.track_width = 20;
        testConfig.wheel_base = 20;
    }


    //region TEST - getDriveValues
    @org.junit.Test
    public void testDriveValuesStraightForward() {
        SwerveHeading heading = new SwerveHeading();
        heading.y = 1.0;
        SwerveDriveResult result = SwerveDriveMath.getDriveValues(testConfig, heading);

        // Speeds should all be 1:
        assertEquals(1.0, result.getFrontLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getFrontRightModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackRightModule().speedPercentage, DOUBLE_DELTA);
        // Ticks should all be 0 (straight forward):
        assertEquals(0, result.getFrontLeftModule().turnHeadingTicks);
        assertEquals(0, result.getFrontRightModule().turnHeadingTicks);
        assertEquals(0, result.getBackLeftModule().turnHeadingTicks);
        assertEquals(0, result.getBackRightModule().turnHeadingTicks);
    }


    @org.junit.Test
    public void testDriveValuesStraightBackwards() {
        SwerveHeading heading = new SwerveHeading();
        heading.y = -1.0;
        SwerveDriveResult result = SwerveDriveMath.getDriveValues(testConfig, heading);

        // Speeds should all be 1:
        assertEquals(1.0, result.getFrontLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getFrontRightModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackRightModule().speedPercentage, DOUBLE_DELTA);
        // Ticks should all be 180 (straight back):
        assertEquals(180, result.getFrontLeftModule().turnHeadingTicks);
        assertEquals(180, result.getFrontRightModule().turnHeadingTicks);
        assertEquals(180, result.getBackLeftModule().turnHeadingTicks);
        assertEquals(180, result.getBackRightModule().turnHeadingTicks);
    }


    @org.junit.Test
    public void testDriveValuesStraightLeft() {
        SwerveHeading heading = new SwerveHeading();
        heading.x = -1.0;
        SwerveDriveResult result = SwerveDriveMath.getDriveValues(testConfig, heading);

        // Speeds should all be 1:
        assertEquals(1.0, result.getFrontLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getFrontRightModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackRightModule().speedPercentage, DOUBLE_DELTA);
        // Ticks should all be -90 (straight left):
        assertEquals(-90, result.getFrontLeftModule().turnHeadingTicks);
        assertEquals(-90, result.getFrontRightModule().turnHeadingTicks);
        assertEquals(-90, result.getBackLeftModule().turnHeadingTicks);
        assertEquals(-90, result.getBackRightModule().turnHeadingTicks);
    }


    @org.junit.Test
    public void testDriveValuesStraightRight() {
        SwerveHeading heading = new SwerveHeading();
        heading.x = 1.0;
        SwerveDriveResult result = SwerveDriveMath.getDriveValues(testConfig, heading);

        // Speeds should all be 1:
        assertEquals(1.0, result.getFrontLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getFrontRightModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackRightModule().speedPercentage, DOUBLE_DELTA);
        // Ticks should all be 90 (straight right):
        assertEquals(90, result.getFrontLeftModule().turnHeadingTicks);
        assertEquals(90, result.getFrontRightModule().turnHeadingTicks);
        assertEquals(90, result.getBackLeftModule().turnHeadingTicks);
        assertEquals(90, result.getBackRightModule().turnHeadingTicks);
    }


    @org.junit.Test
    public void testDriveValuesForwardRight() {
        SwerveHeading heading = new SwerveHeading();
        heading.x = 1.0;
        heading.y = 1.0;
        SwerveDriveResult result = SwerveDriveMath.getDriveValues(testConfig, heading);

        // Speeds should all be 1:
        assertEquals(1.0, result.getFrontLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getFrontRightModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackRightModule().speedPercentage, DOUBLE_DELTA);
        // Ticks should all be 45 (forward right):
        assertEquals(45, result.getFrontLeftModule().turnHeadingTicks);
        assertEquals(45, result.getFrontRightModule().turnHeadingTicks);
        assertEquals(45, result.getBackLeftModule().turnHeadingTicks);
        assertEquals(45, result.getBackRightModule().turnHeadingTicks);
    }

    @org.junit.Test
    public void testDriveValuesRotateClockwise() {
        SwerveHeading heading = new SwerveHeading();
        heading.rotation = 1.0;
        SwerveDriveResult result = SwerveDriveMath.getDriveValues(testConfig, heading);

        // Speeds should all be 1:
        assertEquals(1.0, result.getFrontLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getFrontRightModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackLeftModule().speedPercentage, DOUBLE_DELTA);
        assertEquals(1.0, result.getBackRightModule().speedPercentage, DOUBLE_DELTA);

        // Forward right:
        assertEquals(45, result.getFrontLeftModule().turnHeadingTicks);
        // Backwards right
        assertEquals(135, result.getFrontRightModule().turnHeadingTicks);
        // Forward left
        assertEquals(-45, result.getBackLeftModule().turnHeadingTicks);
        // Backward left:
        assertEquals(-135, result.getBackRightModule().turnHeadingTicks);
    }

    //endregion TEST - getDriveValues

    //region TEST - ticksForDesiredAngle
    @org.junit.Test
    public void testTicksForDesiredAngle90From0() {
        int resultsTicks = SwerveDriveMath.ticksForDesiredAngle(testConfig,0, 90);
        assertEquals(90, resultsTicks);
    }

    @org.junit.Test
    public void testTicksForDesiredAngle90From180() {
        int resultsTicks = SwerveDriveMath.ticksForDesiredAngle(testConfig,180, 90);
        assertEquals(90, resultsTicks);
    }

    @org.junit.Test
    public void testTicksForDesiredAngle90From360() {
        int resultsTicks = SwerveDriveMath.ticksForDesiredAngle(testConfig,360, 90);

        // Shortest path is to go right 90 (360 + 90 = 460)
        assertEquals(450, resultsTicks);
    }

    @org.junit.Test
    public void testTicksForDesiredAngle90From540() {
        //540 = 360 + 180 (so "down")
        int resultsTicks = SwerveDriveMath.ticksForDesiredAngle(testConfig,540, 90);

        // Shortest path is to go left 90 (360 + 90 = 460)
        assertEquals(450, resultsTicks);
    }

    // TODO: Test 90 wrapped negative (ie: -360/-540)
    // TODO: Test -90
    // TODO: Test -90 wrapped negative (ie: -360/-540)
    // TODO: Test -90 wrapped positive (ie: 180/360/540)

    //endregion TEST - ticksForDesiredAngle


}