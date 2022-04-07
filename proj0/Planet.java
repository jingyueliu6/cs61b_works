public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double g = 6.67e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        this.yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p){
        xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet another){
        double dx = this.xxPos - another.xxPos;
        double dy = this.yyPos - another.yyPos;
        double distance = Math.sqrt(dx*dx + dy* dy);
        return distance;
    }

    public double calcForceExertedBy(Planet another){
        double distance = this.calcDistance(another);
        double force = g*this.mass*another.mass/distance/distance;
        return force;
    }

    public double calcForceExertedByX(Planet another){
        double force = calcForceExertedBy(another);
        double distance = calcDistance(another);
        double dx = another.xxPos - this.xxPos;
        double forceX = force * dx / distance;
        return forceX;
    }

    public double calcForceExertedByY(Planet another){
        double force = calcForceExertedBy(another);
        double distance = calcDistance(another);
        double dy = another.yyPos - this.yyPos;
        double forceY = force * dy / distance;
        return forceY;
    }

    public double calcNetForceExertedByX(Planet[] allPlants){
        double forceX = 0;
        for(Planet planet: allPlants){
            if(this.equals(planet)) continue;
            forceX += calcForceExertedByX(planet);
        }
        return forceX;
    }

    public double calcNetForceExertedByY(Planet[] allPlants){
        double forceY = 0;
        for(Planet planet: allPlants){
            if(this.equals(planet)) continue;
            forceY += calcForceExertedByY(planet);
        }
        return forceY;
    }

    public void update(double dt, double forceX, double forceY){
        double aX = forceX/this.mass;
        double aY = forceY/this.mass;

        this.xxVel += dt* aX;
        this.yyVel += dt* aY;

        this.xxPos += this.xxVel*dt;
        this.yyPos += this.yyVel*dt;
    }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }

}
