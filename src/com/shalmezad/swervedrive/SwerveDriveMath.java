package com.shalmezad.swervedrive;

public class SwerveDriveMath {
    public static SwerveDriveResult getDriveValues(SwerveDriveConfig config, SwerveHeading desired)
    {
        SwerveDriveResult result = new SwerveDriveResult();

        double A = desired.x - desired.rotation * (config.wheel_base / config.getRadius());
        double B = desired.x + desired.rotation * (config.wheel_base / config.getRadius());
        double C = desired.y - desired.rotation * (config.track_width / config.getRadius());
        double D = desired.y + desired.rotation * (config.track_width / config.getRadius());

        // calculate speed/rotation for each wheel
        result.getFrontRightModule().speedPercentage = Math.sqrt(Math.pow(B, 2) + Math.pow(C, 2));
        result.getFrontRightModule().setTurnHeadingDegrees(config, Math.toDegrees(Math.atan2(B, C)));

        result.getFrontLeftModule().speedPercentage = Math.sqrt(Math.pow(B, 2) + Math.pow(D, 2));
        result.getFrontLeftModule().setTurnHeadingDegrees(config, Math.toDegrees(Math.atan2(B, D)));

        result.getBackLeftModule().speedPercentage = Math.sqrt(Math.pow(A, 2) + Math.pow(D, 2));
        result.getBackLeftModule().setTurnHeadingDegrees(config, Math.toDegrees(Math.atan2(A, D)));

        result.getBackRightModule().speedPercentage = Math.sqrt(Math.pow(A, 2) + Math.pow(C, 2));
        result.getBackRightModule().setTurnHeadingDegrees(config, Math.toDegrees(Math.atan2(A, C)));

        result.normalizeSpeeds();

        return result;
    }

    /**
     * Returns the ticks of where you should go to get the desired angle from the current angle
     * This takes into account that ticks accumulate
     */
    public static int ticksForDesiredAngle(SwerveDriveConfig config,
                                           double currentTicks,
                                           double desiredDegrees)
    {
        return 0;
    }

}
