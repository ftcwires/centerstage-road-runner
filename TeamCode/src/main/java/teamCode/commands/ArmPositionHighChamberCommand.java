package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import teamCode.Constants;
import teamCode.Logic;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.SlideArmSubsystem;
import teamCode.subsystems.LiftArmSubsystem;

public class ArmPositionHighChamberCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsystem;

    public ArmPositionHighChamberCommand(LiftArmSubsystem liftArmSubsystem,
                                         SlideArmSubsystem slideArmSubsystem, IntakePivotSubsystem intakePivotSubsystem)
    {
        this.m_liftArmSubsystem = liftArmSubsystem;
        this.m_slideArmSubsystem = slideArmSubsystem;
        this.m_intakePivotSubsystem = intakePivotSubsystem;

        addRequirements(m_liftArmSubsystem, m_slideArmSubsystem, m_intakePivotSubsystem);
    }

    @Override
    public void initialize()
    {
    }

    @Override
    public void execute()
    {
        this.m_liftArmSubsystem.liftArm(Constants.LiftArmConstants.kLiftArmHighChamber);
        if (m_liftArmSubsystem.atTarget(Constants.LiftArmConstants.kLiftArmHighChamber))
        {
            this.m_slideArmSubsystem.slideArm(Constants.SlideArmConstants.kSlideArmHighChamber);
            this.m_intakePivotSubsystem.pivotIntake(Constants.PivotIntakeConstants.kIntakePivotScore);
        }
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
