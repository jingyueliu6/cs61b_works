public class NBody {
    public static double readRadius(String filePath){
        In in = new In(filePath);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filePath) {
        In in = new In(filePath);
        int num = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[num];
        for(int i = 0; i<num; i++){
            double pX = in.readDouble();
            double pY = in.readDouble();
            double vX = in.readDouble();
            double vY = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(pX, pY, vX, vY, mass, img);
        }
        return planets;
    }

    public static void main(String[] args){
        //Double.parseDouble(text);
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        int num = planets.length;

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);

        double time = 0;
        while(time<T){
            StdDraw.clear();
            double[] xForces = new double[num];
            double[] yForces = new double[num];
            for(int i =0; i<num; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            for(int i =0; i<num; i++){
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");
            for(Planet planet: planets){
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }
}
