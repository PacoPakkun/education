using System;
using System.Runtime.InteropServices;

namespace rt
{
    class RayTracer
    {
        private Geometry[] geometries;
        private Light[] lights;

        public RayTracer(Geometry[] geometries, Light[] lights)
        {
            this.geometries = geometries;
            this.lights = lights;
        }

        private double ImageToViewPlane(int n, int imgSize, double viewPlaneSize)
        {
            var u = n * viewPlaneSize / imgSize;
            u -= viewPlaneSize / 2;
            return u;
        }

        private Intersection FindFirstIntersection(Line ray, double minDist, double maxDist)
        {
            var intersection = new Intersection();

            foreach (var geometry in geometries)
            {
                var intr = geometry.GetIntersection(ray, minDist, maxDist);

                if (!intr.Valid || !intr.Visible) continue;

                if (!intersection.Valid || !intersection.Visible)
                {
                    intersection = intr;
                }
                else if (intr.T < intersection.T)
                {
                    intersection = intr;
                }
            }

            return intersection;
        }

        private bool IsLit(Vector point, Light light)
        {
            // ADD CODE HERE: Detect whether the given point has a clear line of sight to the given light

            Line line = new Line(light.Position, point);
            Intersection intersection = FindFirstIntersection(line, 0, (point - light.Position).Length() + 1);

            if (intersection.Valid &&
                Math.Abs(intersection.Position.X - point.X) < 0.1 &&
                Math.Abs(intersection.Position.Y - point.Y) < 0.1 &&
                Math.Abs(intersection.Position.Z - point.Z) < 0.1)
                return true;
            return false;
        }

        public void Render(Camera camera, int width, int height, string filename)
        {
            var background = new Color();
            var image = new Image(width, height);

            for (var i = 0; i < width; i++)
            {
                for (var j = 0; j < height; j++)
                {
                    // ADD CODE HERE: Implement pixel color calculation

                    // current pixel line
                    Vector x0 = camera.Position;
                    Vector x1 = camera.Position +
                                camera.Direction * camera.ViewPlaneDistance +
                                (camera.Direction ^ camera.Up) * ImageToViewPlane(i, width, camera.ViewPlaneWidth) +
                                camera.Up * ImageToViewPlane(j, height, camera.ViewPlaneHeight);
                    Line line = new Line(x0, x1);

                    // current pixel intersection
                    Intersection intersection =
                        FindFirstIntersection(line, camera.FrontPlaneDistance, camera.BackPlaneDistance);

                    // current pixel color
                    if (intersection.Valid)
                    {
                        Color color = new Color();
                        foreach (var light in lights)
                        {
                            // ambient
                            Color currentColor = intersection.Geometry.Material.Ambient * light.Ambient;

                            if (IsLit(intersection.Position, light))
                            {
                                // diffuse
                                Vector N = intersection.Geometry.Normal(intersection.Position);
                                Vector T = (light.Position - intersection.Position).Normalize();
                                if (N * T > 0)
                                {
                                    currentColor += intersection.Geometry.Material.Diffuse * light.Diffuse * (N * T);
                                }

                                // specular
                                Vector E = (camera.Position - intersection.Position).Normalize();
                                Vector R = N * (N * T) * 2 - T;
                                if (E * R > 0)
                                {
                                    currentColor += intersection.Geometry.Material.Specular * light.Specular *
                                                    Math.Pow(E * R, intersection.Geometry.Material.Shininess);
                                }

                                // intensity
                                currentColor *= light.Intensity;
                            }

                            // update total color
                            color += currentColor;
                        }

                        // set pixel color
                        image.SetPixel(i, j, color);
                    }
                    else
                        image.SetPixel(i, j, background);
                }
            }

            image.Store(filename);
        }
    }
}