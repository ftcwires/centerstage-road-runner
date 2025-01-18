package teamCode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ConditionalCommand;

import teamCode.Constants;
import teamCode.subsystems.IntakePivotSubsystem;
import teamCode.subsystems.SlideArmSubsystem;
import teamCode.subsystems.LiftArmSubsystem;

public class ArmPositionLowBasketCommand extends CommandBase
{
    private LiftArmSubsystem m_liftArmSubsystem;
    private SlideArmSubsystem m_slideArmSubsystem;
    private IntakePivotSubsystem m_intakePivotSubsystem;

    public ArmPositionLowBasketCommand(LiftArmSubsystem liftArmSubsystem,
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
        this.m_liftArmSubsystem.liftArm(Constants.LiftArmConstants.kLiftArmLowBasket);
        if (m_liftArmSubsystem.atTarget(Constants.LiftArmConstants.kLiftArmLowBasket))
        {
            this.m_slideArmSubsystem.slideArm(Constants.SlideArmConstants.kSlideArmLowBasket);
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
