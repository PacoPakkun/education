using System;
using System.Collections.Generic;
using System.IO;

namespace lab1_csharp
{
    public class Encoder
    {
        public List<List<List<List<double>>>> yBlocks = new List<List<List<List<double>>>>();
        public List<List<List<List<double>>>> uBlocks = new List<List<List<List<double>>>>();
        public List<List<List<List<double>>>> vBlocks = new List<List<List<List<double>>>>();

        // converts a pixel from rgb to yuv format
        private static List<double> rgbToYuv(List<int> pixel)
        {
            int r = pixel[0];
            int g = pixel[1];
            int b = pixel[2];
            double y = 0.299 * r + 0.587 * g + 0.114 * b;
            double u = -0.1687 * r - 0.3312 * g + 0.5 * b + 128;
            double v = 0.5 * r - 0.4186 * g - 0.0813 * b + 128;
            return new List<double>() {y, u, v};
        }

        // divides a matrix intro 8x8 blocks
        private static List<List<List<List<double>>>> divideToBlocks(List<List<double>> matrix,
            bool shouldSubsample)
        {
            List<List<List<List<double>>>> result = new List<List<List<List<double>>>>();
            for (int i = 0; i < matrix.Count / 8; i++)
            {
                List<List<List<double>>> blocks = new List<List<List<double>>>();
                for (int j = 0; j < matrix[0].Count / 8; j++)
                {
                    List<List<double>> block = new List<List<double>>();
                    for (int k = 0; k < 8; k++)
                    {
                        List<double> row = new List<double>();
                        for (int l = 0; l < 8; l++)
                        {
                            row.Add(matrix[i * 8 + k][j * 8 + l]);
                        }

                        block.Add(row);
                    }

                    if (shouldSubsample)
                    {
                        // subsamples an 8x8 block into a 4x4 block of average values
                        List<List<double>> subsampled_block = new List<List<double>>();
                        for (int x = 0; x < 4; x++)
                            subsampled_block.Add(new List<double>() {0, 0, 0, 0});

                        for (int k = 0; k < 4; k++)
                        {
                            for (int l = 0; l < 4; l++)
                            {
                                double sample = (block[k * 2][l * 2] + block[k * 2][l * 2 + 1] +
                                                 block[k * 2 + 1][l * 2] + block[k * 2 + 1][
                                                     l * 2 + 1]) / 4;
                                subsampled_block[k][l] = sample;
                            }
                        }

                        blocks.Add(subsampled_block);
                    }
                    else
                        blocks.Add(block);
                }

                result.Add(blocks);
            }

            return result;
        }

        private static List<List<double>> fdct(List<List<double>> block, bool isSubsampled)
        {
            if (isSubsampled)
            {
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

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    block[i][j] -= 128;
                }
            }

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
                            average += block[k][p] * Math.Cos((2 * k + 1) * i * Math.PI / 16) *
                                       Math.Cos((2 * p + 1) * j * Math.PI / 16);
                        }
                    }

                    result[i][j] = average / 4;

                    if (i == 0)
                        result[i][j] *= (1 / Math.Sqrt(2));
                    if (j == 0)
                        result[i][j] *= (1 / Math.Sqrt(2));
                }
            }

            return result;
        }

        private static List<List<double>> quantization(List<List<double>> block)
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
                    block[i][j] = Math.Round(block[i][j] / q[i][j]);
                }
            }

            return block;
        }

        // reads input picture from ppm file
        public void encode()
        {
            List<List<double>> y = new List<List<double>>();
            List<List<double>> u = new List<List<double>>();
            List<List<double>> v = new List<List<double>>();

            using (StreamReader file = new StreamReader("input.ppm"))
            {
                file.ReadLine();
                file.ReadLine();
                string size = file.ReadLine();
                int width = Int32.Parse(size.Split(' ')[0]);
                int height = Int32.Parse(size.Split(' ')[1]);
                file.ReadLine();

                for (int i = 0; i < height; i++)
                {
                    List<double> yRow = new List<double>();
                    List<double> uRow = new List<double>();
                    List<double> vRow = new List<double>();
                    for (int j = 0; j < width; j++)
                    {
                        int r = Int32.Parse(file.ReadLine());
                        int g = Int32.Parse(file.ReadLine());
                        int b = Int32.Parse(file.ReadLine());
                        List<int> rgb = new List<int>() {r, g, b};
                        List<double> yuv = rgbToYuv(rgb);
                        yRow.Add(yuv[0]);
                        uRow.Add(yuv[1]);
                        vRow.Add(yuv[2]);
                    }

                    y.Add(yRow);
                    u.Add(uRow);
                    v.Add(vRow);
                }

                file.Close();
            }

            yBlocks = divideToBlocks(y, false);
            uBlocks = divideToBlocks(u, true);
            vBlocks = divideToBlocks(v, true);

            for (int i = 0; i < yBlocks.Count; i++)
            {
                for (int j = 0; j < yBlocks[0].Count; j++)
                {
                    yBlocks[i][j] = quantization(fdct(yBlocks[i][j], false));
                }
            }

            for (int i = 0; i < uBlocks.Count; i++)
            {
                for (int j = 0; j < uBlocks[0].Count; j++)
                {
                    uBlocks[i][j] = quantization(fdct(uBlocks[i][j], true));
                    vBlocks[i][j] = quantization(fdct(vBlocks[i][j], true));
                }
            }
        }
    }
}