package myRobotsWindows;
import robocode.*;
import robocode.DeathEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
//import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.*;

public class BOT_CHICO extends Robot {
	double corner = 0;
    boolean peek; // Don't turn if there's a robot there

	public void run() {
		// Cores do robo
		setBodyColor(Color.pink);
		setGunColor(Color.pink);
		setRadarColor(Color.pink);
		setBulletColor(Color.pink);
		setScanColor(Color.pink);
		
		//Altura do campo de batalha
		double altura = getBattleFieldHeight();
		//Largura do campo de batalha
		double largura = getBattleFieldWidth();		

		// Mover o robo para o cantp
		goCorner();
		
		//Virar 90 graus para esquerda 
		turnLeft(90);



		while (true) {
			// olhar antes do "ahead()" terminar.
			peek = true;
			// Mover o robo para frente
			ahead(5000);
			// Não olhar mais
			peek = false;
			// Virar 90 graus para esquerda
			turnLeft(90);
		}
	}

	/**
	 * Ir para o canto superior esquerdo
	 */
	public void goCorner() {
		//vire-se para a parede para a "direita" do seu canto desejado.
		//getHeading() retorna o cabeçalho do robô, em graus (0 <= getheading () <360)
		turnRight(normalRelativeAngleDegrees(corner - getHeading()));
		//Mover o robo para trás
		ahead(5000);
		// Virar o robo para a direita
		turnLeft(90);
		// Mover para o canto
		ahead(5000);
		// virar a arma para o ponto de partida
		turnGunLeft(90);
	}

	/**
	 * onScannedRobot:  Pare e atire
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		smartFire(e.getDistance());
	}

	/**
	 * smartFire:  método de fogo personalizado que determina o poder de fogo baseado na vida do robo
	 */
	public void smartFire(double robotDistance) {
		if(getEnergy() < 15){
			fire(1);
		}else {
			fire(Rules.MAX_BULLET_POWER);
		}
		
		//Olhar antes de virar		
		if (peek) {
			scan();
		}
	
	}
	
	/**
	 * Método que é ativado quando o robo bate em outro robo
	 */
	public void onHitRobot(HitRobotEvent e) {
		// se ele estiver na nossa frente, volte um pouco.
		if (e.getBearing() > -90 && e.getBearing() < 90) {
			turnGunRight(90);
			fire(3);
			turnRight(90);
			back(100);
			turnGunLeft(90);
		} // caso contrário, ele está atrás de nós, então vá em frente um pouco.
		else {
			turnGunLeft(90);
			fire(3);
			turnLeft(90);
			ahead(100);
			turnGunRight(90);
		}
	}
	
	/**
	 * Método que é ativado quando o robo leva um tiro
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		//turnRight(90);
		//ahead(5000);
	}
	
	//normalize um ângulo para um ângulo relativo.	
	public double normalRelativeAngleDegrees(double angle) {
        if (angle > -180 && angle <= 180) {
            return angle;
        }
 
        double fixedAngle = angle;
        while (fixedAngle <= -180) {
            fixedAngle += 360;
        }
 
        while (fixedAngle > 180) {
            fixedAngle -= 360;
        }
 
        return fixedAngle;
    }

}
		