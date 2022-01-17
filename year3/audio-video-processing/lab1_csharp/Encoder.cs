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
        }
    }
}