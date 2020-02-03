/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Disabled
@Autonomous(name="Concept: VuMark Id", group ="Concept")

public class VuMarkTest extends LinearOpMode {
    Hardware robot = new Hardware();

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, true);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        
        parameters.vuforiaLicenseKey = "AfmBbcz/////AAAAGbLGg++zzk4MiOrcPTc3t9xQj3QHfISJprebOgt5JJ4+83xtFO+ApGlI3GVY/aMgCpoGEIzaJse9sXiYDiLYpJQlGDX765tWJUrqM+pzqLxVXjWA1J6c968/YqYq74Vq5emNxGHj5SF3HP3m43Iq/YYgkSdMv4BR+RThPPnIIzrbAjEAHHtMgH7vVh036+bcw9UqBfSdD/IBqrKpJLERn5+Qi/4Q4EoReCC0CTDfZ+LcY0rUur0QZRkMpxx/9s4eCgIU+qfOcSlBvjoX7QAQ2MImUME1y5yJiyaWueamnhRBOwERGBuDKyGp4eBWp4i3esJcplrWYovjzPg9fL7Thy8v9KnrHy22PUFAYY+1vjKp";
        
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);


        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary GAMER MOMENTS 2020


        waitForStart();

        relicTrackables.activate();
        //robot.jewelSensor.enableLed(true);
        while (opModeIsActive()) {


           /* RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
            } else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.addData(getColor()+"",robot.jewelSensor.argb());
            telemetry.update();

        }
    }

   public int getColor(){
       if(robot.jewelSensor.red() > 150){
          return 1; //1 = red
        } else if(robot.jewelSensor.blue() > 150) {
            return 0; // 0 = blue
        } else {
            return -1; // -1 is abort
        }
    }*/
        }}
}
