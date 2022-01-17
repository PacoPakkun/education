using System;
using System.Collections.Generic;
using System.IO;

namespace lab1_csharp
{
    public class Decoder
    {
        private List<List<List<List<double>>>> yBlocks;
        private List<List<List<List<double>>>> uBlocks;
        private List<List<List<List<double>>>> vBlocks;

        // converts a pixel from yuv to rgb format
        private static List<int> yuvToRgb(double y, double u, double v)
        {
            double r = y + 1.402 * (v - 128);
            double g = y - 0.344 * (u - 128) - 0.714 * (v - 128);
            double b = y + 1.772 * (u - 128);
            if (r < 0) r = 0;
            if (r > 255) r = 255;
            if (g < 0) g = 0;
            if (g > 255) g = 255;
            if (b < 0) b = 0;
            if (b > 255) b = 255;
            return new List<int>() {Convert.ToInt32(r), Convert.ToInt32(g), Convert.ToInt32(b)};
        }

        // composes a matrix from 8x8 blocks
        private static List<List<double>> composeFromBlocks(List<List<List<List<double>>>> matrix,
            bool isSubsampled)
        {
            List<List<double>> result = new List<List<double>>(matrix.Count * 8);
            for (int i = 0; i < matrix.Count * 8; i++)
            {
                List<double> list = new List<double>();
                for (int j = 0; j < matrix[0].Count * 8; j++)
                {
                    list.Add(0);
                }

                result.Add(list);
            }

            for (int i = 0; i < matrix.Count; i++)
            {
                for (int j = 0; j < matrix[0].Count; j++)
                {
                    List<List<double>> block = matrix[i][j];
                    if (isSubsampled)
                    {
                        // recomposes 8x8 blocks in a yuv matrix
                        List<List<double>> new_block = new List<List<double>>(8);
                        for (int x = 0; x < 8; x++)
                            new_block.Add(new List<double>(8) {0, 0, 0, 0, 0, 0, 0, 0});

                        for (int k = 0; k < 4; k++)
                        {
                            for (int l = 0; l < 4; l++)
                            {
                                new_block[k * 2][l * 2] = block[k][l];
                                new_block[k * 2][l * 2 + 1] = block[k][l];
                                new_block[k * 2 + 1][l * 2] = block[k][l];
                                new_block[k * 2 + 1][l * 2 + 1] = block[k][l];
                            }
                        }

                        block = new_block;
                    }

                    for (int k = 0; k < 8; k++)
                    {
                        for (int l = 0; l < 8; l++)
                        {
                            result[i * 8 + k][j * 8 + l] = block[k][l];
                        }
                    }
                }
            }

            return result;
        }

        public Decoder(List<List<List<List<double>>>> yBlocks, List<List<List<List<double>>>> uBlocks,
            List<List<List<List<double>>>> vBlocks)
        {
            this.yBlocks = yBlocks;
            this.uBlocks = uBlocks;
            this.vBlocks = vBlocks;
        }

        private static List<List<double>> idct(List<List<double>> matrix)
        {
            List<List<double>> result = new List<List<double>>(8);
            for (int x = 0; x < 8; x++)
                result.Add(new List<double>(8) {0, 0, 0, 0, 0, 0, 0, 0});

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    double average = 0;
                    for (int k = 0; k < 8; k++)
                    {
                        for (int p = 0; p < 8; p++)
                        {
                            double s = matrix[k][p] * Math.Cos((2 * i + 1) * k * Math.PI / 16) *
                                       Math.Cos((2 * j + 1) * p * Math.PI / 16);
                            if (k == 0) s *= (1 / Math.Sqrt(2));
                            if (p == 0) s *= (1 / Math.Sqrt(2));
                            average += s;
                        }
                    }

                    result[i][j] = average / 4 + 128;
                }
            }

            return result;
        }

        private static List<List<double>> dequantization(List<List<double>> block)
        {
            List<List<double>> q = new List<List<double>>()
            {
                new List<double>() {6, 4, 4, 6, 10, 16, 20, 24},
                new List<double>() {5, 5, 6, 8, 10, 23, 24, 22},
                new List<double>() {6, 5, 6, 10, 16, 23, 28, 22},
                new List<double>() {6, 7, 9, 12, 20, 35, 32, 25},
                new List<double>() {7, 9, 15, 22, 27, 44, 41, 31},
                new List<double>() {10, 14, 22, 26, 32, 42, 45, 37},
                new List<double>() {20, 26, 31, 35, 41, 48, 48, 40},
                new List<double>() {29, 37, 38, 39, 45, 40, 41, 40}
            };

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    block[i][j] = block[i][j] * q[i][j];
                }
            }

            return block;
        }

        // writes picture to ppm file
        public void decode()
        {
            for (int i = 0; i < yBlocks.Count; i++)
            {
                for (int j = 0; j < yBlocks[0].Count; j++)
                {
                    yBlocks[i][j] = idct(dequantization(yBlocks[i][j]));
                    uBlocks[i][j] = idct(dequantization(uBlocks[i][j]));
                    vBlocks[i][j] = idct(dequantization(vBlocks[i][j]));
                }
            }

            List<List<double>> y = composeFromBlocks(yBlocks, false);
            List<List<double>> u = composeFromBlocks(uBlocks, true);
            List<List<double>> v = composeFromBlocks(vBlocks, true);

            using (StreamWriter file = new StreamWriter("output.ppm"))
            {
                int height = y.Count;
                int width = y[0].Count;
                file.WriteLine("P3");
                file.WriteLine("# CREATOR: gIMP PNM Filter version 1.1");
                file.WriteLine($"{width} {height}");
                file.WriteLine(255);

                for (int i = 0; i < height; i++)
                {
                    for (int j = 0; j < width; j++)
                    {
                        List<int> rgb = yuvToRgb(y[i][j], u[i][j], v[i][j]);
                        file.WriteLine(rgb[0]);
                        file.WriteLine(rgb[1]);
                        file.WriteLine(rgb[2]);
                    }
                }

                file.Close();
            }
        }
    }
}