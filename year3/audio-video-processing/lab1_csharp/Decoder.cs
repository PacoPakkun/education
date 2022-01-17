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

        // writes picture to ppm file
        public void decode()
        {
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