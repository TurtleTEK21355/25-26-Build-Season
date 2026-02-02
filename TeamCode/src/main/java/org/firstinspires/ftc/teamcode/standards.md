# THIS NEEDS TO BE UPDATED FOR STATE BOT
# Robot Motor/Servo Control Standards
Defines how power manipulates each motor/servo on the robot.

## Drivetrain
- Positive values move motor forwards relative to the robot.
- All positive = go forward
- positive strafe = strafe right
- positive turn = turn right/clockwise
## OTOS Sensor
- Positive Y = towards obelisk/scoring side
- Positive X = audience right/red goal
- Positive H = clockwise
- all units of distance are in inches
- 0,0 on the field is the center of the field
- that means blue goal corner =
- red goal corner = 

## Shooter System
### FlyWheel
- Positive value will shoot ball out
### Intake/Hopper
- Positive value pulls balls through shooter system (ball go intake in)
### Ball Gate
- Position 0 opens the gate to be parallel to the movement of the balls
- Another position (ex. 0.25) closes the gate to be perpendicular to the movement of the balls

## Partner Park System
- Sending positive power to both viper slides pushes the plate downwards, lifting the robot.