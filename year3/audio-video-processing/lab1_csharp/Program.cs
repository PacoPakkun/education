using System;

namespace lab1_csharp
{
    class Program
    {
        static void Main(string[] args)
        {
            Encoder encoder = new Encoder();
            encoder.encode();
            Decoder decoder = new Decoder(encoder.yBlocks, encoder.uBlocks, encoder.vBlocks);
            decoder.decode();

            // Console.WriteLine("\nFirst Y block:\n");
            // for (int i = 0; i < 8; i++)
            // {
            //     for (int j = 0; j < 8; j++)
            //     {
            //         Console.Write($"{System.Math.Round(encoder.yBlocks[0][0][i][j], 2)}\t");
            //     }
            //
            //     if (i == 3)
            //         Console.WriteLine("    ...");
            //     else
            //         Console.WriteLine();
            // }
            //
            // Console.WriteLine("\n...\n");
            // Console.WriteLine("First U block:\n");
            // for (int i = 0; i < 4; i++)
            // {
            //     for (int j = 0; j < 4; j++)
            //     {
            //         Console.Write($"{System.Math.Round(encoder.uBlocks[0][0][i][j], 2)}\t");
            //     }
            //
            //     if (i == 1)
            //         Console.WriteLine("    ...");
            //     else
            //         Console.WriteLine();
            // }
            //
            // Console.WriteLine("\n...\n");
            // Console.WriteLine("First V block:\n");
            // for (int i = 0; i < 4; i++)
            // {
            //     for (int j = 0; j < 4; j++)
            //     {
            //         Console.Write($"{System.Math.Round(encoder.vBlocks[0][0][i][j], 2)}\t");
            //     }
            //
            //     if (i == 1)
            //         Console.WriteLine("    ...");
            //     else
            //         Console.WriteLine();
            // }
            //
            // Console.WriteLine("\n...\n");
            //
            // Console.WriteLine();
        }
    }
}