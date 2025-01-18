package teamCode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import teamCode.GoBildaPinpointDriver;

public class PinPointOdometrySubsystem extends SubsystemBase
{
    GoBildaPinpointDriver odo;
    private double oldTime = 0;


    private Pose2D testPose = new Pose2D(DistanceUnit.MM, 100, 100, AngleUnit.DEGREES, 0.0);

    private final GoBildaPinpointDriver m_odo;

    public PinPointOdometrySubsystem(GoBildaPinpointDriver odo)
    {
        this.m_odo = odo;
        this.m_odo.setOffsets(68,-178);
        this.m_odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        this.m_odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        this.m_odo.setPosition(new Pose2D(DistanceUnit.MM, 0,0, AngleUnit.DEGREES, 0.0));
    }

    public void resetOdo()
    {
        this.m_odo.resetPosAndIMU();
    }

    public double[] getDeltaPosition(double target)
    {
        return new double[]
                {
                        (target - m_odo.getPosX()) / target,
                        (target - m_odo.getPosY()) / target
                };
    }
}