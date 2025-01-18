package teamCode;//package teamCode;
//
//import androidx.annotation.NonNull;
//
//import com.arcrobotics.ftclib.drivebase.MecanumDrive;
//import com.arcrobotics.ftclib.hardware.motors.CRServo;
//import com.arcrobotics.ftclib.hardware.motors.Motor;
//import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
//
//import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ImuParameters;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
//
//import java.util.Locale;
//import java.util.function.BooleanSupplier;
//
//import teamCode.autoSubsystems.AutoDriveSubsystem;
//import teamCode.commands.ArmPositionCloseSampleCommand;
//import teamCode.commands.ArmPositionHighBasketCommand;
//import teamCode.commands.ArmPositionHighChamberCommand;
//import teamCode.commands.ArmPositionHomeCommand;
//import teamCode.commands.IntakePivotCommand;
//import teamCode.commands.ScoreSpecimenCommand;
//import teamCode.commands.StingrayArmCommand;
//import teamCode.subsystems.AscentArmSubsystem;
//import teamCode.subsystems.DriveSubsystem;
//import teamCode.subsystems.IntakePivotSubsystem;
//import teamCode.subsystems.IntakeWheelSubsystem;
//import teamCode.subsystems.LiftArmSubsystem;
//import teamCode.subsystems.PinPointOdometrySubsystem;
//import teamCode.subsystems.SlideArmSubsystem;
//
//@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "AutoOdo")
//public class AutoOdo extends LinearOpMode
//{
//    private DcMotor m_fLMotor;
//    private DcMotor m_fRMotor;
//    private DcMotor m_bLMotor;
//    private DcMotor m_bRMotor;
//    private DcMotor m_liftArmMotor;
//    private DcMotor m_slideArmMotor;
//    private CRServo m_intakeWheelServo;
//    private GoBildaPinpointDriver m_odo;
//    private IntakePivotSubsystem m_intakePivotSubsystem;
//    private AscentArmSubsystem m_ascentArmSubsystem;
//    private DriveSubsystem m_driveSubsystem;
//    private LiftArmSubsystem m_liftArmSubsystem;
//    private SlideArmSubsystem m_slideArmSubsystem;
//    private IntakeWheelSubsystem m_intakeWheelSubsystem;
//    private PinPointOdometrySubsystem m_pinPointOdometrySubsystem;
//    private ArmPositionHighBasketCommand m_armPositionHighBasketCommand;
//    private ArmPositionHighChamberCommand m_armPositionHighChamberCommand;
//    private ArmPositionHomeCommand m_armPositionHomeCommand;
//    private ArmPositionCloseSampleCommand m_armPositionCloseSampleCommand;
//    private IntakePivotCommand m_intakePivotCommand;
//    private StingrayArmCommand m_ascentArmCommand;
//   // private ArmFudgeFactorUpCommand m_armFudgeFactorUpCommand;
//    private ScoreSpecimenCommand m_scoreSpecimenCommand;
//    private IMU m_imu;
//    private IMU.Parameters m_imuParameters;
//    public MecanumDrive m_drive;
//
//    double oldTime = 0;
//
//    @Override
//    public void runOpMode()
//    {
//        double oldTime = 0;
//
//        Logic.OpModeType.opMode = "Auto Odo";
//        this.m_fLMotor = hardwareMap.get(DcMotor.class, "frontLeft");
//        this.m_fRMotor = hardwareMap.get(DcMotor.class, "frontRight");
//        this.m_bLMotor = hardwareMap.get(DcMotor.class, "backLeft");
//        this.m_bRMotor = hardwareMap.get(DcMotor.class, "backRight");
//
//        this.m_fLMotor.setDirection(DcMotor.Direction.REVERSE);
//        this.m_bLMotor.setDirection(DcMotor.Direction.REVERSE);
//
//        this.m_fLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        this.m_fRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        this.m_bLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        this.m_bRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        this.m_liftArmMotor = hardwareMap.get(DcMotor.class, "liftArmMotor");
//        this.m_slideArmMotor = hardwareMap.get(DcMotor.class, "slideArmMotor");
//        this.m_intakeWheelServo = new CRServo(hardwareMap, "intakeWheelServo");
//        this.m_intakePivotSubsystem = new IntakePivotSubsystem(hardwareMap, "intakePivotServo");
//        this.m_ascentArmSubsystem = new AscentArmSubsystem(hardwareMap, "ascentArmServo");
//        this.m_odo = hardwareMap.get(GoBildaPinpointDriver.class,"odo");
//
//        /* IMU */
//
//        this.m_imu = hardwareMap.get(IMU.class, "imu");
//        this.m_imuParameters = new IMU.Parameters(new RevHubOrientationOnRobot(
//                RevHubOrientationOnRobot.LogoFacingDirection.UP,
//                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
//        ));
//
//        this.m_imu.initialize(this.m_imuParameters);
//
//        this.m_driveSubsystem = new DriveSubsystem(new MecanumDrive
//                        (
//                                false,
//                                new Motor(hardwareMap, "frontLeft", Motor.GoBILDA.RPM_312),
//                                new Motor(hardwareMap, "frontRight", Motor.GoBILDA.RPM_312),
//                                new Motor(hardwareMap, "backLeft", Motor.GoBILDA.RPM_312),
//                                new Motor(hardwareMap, "backRight", Motor.GoBILDA.RPM_312)
//                        ),
//                m_imu,
//                m_odo
//        );
//        this.m_liftArmSubsystem = new LiftArmSubsystem(this.m_liftArmMotor);
//        this.m_slideArmSubsystem = new SlideArmSubsystem(this.m_slideArmMotor);
//        this.m_intakeWheelSubsystem = new IntakeWheelSubsystem(this.m_intakeWheelServo);
//
//
//        this.m_armPositionHomeCommand = new ArmPositionHomeCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_armPositionCloseSampleCommand = new ArmPositionCloseSampleCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_armPositionHighBasketCommand = new ArmPositionHighBasketCommand(this.m_liftArmSubsystem, this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_armPositionHighChamberCommand = new ArmPositionHighChamberCommand(this.m_liftArmSubsystem,this.m_slideArmSubsystem, this.m_intakePivotSubsystem);
//        this.m_intakePivotCommand = new IntakePivotCommand(this.m_intakePivotSubsystem);
//        this.m_ascentArmCommand = new StingrayArmCommand(this.m_ascentArmSubsystem);
//        //this.m_armFudgeFactorUpCommand = new ArmFudgeFactorUpCommand(this.m_liftArmSubsystem);
//        this.m_scoreSpecimenCommand = new ScoreSpecimenCommand(this.m_liftArmSubsystem);
//
//
//        //Initialize
//        waitForStart();
//        this.m_driveSubsystem.autoHeadingDrive(()-> -50, ()-> 20,()-> 0);
//        m_odo.update();
//        sleep(1000);
//
//        if (true)
//        {
//            double newTime = getRuntime();
//            double loopTime = newTime-oldTime;
//            double frequency = 1/loopTime;
//            oldTime = newTime;
//
//            m_odo.update();
//            telemetry.addData("Status", "Initialized");
//            telemetry.addData("X = ", m_odo.getPosX());
//            telemetry.addData("Y = ", m_odo.getPosY());
//            telemetry.update();
//        }
//    }
//
//    /**
//     * @Params: Wait until parameter event is true.
//     */
//    public void wait(@NonNull BooleanSupplier condition)
//    {
//        while(!condition.getAsBoolean() && opModeIsActive())
//        {
//            telemetry.addData("Waiting: ", condition.getAsBoolean());
//            telemetry.update();
//        }
//    }
//}