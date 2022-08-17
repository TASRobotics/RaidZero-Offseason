package raidzero.robot.teleop;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import raidzero.robot.submodules.Swerve;
import raidzero.robot.utils.JoystickUtils;

public class Teleop {

    private static Teleop instance = null;
    private static XboxController p1 = new XboxController(0);
    private static XboxController p2 = new XboxController(1);

    private static final Swerve swerve = Swerve.getInstance();

    private static boolean shift1 = false;
    private static boolean shift2 = false;
    private static double intakeOut = 0;
    private boolean autoDisabled = true;

    private double ramprate = 0.0;
    // private SlewRateLimiter slew = new SlewRateLimiter(rateLimit)

    public static Teleop getInstance() {
        if (instance == null) {
            instance = new Teleop();
        }
        return instance;
    }

    public void onStart() {
        swerve.zero();
    }

    /**
     * Continuously loops in teleop.
     */
    public void onLoop() {
        /**
         * shared controls
         */

        /**
         * p1 controls
         */
        p1Loop(p1);
        /**
         * p2 controls
         */
        // p2Loop(p2);
    }

    private void p1Loop(XboxController p) {
        /**
         * Disable auto
         */

        if(p.getRawButton(3))autoDisabled = true;
        if(p.getRawButton(4))autoDisabled = false;

        /**
         * Drive
        */
        // boolean turning = p.getRawButton(12);
        // swerve.fieldOrientedDrive(
        //     JoystickUtils.deadband(p.getLeftX() * (p.getRawButton(1) ? 1 : 0.5)),
        //     JoystickUtils.deadband(p.getLeftY() * (p.getRawButton(1) ? -1 : -0.5)),
        //     //JoystickUtils.deadband(p.getRightX()));
        //     (turning) ? JoystickUtils.deadband(p.getRawAxis(2)) * (p.getRawButton(1) ? 0.5 : 0.25) : 0);

        // double leftY = JoystickUtils.deadband(Math.pow(p.getLeftY(), 3))*0.5;
        // double leftX = JoystickUtils.deadband(Math.pow(p.getLeftX(), 3))*0.5;
        // double rightX = JoystickUtils.deadband(Math.pow(p.getRightX(), 3))*0.5;

        double leftY = Math.pow(p.getLeftY(), 3)*0.5;
        double leftX = Math.pow(p.getLeftX(), 3)*0.5;
        double rightX = Math.pow(p.getRightX(), 3)*0.5;


        swerve.fieldOrientedDrive(
            leftY, 
            leftX, 
            rightX
        );

        SmartDashboard.putNumber("left y", leftY);
        SmartDashboard.putNumber("left x", leftX);
        SmartDashboard.putNumber("right x", rightX);



        // SmartDashboard.putNumber("Ramp rate", ramprate);
        ramprate = SmartDashboard.getNumber("Ramp rate", ramprate);
        swerve.setOpenLoopRampRate(ramprate);

        SmartDashboard.putNumber("testing", ramprate);

        /**
         * DO NOT CONTINUOUSLY CALL THE ZERO FUNCTION its not that bad but the absolute encoders are
         * not good to PID off of so a quick setting of the relative encoder is better
         */
        if (p.getRawButton(2)) {
            swerve.zero();
            return;
        }       
    }
}
