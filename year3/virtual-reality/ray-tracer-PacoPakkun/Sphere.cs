using System;

namespace rt
{
    public class Sphere : Geometry
    {
        private Vector Center { get; set; }
        private double Radius { get; set; }

        public Sphere(Vector center, double radius, Material material, Color color) : base(material, color)
        {
            Center = center;
            Radius = radius;
        }

        public override Intersection GetIntersection(Line line, double minDist, double maxDist)
        {
            // ADD CODE HERE: Calculate the intersection between the given line and this sphere

            double a = line.Dx.X, c = line.Dx.Y, e = line.Dx.Z;
            double b = line.X0.X, d = line.X0.Y, f = line.X0.Z;
            double xc = Center.X, yc = Center.Y, zc = Center.Z;

            double A = a * a + c * c + e * e;
            double B = 2 * a * b - 2 * a * xc + 2 * c * d - 2 * c * yc + 2 * e * f - 2 * e * zc;
            double C = b * b - 2 * b * xc + d * d - 2 * d * yc + f * f - 2 * f * zc + xc * xc + yc * yc + zc * zc -
                       Radius * Radius;

            double delta = B * B - 4 * A * C;

            if (delta >= 0)
            {
                double t1 = (-B + Math.Sqrt(delta)) / (2 * A);
                double t2 = (-B - Math.Sqrt(delta)) / (2 * A);
                if (t1 < t2 && t1 > 0 && minDist < t1 && maxDist > t1)
                    return new Intersection(true, true, this, line, t1);
                if (t2 < t1 && t2 > 0 && minDist < t2 && maxDist > t2)
                    return new Intersection(true, true, this, line, t2);
            }

            return new Intersection();
        }

        public override Vector Normal(Vector v)
        {
            var n = v - Center;
            n.Normalize();
            return n;
        }
    }
}