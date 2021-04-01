using lab7.Domain;
using lab7.Domain.Validator;
using lab7.Repository;
using lab7.Service;
using System;
using System.Collections.Generic;

namespace lab7
{
    class Program
    {
        static void Main(string[] args)
        {
            AppService srv = GetService();
            while (true)
            {
                Console.WriteLine("give command (1/2/3/4)");
                String cmd = Console.ReadLine();
                Console.WriteLine();
                switch (cmd)
                {
                    case "1":
                        Console.WriteLine("give echipa");
                        String echipa = Console.ReadLine();
                        Console.WriteLine();
                        srv.FindJucatori(echipa).ForEach(Console.WriteLine);
                        Console.WriteLine("....................");
                        break;
                    case "2":
                        Console.WriteLine("give meci");
                        String meci = Console.ReadLine();
                        Console.WriteLine("give echipa");
                        echipa = Console.ReadLine();
                        Console.WriteLine();
                        srv.FindJucatoriActivi(echipa, Int32.Parse(meci)).ForEach(Console.WriteLine);
                        Console.WriteLine("....................");
                        break;
                    case "3":
                        Console.WriteLine("give begin date");
                        DateTime data1 = DateTime.Parse(Console.ReadLine());
                        Console.WriteLine("give end date");                      
                        DateTime data2 = DateTime.Parse(Console.ReadLine());
                        Console.WriteLine();
                        srv.FindMeciuri(data1, data2).ForEach(Console.WriteLine);
                        Console.WriteLine("....................");
                        break;
                    case "4":
                        Console.WriteLine("give meci");
                        meci = Console.ReadLine();
                        Console.WriteLine();
                        Console.WriteLine(srv.FindScor(Int32.Parse(meci)));
                        Console.WriteLine("....................");
                        break;
                    case "exit":
                        return;
                    default:
                        Console.WriteLine("invalid command");
                        break;
                }
            }
        }

        private static AppService GetService()
        {
            string fileName = "..\\..\\..\\data\\echipe.txt";
            string fileName2 = "..\\..\\..\\data\\jucatori.txt";
            string fileName3 = "..\\..\\..\\data\\meciuri.txt";
            string fileName4 = "..\\..\\..\\data\\jucatoriActivi.txt";
            Validator<Echipa> valiEchipa = new EchipaValidator();
            Validator<Jucator> valiJucator = new JucatorValidator();
            Validator<Meci> valiMeci = new MeciValidator();
            Validator<JucatorActiv> valiJucatorActiv = new JucatorActivValidator();
            Repository<int, Echipa> echipe = new EchipeRepository(valiEchipa, fileName); ;
            Repository<int, Jucator> jucatori = new JucatoriRepository(valiJucator, fileName2); ;
            Repository<int, Meci> meciuri = new MeciuriRepository(valiMeci, fileName3); ;
            Repository<Tuple<int, int>, JucatorActiv> jucatoriActivi = new JucatoriActiviRepository(valiJucatorActiv, fileName4); ;
            AppService service = new AppService(echipe, jucatori, meciuri, jucatoriActivi);
            return service;
        }
    }
}
