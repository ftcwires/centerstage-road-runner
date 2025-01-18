package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.DcMotor;

import teamCode.Constants;
import teamCode.subsystems.LiftArmSubsystem;
import teamCode.subsystems.SlideArmSubsystem;

public class ArmFudgeFactorUpCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;

    public ArmFudgeFactorUpCommand(LiftArmSubsystem liftArmSubsystem)
    {
        this.m_liftArmSubsystem = liftArmSubsystem;

        addRequirements(m_liftArmSubsystem);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        this.m_liftArmSubsystem.fudgeFactor(Constants.LiftArmConstants.kLiftArmFudgeFactorUp);
    }

    @Override
    public void end(boolean interrupted)
    {
    }

    @Override
    public boolean isFinished()
    {
        return true;
    }
}
