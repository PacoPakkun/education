using System.Collections.Generic;
using UDT.Model.Enums;

namespace UDT.Model.ViewModels
{
    public class SubjectViewModel
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public SubjectType Type { get; set; }
        public int Year { get; set; }
        public List<int> Users { get; set; }
    }
}