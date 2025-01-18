package teamCode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.function.DoubleSupplier;

import teamCode.GoBildaPinpointDriver;

public class DriveSubsystem extends SubsystemBase
{
    public MecanumDrive m_drive;
    private DcMotor m_fLMotor;
    private DcMotor m_fRMotor;
    private DcMotor m_bLMotor;
    private DcMotor m_bRMotor;

    private int m_fLPos;
    private int m_fRPos;
    private int m_bLPos;
    private int m_bRPos;

    private Orientation m_lastRecordedAngle;
    private double m_currentAngle;
    private double error;

    IMU m_imu;

    private PinPointOdometrySubsystem m_pinPointOdometrySubsystem;

    public DriveSubsystem(MecanumDrive drive, IMU imu)
    {
        this.m_drive = drive;
        this.m_lastRecordedAngle = new Orientation();
        this.m_currentAngle = 0.0;
        this.m_imu = imu;


    }

//    public DriveSubsystem(MecanumDrive drive, IMU imu, GoBildaPinpointDriver pinPoint)
//    {
//        this.m_drive = drive;
//        this.m_lastRecordedAngle = new Orientation();
//        this.m_currentAngle = 0.0;
//        this.m_imu = imu;
//        this.m_pinPointOdometrySubsystem = new PinPointOdometrySubsystem(pinPoint);
//
//    }

    public void headingDrive(double leftX, double leftY, double rightX, double rightY)
    {
        m_drive.driveFieldCentric
                (
                        leftX * leftX * leftX * -1,
                        leftY * leftY * leftY * -1,
                        getJoystickAngle(rightX, rightY),
                        this.m_imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)
                );
//        System.out.println("Error: " + error);
//        getTurnPower(rightX, rightY);

//        System.out.println("Error: " + error);
//        System.out.println("Error: " + error);
//        System.out.println(this.m_imu.getRobotYawPitchRollAngles().getYaw());
//        System.out.println("Turn angle: " + Math.atan2(rightX, rightY * -1) * -1 * (180 / Math.PI));
    }

    public void autoHeadingDrive (DoubleSupplier targetX, DoubleSupplier targetY, DoubleSupplier targetAngle)
    {
        m_drive.driveFieldCentric
                (
                        this.m_pinPointOdometrySubsystem.getDeltaPosition(targetX.getAsDouble())[0],
                        this.m_pinPointOdometrySubsystem.getDeltaPosition(targetY.getAsDouble())[1],
                        getTurnPower(true,0),
                        this.m_imu.getRobotYawPitchRollAngles().getYaw()
                );
    }

    public double getJoystickAngle (double rightX, double rightY)
    {
        return getTurnPower(rightX > 0.5 || rightX < -0.5 || rightY > 0.5 || rightY < -0.5, Math.atan2(rightX, rightY * -1) * -1 * (180 / Math.PI));
    }


    public double getTurnPower(boolean deadband, double angle)
    {
        turnTo(deadband, angle);
        System.out.println("Running!");

        if (Math.abs(error) > 6)
        {
            double motorPower = 0.5;
            error = error - getAngle();
//            this.m_robot.driveWithMotorPowers(motorPower, -motorPower, motorPower, -motorPower);
//            this.m_drive.driveWithMotorPowers(motorPower, -motorPower, motorPower, -motorPower);
            return motorPower * error / 100 + (0.1 * (error / Math.abs(error)));
        }
        else
        {
            return 0.0;
        }
    }

    public void turnTo(boolean deadband, double angle)
    {
        Orientation orientation = m_imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double desiredAngle;
        if (deadband)
        {
            desiredAngle = angle;
        }
        else
        {
            desiredAngle = orientation.firstAngle;
        }

        error = desiredAngle - orientation.firstAngle;

        if(error > 180)
        {
            error -= 360;
        }
        else if (error < -180)
        {
            error += 360;
        }

        turn(error, desiredAngle);
    }

    public void turn(double degrees, double desiredAngle)
    {
        resetAngle();

        error = degrees;
    }
    public void resetAngle()
    {
        m_lastRecordedAngle = m_imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); // .getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.YXZ, BNO055IMU.AngleUnit.DEGREES.toAngleUnit());
        m_currentAngle = 0;
    }

    public double getAngle()
    {
        Orientation orientation = this.m_imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = orientation.firstAngle - m_lastRecordedAngle.firstAngle;

        if (deltaAngle > 180)
        {
            deltaAngle -= 360;
        }
        else if (deltaAngle <= -180)
        {
            deltaAngle += 360;
        }

        m_currentAngle += deltaAngle;
        m_lastRecordedAngle = orientation;
        return m_currentAngle;
    }
}