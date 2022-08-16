package raidzero.robot.teleop;

import edu.wpi.first.wpilibj.XboxController;

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

        swerve.fieldOrientedDrive(
            JoystickUtils.deadband(p.getLeftX()), 
            JoystickUtils.deadband(p.getLeftY()), 
            JoystickUtils.deadband(p.getRightX())
        );

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
